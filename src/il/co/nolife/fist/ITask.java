package il.co.nolife.fist;

import java.util.Date;

public interface ITask {

	public int GetId();
	public TaskType GetType();
	public String GetDescription();
	public Date GetDate();
	public long GetLongitude();
	public long GetLatitude();
	public boolean GetNotify();
	
	public void setType(TaskType type);
	public void setDescription(String decscription);
	public void setDate(Date date);
	public void setLongitude(long longitude);
	public void setLatitude(long latitude);
	public void setNotify(int notify);
	
}
