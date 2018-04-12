package com.cjym.yunmabao.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.cjym.yunmabao.R;
import com.cjym.yunmabao.utils.LogUtils;

/**
 * 项目名称：Serialport
 * 类描述：
 * 创建人：kejian
 * 创建时间：2016-08-08 10:19
 * 修改人：Administrator
 * 修改时间：2016-08-08 10:19
 * 修改备注：
 */
public class CustomView extends View {
    private static final String TAG = "CustomView";
    private String text;
    private int textSize;
    private String textN = null;
    private int textNSize;
    private Bitmap mBitmap;
    private int imageScaleType;
    private int mTextColor;
    private Path mPt;

    private Rect rect;
    private Paint mPaint;
    private Rect mTextBound;
    private Rect mTextNBound = null;
    private int margin = 16;

    private static final int FILL_XY = 0;
    private static final int CENTER = 1;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomView_text:
                    text = a.getString(attr);
                    break;
                case R.styleable.CustomView_textsize:
                    textSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomView_image:
                    mBitmap = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomView_imageScaleType:
                    imageScaleType = a.getInt(attr, 0);
                    break;
                case R.styleable.CustomView_titleTextColor:
                    mTextColor = a.getColor(attr, Color.WHITE);
                    LogUtils.w(TAG,"mTextColor = "+mTextColor);
                    break;
                case R.styleable.CustomView_textN:
                    textN = a.getString(attr);
                    break;
                case R.styleable.CustomView_textNSize:
                    textNSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomView_margin:
                    margin = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            8, getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }
        a.recycle();
        rect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(textSize);
        //计算出文字所占的宽高
        mPaint.getTextBounds(text, 0, text.length(), mTextBound);

        if(textN != null) {
            mTextNBound = new Rect();
            mPaint.setTextSize(textNSize);
            mPaint.getTextBounds(textN, 0, textN.length(), mTextNBound);
        }

    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//    }

    int mWidth, mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /***
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mWidth = specSize;
        } else {                            //wrap_content
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mBitmap.getWidth();
            // 由字体决定的宽
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();

            int desireByTextN = 0;
            if(mTextNBound != null) {
                desireByTextN = getPaddingLeft() + getPaddingRight() + mTextNBound.width();
            }

            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                int desire = Math.max(Math.max(desireByImg, desireByTitle),desireByTextN);
                mWidth = Math.min(desire, specSize);
            }
        }
        /***
         * 设置高度
         */

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = specSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mBitmap.getHeight() + mTextBound.height()+margin;
            if(mTextNBound != null) {
                desire += mTextNBound.height();
                LogUtils.w(TAG,"mTextBound.height() "+mTextBound.height());
                LogUtils.w(TAG,"mTextNBound.height() "+mTextNBound.height());
            }
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(desire, specSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setTextSize(textSize);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        /**
         * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
         */

        if(mTextNBound == null) {
//            LogUtils.w(TAG,"3 为空");
            if (mTextBound.width() > mWidth) {
                TextPaint paint = new TextPaint(mPaint);
                String msg = TextUtils.ellipsize(text, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                        TextUtils.TruncateAt.END).toString();
                mPaint.setColor(mTextColor);
                canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

            } else {
    //            LogUtils.w(TAG,"mTextColor onDraw = "+mTextColor);
                mPaint.setColor(mTextColor);
                //正常情况，将字体居中
                canvas.drawText(text, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight/2 + mTextBound.height()/2 + mBitmap.getHeight()/2 , mPaint);
            }

            //
            rect.bottom -= mTextBound.height();
            mPaint.setColor(Color.WHITE);
            if (imageScaleType == FILL_XY) {
                canvas.drawBitmap(mBitmap, null, rect, mPaint);
            } else {
                //计算居中的矩形范围
                rect.left = mWidth / 2 - mBitmap.getWidth() / 2;
                rect.right = mWidth / 2 + mBitmap.getWidth() / 2;
                rect.top = (mHeight - mTextBound.height()) / 2 - mBitmap.getHeight() / 2 - margin;
                rect.bottom = (mHeight - mTextBound.height()) / 2 + mBitmap.getHeight() / 2 - margin;

                canvas.drawBitmap(mBitmap, null, rect, mPaint);
            }
        } else {//第三行文字不为空
            LogUtils.w(TAG,"3 不为空"+mTextColor);
            if (mTextBound.width() > mWidth) {
                TextPaint paint = new TextPaint(mPaint);
                String msg = TextUtils.ellipsize(text, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                        TextUtils.TruncateAt.END).toString();
                mPaint.setColor(mTextColor);
                canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

            } else {
                LogUtils.w(TAG,"mTextColor onDraw = "+mTextColor);
                mPaint.setColor(mTextColor);
                //正常情况，将字体居中
                LogUtils.w(TAG,"mHeight "+mHeight+"; mTextBound "+mTextBound.height()+";mBitmap = "+mBitmap.getHeight()+"; mTextNBound = "+mTextNBound.height());
                canvas.drawText(textN, mWidth / 2 - mTextNBound.width() * 1.0f / 2, mHeight/2 +mTextBound.height()/2+mBitmap.getHeight()/2+mTextNBound.height()/2 , mPaint);
                canvas.drawText(text, mWidth / 2 - mTextBound.width() * 1.0f / 2, ((int)(mHeight-mTextNBound.height()))/2 +mTextBound.height()/2+mBitmap.getHeight()/2 , mPaint);
            }

            //
            rect.bottom = rect.bottom - mTextBound.height() - mTextNBound.height();
            mPaint.setColor(Color.WHITE);
            if (imageScaleType == FILL_XY) {
                canvas.drawBitmap(mBitmap, null, rect, mPaint);
            } else {
                //计算居中的矩形范围
                rect.left = mWidth / 2 - mBitmap.getWidth() / 2;
                rect.right = mWidth / 2 + mBitmap.getWidth() / 2;
                rect.top = (mHeight - mTextBound.height() - mTextNBound.height()) / 2 - mBitmap.getHeight() / 2;
                rect.bottom = (mHeight - mTextBound.height() - mTextNBound.height()) / 2 + mBitmap.getHeight() / 2;

                canvas.drawBitmap(mBitmap, null, rect, mPaint);
            }
        }
    }
}
