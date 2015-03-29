package il.co.nolife.fist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class MapActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState){
		Log.i("TESTING", "Map3");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.map);
		
		
	}
}
