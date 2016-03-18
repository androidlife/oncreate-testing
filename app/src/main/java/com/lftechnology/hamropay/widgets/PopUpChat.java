package com.lftechnology.hamropay.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.utils.GeneralUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ManasShrestha on 2/12/16.
 */
public class PopUpChat extends PopupWindow {
    @Bind(R.id.ll_pop_up)
    LinearLayout llPopUp;

    private  static int STATUS_BAR_SIZE = (int) GeneralUtils.convertDpToPixel(24);
    private  static long TRANSLATE_ANIMATION_DURATION = 250;
    private  static int STATUS_TITLE_SIZE = (int) GeneralUtils.convertDpToPixel(48);

    private final Context context;
    int height;
    private boolean expanded = false;

    public PopUpChat(Context context) {
        this.context = context;
        initializePop();
    }

    /**
     * Method to show pop up anchored at give view
     *
     * @param parentView anchor view
     */
    public void show(View parentView) {
        showAtLocation(parentView, Gravity.BOTTOM, 0, (STATUS_BAR_SIZE - STATUS_TITLE_SIZE));
    }

    /**
     * initialize pop up
     * set height/width of pop up.
     * inflate the view to pop up.
     */
    private void initializePop() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;

        //set height and width of the pop up
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(STATUS_TITLE_SIZE);

        View view = LayoutInflater.from(context).inflate(R.layout.pop_up_chat, null, false);
        ButterKnife.bind(this, view);

        setContentView(view);

    }

//    @OnClick(R.id.edt_send_message)
//    public void onClick() {
//
//        if (!expanded) {
//            update(0, 0, WindowManager.LayoutParams.MATCH_PARENT, ((int) (200 * 5) + STATUS_BAR_SIZE));
//        } else {
//            ValueAnimator animator = new ValueAnimator().ofFloat(llPopUp.getHeight(), STATUS_TITLE_SIZE);
//            animator.setDuration(TRANSLATE_ANIMATION_DURATION);
//
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                    update(0, STATUS_BAR_SIZE - STATUS_TITLE_SIZE, WindowManager.LayoutParams.MATCH_PARENT, (int) ((Float) (valueAnimator.getAnimatedValue())).floatValue());
//                }
//            });
//
//            animator.start();
//        }
//
//        expanded = !expanded;
//
//    }

}

