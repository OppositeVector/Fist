package il.co.nolife.fist;

import java.util.Date;

public interface ITask {

	public TaskType GetType();
	public String GetDescription();
	public Date GetDate();
	public long GetLongitude();
	public long GetLatitude();
	public boolean GetNotify();
	
}
