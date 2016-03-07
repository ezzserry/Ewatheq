package ae.ewatheq.utils;




import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.TextView;


public class CustomTextViewEqualWidthHeight extends TextView {

	public CustomTextViewEqualWidthHeight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomTextViewEqualWidthHeight(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomTextViewEqualWidthHeight(Context context) {
		super(context);
	}

	@Override
	public void onMeasure(int widthSpec, int heightSpec) {
		  super.onMeasure(widthSpec, heightSpec);
		  int width = getMeasuredWidth();
		  int height = getMeasuredHeight();
		  if(width>height)
			  height = width;
		  else 
			  width = height;
		  width = width+10;
		  setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
		}

	/*@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
	    int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
	    setMeasuredDimension(height,height);
	    Log.v("measure","width:"+height + " height:"+height);
	}*/
	/*@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if (widthMeasureSpec>0 && heightMeasureSpec>0)
		{
			if (heightMeasureSpec>widthMeasureSpec)
			{
				widthMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasureSpec,
						MeasureSpec.EXACTLY);
			
			}
			else {
				heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasureSpec,
						MeasureSpec.EXACTLY);
				
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}*/

}