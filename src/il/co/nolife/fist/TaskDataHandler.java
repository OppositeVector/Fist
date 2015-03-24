package il.co.nolife.fist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

public class TaskDataHandler extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "fistDatabase.db";
	public static final int DATABASE_VERSION = 1;
	
	public static final String TASKS_TABLE = "tasks";
	public static final String ID = "tasks_id";
	public static final String DESCRIPTION = "task_description";
	public static final String TYPE = "icon_type";
	public static final String DATE = "date";
	public static final String LONGITUDE = "loc_long";
	public static final String LATITUDE = "loc_lat";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private SQLiteDatabase db;
	private SimpleDateFormat dateFormat;
	
	public TaskDataHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = getWritableDatabase();
		dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		database.execSQL("create table "
					+ TASKS_TABLE + "(" + ID + " integer primary key autoincrement, " 
					+ TYPE + " integer, " 
					+ DESCRIPTION + " ntext not null, "
					+ DATE + " datetime, " 
					+ LONGITUDE + " double, " 
					+ LATITUDE + " double);");
		
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
	    
	    db.insert(TASKS_TABLE, null, values);
	    
	}
	
	public List<ITask> GetTasks() {
	
		List<ITask> retVal = new ArrayList<ITask>();
		
		Cursor tasksData = db.rawQuery("select * from " + TASKS_TABLE, null);
		
		// int idIndex = tasksData.getColumnIndex(ID);
		int desriptionIndex = tasksData.getColumnIndex(DESCRIPTION);
		int typeIndex = tasksData.getColumnIndex(TYPE);
		int dateIndex = tasksData.getColumnIndex(DATE);
		int longtitudeIndex = tasksData.getColumnIndex(LONGITUDE);
		int latitudeIndex = tasksData.getColumnIndex(LATITUDE);
		
		if(tasksData.moveToFirst()) {
			do {
				try {
					retVal.add(new IconedTask(tasksData.getString(desriptionIndex), 
											  dateFormat.parse(tasksData.getString(dateIndex)), 
											  TaskType.values()[tasksData.getInt(typeIndex)], 
											  tasksData.getLong(longtitudeIndex), 
											  tasksData.getLong(latitudeIndex)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while(tasksData.moveToNext());
		}
		
		return retVal;
		
	}
	
	public void Close() {
		db.close();
	}
	
}
