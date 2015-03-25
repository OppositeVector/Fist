package il.co.nolife.fist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TypeChooser extends Activity {

	public static final String EXTRA_NAME = "ChosenType";
	
	protected void onCreate(Bundle savedInstanceState) {
			
		Log.i("TESTING", "HERE");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.icons_layout);
		
		ImageView b = (ImageView) findViewById(R.id.custom);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ButtonClicked(TaskType.Custom);
			}
		});
		
	}
	
	private void ButtonClicked(TaskType type) {
		
		Intent creator = new Intent(this, TaskCreator.class);
		creator.putExtra(EXTRA_NAME, type.ordinal());
		startActivityForResult(creator, 0);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		finish();
		
	}
	
}
