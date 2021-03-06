package il.co.nolife.fist;

import il.co.nolife.fist.MainActivity.TrackerName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

public class TaskDataHandler extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "fistDatabase.db";
	public static final int DATABASE_VERSION = 5;
	
	public static final String TASKS_TABLE = "tasks";
	public static final String COMPLETED_TABLE = "complete_task";
	public static final String ID = "tasks_id";
	public static final String DESCRIPTION = "task_description";
	public static final String TYPE = "icon_type";
	public static final String DATE = "date";
	public static final String LONGITUDE = "loc_long";
	public static final String LATITUDE = "loc_lat";
	public static final String NOTIFY = "notify";
	public static final String ALARM_ID = "alarm_id";
	public static final String GEO_ID = "geo_id";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
	
	private static SQLiteDatabase db;
	private static int counter = 0;
	private static List<ITask> taskList;
	private static List<ITask> completeList;
	
	private Context context;
	
	static {
		
		db = null;
		counter = 0;
		taskList = null;
		completeList = null;
		
	}

	public TaskDataHandler(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		if(db == null) {
			db = getWritableDatabase();
		}
		
		if(taskList == null) {
			taskList = new ArrayList<ITask>();
			InternalGetTasks(taskList, TASKS_TABLE);
			completeList = new ArrayList<ITask>();
			InternalGetTasks(completeList, COMPLETED_TABLE);
		}
		
		this.context = context;
		
		++counter;
		
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		database.execSQL("create table "
				+ TASKS_TABLE + "(" + ID + " integer primary key autoincrement, " 
				+ TYPE + " integer, " 
				+ DESCRIPTION + " ntext not null, "
				+ DATE + " datetime, " 
				+ LONGITUDE + " double, " 
				+ LATITUDE + " double, "
				+ NOTIFY + " boolean, "
				+ ALARM_ID + " integer default -1, "
				+ GEO_ID + " text default '0');");
		
		database.execSQL("create table "
				+ COMPLETED_TABLE + "(" + ID + " integer primary key autoincrement, " 
				+ TYPE + " integer, " 
				+ DESCRIPTION + " ntext not null, "
				+ DATE + " datetime, " 
				+ LONGITUDE + " double, " 
				+ LATITUDE + " double, "
				+ NOTIFY + " boolean, "
				+ ALARM_ID + " integer default -1, "
				+ GEO_ID + " text default '0');");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		
		if(oldVersion < newVersion) {
			database.execSQL("drop table if exists " + TASKS_TABLE);
			database.execSQL("drop table if exists " + COMPLETED_TABLE);
		    onCreate(database);
		}
		
	}

	public int CreateTask(IconedTask task) {
		
		ContentValues values = new ContentValues();
		
	    values.put(DESCRIPTION, task.getDescription());
	    values.put(TYPE, task.getType().ordinal());
	    values.put(DATE, dateFormat.format(task.getDate()));
	    values.put(LONGITUDE, task.getLongitude());
	    values.put(LATITUDE, task.getLatitude());
	    values.put(NOTIFY, task.getNotify());
	    values.put(ALARM_ID, task.getAlarmId());
	    values.put(GEO_ID, task.getGeofence());
	    
	    taskList.add(task);
	    
	    Tracker t = MainActivity.getTracker(TrackerName.APP_TRACKER, context);
		t.send(new HitBuilders.EventBuilder()
				 .setCategory("Tasks")
				 .setAction("Created")
				 .setLabel(task.getType().toString())
				 .build());
	    
	    return (int) db.insert(TASKS_TABLE, null, values);
	    
	}
	
	public void MoveTaskToComplete(ITask t) {
		
		IconedTask task = (IconedTask) t;
		
		db.execSQL("delete from " + TASKS_TABLE + " where " + ID + "=" + task.getId() + ";");
		
		ContentValues values = new ContentValues();
		
	    values.put(DESCRIPTION, task.getDescription());
	    values.put(TYPE, task.getType().ordinal());
	    values.put(DATE, dateFormat.format(task.getDate()));
	    values.put(LONGITUDE, task.getLongitude());
	    values.put(LATITUDE, task.getLatitude());
	    values.put(NOTIFY, task.getNotify());
	    values.put(ALARM_ID, task.getAlarmId());
	    values.put(GEO_ID, task.getGeofence());
		
	    db.insert(COMPLETED_TABLE, null, values);
	    
	    taskList.remove(task);
	    completeList.add(task);
	    
	    Tracker tr = MainActivity.getTracker(TrackerName.APP_TRACKER, context);
	    tr.send(new HitBuilders.EventBuilder()
				 .setCategory("Tasks")
				 .setAction("Moved to completed")
				 .setLabel(task.getType().toString())
				 .build());
	    
	}
	
	public void DeleteTask(ITask t) {
		
		db.execSQL("delete from " + COMPLETED_TABLE + " where " + ID + "=" + t.getId() + ";");
		
		completeList.remove(t);
		
		Tracker tr = MainActivity.getTracker(TrackerName.APP_TRACKER, context);
		tr.send(new HitBuilders.EventBuilder()
		 .setCategory("Tasks")
		 .setAction("Deleted")
		 .setLabel(t.getType().toString())
		 .build());
		
	}
	
	public ITask GetTask(int id) {
		
		Cursor tasksData = db.rawQuery("select * from " + TASKS_TABLE + " where " + ID + "='" + id + "'", null);
		tasksData.moveToFirst();
		
		int idIndex = tasksData.getColumnIndex(ID);
		int desriptionIndex = tasksData.getColumnIndex(DESCRIPTION);
		int typeIndex = tasksData.getColumnIndex(TYPE);
		int dateIndex = tasksData.getColumnIndex(DATE);
		int longitudeIndex = tasksData.getColumnIndex(LONGITUDE);
		int latitudeIndex = tasksData.getColumnIndex(LATITUDE);
		int notifyIndex = tasksData.getColumnIndex(NOTIFY);
		
		String desc = tasksData.getString(desriptionIndex);
		Date date;
		try {
			date = dateFormat.parse(tasksData.getString(dateIndex));
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
		}
		TaskType type = TaskType.values()[tasksData.getInt(typeIndex)];
		long longitude = tasksData.getLong(longitudeIndex);
		long latitude = tasksData.getLong(latitudeIndex);
		int notify = tasksData.getInt(notifyIndex);
		
		return new IconedTask(tasksData.getString(desriptionIndex), date, type, longitude, latitude, notify);
		
	}
	
	private void InternalGetTasks(List<ITask> taskList, String table) {
	
		if(taskList == null) {
			
			Log.e(getClass().toString(), "taskList given is null");
			return;
			
		}
		
		Cursor tasksData = db.rawQuery("select * from " + table, null);
		
		Log.i(getClass().toString(), DatabaseUtils.dumpCursorToString(tasksData));
		
		int idIndex = tasksData.getColumnIndex(ID);
		int desriptionIndex = tasksData.getColumnIndex(DESCRIPTION);
		int typeIndex = tasksData.getColumnIndex(TYPE);
		int dateIndex = tasksData.getColumnIndex(DATE);
		int longitudeIndex = tasksData.getColumnIndex(LONGITUDE);
		int latitudeIndex = tasksData.getColumnIndex(LATITUDE);
		int notifyIndex = tasksData.getColumnIndex(NOTIFY);
		
		int id;
		String desc;
		Date date;
		TaskType type;
		long longitude;
		long latitude;
		int notify;
		
		Iterator<ITask> listIterator = taskList.iterator();
		int i = 0;
		
		if(tasksData.moveToFirst()) {
			do {
				
				id = tasksData.getInt(idIndex);
				desc = tasksData.getString(desriptionIndex);
				try {
					date = dateFormat.parse(tasksData.getString(dateIndex));
				} catch (ParseException e) {
					e.printStackTrace();
					date = new Date();
				}
				type = TaskType.values()[tasksData.getInt(typeIndex)];
				longitude = tasksData.getLong(longitudeIndex);
				latitude = tasksData.getLong(latitudeIndex);
				notify = tasksData.getInt(notifyIndex);
				
				if(i < taskList.size()) {
					
					ITask current = taskList.get(i);
					current.setId(id);
					current.setDescription(desc);
					current.setDate(date);
					current.setType(type);
					current.setLongitude(longitude);
					current.setLatitude(latitude);
					current.setNotify(notify);
					i++;
					
				} else {
					taskList.add(new IconedTask(id, desc, date, type, longitude, latitude, notify));
					i++;
				}
			} while(tasksData.moveToNext());
			
			while(i < taskList.size()) {
				taskList.remove(i);
			}
			
		}
		
	}
	
//	public void GetCompleteTasks(List<ITask> taskList) {
//		
//		if(taskList == null) {
//			
//			Log.e(getClass().toString(), "taskList given is null");
//			return;
//			
//		}
//		
//		Cursor tasksData = db.rawQuery("select * from " + COMPLETED_TABLE, null);
//		
//		int idIndex = tasksData.getColumnIndex(ID);
//		int desriptionIndex = tasksData.getColumnIndex(DESCRIPTION);
//		int typeIndex = tasksData.getColumnIndex(TYPE);
//		int dateIndex = tasksData.getColumnIndex(DATE);
//		int longitudeIndex = tasksData.getColumnIndex(LONGITUDE);
//		int latitudeIndex = tasksData.getColumnIndex(LATITUDE);
//		int notifyIndex = tasksData.getColumnIndex(NOTIFY);
//		
//		int id;
//		String desc;
//		Date date;
//		TaskType type;
//		long longitude;
//		long latitude;
//		int notify;
//		
//		Iterator<ITask> listIterator = taskList.iterator();
//		
//		if(tasksData.moveToFirst()) {
//			do {
//				
//				desc = tasksData.getString(desriptionIndex);
//				try {
//					date = dateFormat.parse(tasksData.getString(dateIndex));
//				} catch (ParseException e) {
//					e.printStackTrace();
//					date = new Date();
//				}
//				type = TaskType.values()[tasksData.getInt(typeIndex)];
//				longitude = tasksData.getLong(longitudeIndex);
//				latitude = tasksData.getLong(latitudeIndex);
//				notify = tasksData.getInt(notifyIndex);
//				
//				if(listIterator.hasNext()) {
//					
//					ITask current = listIterator.next();
//					current.setDescription(desc);
//					current.setDate(date);
//					current.setType(type);
//					current.setLongitude(longitude);
//					current.setLatitude(latitude);
//					current.setNotify(notify);
//					
//				} else {
//					
//					taskList.add(new IconedTask(desc, date, type, longitude, latitude, notify));
//				}
//			} while(tasksData.moveToNext());
//			
//		}
//		
//	}
	
	public List<ComboData> GetAlarmData() {
		
		List<ComboData> retVal = new ArrayList<ComboData>();
		
		Cursor tasksData = db.rawQuery("select * from " + TASKS_TABLE, null);
		
		// Log.i("SQL DUMP: ", DatabaseUtils.dumpCursorToString(tasksData));
		
		int idIndex = tasksData.getColumnIndex(ID);
		// int desriptionIndex = tasksData.getColumnIndex(DESCRIPTION);
		// int typeIndex = tasksData.getColumnIndex(TYPE);
		int dateIndex = tasksData.getColumnIndex(DATE);
		int longitudeIndex = tasksData.getColumnIndex(LONGITUDE);
		int latitudeIndex = tasksData.getColumnIndex(LATITUDE);
		int notifyIndex = tasksData.getColumnIndex(NOTIFY);
		int alarmIndex = tasksData.getColumnIndex(ALARM_ID);
		int geoIndex = tasksData.getColumnIndex(GEO_ID);
		
		int id;
		// String desc;
		//TaskType type;
		Date date;
		long longitude;
		long latitude;
		int notify;
		int alarm;
		String geofence;
		
		if(tasksData.moveToFirst()) {
			do {
				
				// desc = tasksData.getString(desriptionIndex);
				// type = TaskType.values()[tasksData.getInt(typeIndex)];
				id = tasksData.getInt(idIndex);
				try {
					date = dateFormat.parse(tasksData.getString(dateIndex));
				} catch (ParseException e) {
					e.printStackTrace();
					date = new Date();
				}
				longitude = tasksData.getLong(longitudeIndex);
				latitude = tasksData.getLong(latitudeIndex);
				notify = tasksData.getInt(notifyIndex);
				alarm = tasksData.getInt(alarmIndex);
				
				ComboData d = new ComboData(id, date, notify, longitude, latitude);
				
				if(notify != 0) {
					if(alarm > -1) {
						d.setAlarmId(alarm);
					}
				}
				
				retVal.add(d);
				
				
			} while(tasksData.moveToNext());
			
		}
		
		return retVal;
		
	}
	
	public List<ITask> GetList(boolean complete) {
		if(complete) {
			return completeList;
		} else {
			return taskList;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		--counter;
		if(counter < 1) {
			
			db.close();
			db = null;
			counter = 0;
			taskList = null;
			completeList = null;
			
		}
	}
	
}
