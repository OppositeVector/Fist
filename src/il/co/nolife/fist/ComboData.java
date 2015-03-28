package il.co.nolife.fist;

import java.util.Date;

public class ComboData {

	protected int id;
	protected Date date;
	protected boolean notify;
	protected int alarmId;
	protected long longitude;
	protected long latitude;
	protected String geofence;
	
	public ComboData(Date date, boolean notify, long longitude, long latitude) {
		super();
		this.date = date;
		this.notify = notify;
		this.alarmId = -1;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public ComboData(Date date, int notify, long longitude, long latitude) {
		super();
		this.date = date;
		this.notify = (notify != 0);
		this.alarmId = -1;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public ComboData(int id, Date date, int notify, long longitude, long latitude) {
		super();
		this.id = id;
		this.date = date;
		this.notify = (notify != 0);
		this.alarmId = -1;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public int getId(){
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	public long getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isNotify() {
		return notify;
	}
	public void setNotify(boolean notify) {
		this.notify = notify;
	}
	public int getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(int id) {
		this.alarmId = id;
	}
	public String getGeofence() {
		return geofence;
	}

	public void setGeofence(String geofence) {
		this.geofence = geofence;
	}
	
}
