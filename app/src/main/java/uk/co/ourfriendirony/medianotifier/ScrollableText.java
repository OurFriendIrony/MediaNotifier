package uk.co.ourfriendirony.medianotifier;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class ScrollableText implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean isLarger;
        isLarger = ((TextView) v).getLineCount() * ((TextView) v).getLineHeight() > v.getHeight();
        if (event.getAction() == MotionEvent.ACTION_MOVE && isLarger) {
            v.getParent().requestDisallowInterceptTouchEvent(true);
        } else {
            v.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return false;
    }
}
