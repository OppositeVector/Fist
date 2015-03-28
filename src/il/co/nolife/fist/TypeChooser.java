package il.co.nolife.fist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class TypeChooser extends Activity implements ButtonClickAction {

	public static final String EXTRA_NAME = "ChosenType";
	
	protected void onCreate(Bundle savedInstanceState) {
			
		Log.i("TESTING", "HERE");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.icons_layout);
		
		
		GridViewAdapter gridvA = new GridViewAdapter(this, TaskType.GetList(), this);
		Log.i("TESTING", "GridViewAdapter");
		GridView g = (GridView) findViewById(R.id.gridview);
		Log.i("TESTING", "GridViewAdapter2");
		g.setAdapter(gridvA);
		Log.i("TESTING", "GridViewAdapter3");
//		ImageView b = (ImageView) findViewById(R.id.custom);
//		b.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				ButtonClicked(TaskType.Custom);
//			}
//		});
		
	}
	
	public void ButtonClick(TaskType t) {
		
		Intent creator = new Intent(this, TaskCreator.class);
		creator.putExtra(EXTRA_NAME, t.ordinal());
		startActivityForResult(creator, 0);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		finish();
		
	}
	
}
