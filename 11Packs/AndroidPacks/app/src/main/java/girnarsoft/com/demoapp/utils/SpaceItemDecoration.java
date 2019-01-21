package girnarsoft.com.demoapp.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private Spacing spacingFrom;


    public SpaceItemDecoration(int space, Spacing spacingFrom) {
        this.space = space;
        this.spacingFrom = spacingFrom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        setItemOffSetSpace(outRect);
    }

    private void setItemOffSetSpace(Rect outRect) {


        switch (spacingFrom) {
            case SPACE_LEFT:
                outRect.left = space;
                break;
            case SPACE_RIGHT:
                outRect.right = space;
                break;
            case SPACE_BOTTOM:
                outRect.bottom = space;
                break;
            case SPACE_TOP:
                outRect.top = space;
                break;
            case SPACE_LEFT_RIGHT: {
                outRect.left = space;
                outRect.right = space;
                break;
            }
            case SPACE_TOP_BOTTOM: {
                outRect.top = space;
                outRect.bottom = space;
                break;
            }
            default: {
                outRect.top = space;
                outRect.left = space;
                outRect.bottom = space;
                outRect.right = space;
                break;
            }

        }
    }

    public enum Spacing {
        SPACE_LEFT,
        SPACE_RIGHT,
        SPACE_BOTTOM,
        SPACE_TOP,
        SPACE_LEFT_RIGHT,
        SPACE_TOP_BOTTOM,
        DEFAULT
    }
}