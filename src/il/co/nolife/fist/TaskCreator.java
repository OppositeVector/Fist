package il.co.nolife.fist;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

public class TaskCreator extends Activity {

	private TextView description;
	private DatePicker date;
	private TimePicker time;
	private Switch notify;
	private TaskDataHandler dataHandler;
	private TaskType selectedType;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_task_layout);
		dataHandler = new TaskDataHandler(this);
		selectedType = TaskType.values()[getIntent().getIntExtra(TypeChooser.EXTRA_NAME, -1)];
		
		Button btn = (Button) findViewById(R.id.doneBtn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DoneClicked();
			}
			
		});
		
		btn = (Button) findViewById(R.id.location);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LocationClicked();
			}
			
		});
		
		description = (TextView) findViewById(R.id.description);
		date = (DatePicker) findViewById(R.id.date);
		time = (TimePicker) findViewById(R.id.time);
		notify = (Switch) findViewById(R.id.notify);
		
	}
	
	private void DoneClicked() {
		
		String desc = description.getText().toString();
		Date d = GetDateTimeFromPickers(date, time);
		boolean n = notify.isChecked();
		
		IconedTask task = new IconedTask(desc, d, selectedType, 0, 0, n);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		
		int id = dataHandler.CreateTask(task);
		
		if(n) {
			
			Random r = new Random(new Date().getTime());
			Intent intent = new Intent(this, AlarmWaker.class);
			intent.putExtra("id", id);
			PendingIntent pIntent;
			
			do {
				
				int rand = r.nextInt(2000000000);
				task.setAlarmId(rand);
				pIntent = PendingIntent.getBroadcast(this, task.getAlarmId(), intent, PendingIntent.FLAG_NO_CREATE);
				
			} while(pIntent != null); 
			
			pIntent = PendingIntent.getBroadcast(this, task.getAlarmId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
			Log.i("TESTING", "Alarm created!");
			am.set(AlarmManager.RTC_WAKEUP, task.getDate().getTime(), pIntent);
			
		}
		
		setResult(RESULT_OK);
		finish();
		
	}
	
	private void LocationClicked() {
		
		PlayServicesHandler handler = new PlayServicesHandler(this);
		
		if(handler.isReady()) {
			
			int PLACE_PICKER_REQUEST = 1;
			try {
				startActivityForResult(handler.GetPlacePicker().build(this), PLACE_PICKER_REQUEST);
			} catch (GooglePlayServicesRepairableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GooglePlayServicesNotAvailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	private Date GetDateTimeFromPickers(DatePicker d, TimePicker t) {
	
		
		 String year    = Integer.toString(d.getYear()) ;
		 String month;
		 int monthInt = d.getMonth() + 1;
		 if(monthInt < 10) {
			 month = "0" + Integer.toString(monthInt);
		 } else {
			 month = Integer.toString(monthInt);
		 }
		 
		 String day;
		 int dayInt = d.getDayOfMonth();
		 if(dayInt < 10) {
			 day = "0" + Integer.toString(dayInt);
		 } else {
			 day = Integer.toString(dayInt);
		 }
		
		 String hour;
		 int hourInt = t.getCurrentHour();
		 if(hourInt < 10) {
			 hour = "0" + Integer.toString(hourInt);
		 } else {
			 hour = Integer.toString(hourInt);
		 }
		 
		 String minutes;
		 int minutesInt = t.getCurrentMinute();
		 if(minutesInt < 10) {
			 minutes = "0" + Integer.toString(minutesInt);
		 } else {
			 minutes = Integer.toString(minutesInt);
		 }
		 
		 try {
			return TaskDataHandler.dateFormat.parse(year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":00");
		 } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 
		 return null;
		 
	 }
	
}
