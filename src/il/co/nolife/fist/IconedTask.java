package il.co.nolife.fist;

import java.util.Date;
import android.location.Location;

public class IconedTask implements ITask {

	private String description;
	private Date date;
	private TaskType type;
	private long longitude;
	private long latitude;
	private boolean notify;

	public IconedTask(String description, Date date, TaskType type, long lon, long lat, int n) {
		super();
		this.description = description;
		this.date = date;
		this.type = type;
		this.longitude = lon;
		this.latitude = lat;
		if(n == 0) {
			notify = false;
		} else {
			notify = true;
		}
	}
	
	public IconedTask(String description, Date date, TaskType type,
			long longitude, long latitude, boolean notify) {
		super();
		this.description = description;
		this.date = date;
		this.type = type;
		this.longitude = longitude;
		this.latitude = latitude;
		this.notify = notify;
	}

	@Override
	public long GetLongitude() {
		return longitude;
	}

	@Override
	public long GetLatitude() {
		return latitude;
	}
	@Override
	public TaskType GetType() {
		return type;
	}
	@Override
	public String GetDescription() {
		return description;
	}
	@Override
	public Date GetDate() {
		return date;
	}
	@Override 
	public boolean GetNotify() {
		return notify;
	}
	
}
