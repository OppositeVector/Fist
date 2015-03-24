package il.co.nolife.fist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

public class FistController implements ConnectionCallbacks, OnConnectionFailedListener {
	
	private static FistController instance;
	
	static {
		instance = new FistController();
	}
	
	private FistController() {
		
		tasks = new ArrayList<ITask>();
		BuildGoogleApiClient();
		googlePlayUnavailable = false;
		
	}
	
	// End of static block Setup
	
	private List<ITask> tasks;
	private GoogleApiClient googlePlay;
	private boolean  googlePlayUnavailable;
	private Context context;
	
	public static void AttachContext(Context c) {
		instance.context = c;
	}
	
	public static List<ITask> GetTaskList() {
		return instance.tasks;
	}
	
	public static List<ITask> GetTaskList(String filter) {
		
		List<ITask> retVal = new ArrayList<ITask>();
		
		for(int i = 0; i < instance.tasks.size(); ++i) {
			if(instance.tasks.get(i).GetDescription().contains(filter)) {
				retVal.add(instance.tasks.get(i));
			}
		}
		
		return retVal;
		
	}
	
	public static List<ITask> GetTaskList(TaskType type) {
		
		List<ITask> retVal = new ArrayList<ITask>();
		
		for(int i = 0; i < instance.tasks.size(); ++i) {
			if(instance.tasks.get(i).GetType() == type) {
				retVal.add(instance.tasks.get(i));
			}
		}
		
		return retVal;
		
	}
	
	// Google play object related
	
	protected synchronized void BuildGoogleApiClient() {
	    googlePlay = new GoogleApiClient.Builder(context)
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(LocationServices.API)
	        .build();
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		System.out.println("Connection Failed");
	}

	@Override
	public void onConnected(Bundle arg0) {
		System.out.println("Connection Succeded");
		googlePlayUnavailable = true;
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		System.out.println("Connection Suspended");
		googlePlayUnavailable = false;
	}

	
}
