package il.co.nolife.fist;

import java.util.Date;

public interface ITask {

	// public int getId();
	public TaskType getType();
	public String getDescription();
	public Date getDate();
	public long getLongitude();
	public long getLatitude();
	public boolean getNotify();
	
	public void setType(TaskType type);
	public void setDescription(String decscription);
	public void setDate(Date date);
	public void setLongitude(long longitude);
	public void setLatitude(long latitude);
	public void setNotify(int notify);
	
}
