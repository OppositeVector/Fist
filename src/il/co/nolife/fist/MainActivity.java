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

public class MainActivity extends Activity {
	
	private TaskDataHandler doa;
	private TaskListAdapter listAdapter;
	private List<ITask> filteredList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		doa = new TaskDataHandler(this);
		filteredList = new ArrayList<ITask>();
		UpdateList();
		ListView lv = (ListView) findViewById(R.id.task_list);
		listAdapter = new TaskListAdapter(this, filteredList);
		lv.setAdapter(listAdapter);
		
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
		doa.GetTasks(filteredList);
	}
	
}
