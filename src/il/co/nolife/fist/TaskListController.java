package il.co.nolife.fist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ViewAnimator;

public class TaskListController implements OnClickListener {

	private List<ITask> tasks;
	private View myView;
	private ListView tasksView;
	private LayoutInflater inflater;
	private TaskListAdapter listAdapter;
	private ViewAnimator parent;
	
	public TaskListController(Context context, ViewGroup rootView) {
		
		parent = (ViewAnimator) rootView;
		tasks = new ArrayList<ITask>();
		inflater = (LayoutInflater) context.getSystemService("LayoutInflater");
		myView = inflater.inflate(R.layout.task_list_view, rootView);
		tasksView = (ListView) myView.findViewById(R.id.taskList);
		listAdapter = new TaskListAdapter(context, tasks);
		tasksView.setAdapter(listAdapter);
		Button addTaskButton = (Button) myView.findViewById(R.id.addTaskBtn);
		addTaskButton.setOnClickListener(this);

	}
	
	public void AddTask(ITask newTask) {
		tasks.add(newTask);
	}
	
	public List<ITask> getTaskList() {
		return tasks;
	}
	
	@Override
	public void onClick(View v) {
	}
	
}
