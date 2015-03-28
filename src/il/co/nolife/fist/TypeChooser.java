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
			
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.icons_layout);
		
		GridViewAdapter gridvA = new GridViewAdapter(this, TaskType.GetList(), this);
		GridView g = (GridView) findViewById(R.id.gridview);
		g.setAdapter(gridvA);
		
	}
	
	public void ButtonClick(TaskType t) {
		
		Intent creator = new Intent(this, TaskCreator.class);
		creator.putExtra(EXTRA_NAME, t.ordinal());
		startActivityForResult(creator, 0);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(resultCode != RESULT_CANCELED) {
			setResult(RESULT_OK);
			finish();
		}
		
	}
	
}
