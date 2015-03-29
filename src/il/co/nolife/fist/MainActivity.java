package il.co.nolife.fist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
import android.database.sqlite.*;

public class MainActivity extends Activity implements ItemRemover {
	
	private TaskDataHandler doa;
	private TaskListAdapter listAdapter;
	private List<ITask> filteredList;
	private DraggableListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		doa = new TaskDataHandler(this);
		filteredList = doa.GetList(false);
		lv = (DraggableListView) findViewById(R.id.task_list);
		listAdapter = new TaskListAdapter(this, filteredList);
		lv.setAdapter(listAdapter);
		lv.SetRemover(this);
		
		Button newTaskButton = (Button) findViewById(R.id.plus_button);
		newTaskButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PlusButtonClicked();
			}
			
		});
		
		Button completeButton = (Button) findViewById(R.id.complete_button);
		completeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CompleteButtonClicked();
			}
		});
		
//		Button debug = (Button) findViewById(R.id.Button01);
//		debug.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Log.i("HERE", "HERE");
//				sendBroadcast(new Intent(getApplicationContext(), RebootAlarmSetter.class));
//			}
//		});
		
	}
	
	private void PlusButtonClicked() {
		
		Intent chooseType = new Intent(this, TypeChooser.class);
		startActivityForResult(chooseType, 0);
		
	}
	
	private void CompleteButtonClicked() {
		
		Intent intent = new Intent(this, CompletedActivity.class);
		startActivityForResult(intent, 0);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		UpdateList();
	}
	
	void UpdateList() {
		Log.i(getClass().toString(), "before");
		lv.postInvalidate();
		listAdapter.notifyDataSetChanged();
		for(int i = 0; i < filteredList.size(); i++) {
			Log.i(getClass().toString(), " " + filteredList.get(i).getId());
		}
	}

	@Override
	public void RemoveTaskItem(ITask t) {
		doa.MoveTaskToComplete(t);
		UpdateList();
	}
	
}
