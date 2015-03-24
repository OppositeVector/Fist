package il.co.nolife.fist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskListAdapter extends ArrayAdapter<ITask> {

	private List<ITask> tasks;
	private LayoutInflater inflater;
	
	private class ViewHolder {
		
		public ImageView icon;
		public TextView text;
		public TextView date;
		
	}
	
	public TaskListAdapter(Context context, List<ITask> t) {
		
		super(context, R.layout.list_item, t);
		tasks = t;
		inflater = (LayoutInflater) context.getSystemService("LayoutInfalter");
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = (ViewHolder) convertView.getTag();
		
		if(holder == null) {

			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item, parent, true);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.text = (TextView) convertView.findViewById(R.id.description);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			convertView.setTag(holder);
			
		}
		
		holder.icon.setImageResource(R.drawable.ic_launcher);
		holder.text.setText(tasks.get(position).GetDescription());
		// holder.date.setText(tasks.get(position).GetDate());
		
		return convertView;
	}
	
}
