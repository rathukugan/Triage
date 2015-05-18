package group0932.triage;

import java.io.IOException;

import emergencyRoom.ER;
import emergencyRoom.MissingPatientException;
import emergencyRoom.Nurse;
import emergencyRoom.Patient;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends BaseActivity {
	/** File name that contains patient data. */
	public static final String FILENAME = "patient_records.txt";
	/** A Nurse to perform operations on Patient data. */
	private Nurse nurse;
	/** An ER for holding all Patient data. */
	private ER er;
	/** The username/login of the nurse. */
	private String user;
	
	/**
	 * Creates a new nurse object (the specific nurse using the app) and an
	 * ER object which reads information about the patients from file.
	 */	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nurse = new Nurse();
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
	 * Get new ER data from createNewPatient, when CreatePatientActivity
	 * finishes and replace existing ER variable.
	 */	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if(resultCode == RESULT_OK){
				er = (ER) data.getSerializableExtra("erKey");
			}

		}
	}
    
    /**
	 * On click for enter button, checks whether a patient is currently in the ER. If not, 
	 * raises MissingPatientException and catches it.
	 */	
    public void patientView(View view) {
       // Specifies the next Activity to move to: PatientView.
    	Intent intent = new Intent(this, PatientView.class);
    		
    	// Gets health card from the text view
    	EditText healthCardText = (EditText) findViewById(R.id.health_card_field);
    	String healthCard = healthCardText.getText().toString();
    	Patient selectedPatient = null;
		try {
			//Get patient object from ER collection of patient's
			selectedPatient = nurse.lookup_patient(healthCard, er);
			//store nurse and current patient object for next activity
	    	intent.putExtra("patientKey", selectedPatient);
	    	intent.putExtra("nurseKey", nurse);
	     	intent.putExtra("user", user);
	    	startActivity(intent);   	// Starts DisplayActivity.
	    	this.finish();
		} catch (MissingPatientException e) {
			//Display alert dialog
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Missing Patient");
            dialog.setMessage(e.getMessage());
            dialog.setNeutralButton("Ok", null);
            dialog.create().show();
		}

    }
    
    /**
	 * Move to CreatePatientActivity to create a new Patient to file,
	 * on click of add patient button.
	 */	
    public void createNewPatient(View view){
    	//nurse wants to add new patients
    	Intent intent = new Intent(this, CreatePatientActivity.class);
    	intent.putExtra("nurse_key", nurse);
    	intent.putExtra("er_key", er);
     	intent.putExtra("user", user);
    	startActivityForResult(intent, 1);
    }
    /**
	 * Move to UrgencyList to display the current triage sorting of patients,
	 * by urgency.
	 */	
    public void urgencyView(View view) {
        // Specifies the next Activity to move to: PatientView.
     	Intent intent = new Intent(this, UrgencyList.class);
     	intent.putExtra("erKey", er);
     	intent.putExtra("user", user);
 	    startActivity(intent);   	// Starts DisplayActivity.
 	    this.finish();
     }

}
