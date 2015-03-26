package il.co.nolife.fist;

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
	
}
