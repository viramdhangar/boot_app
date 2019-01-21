package girnarsoft.com.demoapp.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * TabLayout for use with the default Calligraphy font
 */
public class CalligraphyDefaultTabLayout extends TabLayout {

    Typeface calligraphyTypeface;

    public CalligraphyDefaultTabLayout(Context context) {
        super(context);
        initCalligraphyTypeface();
    }

    public CalligraphyDefaultTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCalligraphyTypeface();
    }

    public CalligraphyDefaultTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCalligraphyTypeface();
    }

    @Override
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        super.addTab(tab, position, setSelected);
        if (calligraphyTypeface != null) {
            ViewGroup mainView = (ViewGroup) getChildAt(0);
            ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());
            int tabChildCount = tabView.getChildCount();
            for (int i = 0; i < tabChildCount; i++) {
                View tabViewChild = tabView.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(calligraphyTypeface);
                }
            }
        }
    }

    private void initCalligraphyTypeface() {
        String fontPath = CalligraphyConfig.get().getFontPath();
        if (fontPath != null) {
            calligraphyTypeface = TypefaceUtils.load(getResources().getAssets(), fontPath);
        }
    }
}