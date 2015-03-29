package il.co.nolife.fist;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmWaker extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(getClass().toString(), "Recieved !");
		Intent toast = new Intent(context, AlarmToast.class);
		Log.i(getClass().toString(), "Created Intent !");
		toast.putExtra("id", intent.getIntExtra("id", -1));
		Log.i(getClass().toString(), "Put Extra !");
		toast.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(toast);
	}
	
}
