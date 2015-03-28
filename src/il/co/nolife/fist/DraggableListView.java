package il.co.nolife.fist;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;

public class DraggableListView extends ListView {

	public static View currentlyPressed;
	public static float currentlyPressedOriginalX;
	
	static {
		currentlyPressed = null;
	}
	
	List<OnDropListener> dropListeners;
	public enum DropType { InsideView, OutsideView, OutsideWindow }
	
	ViewPropertyAnimator currentlyAnimating;
	float itemVelocity;
	float previousX;
	float currentDelta;
	float offset;
	float originalPress;
	float originalX;
	
	public DraggableListView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		dropListeners = new ArrayList<OnDropListener>();
		itemVelocity = 0;
		
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		switch(ev.getAction()) {
		
		case MotionEvent.ACTION_DOWN:
			offset = 0;
			originalPress = ev.getX();
			previousX = originalPress;
			currentDelta = 0;
			break;
		
		}
		
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		switch(ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			if(currentlyPressed != null) {
				offset = ev.getX() - originalPress;
				currentlyPressed.setX(currentlyPressedOriginalX + offset);
				currentDelta = ((ev.getX() - previousX) + currentDelta) / 2;
				previousX = ev.getX();
			}
			break;
			
		case MotionEvent.ACTION_UP:
			if(currentlyPressed != null) {
				currentlyAnimating = currentlyPressed.animate();
				currentlyAnimating.setInterpolator(new DecelerateInterpolator(0.8f));
				currentlyAnimating.xBy(currentDelta * 10);
				currentlyAnimating.setListener(new AnimatorListener() {
					
					
					@Override
					public void onAnimationEnd(Animator animation) {
						currentlyAnimating.setInterpolator(new AccelerateInterpolator(0.5f));
						currentlyAnimating.translationX(0);
						currentlyAnimating.start();
						currentlyAnimating.setListener(null);
					}

					@Override
					public void onAnimationStart(Animator animation) {
						animation.setStartDelay(0);
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onAnimationRepeat(Animator animation) {
						// TODO Auto-generated method stub
						
					}
					
				});
				previousX = 0;
			}
			// itemVelocity = ev.getX() - previousX;
			break;
		}
		
		return super.onTouchEvent(ev);
	}

	public void AddOnDropListener(OnDropListener listener) {
		
	}
	
	@Override
	public void setSelection(int position) {
		Log.i(getClass().toString(), "Selected Item " + position);
		super.setSelection(position);
	}

	public interface OnDropListener {
		// public void ItemDropped(View v, );
	}
	
}
