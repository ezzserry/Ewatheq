package ae.ewatheq.utils;


import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import java.io.UnsupportedEncodingException;



public class VolleyResponseUtf8String extends JsonRequest<String> {
	public VolleyResponseUtf8String(int post, String url, String jsonRequest,
			Listener<String> listener, ErrorListener errorListener) {
		super(post, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
				errorListener);
	}

	public VolleyResponseUtf8String(int post, String url, 
			Listener<String> listener, ErrorListener errorListener) {
		super(post, url, null, listener,
				errorListener);


	}
	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		try {
			// solution 1:
			String jsonString = new String(response.data, "UTF-8");
			// solution 2:
			/* response.headers.put(HTTP.CONTENT_TYPE,
                response.headers.get("content-type"));
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));*/
			//
			return Response.success( jsonString,
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} 
	}
}