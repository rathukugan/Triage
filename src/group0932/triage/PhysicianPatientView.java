package group0932.triage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import emergencyRoom.Patient;
import emergencyRoom.Physician;
import emergencyRoom.VisitRecords;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class PhysicianPatientView extends BaseActivity {
	/** The Patient for which data will be added. */
	private Patient patient;
	
	/** A Physician to perform operations on visit records of Patient. */
	private Physician physician;
	
	/**VisitRecords to manage reading and writing of Patient's visit file. */
	private VisitRecords vr;
	
	/** Patient's visit records filename. */	
	private String filename;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician_patient_view);
		Intent intent = getIntent();
		//Get Patient and Physician object from previous activity
		patient = (Patient) intent.getSerializableExtra("patientKey");
		physician = (Physician) intent.getSerializableExtra("physicianKey");
		//Display current date and time
	    TextView tv = (TextView) findViewById(R.id.show_date4);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    String myDate = sdf.format(new Date());
	    tv.setText("Time:" + " " + myDate);
	    
		//create Visit records manager 
		filename = patient.getHealthCardNum() +".txt";
        try {
            vr = new VisitRecords(this.getApplicationContext().getFilesDir(), 
					filename);
        }catch (IOException e) {
			e.printStackTrace();
        }
	    
		TextView newName = (TextView) findViewById(R.id.show_name2);
		newName.setText("Name:" + " " + patient.getName());
		TextView newDob = (TextView) findViewById(R.id.show_dob2);
		newDob.setText("DOB:" + " " + patient.getDob());
		TextView newHealthCardNum = (TextView) findViewById(R.id.show_health_card_num2);
		newHealthCardNum.setText("Health Card No:" + " " + patient.getHealthCardNum());
	}
    /**
	 * Get new Patient data with prescription information from UpdatePrescrip.
	 */	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 2) {
	        if(resultCode == RESULT_OK){
	            patient = (Patient) data.getSerializableExtra("patientKey");
	        }

	      }
	    }
	/**
	 * Starts the next activity in which a Physician will update Prescription information.
	 */ 
    public void updatePrescrip(View view) {
    	//Move to UpdatePrescrip activity
     	Intent intent = new Intent(this, UpdatePrescrip.class);
     	intent.putExtra("patientKey", patient);
    	intent.putExtra("physicianKey", physician);
    	startActivityForResult(intent, 2);

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
}
