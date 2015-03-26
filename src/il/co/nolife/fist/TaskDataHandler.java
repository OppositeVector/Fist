package il.co.nolife.fist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
	public static final int DATABASE_VERSION = 2;
	
	public static final String TASKS_TABLE = "tasks";
	public static final String ID = "tasks_id";
	public static final String DESCRIPTION = "task_description";
	public static final String TYPE = "icon_type";
	public static final String DATE = "date";
	public static final String LONGITUDE = "loc_long";
	public static final String LATITUDE = "loc_lat";
	public static final String NOTIFY = "notify";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
	
	private SQLiteDatabase db;

	public TaskDataHandler(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.i("TESTING", "TaskDataHandler after super class constructor");
		db = getWritableDatabase();
		
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
					+ NOTIFY + " boolean);");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		
		if(oldVersion < newVersion) {
			database.execSQL("drop table if exists " + TASKS_TABLE);
		    onCreate(database);
		}
		
	}

	public void CreateTask(ITask task) {
		
		ContentValues values = new ContentValues();
		
	    values.put(DESCRIPTION, task.GetDescription());
	    values.put(TYPE, task.GetType().ordinal());
	    values.put(DATE, dateFormat.format(task.GetDate()));
	    values.put(LONGITUDE, task.GetLongitude());
	    values.put(LATITUDE, task.GetLatitude());
	    values.put(NOTIFY, task.GetNotify());
	    
	    db.insert(TASKS_TABLE, null, values);
	    
	}
		
	public void GetTasks(List<ITask> taskList) {
	
		if(taskList == null) {
			
			Log.e(getClass().toString(), "taskList given is null");
			return;
			
		}
		
		Cursor tasksData = db.rawQuery("select * from " + TASKS_TABLE, null);
		
		Log.i("SQL DUMP: ", DatabaseUtils.dumpCursorToString(tasksData));
		
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
		
		if(tasksData.moveToFirst()) {
			do {
				
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
				
				if(listIterator.hasNext()) {
					
					ITask current = listIterator.next();
					current.setDescription(desc);
					current.setDate(date);
					current.setType(type);
					current.setLongitude(longitude);
					current.setLatitude(latitude);
					current.setNotify(notify);
					
				} else {
					
					taskList.add(new IconedTask(desc, date, type, longitude, latitude, notify));
				}
			} while(tasksData.moveToNext());
			
		}
		
	}
	
	public void Close() {
		db.close();
	}
	
}
