package com.lftechnology.hamropay.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.lftechnology.hamropay.R;

import java.lang.ref.WeakReference;

/**
 */
public class CircleLogoLoader extends View {

    private Bitmap logoImage;
    private int size = 0;
    private final RectF mainRect = new RectF();
    private Path clipPath;
    private UpdateHandler updateHandler;
    private int angle = 0, radius = 0;

    private OnAnimationCompleteListener listener;

    public interface OnAnimationCompleteListener {
        void onAnimationComplete();
    }


    public CircleLogoLoader(Context context) {
        this(context, null);
    }

    public CircleLogoLoader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLogoLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        logoImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_home_logo);
        clipPath = new Path();
        updateHandler = new UpdateHandler(this);
        size = context.getResources().getDimensionPixelSize(R.dimen.min_dimen);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(
                findOptimumSize(widthMeasureSpec),
                findOptimumSize(heightMeasureSpec)
        );
    }

    private int findOptimumSize(int measureSpec) {
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        final int result;
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                if (specSize < size) {
                    result = specSize | MEASURED_STATE_TOO_SMALL;
                } else {
                    result = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize < size ? size : specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                result = size;
        }
        return result;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mainRect.set(getPaddingLeft(), getPaddingTop(),
                w - getPaddingRight(), h - getPaddingBottom());
        radius = (int) (mainRect.height() / 2 - getContext().getResources().getDimensionPixelSize(R.dimen.stroke_width));
        if (radius <= 0) {
            radius = (int) (mainRect.height() / 2);
        }
    }

    public void updateAngle(int angle) {
        this.angle = angle;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        clipPath.reset();
        clipPath.addCircle(mainRect.centerX(), mainRect.centerY(), radius, Path.Direction.CW);
        clipPath.moveTo(mainRect.centerX(), mainRect.centerY());
        clipPath.addArc(mainRect, 270, angle);
        clipPath.lineTo(mainRect.centerX(), mainRect.centerY());
        canvas.clipPath(clipPath);
        canvas.drawBitmap(logoImage, null, mainRect, null);
    }


    public void sendAnimationComplete() {
        if (listener != null)
            listener.onAnimationComplete();

    }

    public void setOnAnimationCompleteListener(OnAnimationCompleteListener listener) {
        this.listener = listener;
    }


    public void animateProgress() {
        updateHandler.sendEmptyMessage(UpdateHandler.UPDATE_ANGLE);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        updateHandler.removeMessages(UpdateHandler.UPDATE_ANGLE);
    }


    public static class UpdateHandler extends Handler {
        private final WeakReference<CircleLogoLoader> weakReference;
        public static final int UPDATE_ANGLE = 0x1;
        private int angle;
        public static int interval = 50;

        public UpdateHandler(CircleLogoLoader view) {
            weakReference = new WeakReference<>(view);
        }

        public void init(int angle) {
            this.angle = angle;
            sendEmptyMessageDelayed(UPDATE_ANGLE, interval);

        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (angle < 360 && msg.what == UPDATE_ANGLE) {
                angle += 5;
                sendEmptyMessageDelayed(UPDATE_ANGLE, interval);
                weakReference.get().updateAngle(angle);
            } else {
                removeMessages(UPDATE_ANGLE);
                weakReference.get().sendAnimationComplete();
            }
        }
    }
}
