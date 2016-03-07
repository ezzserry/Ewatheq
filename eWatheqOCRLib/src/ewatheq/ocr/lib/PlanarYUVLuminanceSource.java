/*
 * Copyright 2009 ZXing authors
 * Copyright 2011 Robert Theis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ewatheq.ocr.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;

/**
 * This object extends LuminanceSource around an array of YUV data returned from the camera driver,
 * with the option to crop to a rectangle within the full data. This can be used to exclude
 * superfluous pixels around the perimeter and speed up decoding.
 *
 * It works for any pixel format where the Y channel is planar and appears first, including
 * YCbCr_420_SP and YCbCr_422_SP.
 *
 * The code for this class was adapted from the ZXing project: http://code.google.com/p/zxing
 */
public final class PlanarYUVLuminanceSource extends LuminanceSource {

	private final byte[] yuvData;
	private final int dataWidth;
	private final int dataHeight;
	private final int left;
	private final int top;

	public PlanarYUVLuminanceSource(byte[] yuvData,
			int dataWidth,
			int dataHeight,
			int left,
			int top,
			int width,
			int height,
			boolean reverseHorizontal) {
		super(width, height);

		if (left + width > dataWidth || top + height > dataHeight) {
			throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
		}

		this.yuvData = yuvData;
		this.dataWidth = dataWidth;
		this.dataHeight = dataHeight;
		this.left = left;
		this.top = top;
		if (reverseHorizontal) {
			reverseHorizontal(width, height);
		}
	}

	@Override
	public byte[] getRow(int y, byte[] row) {
		if (y < 0 || y >= getHeight()) {
			throw new IllegalArgumentException("Requested row is outside the image: " + y);
		}
		int width = getWidth();
		if (row == null || row.length < width) {
			row = new byte[width];
		}
		int offset = (y + top) * dataWidth + left;
		System.arraycopy(yuvData, offset, row, 0, width);
		return row;
	}

	@Override
	public byte[] getMatrix() {
		int width = getWidth();
		int height = getHeight();

		// If the caller asks for the entire underlying image, save the copy and give them the
		// original data. The docs specifically warn that result.length must be ignored.
		if (width == dataWidth && height == dataHeight) {
			return yuvData;
		}

		int area = width * height;
		byte[] matrix = new byte[area];
		int inputOffset = top * dataWidth + left;

		// If the width matches the full width of the underlying data, perform a single copy.
		if (width == dataWidth) {
			System.arraycopy(yuvData, inputOffset, matrix, 0, area);
			return matrix;
		}

		// Otherwise copy one cropped row at a time.
		byte[] yuv = yuvData;
		for (int y = 0; y < height; y++) {
			int outputOffset = y * width;
			System.arraycopy(yuv, inputOffset, matrix, outputOffset, width);
			inputOffset += dataWidth;
		}
		return matrix;
	}

	@Override
	public boolean isCropSupported() {
		return true;
	}

	@Override
	public LuminanceSource crop(int left, int top, int width, int height) {
		return new PlanarYUVLuminanceSource(yuvData,
				dataWidth,
				dataHeight,
				this.left + left,
				this.top + top,
				width,
				height,
				false);
	}

	public Bitmap renderCroppedGreyscaleBitmap() {
		int width = getWidth();
		int height = getHeight();
		int[] pixels = new int[width * height];
		byte[] yuv = yuvData;
		int inputOffset = top * dataWidth + left;

		for (int y = 0; y < height; y++) {
			int outputOffset = y * width;
			for (int x = 0; x < width; x++) {
				int grey = yuv[inputOffset + x] & 0xff;
				pixels[outputOffset + x] = 0xFF000000 | (grey * 0x00010101);
			}
			inputOffset += dataWidth;
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		// --> Noman : Save Grayescale Image to sdcard
		
		bitmap = scaleBitmap(bitmap,width*3,height*3);
		saveGrayeScaleBitmap(bitmap);
		return bitmap;
	}
	
	// --> Noman : Save Grayescale Image to sdcard
	public static Bitmap scaleBitmap(Bitmap bitmapToScale, float newWidth, float newHeight) {   
		if(bitmapToScale == null)
		    return null;
		
		//get the original width and height
		int width = bitmapToScale.getWidth();
		int height = bitmapToScale.getHeight();
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(newWidth / width, newHeight / height);
		// recreate the new Bitmap and set it back
		return Bitmap.createBitmap(bitmapToScale, 0, 0, bitmapToScale.getWidth(), bitmapToScale.getHeight(), matrix, true);  	
	}
	private void saveGrayeScaleBitmap(Bitmap bmp)
	{
		try {
			String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + 
					"/OCRTest";
			File dir = new File(file_path);
			if(!dir.exists())
				dir.mkdirs();
			File file = new File(dir, "ocr_test" + ".png");
			FileOutputStream fOut;

			fOut = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void reverseHorizontal(int width, int height) {
		byte[] yuvData = this.yuvData;
		for (int y = 0, rowStart = top * dataWidth + left; y < height; y++, rowStart += dataWidth) {
			int middle = rowStart + width / 2;
			for (int x1 = rowStart, x2 = rowStart + width - 1; x1 < middle; x1++, x2--) {
				byte temp = yuvData[x1];
				yuvData[x1] = yuvData[x2];
				yuvData[x2] = temp;
			}
		}
	}

}
