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

		}
		if (holder == null){
			holder = new ViewHolder();
			holder.icon = new ImageView(con);
			holder.icon.setLayoutParams(new GridView.LayoutParams(220,280));
			holder.icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
			holder.icon.setPadding(0,4,0,4);

		}
		convertView = holder.icon;
		convertView.setTag(holder);
		holder.icon.setImageResource(li.get(position).GetDrawable());

		final TaskType tt = li.get(position);
		holder.icon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				actionHandler.ButtonClick(tt);
			}
		});

		return convertView;
		
	}
}
