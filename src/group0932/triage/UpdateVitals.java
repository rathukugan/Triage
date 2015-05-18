package group0932.triage;

import java.text.SimpleDateFormat;
import java.util.Date;

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

public class UpdateVitals extends BaseActivity {
	
	/** A Nurse to update vitals of a Patient. */	
	private Nurse nurse;
	
	/** The Patient to record vitals too. */
	private Patient patient;

	/**
	 * Displays the current date and time
	 */ 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_vitals);
		Intent intent = getIntent();
		//Get Patient and Nurse object from previous activity
		patient = (Patient) intent.getSerializableExtra("patientKey");
		nurse = (Nurse) intent.getSerializableExtra("nurseKey");
		//Display current date and time
	    TextView tv = (TextView) findViewById(R.id.show_date2);
	    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    String myDate = sdf2.format(new Date());
	    tv.setText("Time:" + " " + myDate);
	}
	
	/**
	 * Records the vital signs information and uses a nurse to update the values.
	 */ 
    public void recordVitals(View view) {
    	//Get time that can be formatted to a Long for use in VitalSigns
	    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
	    //Date and time for display on app
	    SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    String currentDateandTime = sdf3.format(new Date());
	    String dateDisplay = sdf4.format(new Date());
	    Long dateTime = Long.valueOf(currentDateandTime);
	    
     	// Get strings from each text field
     	EditText tempText = (EditText) findViewById(R.id.temp_field);
     	String temp = tempText.getText().toString();

     	EditText heartText = (EditText) findViewById(R.id.heart_rate_field);
     	String heart_rate = heartText.getText().toString();
     	
     	EditText bloodText = (EditText) findViewById(R.id.blood_press_field);
     	String blood_press = bloodText.getText().toString();
     	EditText bloodText2 = (EditText) findViewById(R.id.blood_press_field2);
     	String blood_press2 = bloodText2.getText().toString();

     	//Error check to make sure values are numbers otherwise throw exception
     	try {
     		if(temp.equals("")) {
    	        temp = null;
         	} else {
         		//Number check, throws error
         		Double.parseDouble(temp);
         		temp += "\u00b0" + "c";
         	}
         	if (heart_rate.equals("")) {
         		heart_rate = null;
         	} else {
         		Double.parseDouble(heart_rate);
         		heart_rate += " bpm";
         	}
         	if (blood_press.equals("")) { //systolic
         		blood_press = null;
         	} else {   
         		Double.parseDouble(blood_press);
         		blood_press += " mmHg";
         	}
         	if (blood_press2.equals("")) { //diastolic
         		blood_press2 = null;
         	} else {
         		Double.parseDouble(blood_press2);
         		blood_press2 += " mmHg";
         	}
         	//Adds values to map in VitalSigns class
         	nurse.update_visit_record(patient, dateTime, blood_press, blood_press2, heart_rate, temp);

         	String msg = "Vitals updated for " + patient.getName() + " on " + dateDisplay;
			Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
			toast.show();
    		Intent returnIntent = new Intent();
    	    returnIntent.putExtra("patientKey", patient);
    	    returnIntent.putExtra("nurseKey", nurse);
    	    setResult(RESULT_OK, returnIntent);
    	    finish();
     	} catch (NumberFormatException e) {
     		//Display alert if input wasn't a non-number or a blank
            AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
            dialog2.setTitle("Invalid Input");
            dialog2.setMessage("Please enter a number for each field or leave blank, do not include units.");
            dialog2.setNeutralButton("Ok", null);
            dialog2.create().show();
     		
     	}
     }
}
