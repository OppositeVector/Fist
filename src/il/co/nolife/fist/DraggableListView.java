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
	float delta1;
	float delta2;
	float delta3;
	float currentDelta;
	float offset;
	float originalPress;
	float originalX;
	
	List<AnimationHelper> helpers;
	
	private ItemRemover remover;
	
	public DraggableListView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		dropListeners = new ArrayList<OnDropListener>();
		itemVelocity = 0;
		helpers = new ArrayList<AnimationHelper>();
		remover = null;
		
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		switch(ev.getAction()) {
		
		case MotionEvent.ACTION_DOWN:
			offset = 0;
			originalPress = ev.getX();
			previousX = originalPress;
			currentDelta = 0;
			delta1 = 0;
			delta2 = 0;
			delta3 = 0;
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
				currentDelta = ((ev.getX() - previousX) + delta1 + delta2 + delta3) / 4;
				delta3 = delta2;
				delta2 = delta1;
				delta1 = currentDelta;
				previousX = ev.getX();
			}
			break;
			
		case MotionEvent.ACTION_UP:
			if(currentlyPressed != null) {
				
				TaskListAdapter.ViewHolder h = (TaskListAdapter.ViewHolder)currentlyPressed.getTag();
				Log.i(getClass().toString(), ((ITask) getAdapter().getItem(h.taskListIndex)).toString());
				AnimationHelper helper;
				if(remover != null) {
					helper = new AnimationHelper(currentlyPressed, (ITask) getAdapter().getItem(h.taskListIndex), this, remover);
				} else {
					helper = new AnimationHelper(currentlyPressed, (ITask) getAdapter().getItem(h.taskListIndex), this);
				}
				helpers.add(helper);
				helper.AnimateInDirection(currentDelta);
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
	
	public void RemoveFromList(AnimationHelper h) {
		helpers.remove(h);
	}
	
	public void SetRemover(ItemRemover r) {
		remover = r;
	}
	
}
