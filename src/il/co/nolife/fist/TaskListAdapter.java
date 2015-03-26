package il.co.nolife.fist;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskListAdapter extends ArrayAdapter<ITask> {

	private List<ITask> tasks;
	private LayoutInflater inflater;
	
	View CurrentlyDragged;
	
	private class ViewHolder {
		
		public ImageView icon;
		public TextView text;
		public TextView date;
		
	}
	
	public TaskListAdapter(Context context, List<ITask> t) {
		
		super(context, R.layout.item_layout, t);
		tasks = t;
		inflater = (LayoutInflater) context.getSystemService("layout_inflater");
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if(convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(holder == null) {

			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_layout, parent, false);
			holder.icon = (ImageView) convertView.findViewById(R.id.item_icon);
			holder.text = (TextView) convertView.findViewById(R.id.item_description);
			holder.date = (TextView) convertView.findViewById(R.id.item_date);
			convertView.setTag(holder);
			convertView.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					switch(event.getAction()) {
						
						case MotionEvent.ACTION_DOWN:
							break;
						case MotionEvent.ACTION_UP:
							break;
						case MotionEvent.ACTION_MOVE:
							
							break;
						
					}
					return false;
				}
			});
			
		}
		
		holder.icon.setImageResource(tasks.get(position).GetType().GetDrawable());
		holder.text.setText(tasks.get(position).GetDescription());
		holder.date.setText(tasks.get(position).GetDate().toString());
		
		return convertView;
		
	}
	
}
