package il.co.nolife.fist;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class AnimationHelper implements AnimatorListener {

	private View view;
	private ITask task;
	private ViewPropertyAnimator animator;
	private DraggableListView dragger;
	private ItemRemover remover;
	private boolean stage2;
	
	public AnimationHelper(View v, ITask t, DraggableListView d, ItemRemover r) {
		view = v;
		task = t;
		dragger = d;
		remover = r;
		stage2 = false;
		
		Log.i(getClass().toString(), view.toString());
		Log.i(getClass().toString(), task.toString());
		Log.i(getClass().toString(), dragger.toString());
		Log.i(getClass().toString(), remover.toString());
		
	}
	
	public AnimationHelper(View v, ITask t, DraggableListView d) {
		view = v;
		task = t;
		dragger = d;
		remover = null;
		stage2 = false;
		
		Log.i(getClass().toString(), view.toString());
		Log.i(getClass().toString(), task.toString());
		Log.i(getClass().toString(), dragger.toString());
		
	}
	
	public void AnimateInDirection(float factor) {
		animator = view.animate();
		animator.setInterpolator(new DecelerateInterpolator(0.8f));
		animator.xBy(factor * factor * (factor > 0 ? 1 : -1));
		animator.setListener(this);
	}
	
	@Override
	public void onAnimationStart(Animator animation) {
		animation.setStartDelay(0);
	}

	@Override
	public void onAnimationEnd(Animator animation) {

		if(stage2) {
			dragger.RemoveFromList(this);
			return;
		}
		
		if(remover != null) {
			if((view.getX() + view.getWidth()) < (dragger.getWidth() / 6)) {
				remover.RemoveTaskItem(task);
				dragger.RemoveFromList(this);
				return;
			} else if(view.getX() > (dragger.getWidth() / 1.333333)) {
				remover.RemoveTaskItem(task);
				dragger.RemoveFromList(this);
				return;
			}
		}
			
		animator.setInterpolator(new AccelerateInterpolator(0.5f));
		animator.translationX(0);
		animator.start();
		animator.setListener(null);
		stage2 = true;
		
	}

	@Override
	public void onAnimationCancel(Animator animation) {
		
	}

	@Override
	public void onAnimationRepeat(Animator animation) {
		
	}

}
