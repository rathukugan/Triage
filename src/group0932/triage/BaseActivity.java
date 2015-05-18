package group0932.triage;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {
	
	/**
	 * Create menu with sign out button for all activities.
	 */	
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	/**
	 * Add functionality to sign out button for every activity.
	 */	
	public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	      case R.id.back_press:
	    	  onBackPressed();
	      	  return true;
	      case R.id.sign_out:
	    	  signOutActivity();
	    	  return true;
	      default:
	        return super.onOptionsItemSelected(item);
	      }

	    }

	/**
	 * Sign out of app by moving to LoginActivity, and
	 * finishing current activity.
	 */	    
    public void signOutActivity() {
	    Toast toast = Toast.makeText(getApplicationContext(), "Log Out Successful", Toast.LENGTH_LONG);
		toast.show();
	    Intent intent = new Intent(this, LoginActivity.class);
	    startActivity(intent);
	    this.finish();
	   }
}
