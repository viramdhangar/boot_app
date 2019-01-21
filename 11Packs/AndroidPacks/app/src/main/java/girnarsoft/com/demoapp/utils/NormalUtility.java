package girnarsoft.com.demoapp.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import girnarsoft.com.demoapp.BuildConfig;
import girnarsoft.com.demoapp.R;
import girnarsoft.com.demoapp.activity.AbstractAppCompactActivity;
import girnarsoft.com.demoapp.fragment.AbstractBaseFragment;

public final class NormalUtility {

    /**
     * The constant isShowLog.
     */
    private static boolean isShowLog = BuildConfig.DEBUG;

    private NormalUtility() {

    }

    /**
     * Gets resources string.
     *
     * @param context the context
     * @param id      the id
     * @return resources string
     */
    public static String getResourcesString(@NonNull Context context, int id) {
        if (id != -1) {
            return context.getResources().getString(id);
        }
        return null;
    }

    /**
     * Gets resources integer.
     *
     * @param context the context
     * @param id      the id
     * @return the resources integer
     */
    public static Integer getResourcesInteger(@NonNull Context context, int id) {
        if (id != -1) {
            return context.getResources().getInteger(id);
        }
        return null;
    }

    /**
     * Gets ressource drawable.
     *
     * @param context the context
     * @param id      the id
     * @return the ressource drawable
     */
    public static Drawable getResourceDrawable(@NonNull Context context, int id) {
        if (id != -1) {
            return context.getResources().getDrawable(id);
        }
        return null;
    }

    /**
     * Is below marshmallow version boolean.
     *
     * @return the boolean
     */
    public static boolean isBelowMarshmallowVersion() {
        return Build.VERSION.SDK_INT < 23;
    }

    /**
     * Re attach fragment.
     *
     * @param tag          the tag
     * @param baseActivity the base activity
     * @throws Exception the exception
     */
    public static void reAttachFragment(@NonNull String tag, @NonNull AbstractAppCompactActivity baseActivity) {
        Fragment currentFragment = baseActivity.getSupportFragmentManager().findFragmentByTag(tag);
        if (currentFragment != null) {
            FragmentTransaction fragmentTransaction = baseActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.detach(currentFragment);
            fragmentTransaction.attach(currentFragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * Gets active fragment.
     *
     * @param abstractAppCompactActivity the abstract base activity
     * @return active fragment
     */
    public static Fragment getActiveFragment(@NonNull AbstractAppCompactActivity abstractAppCompactActivity) {
        if (abstractAppCompactActivity.getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = abstractAppCompactActivity.getSupportFragmentManager().getBackStackEntryAt(abstractAppCompactActivity.getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return abstractAppCompactActivity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    /**
     * Gets active fragment name.
     *
     * @param abstractAppCompactActivity the abstract base activity
     * @return active fragment name
     */
    public static String getActiveFragmentName(@NonNull AbstractAppCompactActivity abstractAppCompactActivity) {
        if (abstractAppCompactActivity.getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return "";
        }
        return abstractAppCompactActivity.getSupportFragmentManager().getBackStackEntryAt(abstractAppCompactActivity.getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
    }

    /**
     * Pop to back stack.
     *
     * @param abstractAppCompactActivity the abstract base activity
     */
    public static void popToBackStack(@NonNull AbstractAppCompactActivity abstractAppCompactActivity) {
        FragmentManager fragmentManager = abstractAppCompactActivity.getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    /**
     * Remove fragment.
     *
     * @param abstractAppCompactActivity the abstract base activity
     * @param fragment                   the fragment
     * @throws Exception the exception
     */
    public static void removeFragment(@NonNull AbstractAppCompactActivity abstractAppCompactActivity, @NonNull Fragment fragment) {
        FragmentManager manager = abstractAppCompactActivity.getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(fragment);
        trans.commitAllowingStateLoss();
        manager.popBackStack();
    }

    /**
     * Remove all fragment.
     *
     * @param abstractAppCompactActivity the abstract base activity
     * @throws Exception the exception
     */
    public static void removeAllFragment(@NonNull AbstractAppCompactActivity abstractAppCompactActivity) {
        FragmentManager fm = abstractAppCompactActivity.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * Add fragment.
     *
     * @param abstractAppCompactActivity the abstract base activity
     * @param abstractBaseFragment       the abstract base fragment
     * @param tag                        the tag
     * @param frameLayoutId              the frame layout id
     * @param addToBackStack             the add to back stack
     */
    public static void addFragment(@NonNull AbstractAppCompactActivity abstractAppCompactActivity, @NonNull AbstractBaseFragment abstractBaseFragment, @NonNull String tag, int frameLayoutId,
                                   boolean addToBackStack, boolean isTransitionAnimate) {
        if (!abstractAppCompactActivity.isFinishing()) {
            FragmentTransaction transaction = abstractAppCompactActivity.getSupportFragmentManager().beginTransaction();
            transaction.add(frameLayoutId, abstractBaseFragment, tag);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * Add full fragment.
     *
     * @param abstractAppCompactActivity the abstract base activity
     * @param abstractBaseFragment       the abstract base fragment
     * @param tag                        the tag
     * @param frameLayoutId              the frame layout id
     * @param addToBackStack             the add to back stack
     * @param isTransitionAnimate        the Animate the Fragment slide
     */
    public static void addFullFragment(@NonNull AbstractAppCompactActivity abstractAppCompactActivity, @NonNull AbstractBaseFragment abstractBaseFragment, @NonNull String tag, int frameLayoutId,
                                       boolean addToBackStack, boolean isTransitionAnimate) {
        if (!abstractAppCompactActivity.isFinishing()) {
            FragmentTransaction transaction = abstractAppCompactActivity.getSupportFragmentManager().beginTransaction();
            transaction.add(frameLayoutId, abstractBaseFragment, tag);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();

            FragmentManager fragmentManager = abstractAppCompactActivity.getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            boolean fragmentPopped = fragmentManager.popBackStackImmediate(tag, 0);
            if (!fragmentPopped && fragmentManager.findFragmentByTag(tag) == null) {
                ft.add(frameLayoutId, abstractBaseFragment, tag);
                if (addToBackStack) {
                    ft.addToBackStack(tag);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    /**
     * Replace fragment.
     *
     * @param abstractAppCompactActivity the abstract base activity
     * @param abstractBaseFragment       the abstract base fragment
     * @param tag                        the tag
     * @param frameLayoutId              the frame layout id
     * @param addToBackStack             the add to back stack
     * @param isTransitionAnimate        the Animate the Fragment slide
     */
    public static void replaceFragment(@NonNull AbstractAppCompactActivity abstractAppCompactActivity, @NonNull AbstractBaseFragment abstractBaseFragment, @NonNull String tag, int frameLayoutId,
                                       boolean addToBackStack, boolean isTransitionAnimate) {
        if (!abstractAppCompactActivity.isFinishing()) {
            FragmentTransaction transaction = abstractAppCompactActivity.getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, abstractBaseFragment, tag);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * Replace full fragment.
     *
     * @param abstractAppCompactActivity the abstract base activity
     * @param abstractBaseFragment       the abstract base fragment
     * @param tag                        the tag
     * @param frameLayoutId              the frame layout id
     * @param addToBackStack             the add to back stack
     * @param isTransitionAnimate        the Animate the Fragment slide
     */
    public static void replaceFullFragment(@NonNull AppCompatActivity abstractAppCompactActivity, @NonNull AbstractBaseFragment abstractBaseFragment, @NonNull String tag, int frameLayoutId,
                                           boolean addToBackStack, boolean isTransitionAnimate) {
        if (!abstractAppCompactActivity.isFinishing()) {
            FragmentManager fragmentManager = abstractAppCompactActivity.getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            boolean fragmentPopped = fragmentManager.popBackStackImmediate(tag, 0);
            if (!fragmentPopped && fragmentManager.findFragmentByTag(tag) == null) {
                ft.replace(frameLayoutId, abstractBaseFragment, tag);
                if (addToBackStack)
                    ft.addToBackStack(tag);
                ft.commitAllowingStateLoss();
            }
        }
    }

    /**
     * Sets dialog height width.
     *
     * @param context the context
     * @param dialog  the dialog
     */
    public static void setDialogHeightWidth(@NonNull Context context, @NonNull Dialog dialog) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        //   int height = metrics.heightPixels;
        if (null != dialog.getWindow()) {
            dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * Sets dialog height width.
     *
     * @param context the context
     * @param dialog  the dialog
     */
    public static void setDialogHeightWidthForLMS(@NonNull Context context, @NonNull Dialog dialog) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        //   int height = metrics.heightPixels;
        if (null != dialog.getWindow()) {
            dialog.getWindow().setLayout((6 * width) / 6, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * Show snack bar.
     *
     * @param mainView the main view
     * @param message  the message
     */
    public static void showSnackBar(@NonNull View mainView, @NonNull String message) {
        Snackbar.make(mainView, message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Gets formatted value with rupee sign.
     *
     * @param mContext the m context
     * @param value    the value
     * @return formatted value with rupee sign
     */
    public static String getCommaSeparatedValueWithRupeeSign(@NonNull Context mContext, double value) {
        String returnStr = mContext.getString(R.string.rupeeSign);
        return returnStr + getCommaSeparatedValue(value);
    }

    /**
     * Gets formatted amount.
     *
     * @param value the value
     * @return formatted amount
     */
    public static String getCommaSeparatedValue(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("##,##,###");
        return decimalFormat.format(value);
    }

    /**
     * Retrieve contact name string.
     *
     * @param context the context
     * @param uri     the uri
     * @return string string
     */
    public static String retrieveContactName(@NonNull Context context, @NonNull Uri uri) {
        String contactName = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            }
            cursor.close();
        }
        return contactName;
    }



    /**
     * Gets only number in string.
     *
     * @param value the value
     * @return only number in string
     */
    public static String getOnlyNumberInString(@NonNull String value) {
        if (!TextUtils.isEmpty(value)) {
            value = value.replaceAll("\\D+", "");
            return value;
        }
        return null;
    }

    /****
     * Method for Setting the Height of the ListView dynamically.
     *
     * Called after setting adapter
     * *** Hack to fix the issue of not showing all the items of the ListView
     * *** when placed inside a ScrollView
     * @param listView the list view
     * @throws Exception the exception
     */
    public static void setDialogHeightForListView(@NonNull ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);

            if (totalHeight <= 400) {
                totalHeight += listItem.getMeasuredHeight();
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * Stop back ground service.
     *
     * @param context          the m context
     * @param processClassName the process class name
     */
    public static void stopBackGroundService(@NonNull Context context, @NonNull String processClassName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (null != manager) {
            final List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(Integer.MAX_VALUE);
            for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
                if (runningServiceInfo.service.getClassName().equals(processClassName)) {
                    android.os.Process.killProcess(runningServiceInfo.pid);
                    break;
                }
            }
        }
    }

    /**
     * Check drawable click boolean.
     *
     * @param event         the event
     * @param textView      the text view
     * @param drawableClick the drawable click
     * @return boolean boolean
     * @throws Exception the exception
     */
    public static boolean checkDrawableClick(@NonNull MotionEvent event, @NonNull TextView textView, @NonNull DrawableClick drawableClick) {
        return event.getRawX() >= (getDrawablePosition(drawableClick) - textView.getCompoundDrawables()[getDrawablePosition(drawableClick)].getBounds().width());
    }

    /**
     * Check drawable click boolean.
     *
     * @param event         the event
     * @param editText      the edit text
     * @param drawableClick the drawable click
     * @return boolean boolean
     * @throws Exception the exception
     */
    public static boolean checkDrawableClick(@NonNull MotionEvent event, @NonNull EditText editText, @NonNull DrawableClick drawableClick) {
        return event.getRawX() >= (editText.getLeft() - editText.getCompoundDrawables()[getDrawablePosition(drawableClick)].getBounds().width());
    }

    /**
     * @param drawableClick check that drawable clicked
     * @return
     */
    private static int getDrawablePosition(DrawableClick drawableClick) {
        int drawablePosition = 0;
        switch (drawableClick) {
            case DRAWABLE_LEFT:
                drawablePosition = 0;
                break;
            case DRAWABLE_TOP:
                drawablePosition = 1;
                break;
            case DRAWABLE_RIGHT:
                drawablePosition = 2;
                break;
            case DRAWABLE_BOTTOM:
                drawablePosition = 3;
                break;
        }
        return drawablePosition;
    }

    /**
     * @param editText
     * @param drawableClick
     * @return
     */
    private static int getDrawablePosition(EditText editText, DrawableClick drawableClick) {
        int drawablePosition = 0;
        switch (drawableClick) {
            case DRAWABLE_LEFT:
                drawablePosition = editText.getLeft();
                break;
            case DRAWABLE_TOP:
                drawablePosition = editText.getTop();
                break;
            case DRAWABLE_RIGHT:
                drawablePosition = editText.getRight();
                break;
            case DRAWABLE_BOTTOM:
                drawablePosition = editText.getBottom();
                break;
        }
        return drawablePosition;
    }

    /**
     * Smooth scroll.
     *
     * @param scrollView the scroll view
     * @param editText   the edit text
     */
    public static void smoothScroll(@NonNull ScrollView scrollView, @NonNull EditText editText) {
        editText.requestFocus();
        ObjectAnimator.ofInt(scrollView, "scrollY", 247).setDuration(800).start();
    }

    /**
     * Smooth scroll layout.
     *
     * @param scrollView the scroll view
     */
    public static void smoothScrollLayout(@NonNull ScrollView scrollView) {
        ObjectAnimator.ofInt(scrollView, "scrollY", 500).setDuration(800).start();
    }


    /**
     * Smooth scroll with animation.
     *
     * @param scrollView  the scroll view
     * @param toViewYAxis the to view y axis
     * @param duration    the duration
     */
    public static void smoothScrollWithAnimation(@NonNull ScrollView scrollView, int toViewYAxis, int duration) {
        ObjectAnimator.ofInt(scrollView, "scrollY", toViewYAxis).setDuration(duration).start();
    }

    public static void smoothScrollWithAnimation(@NonNull NestedScrollView scrollView, int toViewYAxis, int duration) {
        ObjectAnimator.ofInt(scrollView, "scrollY", toViewYAxis).setDuration(duration).start();
    }


    /**
     * Send sms.
     *
     * @param context      the context
     * @param mobileNumber the mobile number
     * @param msg          the msg
     * @throws Exception the exception
     */
    public static void sendSMS(@NonNull Context context, @NonNull String mobileNumber, @NonNull String msg) {
        Uri uri = Uri.parse("smsto:" + mobileNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", msg);
        context.startActivity(intent);
    }

    /**
     * Send sms.
     *
     * @param phoneNo the phone no
     * @param msg     the msg
     * @throws Exception the exception
     */
    public static void sendSMSInstant(@NonNull String phoneNo, @NonNull String msg) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, msg, null, null);
    }

    /**
     * Send whats app msg.
     *
     * @param context the context
     * @param msg     the msg
     * @throws PackageManager.NameNotFoundException the name not found exception
     */
    public static void sendWhatsAppMsg(@NonNull Context context, @NonNull String msg) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        context.startActivity(Intent.createChooser(intent, "Share with"));
    }

    /**
     * Is my service running boolean.
     *
     * @param context   the context
     * @param className the class name
     * @return boolean boolean
     */
    public static boolean isServiceRunning(@NonNull Context context, @NonNull String className) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (null != manager) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (className.equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Expand collapse with animation.
     *
     * @param view   the view
     * @param expand the expand
     * @throws IllegalAccessException    the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     * @throws NoSuchMethodException     the no such method exception
     */
    public static void expandCollapseAnimation(@NonNull final View view, final boolean expand) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Method m = view.getClass().getDeclaredMethod("onMeasure", int.class, int.class);
        m.setAccessible(true);
        m.invoke(
                view,
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(((View) view.getParent()).getMeasuredWidth(), View.MeasureSpec.AT_MOST)
        );
        final int initialHeight = view.getMeasuredHeight();
        if (expand) {
            view.getLayoutParams().height = 0;
        } else {
            view.getLayoutParams().height = initialHeight;
        }
        view.setVisibility(View.VISIBLE);

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int newHeight;
                if (expand) {
                    newHeight = (int) (initialHeight * interpolatedTime);
                } else {
                    newHeight = (int) (initialHeight * (1 - interpolatedTime));
                }
                view.getLayoutParams().height = newHeight;
                view.requestLayout();

                if (interpolatedTime == 1 && !expand)
                    view.setVisibility(View.GONE);
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration(((int) (initialHeight / view.getContext().getResources().getDisplayMetrics().density)) * 2);
        view.startAnimation(a);
    }

    /**
     * Start download file.
     *
     * @param context the m context
     * @param url     the url
     */
    public static void startDownloadFile(@NonNull Context context, @NonNull String url) {
        if (NetworkUtility.isNetworkAvailable(context)) {
            DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (null != downloadmanager) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadmanager.enqueue(request);
            }
        }
    }

    /**
     * Show hide icon on touch boolean.
     *
     * @param motionEvent        the motion event
     * @param editText           the edit text
     * @param isIconShow         the is show
     * @param typeface           the typeface
     * @param iconShowImage      the icon show image
     * @param iconShowImagefalse the icon show imagefalse
     * @return boolean boolean
     */
    public static boolean showHideIconOnTouch(@NonNull MotionEvent motionEvent, @NonNull EditText editText, boolean isIconShow, @NonNull Typeface typeface, int iconShowImage, int iconShowImagefalse) {
        final int DRAWABLE_RIGHT = 2;
        if (motionEvent.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
            if (!isIconShow) {
                isIconShow = true;
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setSelection(editText.getText().length());
                editText.setTypeface(typeface);
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconShowImagefalse, 0);
            } else {
                isIconShow = false;
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setSelection(editText.getText().length());
                editText.setTypeface(typeface);
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconShowImage, 0);
            }
        }
        return isIconShow;
    }

    /**
     * Show log. You can disable Logs by setting "isShowLog" value to False
     *
     * @param tagName the tag name
     * @param message the message
     */
    public static void showLog(String tagName, String message) {
        if (isShowLog && !TextUtils.isEmpty(message)) {
            int maxLogSize = 1000;
            for (int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                Log.v(tagName, message.substring(start, end));
            }
        }

    }

    /**
     * Show log. You can disable Logs by setting "isShowLog" value to False
     *
     * @param message the message
     */
    public static void showLog(String message) {
        if (isShowLog && !TextUtils.isEmpty(message)) {
            int maxLogSize = 1000;
            for (int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                Log.v("", message.substring(start, end));
            }
        }

    }

    /**
     * Show exception.You can disable Logs by setting "isShowLog" value to False
     * <p>
     * * @param tagName     the tag name
     *
     * @param tagName the tag name
     * @param t       the t
     */
    public static void showException(String tagName, Throwable t) {
        if (isShowLog) {
            Log.e(tagName, Log.getStackTraceString(t));
        }
    }

    /**
     * Show exception.You can disable Logs by setting "isShowLog" value to False
     * <p>
     * * @param tagName     the tag name
     *
     * @param t the t
     */
    public static void showException(Throwable t) {
        if (isShowLog) {
            Log.e("", Log.getStackTraceString(t));
        }
    }

    /**
     * Gets random color.
     *
     * @return the random color
     */
    public static int getRandomColor() {
        Random rand = new Random();
        return Color.argb(100, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    /**
     * Sets list view height based on children.
     *
     * @param listView the list view
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


//    public static void openPlayStoreForApp(@NonNull Context context, String playStoreUrl) {
//        final String appPackageName = context.getPackageName();
//        try {
//            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ValidationUtils.getCheckedString(playStoreUrl))));
//            // Uri.parse(context.getResources().getString(R.string.app_market_link) + appPackageName)));
//        } catch (android.content.ActivityNotFoundException e) {
//            context.startActivity(new Intent(Intent.ACTION_VIEW,
//                    Uri.parse(context
//                            .getResources()
//                            .getString(R.string.app_google_play_store_link) + appPackageName)));
//        }
//    }

    public static boolean listNotNull(List<?> list) {
        if (list != null && !list.isEmpty()) {
            list.removeAll(Collections.singleton(null));
            return true;
        }

        return false;
    }


    public static  List<List> getSubDividedList(List list, int subCollectionSize) {
        List<List> subCustomList = new ArrayList();
        for (int i = 0; i < list.size() / subCollectionSize + 1; i++) {
            int maxLength;
            if (i * subCollectionSize + subCollectionSize > list.size()) {
                maxLength = list.size();
            } else {
                maxLength = i * subCollectionSize + subCollectionSize;
            }
            List sublist = new ArrayList();
            for (int j = i * subCollectionSize; j < maxLength; j++) {

                sublist.add(list.get(j));
            }
            subCustomList.add(sublist);
        }

        return subCustomList;
    }


    public static void scrollListViewToBottom(final ListView myListView) {
        myListView.post(new Runnable() {
            @Override
            public void run() {
                myListView.setSelection(myListView.getCount());
            }
        });
    }

    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

   /* public static Drawable drawableFromUrl(Context context, String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(context.getResources(), x);
    }

    public static void loadDrawableFromUrl(final AbstractAppCompactActivity mContext, final String url, final TextView tv) {
        if (NetworkUtility.isNetworkAvailable(mContext)) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        extraLayoutDrawable = null;// = drawableFromUrl(mContext, url);
                        if (null != extraLayoutDrawable) {
                            mContext.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv.setCompoundDrawablesWithIntrinsicBounds(extraLayoutDrawable, null, null, null);
                                    tv.invalidate();
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }*/

    /**
     *
     * @param date
     * @return
     */
    public static long getCurrentMiliSecond(String date){
        long timeInMilliseconds = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            Date mDate = sdf.parse(date);
            timeInMilliseconds = mDate.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static int convertDPtoPixels(Context context, int dp) {
        Resources resource = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics());
    }

    /**
     * Sets full screen dialog.
     *
     * @param context the context
     * @param dialog  the dialog
     */
    public static void setFullScreenDialog(Context context, Dialog dialog) {
        WindowManager manager = (WindowManager) context.getSystemService(Activity.WINDOW_SERVICE);
        int width, height;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            width = manager.getDefaultDisplay().getWidth();
            height = manager.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            manager.getDefaultDisplay().getSize(point);
            width = point.x;
            height = point.y;
        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        dialog.getWindow().setAttributes(lp);

    }


    public static Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    /**
     * The enum Drawable click.
     */
    public enum DrawableClick {
        /**
         * Drawable left drawable click.
         */
        DRAWABLE_LEFT,
        /**
         * Drawable top drawable click.
         */
        DRAWABLE_TOP,
        /**
         * Drawable right drawable click.
         */
        DRAWABLE_RIGHT,
        /**
         * Drawable bottom drawable click.
         */
        DRAWABLE_BOTTOM
    }

}