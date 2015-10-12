package com.beyole.view;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
		// 设置点击事件
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mTitleText = randomText();
				// 重绘 可以在线程中直接执行
				postInvalidate();
			}
		});
	}

	/**
	 * 
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 *           如果我们不进行测量，当设置控件宽度高度为wrap_content的时候，系统默认测量的是match_parent
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 获取宽度设置类型
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int width;
		int height;
		// 如果宽度是设置了精确的值时，直接把宽度赋值给width
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			mPaint.setTextSize(mTitleTextSize);
			mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
			float boundWidth = mBound.width();
			width = (int) (getPaddingLeft() + getPaddingRight() + boundWidth);
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			mPaint.setTextSize(mTitleTextSize);
			mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
			float boundHeight = mBound.height();
			height = (int) (getPaddingTop() + getPaddingBottom() + boundHeight);
		}
		// 设置测量的布局
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setColor(Color.YELLOW);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
		mPaint.setColor(mTitleColor);
		canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
	}

	private String randomText() {
		Random random = new Random();
		Set<Integer> integers = new HashSet<Integer>();
		while (integers.size() < 4) {
			int randomInt = random.nextInt(10);
			integers.add(randomInt);
		}
		StringBuffer sb = new StringBuffer();
		for (Integer integer : integers) {
			sb.append("" + integer);
		}
		return sb.toString();
	}
}
