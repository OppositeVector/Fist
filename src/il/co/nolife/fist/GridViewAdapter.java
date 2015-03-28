package il.co.nolife.fist;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends ArrayAdapter<TaskType> {
	
	private class ViewHolder {
		public ImageView icon;
		
	}
	Context con;
	List<TaskType> li;
	ButtonClickAction actionHandler;
	public GridViewAdapter(Context context,List<TaskType> l, ButtonClickAction bca){
		super (context, R.layout.icons_layout, l);
		con = context;
		li = l;
		actionHandler = bca;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView != null) {
			holder = (ViewHolder) convertView.getTag();
			Log.i("TESTING", "1");
		}
		if (holder == null){
			holder = new ViewHolder();
			Log.i("TESTING", "1");
			holder.icon = new ImageView(con);
			Log.i("TESTING", "2");
			holder.icon.setLayoutParams(new GridView.LayoutParams(220,280));
			Log.i("TESTING", "3");
			holder.icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
			Log.i("TESTING", "4");
			holder.icon.setPadding(0,4,0,4);
			Log.i("TESTING", "5");
			
			
		}
		convertView = holder.icon;
		Log.i("TESTING", "6");
		convertView.setTag(holder);
		Log.i("TESTING", "7");
		holder.icon.setImageResource(li.get(position).GetDrawable());
		Log.i("TESTING", "8");
		
		final TaskType tt = li.get(position);
		Log.i("TESTING", "9");
		holder.icon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				actionHandler.ButtonClick(tt);
				
				
			}
		});
		Log.i("TESTING", "10");
		return convertView;
		
	}
}
