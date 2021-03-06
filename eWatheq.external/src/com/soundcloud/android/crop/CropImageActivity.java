/*
 * Copyright (C) 2007 The Android Open Source Project
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

package com.soundcloud.android.crop;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

import ae.ewatheq.external.R;
/*
 * Modified from original in AOSP.
 */
public class CropImageActivity extends MonitoredActivity {

	private static final int SIZE_DEFAULT = 2048;
	private static final int SIZE_LIMIT = 4096;

	private final Handler handler = new Handler();

	private int aspectX;
	private int aspectY;

	// Output image
	private int maxX;
	private int maxY;
	private int exifRotation;

	private Uri sourceUri;
	private Uri saveUri;

	private boolean isSaving;

	private int sampleSize;
	private RotateBitmap rotateBitmap;
	private Bitmap origionalBitmap; 
	private CropImageView imageView;
	private SeekBar sbBrightness;
	private HighlightView cropView;
	private int brightness;
	private ProgressDialog ringProgressDialog;
	private MonitoredActivity activity;
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		activity = this;
		setupWindowFlags();
		setupViews();

		loadInput();
		if (rotateBitmap == null) {
			finish();
			return;
		}
		startCrop();
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void setupWindowFlags() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
	}

	private void setupViews() {
		setContentView(R.layout.crop__activity_crop);

		setupBrightnessView();
		imageView = (CropImageView) findViewById(R.id.crop_image);
		


		imageView.context = this;
		imageView.setRecycler(new ImageViewTouchBase.Recycler() {
			@Override
			public void recycle(Bitmap b) {
				b.recycle();
				System.gc();
			}
		});

		findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onSaveClicked();
			}
		});
	}
	public void setupBrightnessView(){
		sbBrightness = (SeekBar) findViewById(R.id.sb_image_brightness);
		brightness = 0;
		
		
		sbBrightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progress = 0;

			@Override
			public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {	
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				progress = seekBar.getProgress();
				brightness = progress - 100;
				new BrighnessTask().execute();
			}

		});
	}
	private class BrighnessTask extends AsyncTask<String, Void, Boolean> {


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showProgressDialog ("");

		}
		@Override
		protected Boolean doInBackground(String... params) {
			if (origionalBitmap == null)
			{
				origionalBitmap = rotateBitmap.getBitmap().copy(rotateBitmap.getBitmap().getConfig(), true);
				rotateBitmap.setBitmap( adjustedContrast(rotateBitmap.getBitmap(), brightness));
			}
			else {
				rotateBitmap.setBitmap( adjustedContrast(origionalBitmap, brightness));
			}
			return true;
		}
		@Override
		protected void onPostExecute(Boolean result) {

			if (ringProgressDialog!= null && ringProgressDialog.isShowing())
				ringProgressDialog.dismiss();
			imageView.setImageRotateBitmapResetBase(rotateBitmap, true);
		}
	}
	public void showProgressDialog(String message)
	{
		if (ringProgressDialog!=null && ringProgressDialog.isShowing())
		{
			ringProgressDialog.dismiss();
			ringProgressDialog = null;
		}
		ringProgressDialog = ProgressDialog.show(activity, message, "", true);
		ringProgressDialog.setCancelable(false);
	}
	private void loadInput() {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		if (extras != null) {
			aspectX = extras.getInt(Crop.Extra.ASPECT_X);
			aspectY = extras.getInt(Crop.Extra.ASPECT_Y);
			maxX = extras.getInt(Crop.Extra.MAX_X);
			maxY = extras.getInt(Crop.Extra.MAX_Y);
			saveUri = extras.getParcelable(MediaStore.EXTRA_OUTPUT);
		}

		sourceUri = intent.getData();
		String filePath = sourceUri.getPath();
			if (sourceUri != null) {
			exifRotation = CropUtil.getExifRotation(CropUtil.getFromMediaUri(this, getContentResolver(), sourceUri));

			InputStream is = null;
			try {
				sampleSize = calculateBitmapSampleSize(sourceUri);
				is = getContentResolver().openInputStream(sourceUri);
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inSampleSize = sampleSize;
				
				rotateBitmap = new RotateBitmap(BitmapFactory.decodeStream(is, null, option), exifRotation);
			} catch (IOException e) {
				Log.e("Error reading image: " + e.getMessage(), e);
				setResultException(e);
			} catch (OutOfMemoryError e) {
				Log.e("OOM reading image: " + e.getMessage(), e);
				setResultException(e);
			} finally {
				CropUtil.closeSilently(is);
			}
		}
	}

	private int calculateBitmapSampleSize(Uri bitmapUri) throws IOException {
		InputStream is = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		try {
			is = getContentResolver().openInputStream(bitmapUri);
			BitmapFactory.decodeStream(is, null, options); // Just get image size
		} finally {
			CropUtil.closeSilently(is);
		}

		int maxSize = getMaxImageSize();
		int sampleSize = 1;
		while (options.outHeight / sampleSize > maxSize || options.outWidth / sampleSize > maxSize) {
			sampleSize = sampleSize << 1;
		}
		return sampleSize;
	}

	private int getMaxImageSize() {
		int textureLimit = getMaxTextureSize();
		if (textureLimit == 0) {
			return SIZE_DEFAULT;
		} else {
			return Math.min(textureLimit, SIZE_LIMIT);
		}
	}

	private int getMaxTextureSize() {
		// The OpenGL texture size is the maximum size that can be drawn in an ImageView
		int[] maxSize = new int[1];
		GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE, maxSize, 0);
		return maxSize[0];
	}

	private void startCrop() {
		if (isFinishing()) {
			return;
		}
		imageView.setImageRotateBitmapResetBase(rotateBitmap, true);
		CropUtil.startBackgroundJob(this, null, getResources().getString(R.string.crop__wait),
				new Runnable() {
			public void run() {
				final CountDownLatch latch = new CountDownLatch(1);
				handler.post(new Runnable() {
					public void run() {
						if (imageView.getScale() == 1F) {
							imageView.center();
						}
						latch.countDown();
					}
				});
				try {
					latch.await();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				new Cropper().crop();
			}
		}, handler
				);
	}

	private class Cropper {

		private void makeDefault() {
			if (rotateBitmap == null) {
				return;
			}

			HighlightView hv = new HighlightView(imageView);
			final int width = rotateBitmap.getWidth();
			final int height = rotateBitmap.getHeight();

			Rect imageRect = new Rect(0, 0, width, height);

			// Make the default size about 4/5 of the width or height
			int cropWidth = Math.min(width, height) * 4 / 5;
			@SuppressWarnings("SuspiciousNameCombination")
			int cropHeight = cropWidth;

			if (aspectX != 0 && aspectY != 0) {
				if (aspectX > aspectY) {
					cropHeight = cropWidth * aspectY / aspectX;
				} else {
					cropWidth = cropHeight * aspectX / aspectY;
				}
			}

			int x = (width - cropWidth) / 2;
			int y = (height - cropHeight) / 2;

			RectF cropRect = new RectF(x, y, x + cropWidth, y + cropHeight);
			hv.setup(imageView.getUnrotatedMatrix(), imageRect, cropRect, aspectX != 0 && aspectY != 0);
			imageView.add(hv);
		}

		public void crop() {
			handler.post(new Runnable() {
				public void run() {
					makeDefault();
					imageView.invalidate();
					if (imageView.highlightViews.size() == 1) {
						cropView = imageView.highlightViews.get(0);
						cropView.setFocus(true);
					}
				}
			});
		}
	}

	private void onSaveClicked() {
		if (cropView == null || isSaving) {
			return;
		}
		isSaving = true;

		Bitmap croppedImage;
		Rect r = cropView.getScaledCropRect(sampleSize);
		int width = r.width();
		int height = r.height();

		int outWidth = width;
		int outHeight = height;
		if (maxX > 0 && maxY > 0 && (width > maxX || height > maxY)) {
			float ratio = (float) width / (float) height;
			if ((float) maxX / (float) maxY > ratio) {
				outHeight = maxY;
				outWidth = (int) ((float) maxY * ratio + .5f);
			} else {
				outWidth = maxX;
				outHeight = (int) ((float) maxX / ratio + .5f);
			}
		}

		try {
			croppedImage = decodeRegionCrop(r, outWidth, outHeight);
		} catch (IllegalArgumentException e) {
			setResultException(e);
			finish();
			return;
		}

		if (croppedImage != null) {
			imageView.setImageRotateBitmapResetBase(new RotateBitmap(croppedImage, exifRotation), true);
			imageView.center();
			imageView.highlightViews.clear();
		}
		saveImage(croppedImage);
	}

	private void saveImage(Bitmap croppedImage) {
		if (croppedImage != null) {
			new SaveImageBrightnessTask(croppedImage).execute();
			/*final Bitmap b = adjustedContrast(croppedImage, brightness);;
		
			CropUtil.startBackgroundJob(this, null, getResources().getString(R.string.crop__saving),
					new Runnable() {
				public void run() {
					saveOutput(b);
				}
			}, handler
					);*/
		} else {
			finish();
		}
	}

	private class SaveImageBrightnessTask extends AsyncTask<String, Void, Boolean> {

		private Bitmap bmp ;
		SaveImageBrightnessTask(Bitmap bmp)
		{
			this.bmp = bmp;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showProgressDialog ("");

		}
		@Override
		protected Boolean doInBackground(String... params) {
			bmp = adjustedContrast(bmp, brightness);
			return true;
		}
		@Override
		protected void onPostExecute(Boolean result) {

			CropUtil.startBackgroundJob(activity, null, getResources().getString(R.string.crop__saving),
					new Runnable() {
						public void run() {
							saveOutput(bmp);
						}
					}, handler
			);
		}
	}
	private Bitmap decodeRegionCrop(Rect rect, int outWidth, int outHeight) {
		// Release memory now
		clearImageView();

		InputStream is = null;
		Bitmap croppedImage = null;
		try {
			is = getContentResolver().openInputStream(sourceUri);
			BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is, false);
			final int width = decoder.getWidth();
			final int height = decoder.getHeight();

			if (exifRotation != 0) {
				// Adjust crop area to account for image rotation
				Matrix matrix = new Matrix();
				matrix.setRotate(-exifRotation);

				RectF adjusted = new RectF();
				matrix.mapRect(adjusted, new RectF(rect));

				// Adjust to account for origin at 0,0
				adjusted.offset(adjusted.left < 0 ? width : 0, adjusted.top < 0 ? height : 0);
				rect = new Rect((int) adjusted.left, (int) adjusted.top, (int) adjusted.right, (int) adjusted.bottom);
			}

			try {
				croppedImage = decoder.decodeRegion(rect, new BitmapFactory.Options());
				if (croppedImage != null && (rect.width() > outWidth || rect.height() > outHeight)) {
					Matrix matrix = new Matrix();
					matrix.postScale((float) outWidth / rect.width(), (float) outHeight / rect.height());
					croppedImage = Bitmap.createBitmap(croppedImage, 0, 0, croppedImage.getWidth(), croppedImage.getHeight(), matrix, true);
				}
			} catch (IllegalArgumentException e) {
				// Rethrow with some extra information
				throw new IllegalArgumentException("Rectangle " + rect + " is outside of the image ("
						+ width + "," + height + "," + exifRotation + ")", e);
			}

		} catch (IOException e) {
			Log.e("Error cropping image: " + e.getMessage(), e);
			setResultException(e);
		} catch (OutOfMemoryError e) {
			Log.e("OOM cropping image: " + e.getMessage(), e);
			setResultException(e);
		} finally {
			CropUtil.closeSilently(is);
		}
		return croppedImage;
	}

	private void clearImageView() {
		imageView.clear();
		if (rotateBitmap != null) {
			rotateBitmap.recycle();
		}
		System.gc();
	}

	private void saveOutput(Bitmap croppedImage) {
		if (saveUri != null) {
			OutputStream outputStream = null;
			try {
				outputStream = getContentResolver().openOutputStream(saveUri);
				if (outputStream != null) {
					croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
				}
			} catch (IOException e) {
				setResultException(e);
				Log.e("Cannot open file: " + saveUri, e);
			} finally {
				CropUtil.closeSilently(outputStream);
			}

			CropUtil.copyExifRotation(
					CropUtil.getFromMediaUri(this, getContentResolver(), sourceUri),
					CropUtil.getFromMediaUri(this, getContentResolver(), saveUri)
					);

			setResultUri(saveUri);
		}

		final Bitmap b = croppedImage;
		handler.post(new Runnable() {
			public void run() {
				imageView.clear();
				b.recycle();
			}
		});

		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (rotateBitmap != null) {
			rotateBitmap.recycle();
		}
	}

	@Override
	public boolean onSearchRequested() {
		return false;
	}

	public boolean isSaving() {
		return isSaving;
	}

	private void setResultUri(Uri uri) {
		setResult(RESULT_OK, new Intent().putExtra(MediaStore.EXTRA_OUTPUT, uri));
	}

	private void setResultException(Throwable throwable) {
		setResult(Crop.RESULT_ERROR, new Intent().putExtra(Crop.Extra.ERROR, throwable));
	}

	private Bitmap adjustedContrast(Bitmap src, double value)
	{

		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		if (origionalBitmap == null)
			origionalBitmap = Bitmap.createBitmap(width, height, src.getConfig());
		// color information
		int A, R, G, B;
		int pixel;

		// scan through all pixels
		for(int x = 0; x < width; ++x) {
			for(int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);

				// increase/decrease each channel
				R += value;
				if(R > 255) { R = 255; }
				else if(R < 0) { R = 0; }

				G += value;
				if(G > 255) { G = 255; }
				else if(G < 0) { G = 0; }

				B += value;
				if(B > 255) { B = 255; }
				else if(B < 0) { B = 0; }

				// apply new pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;

	}

}
