package girnarsoft.com.demoapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.View;
import android.widget.ImageView;

import girnarsoft.com.demoapp.R;

public final class NetworkUtility {

    private NetworkUtility() {

    }

    /**
     * Is network available boolean.
     *
     * @param context the context
     * @return boolean boolean
     */
    public static boolean isNetworkAvailable(@NonNull Context context) {
        try {
            if (null != context) {
                return isWIFINetworkAvailable(context) || isMobileNetworkAvailable(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Is wifi network available boolean.
     *
     * @param context the context
     * @return boolean boolean
     */
    public static boolean isWIFINetworkAvailable(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
                || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING;
    }


    /**
     * Is mobile network available boolean.
     *
     * @param context the context
     * @return boolean boolean
     */
    public static boolean isMobileNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING;
    }


    // TODO update the method from carDekho Utility class
    public static void showNoConnectionBar(final Context mContext, View anchorView) {
        Snackbar snackbar = Snackbar.make(anchorView, "", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            layout.setBackgroundDrawable(AppCompatDrawableManager.get().getDrawable(mContext, R.drawable.no_internet_bar_background));
        } else {
            layout.setBackground(AppCompatDrawableManager.get().getDrawable(mContext, R.drawable.no_internet_bar_background));
        }
        // Hide the text
        //        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        //        textView.setVisibility(View.INVISIBLE);

        // Inflate our custom view
        View snackView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.no_internet_connection_layout, null);
        // Configure the view
        ImageView ivSettings = snackView.findViewById(R.id.iv_setting);
        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }
        });
        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);
        // Show the Snackbar
        snackbar.show();
    }
}
