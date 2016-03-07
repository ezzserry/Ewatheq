/*
 * Copyright (C) 2010 ZXing authors
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

package ae.ewatheq.ocr;

import ae.ewatheq.internal.R;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * Class to send bitmap data for OCR.
 * 
 * The code for this class was adapted from the ZXing project: http://code.google.com/p/zxing/
 */
final class DecodeHandler extends Handler {

	private final OCRActivity activity;
	private boolean running = true;
	private final TessBaseAPI baseApi;
	private BeepManager beepManager;
	private Bitmap bitmap;
	private static boolean isDecodePending;
	private long timeRequired;

	DecodeHandler(OCRActivity activity) {
		this.activity = activity;
		baseApi = activity.getBaseApi();
		beepManager = new BeepManager(activity);
		beepManager.updatePrefs();
	}

	@Override
	public void handleMessage(Message message) {
		if (!running) {
			return;
		}
		if (message.what == R.id.ocr_continuous_decode) {        

			// Only request a decode if a request is not already pending.
			if (!isDecodePending) {
				isDecodePending = true;
				ocrContinuousDecode((byte[]) message.obj, message.arg1, message.arg2);
			}
		}
		else if (message.what == R.id.ocr_decode) {        

			// Only request a decode if a request is not already pending.
			ocrDecode((byte[]) message.obj, message.arg1, message.arg2);
		}
		else if (message.what == R.id.quit) {        

			// Only request a decode if a request is not already pending.
			running = false;
			Looper.myLooper().quit();
		}


	}

	static void resetDecodeState() {
		isDecodePending = false;
	}

	/**
	 * Noman : Static method will use only for test and will remove it after that
	 */
	/*	public static class ArrayBitmap{
		public byte[] data;
		public int width;
		public int height;
		public ArrayBitmap(byte[] mData, int mWidth, int mHeight) {
			// TODO Auto-generated constructor stub
			data = mData;
			width = mWidth;
			height = mHeight;
		}
	}
	public static ArrayBitmap getImageFromLocal(Context ctx)
	{
		Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.emirates_id_half_back_1);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		ArrayBitmap aBmp = new ArrayBitmap(stream.toByteArray(), bmp.getWidth(), bmp.getHeight());
		return  aBmp;
		//return  stream.toByteArray();
	}
	public static Bitmap getImageFromLocalBitmap(Context ctx)
	{
		Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.emirates_id_half_back_1);
		Bitmap mutableBitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
		return  mutableBitmap;
		//return  stream.toByteArray();
	}
	 */
	/**
	 *  Launch an AsyncTask to perform an OCR decode for single-shot mode.
	 *  
	 * @param data Image data
	 * @param width Image width
	 * @param height Image height
	 */
	private void ocrDecode(byte[] data, int width, int height) {
		// --> Noman : Testing image from Local android
		//ArrayBitmap aBmp = getImageFromLocal(activity);
		//<--
		beepManager.playBeepSoundAndVibrate();
		activity.displayProgressDialog(); 
		// Launch OCR asynchronously, so we get the dialog box displayed immediately
		//new OcrRecognizeAsyncTask(activity, baseApi, aBmp.data, aBmp.width, aBmp.height).execute();
		new OcrRecognizeAsyncTask(activity, baseApi, data, width, height).execute();
	}

	/**
	 *  Perform an OCR decode for realtime recognition mode.
	 *  
	 * @param data Image data
	 * @param width Image width
	 * @param height Image height
	 */
	private void ocrContinuousDecode(byte[] data, int width, int height) {  
		// --> Noman : Testing image from Local android
		//ArrayBitmap aBmp = getImageFromLocal(activity);
		//<--
		//PlanarYUVLuminanceSource source = activity.getCameraManager().buildLuminanceSource(aBmp.data, aBmp.width, aBmp.height);
		PlanarYUVLuminanceSource source = activity.getCameraManager().buildLuminanceSource(data, width, height);
		if (source == null) {
			sendContinuousOcrFailMessage();
			return;
		}
		bitmap = source.renderCroppedGreyscaleBitmap();

		OcrResult ocrResult = getOcrResult();
		Handler handler = activity.getHandler();
		if (handler == null) {
			return;
		}
		if (ocrResult == null) {
			try {
				sendContinuousOcrFailMessage();
			} catch (NullPointerException e) {
				activity.stopHandler();
			} finally {
				bitmap.recycle();
				baseApi.clear();
			}
			return;
		}
		try {
			Message message = Message.obtain(handler, R.id.ocr_continuous_decode_succeeded, ocrResult);
			message.sendToTarget();
		} catch (NullPointerException e) {
			activity.stopHandler();
		} finally {
			baseApi.clear();
		}
	}
	@SuppressWarnings("unused")
	private OcrResult getOcrResult() {
		OcrResult ocrResult;
		String textResult;
		long start = System.currentTimeMillis();

		try {     
			baseApi.setImage(ReadFile.readBitmap(bitmap));
			textResult = baseApi.getUTF8Text();
			timeRequired = System.currentTimeMillis() - start;

			// Check for failure to recognize text
			if (textResult == null || textResult.equals("")) {
				return null;
			}
			ocrResult = new OcrResult();
			ocrResult.setWordConfidences(baseApi.wordConfidences());
			ocrResult.setMeanConfidence( baseApi.meanConfidence());
			if (ViewfinderView.DRAW_REGION_BOXES) {
				ocrResult.setRegionBoundingBoxes(baseApi.getRegions().getBoxRects());
			}
			if (ViewfinderView.DRAW_TEXTLINE_BOXES) {
				ocrResult.setTextlineBoundingBoxes(baseApi.getTextlines().getBoxRects());
			}
			if (ViewfinderView.DRAW_STRIP_BOXES) {
				ocrResult.setStripBoundingBoxes(baseApi.getStrips().getBoxRects());
			}

			// Always get the word bounding boxes--we want it for annotating the bitmap after the user
			// presses the shutter button, in addition to maybe wanting to draw boxes/words during the
			// continuous mode recognition.
			ocrResult.setWordBoundingBoxes(baseApi.getWords().getBoxRects());

			//      if (ViewfinderView.DRAW_CHARACTER_BOXES || ViewfinderView.DRAW_CHARACTER_TEXT) {
			//        ocrResult.setCharacterBoundingBoxes(baseApi.getCharacters().getBoxRects());
			//      }
		} catch (RuntimeException e) {
			Log.e("OcrRecognizeAsyncTask", "Caught RuntimeException in request to Tesseract. Setting state to CONTINUOUS_STOPPED.");
			e.printStackTrace();
			try {
				baseApi.clear();
				activity.stopHandler();
			} catch (NullPointerException e1) {
				// Continue
			}
			return null;
		}
		timeRequired = System.currentTimeMillis() - start;
		ocrResult.setBitmap(bitmap);
		ocrResult.setText(textResult);
		ocrResult.setRecognitionTimeRequired(timeRequired);
		return ocrResult;
	}

	private void sendContinuousOcrFailMessage() {
		Handler handler = activity.getHandler();
		if (handler != null) {
			Message message = Message.obtain(handler, R.id.ocr_continuous_decode_failed, new OcrResultFailure(timeRequired));
			message.sendToTarget();
		}
	}

}












