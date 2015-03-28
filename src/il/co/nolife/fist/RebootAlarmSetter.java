package il.co.nolife.fist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RebootAlarmSetter extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		
		TaskDataHandler dataHandler = new TaskDataHandler(context);
		List<ComboData> tasks = dataHandler.GetAlarmData();
		Iterator<ComboData> ti = tasks.iterator();
		
		ComboData current;
		Intent aIntent;
		PendingIntent pIntent = null;
		Random r = new Random(new Date().getTime());
		while(ti.hasNext()) {
			
			current = ti.next();
			if(current.isNotify()) {
				
				if(current.getDate().getTime() > System.currentTimeMillis()) {
					
					aIntent = new Intent(context, AlarmWaker.class);
					aIntent.putExtra("id", current.getId());
					if(current.getAlarmId() > -1) {
						
						pIntent = PendingIntent.getActivity(context, current.getAlarmId(), aIntent, PendingIntent.FLAG_NO_CREATE);
						if(pIntent == null) {
							pIntent = PendingIntent.getActivity(context, current.getAlarmId(), aIntent, PendingIntent.FLAG_CANCEL_CURRENT);
							am.set(AlarmManager.RTC_WAKEUP, current.getDate().getTime(), pIntent);
						}
						
					} else {
						
						do {
							current.setAlarmId(r.nextInt(200000000));
							pIntent = PendingIntent.getActivity(context, current.getAlarmId(), aIntent, PendingIntent.FLAG_NO_CREATE);
						} while(pIntent != null);
						pIntent = PendingIntent.getActivity(context, current.getAlarmId(), aIntent, PendingIntent.FLAG_CANCEL_CURRENT);
						am.set(AlarmManager.RTC_WAKEUP, current.getDate().getTime(), pIntent);
						
					}
				
				}
				
			}
			
		}
		
	}
	
}