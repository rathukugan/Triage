package group0932.triage;

import emergencyRoom.Nurse;
import emergencyRoom.Patient;
import emergencyRoom.VisitRecords;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientView extends BaseActivity {
	
	/** A Nurse to perform operations on visit records of Patient. */
	private Nurse nurse;
	
	/** The Patient for which data will be added. */
	private Patient patient;
	
	/**VisitRecords to manage reading and writing of Patient's visit file. */
	private VisitRecords vr;
	
	/** Patient's visit records filename. */
	private String filename;
	
	/** The username/login of the user. */
	private String user;
	
	/** Buttons that will be enabled over the course of the activity. */
	private Button create, update, save, seenByDoctor;

	/**
	 * Displays the basic information of the patient that was selected as well
	 * as the current time.
	 */ 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_view);
		
		// Gets the Intent passed from MainActivity
		Intent intent = getIntent();
		patient = (Patient) intent.getSerializableExtra("patientKey");
		nurse = (Nurse) intent.getSerializableExtra("nurseKey");
		user = (String) intent.getSerializableExtra("user");
		//create Visit records manager 
		filename = patient.getHealthCardNum() +".txt";
        try {
            vr = new VisitRecords(this.getApplicationContext().getFilesDir(), 
					filename);
        }catch (IOException e) {
			e.printStackTrace();
        }
		//Display name, dob, health card
		TextView newName = (TextView) findViewById(R.id.show_name);
		newName.setText("Name:" + " " + patient.getName());
		TextView newDob = (TextView) findViewById(R.id.show_dob);
		newDob.setText("DOB:" + " " + patient.getDob());
		TextView newHealthCardNum = (TextView) findViewById(R.id.show_health_card_num);
		newHealthCardNum.setText("Health Card No:" + " " + patient.getHealthCardNum());
		
		//Display current date and time
	    TextView tv = (TextView) findViewById(R.id.show_date);
	    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    String myDate = sdf2.format(new Date());
	    tv.setText("Time:" + " " + myDate);
	    //Initialize buttons
	    create = (Button) findViewById(R.id.create_visit);
	    update = (Button) findViewById(R.id.update_visit);
	    //Save and update are disabled on activity creation
	    save = (Button) findViewById(R.id.save_visit);
	    seenByDoctor = (Button) findViewById(R.id.seen_by_doctor);
	    update.setEnabled(false);
	    save.setEnabled(false);
	    seenByDoctor.setEnabled(false);
	    

	}
    /**
	 * Get new Patient data with new vital signs from UpdateVitals.
	 */	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            patient = (Patient) data.getSerializableExtra("patientKey");
	            save.setEnabled(true);
	        }

	      }
	    }
	
	/**
	 * Create a visit record of a patient with the corresponding time.
	 */ 
	public void createVisit(View view) {
			//Enabled update,save on click of Create button
			update.setEnabled(true);
			seenByDoctor.setEnabled(true);
			//Get latest time for internal use
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String dateTime = sdf.format(new Date());
			//Update the latest arrival time (set variable)
		    nurse.create_visit_record(patient,dateTime);
		    //Display alert 
		    String msg = "Visit record created for " + patient.getName() + " on " + dateTime;
            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
			toast.show();
	    }
	
	/**
	 * Starts the next activity which would ask a nurse for vital signs information.
	 */ 
    public void updateVisit(View view) {
    	//Move to UpdateVitals activity
     	Intent intent = new Intent(this, UpdateVitals.class);
     	intent.putExtra("patientKey", patient);
    	intent.putExtra("nurseKey", nurse);
    	startActivityForResult(intent, 1);

     }
    
	/**
	 * Saving a visit of a patient by writing to a file.
	 */ 
    public void saveVisit(View view) {
    	FileWriter fw;
		try {
			File file = new File(this.getApplicationContext().getFilesDir(), filename);
			fw = new FileWriter(file,true); //true lets fw append to file
			vr.saveToFile(fw,patient);
			
			//Get latest time for internal use
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String dateTimeSave = sdformat.format(new Date());
			//Display alert 
			String msg = "Visit record saved for " + patient.getName() + " on " + dateTimeSave;
            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
			toast.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
     }
    
	/**
	 * Displays all visit records with the corresponding updates of vital signs.
	 */ 
    public void viewVisit(View view) {
    	File file2 = new File(this.getApplicationContext().getFilesDir(), filename);
    	Intent intent = new Intent(this, ViewPatientRecord.class);
    	try {
			String allData = vr.readFile(file2.getPath());
	     	intent.putExtra("patientKey", patient);
	     	intent.putExtra("data", allData);
	     	startActivity(intent);
		} catch (FileNotFoundException e) {
			String allData = "No Records";
			intent.putExtra("patientKey", patient);
	     	intent.putExtra("data", allData);
		}
    	
     }
    
    /**
     * Records a doctor visit with the date and time
     */
    public void seenByDoctor(View view){
    	SimpleDateFormat simpledate = new SimpleDateFormat("HH:mm dd-MM-yyyy");
		String dateTime = simpledate.format(new Date());
		patient.setSeenByDoctor(dateTime);
		//Display alert
		String msg = patient.getName() + " saw a doctor at " + dateTime;
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
		toast.show();
        
    }
    /**
	 * Pass username information to MainActivity on back press.
	 */	
    public void onBackPressed() {
    	Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    	intent.putExtra("user", user);
    	startActivity(intent);
    	super.onBackPressed();
    }
}
