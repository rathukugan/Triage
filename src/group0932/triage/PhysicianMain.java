package group0932.triage;

import java.io.IOException;

import emergencyRoom.ER;
import emergencyRoom.MissingPatientException;
import emergencyRoom.Patient;
import emergencyRoom.Physician;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PhysicianMain extends BaseActivity {
	/** File name that contains patient data. */
	public static final String FILENAME = "patient_records.txt";
	
	/** A Physician to perform operations on prescriptions of Patient. */
	private Physician physician;
	
	/** An ER for holding all Patient data. */
	private ER er;
	
	/** The username/login of the physician. */
	private String user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician_main);
		physician = new Physician();
        try {
            er = new ER(this.getApplicationContext().getFilesDir(), 
					FILENAME);
        }catch (IOException e) {
			e.printStackTrace();
        }
        
        Intent intent = getIntent();
        user = (String) intent.getSerializableExtra("user");
        
        TextView welcome_message = (TextView) findViewById(R.id.display_user);
        welcome_message.setText("Welcome " + user);
	}
    /**
	 * On click for enter button, checks whether a patient is currently in the ER. If not, 
	 * raises MissingPatientException and catches it.
	 */	
	public void physPatientView(View view) {
		Intent intent = new Intent(this, PhysicianPatientView.class);
		EditText healthCardText = (EditText) findViewById(R.id.health_card_field2);
		String healthCard = healthCardText.getText().toString();
		Patient selectedPatient = null;
		try {
			//Get patient object from ER collection of patient's
			selectedPatient = physician.lookup_patient(healthCard, er);
			//store physician and current patient object for next activity
	    	intent.putExtra("patientKey", selectedPatient);
	    	intent.putExtra("physicianKey", physician);
	    	startActivity(intent);   	// Starts DisplayActivity.
		} catch (MissingPatientException e) {
			//Display alert dialog
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Error");
            dialog.setMessage(e.getMessage());
            dialog.setNeutralButton("Ok", null);
            dialog.create().show();
		}
	}
    /**
	 * Move to UrgencyList to display the current triage sorting of patients,
	 * by urgency.
	 */	
    public void physUrgencyView(View view) {
        // Specifies the next Activity to move to: PatientView.
     	Intent intent = new Intent(this, UrgencyList.class);
     	intent.putExtra("erKey", er);
     	intent.putExtra("user", user);
 	    startActivity(intent);   	// Starts DisplayActivity.
 	    this.finish();
     }

}
