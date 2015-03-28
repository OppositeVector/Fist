package il.co.nolife.fist;

import java.util.ArrayList;
import java.util.List;

public enum TaskType {

	Birthday(R.drawable.birthday),
	Call(R.drawable.call),
	Clean(R.drawable.clean),
	Custom(R.drawable.custom),
	Date(R.drawable.date),
	Gym(R.drawable.gym),
	Homework(R.drawable.homework),
	Meeting(R.drawable.meeting),
	Party(R.drawable.party),
	Pills(R.drawable.pills),
	Shopping(R.drawable.shopping),
	Vacation(R.drawable.vacation);
	
	private int drawableId;
	private TaskType(int di) { drawableId = di; }
	public int GetDrawable() { return drawableId; }
	
	public static List<TaskType> GetList (){
		List<TaskType> l = new ArrayList<TaskType>();
		l.add(Birthday);
		l.add(Call);
		l.add(Clean);
		l.add(Custom);
		l.add(Date);
		l.add(Gym);
		l.add(Homework);
		l.add(Meeting);
		l.add(Party);
		l.add(Pills);
		l.add(Shopping);
		l.add(Vacation);
		
		return l;
	}
	
}

