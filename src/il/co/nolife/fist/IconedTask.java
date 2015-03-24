package il.co.nolife.fist;

import java.util.Date;
import android.location.Location;

public class IconedTask implements ITask {

	private String description;
	private Date date;
	private TaskType type;
	private long longitude;
	private long latitude;
	
	

	public IconedTask(String description, Date date, TaskType type, long lon, long lat) {
		super();
		this.description = description;
		this.date = date;
		this.type = type;
		this.longitude = lon;
		this.latitude = lat;
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
	
}
