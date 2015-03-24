package il.co.nolife.fist;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class PlayServicesHandler {

	private Context context;
	private boolean ready;
	
	public PlayServicesHandler(Context context) {
		super();
		this.context = context;
	}

	private boolean CheckGooglePlayServicesIsAvailable() {
		
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
		if (status != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
				ready = true;
			} else {
				Toast.makeText(context, 
							   "This device is not supported.", 
							   Toast.LENGTH_LONG).show();
				ready = false;
			}
			return false;
		}
		return true;
	}
	
}
