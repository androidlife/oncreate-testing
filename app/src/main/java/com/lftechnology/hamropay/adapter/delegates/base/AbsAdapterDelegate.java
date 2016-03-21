package com.lftechnology.hamropay.adapter.delegates.base;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.lftechnology.hamropay.GlideConfigurator;
import com.lftechnology.hamropay.activities.FriendProfileActivity;
import com.lftechnology.hamropay.db.models.UserTemp;

/**
 * Created by laaptu on 3/21/16.
 */
public abstract class AbsAdapterDelegate<T> implements AdapterDelegate<T> {
    protected int viewType;
    private Context context;

    public AbsAdapterDelegate(int viewType) {
        this.viewType = viewType;
    }


    public AbsAdapterDelegate(Context context, int viewType) {
        this(viewType);
        this.context = context;
    }

    @Override
    public int getItemViewType() {
        return viewType;
    }

    public Context getContext() {
        return context;
    }

    public void addClickListener(View view, final UserTemp userTemp) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfileActivity(userTemp);
            }
        });
    }

    public void loadImage(ImageView imageView, String filePath, int color) {
        GlideConfigurator.loadCircularImage(getContext(), filePath,
                color)
                .into(imageView);
    }

    private void navigateToProfileActivity(UserTemp userTemp) {
        context.startActivity(FriendProfileActivity.launchActivity(context, userTemp.getUserId()));
    }
}
