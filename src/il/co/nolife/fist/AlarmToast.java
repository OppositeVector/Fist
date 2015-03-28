package il.co.nolife.fist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmToast extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.i(getClass().toString(), "Toasting");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alarm_layout);
		Log.i("HERE", "HERE");
		
		ImageView typeImage = (ImageView) findViewById(R.id.alarm_image);
		Log.i("HERE", "HERE");
		TextView desc = (TextView) findViewById(R.id.alarm_desc);
		Log.i("HERE", "HERE");
		TextView time = (TextView) findViewById(R.id.alarm_time);
		Log.i("HERE", "HERE");
		
		TaskDataHandler dataHandler = new TaskDataHandler(this);
		Log.i("HERE", "HERE");
		
		ITask task = dataHandler.GetTask(getIntent().getIntExtra("id", -1));
		Log.i("HERE", "HERE");
		
		typeImage.setImageResource(task.getType().GetDrawable());
		Log.i("HERE", "HERE");
		desc.setText(task.getDescription());
		Log.i("HERE", "HERE");
		time.setText(task.getDate().toString());
		Log.i("HERE", "HERE");
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if(event.getAction() == MotionEvent.ACTION_UP) {
			
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
			
		}
		return true;
		
	}
	
}
