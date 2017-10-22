package dy.view.tagflowlayout;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

public class TagView extends FrameLayout implements Checkable {
    private boolean isChecked;
    private static final int[] CHECK_STATE = new int[]{16842912};

    public TagView(Context context) {
        super(context);
    }

    public View getTagView() {
        return this.getChildAt(0);
    }

    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if(this.isChecked()) {
            mergeDrawableStates(states, CHECK_STATE);
        }

        return states;
    }

    public void setChecked(boolean checked) {
    }

    public boolean isChecked() {
        return false;
    }

    public void toggle() {
    }
}
