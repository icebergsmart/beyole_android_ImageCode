package com.beyole.view;

import com.beyole.imagecode.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ImageCodeView extends View {

	// 获取文本内容
	private String mTitleText;
	// 获取文本颜色信息
	private int mTitleColor;
	// 获取文本大小
	private int mTitleTextSize;

	// 设置绘制范围
	private Rect mBound;
	// 设置画笔
	private Paint mPaint;

	public ImageCodeView(Context context) {
		this(context, null);
	}

	public ImageCodeView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ImageCodeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ImageCodeView, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.ImageCodeView_titleText:
				mTitleText = a.getString(attr);
				break;
			case R.styleable.ImageCodeView_titleColor:
				mTitleColor = a.getColor(attr, Color.GREEN);
				break;
			case R.styleable.ImageCodeView_titleTextSize:
				// 将sp转化为px
				mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
				break;
			}
		}
		// 进行回收
		a.recycle();
		// 初始化画笔
		mPaint = new Paint();
		mPaint.setTextSize(mTitleTextSize);
		mBound = new Rect();
		mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setColor(Color.YELLOW);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
		mPaint.setColor(mTitleColor);
		canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
	}
}
