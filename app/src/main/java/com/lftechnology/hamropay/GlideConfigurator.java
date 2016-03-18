package com.lftechnology.hamropay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.module.GlideModule;

import timber.log.Timber;

/**
 * Created by laaptu on 3/18/16.
 */
public class GlideConfigurator implements GlideModule {
    public int POOL_SIZE_IN_BYTES = 20 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)
                .setBitmapPool(new LruBitmapPool(POOL_SIZE_IN_BYTES))
                .setMemoryCache(new LruResourceCache(POOL_SIZE_IN_BYTES));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

    public static RequestManager getRequestManager(Context context) {
        return Glide.with(context);
    }

    public static BitmapRequestBuilder<String, Bitmap> load(Context context, String path) {
        return getRequestManager(context).load(path)
                .asBitmap()
                .dontTransform()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(false);
    }

    public static BitmapRequestBuilder<String, Bitmap> loadCircularImage(Context context, String path, int borderColor) {

        return load(context, path).transform(new CircleTransformation(context, path, borderColor))
                .placeholder(R.drawable.ic_noprofilepicture)
                .error(R.drawable.ic_noprofilepicture);

    }

    public static class CircleTransformation extends BitmapTransformation {
        private String path;
        private int borderColor;

        public CircleTransformation(Context context, String path, int borderColor) {
            super(context);
            this.path = path;
            this.borderColor = borderColor;
            Timber.d("BorderColor =%d", borderColor);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null)
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            paint.setAntiAlias(true);

            Canvas canvas = new Canvas(result);
            float r = size / 2f;

            if (borderColor != 0) {
                Paint borderPaint = new Paint();
                borderPaint.setColor(borderColor);
                borderPaint.setStyle(Paint.Style.FILL);
                borderPaint.setAntiAlias(true);
                canvas.drawCircle(r, r, r, borderPaint);
            }
            canvas.drawCircle(r, r, borderColor != 0 ? r - 10 : r, paint);

            return result;
        }

        @Override
        public String getId() {
            //this must be a unique one
            return path;
        }


    }

}
