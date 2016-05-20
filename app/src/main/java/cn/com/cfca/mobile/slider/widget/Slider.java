package cn.com.cfca.mobile.slider.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import cn.com.cfca.mobile.slider.R;

/**
 * Created by wufan on 16/5/20.
 */
public class Slider extends View {

    private int barHeight;
    private int circleRadius;
    private int spaceAfterBar;
    private int circleTextSize;
    private int maxValueTextSize;
    private int labelTextSize;
    private int labelTextColor;
    private int maxValueTextColor;
    private int circleTextColor;
    private int baseColor;
    private int fillColor;
    private String labelText;
    private Paint labelTextPaint;
    private Paint maxValuePaint;
    private Paint circleTextPaint;
    private Paint circlePaint;
    private Paint barPaint;

    private Rect labelTextRect = new Rect();

    private int currentValue = 0;
    private int maxValue = 100;


    public Slider(Context context) {
        this(context, null, 0);
    }

    public Slider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Slider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Slider, defStyleAttr, 0);
        Resources resources = context.getResources();
        try {
            barHeight = a.getDimensionPixelSize(R.styleable.Slider_barHeight,
                  resources.getDimensionPixelSize(R.dimen.default_slider_barHeight));
            circleRadius = a.getDimensionPixelSize(R.styleable.Slider_circleRadius,
                  resources.getDimensionPixelSize(R.dimen.default_slider_circleRadius));
            spaceAfterBar = a.getDimensionPixelSize(R.styleable.Slider_spaceAfterBar,
                  resources.getDimensionPixelSize(R.dimen.default_slider_spaceAfterBar));
            circleTextSize = a.getDimensionPixelSize(R.styleable.Slider_circleTextSize,
                  resources.getDimensionPixelSize(R.dimen.default_slider_circleTextSize));
            maxValueTextSize = a.getDimensionPixelSize(R.styleable.Slider_maxValueTextSize,
                  resources.getDimensionPixelSize(R.dimen.default_slider_maxValueTextSize));
            labelTextSize = a.getDimensionPixelSize(R.styleable.Slider_labelTextSize,
                  resources.getDimensionPixelSize(R.dimen.default_slider_labelTextSize));
            labelTextColor =
                  a.getColor(R.styleable.Slider_labelTextColor, ContextCompat.getColor(context, R.color.navy));
            maxValueTextColor =
                  a.getColor(R.styleable.Slider_maxValueTextColor, ContextCompat.getColor(context, R.color.navy));
            circleTextColor =
                  a.getColor(R.styleable.Slider_circleTextColor, ContextCompat.getColor(context, R.color.white));
            baseColor = a.getColor(R.styleable.Slider_baseColor, ContextCompat.getColor(context, R.color.navy));
            fillColor = a.getColor(R.styleable.Slider_fillColor, ContextCompat.getColor(context, R.color.red));
            labelText = a.getString(R.styleable.Slider_labelText);
        } finally {
            a.recycle();
        }
        labelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelTextPaint.setTextSize(labelTextSize);
        labelTextPaint.setColor(labelTextColor);
        labelTextPaint.setTextAlign(Paint.Align.LEFT);
        labelTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        maxValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maxValuePaint.setTextSize(maxValueTextSize);
        maxValuePaint.setColor(maxValueTextColor);
        maxValuePaint.setTextAlign(Paint.Align.RIGHT);
        maxValuePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        circleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleTextPaint.setTextSize(circleTextSize);
        circleTextPaint.setColor(circleTextColor);
        circleTextPaint.setTextAlign(Paint.Align.CENTER);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(fillColor);

        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setColor(baseColor);

        labelTextPaint.getTextBounds(labelText, 0, labelText.length(), labelTextRect);
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        invalidate();
        requestLayout();
    }

    public void setValue(int newValue) {
        if (newValue < 0) {
            currentValue = 0;
        } else if (newValue > maxValue) {
            currentValue = maxValue;
        } else {
            currentValue = newValue;
        }
        invalidate();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.resolveSize(getDesiredWidth(), widthMeasureSpec);
        int height = View.resolveSize(getDesiredHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int getDesiredWidth() {
        int size = getPaddingLeft() + getPaddingRight();
        // TODO
        return size;
    }

    private int getDesiredHeight() {
        int size = getPaddingTop() + getPaddingBottom();
        size += labelTextPaint.getFontSpacing();
        size += Math.max(maxValuePaint.getFontSpacing(), 2 * circleRadius);
        return size;
    }
}
