package girnarsoft.com.demoapp.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import girnarsoft.com.demoapp.utils.NormalUtility;
import girnarsoft.com.demoapp.utils.imageloader.transformation.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * #Ref:https://bumptech.github.io/glide/doc/migrating.html
 * https://github.com/qoqa/glide-svg
 * gif,file,drawable,Bitmap,Svg,Uri
 * String urlPng = "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png";
 * String urlJpg = "https://media.alienwarearena.com/media/tux-r.jpg";
 * String urlGif = "http://ttlstatic-a.akamaihd.net/s3fs/google%20developers%20india%20android%20course%20_2.gif";
 * String urlSVG = "http://upload.wikimedia.org/wikipedia/commons/e/e8/Svg_example3.svg";
 * <p>
 * USE TAG WITH IMAGEVIEW:* http://androidhiker.blogspot.in/2015/10/how-to-resolve-glide-settag-issue.html
 */
public final class ImageLoader {

    private ImageLoader() {
    }

    public static void loadImage(@NonNull final Context mContext, @NonNull final Object arg0, @NonNull final ImageView iv) {
        try {
            GlideApp.with(mContext)
                    .as(Bitmap.class)
                    .load(arg0)
                    .into(iv);
        } catch (Exception e) {
            NormalUtility.showException(e);
        }
        //.signature(new ObjectKey(System.currentTimeMillis()))

    }


    public static void loadImageWithoutCache(@NonNull final Context mContext, @NonNull final Object arg0, @NonNull final ImageView iv, int defaultImageId) {
        try {
            GlideApp.with(mContext)
                    .as(Bitmap.class)
                    .load(arg0)
                    .placeholder(defaultImageId)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(iv);
        } catch (Exception e) {
            NormalUtility.showException(e);
        }
    }

    public static void loadImage(@NonNull Context mContext, @NonNull Object arg0, @NonNull ImageView iv, int defaultImageId) {
        try {
            GlideApp.with(mContext)
                    .as(Bitmap.class)
                    .load(arg0)
                    .placeholder(defaultImageId)
                    .into(iv);
        } catch (Exception e) {
            NormalUtility.showException(e);
        }
    }

    public static void loadImage(@NonNull Context mContext, @NonNull Object arg0, @NonNull ImageView iv, Drawable placeHolder) {
        try {
            GlideApp.with(mContext)
                    .as(Bitmap.class)
                    .load(arg0)
                    .placeholder(placeHolder)
                    .into(iv);
        } catch (Exception e) {
            NormalUtility.showException(e);
        }

    }

    public static void loadImage(@NonNull Context mContext, Object arg0, @NonNull ImageView iv, @NonNull RequestOptions requestOptions, int defaultImage) {
        try {
            GlideApp.with(mContext)
                    .as(Bitmap.class)
                    .load(arg0)
                    .apply(requestOptions)
                    .placeholder(defaultImage)
                    .into(iv);
        } catch (Exception e) {
            NormalUtility.showException(e);
        }
    }

    public static void loadImageWithDrawable(@NonNull final Context mContext, @NonNull Object arg0,
                                             int defaultImageId, final DrawableEnum drawableEnum, final View componentView) {
        try {
            int height = mContext.getResources().getDrawable(defaultImageId).getIntrinsicHeight();
            int width = mContext.getResources().getDrawable(defaultImageId).getIntrinsicWidth();
            GlideApp.with(mContext)
                    .as(Bitmap.class)
                    .load(arg0)
                    .override(width, height)
                    .placeholder(defaultImageId)
                    .into(new BaseTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            BitmapDrawable resource = new BitmapDrawable(mContext.getResources(), bitmap);
                            TextView view = (TextView) componentView;
                            switch (drawableEnum) {
                                case TOP:
                                    view.setCompoundDrawablesWithIntrinsicBounds(null, resource, null, null);
                                    break;
                                case LEFT:
                                    view.setCompoundDrawablesWithIntrinsicBounds(resource, null, null, null);
                                    break;
                                case BOTTOM:
                                    view.setCompoundDrawablesWithIntrinsicBounds(null, null, null, resource);
                                    break;
                                case RIGHT:
                                    view.setCompoundDrawablesWithIntrinsicBounds(null, null, resource, null);
                                    break;
                            }

                        }

                        @Override
                        public void getSize(SizeReadyCallback cb) {
                            cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                        }

                        @Override
                        public void removeCallback(SizeReadyCallback cb) {

                        }
                    });
        } catch (Exception e) {
            NormalUtility.showException(e);
        }

    }

    public static void loadImage(@NonNull Context mContext, @NonNull String url, @NonNull ImageView iv, final ImageLoadCallBack imageLoadCallBack) {
        try {
            GlideApp.with(mContext)
                    .as(Bitmap.class)
                    .load(url)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            if (null != imageLoadCallBack) {
                                imageLoadCallBack.onFailure(e);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            if (null != imageLoadCallBack) {
                                imageLoadCallBack.onSuccess(resource);
                            }
                            return false;
                        }
                    }).into(iv);
        } catch (Exception e) {
            NormalUtility.showException(e);
        }
    }


    public static void loadImage(@NonNull final Context mContext, @NonNull Object arg0, @NonNull final ImageLoadCallBack imageLoadCallBack) {
        try {
            GlideApp.with(mContext)
                    .as(Bitmap.class)
                    .load(arg0)
                    .into(new BaseTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            imageLoadCallBack.onSuccess(bitmap);
                        }

                        @Override
                        public void getSize(SizeReadyCallback cb) {
                            cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                        }

                        @Override
                        public void removeCallback(SizeReadyCallback cb) {

                        }
                    });
        } catch (Exception e) {
            NormalUtility.showException(e);
        }

    }


    public static void loadGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView iv) {
        try {
            Glide.with(context)
                    .load(url)
                    .into(iv);
        } catch (Exception e) {
            NormalUtility.showException(e);
        }
    }

    public static void loadImageWithTransformation(@NonNull Context context, @NonNull String url, @NonNull ImageView iv) {
        Glide.with(context)
                .load(url)
                .apply(bitmapTransform(new BlurTransformation(context)))
                .into(iv);
    }

    public static void clearGlideCache(@NonNull final Context context) {
      /*  AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Glide.get(context).clearDiskCache();
                    Glide.get(context).clearMemory();
                } catch (Exception e) {
                    NormalUtility.showException(e);
                }
            }
        });*/
    }


    public enum DrawableEnum {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }


}
