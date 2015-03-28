package il.co.nolife.fist;

import java.util.Date;
import android.location.Location;

public class IconedTask extends ComboData implements ITask {

	// private int id;
	private String description;
	private TaskType type;

	public IconedTask(String description, Date date, TaskType type, long lon, long lat, int n) {
		super(date, n,lon, lat);
		this.description = description;
		this.type = type;
	}
	
	public IconedTask(String description, Date date, TaskType type,
			long longitude, long latitude, boolean notify) {
		super(date, notify, longitude, latitude);
		this.description = description;
		this.type = type;
	}
	
	@Override
	public boolean getNotify() {
		return isNotify();
	}

	@Override
	public TaskType getType() {
		return type;
	}
	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public void setNotify(int notify) {
		
		if(notify == 0) {
			this.notify = false;
		} else {
			this.notify = true;
		}
		
	}
	
}
