package org.mdcconcepts.com.mdcspauserapp.customitems;




import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontTextView extends TextView {

	public FontTextView(Context context) {
		super(context);
		if (!isInEditMode()) {
			Typeface font = Typeface.createFromAsset(context.getAssets(),
					Util.fontPath);
			this.setTypeface(font);

			this.setTextColor(Color.parseColor("#4e3115"));
		}
	}

	public FontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (!isInEditMode()) {
			// Your custom code that is not letting the Visual Editor draw
			// properly
			// i.e. thread spawning or other things in the constructor
			Typeface font = Typeface.createFromAsset(context.getAssets(),
					Util.fontPath);
			this.setTypeface(font);

			this.setTextColor(Color.parseColor("#4e3115"));
		}
	}

	public FontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (!isInEditMode()) {
			Typeface font = Typeface.createFromAsset(context.getAssets(),
					Util.fontPath);
			this.setTypeface(font);

			this.setTextColor(Color.parseColor("#4e3115"));
		}
	}

	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		

	}

}