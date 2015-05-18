package group0932.triage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import emergencyRoom.EmptyFileException;
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
import android.widget.Toast;

public class UpdatePrescrip extends BaseActivity {
	/** The Patient to record prescriptions too. */
	private Patient patient;
	
	/** A Physician to update prescriptions of a Patient. */	
	private Physician physician;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_prescrip);
		Intent intent = getIntent();
		//Get Patient and Physician object from previous activity
		patient = (Patient) intent.getSerializableExtra("patientKey");
		physician = (Physician) intent.getSerializableExtra("physicianKey");
		//Display current date and time
	    TextView tv = (TextView) findViewById(R.id.show_date3);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    String myDate = sdf.format(new Date());
	    tv.setText("Time:" + " " + myDate);
	}
	/**
	 * Records the prescription information and uses a Physician to update the values and save the values.
	 * @throws IOException 
	 */ 
	public void recordPrescrip(View view) throws IOException {
    	//Get time that can be formatted to a Long for use in Prescriptions
	    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
	    String currentDateandTime = sdf2.format(new Date());
	    Long dateTime = Long.valueOf(currentDateandTime);
	    
	    
     	EditText medText = (EditText) findViewById(R.id.med_field);
     	String medication = medText.getText().toString();

     	EditText instructText = (EditText) findViewById(R.id.instruct_field);
     	String instructions = instructText.getText().toString();

     	//put try and catch with this
     	try {
			physician.update_prescriptions(patient, dateTime, medication, instructions);
			File file = new File(this.getApplicationContext().getFilesDir(), (patient.getHealthCardNum()+".txt"));
			FileWriter fw = new FileWriter(file,true); //true lets fw append to file
			boolean empty = file.exists() && file.length() == 0;
			if (empty) {
				throw new EmptyFileException("A Nurse must create a visit record first.");
			}
			physician.savePrescription(fw, patient, file.getPath());
			String notice = "Prescriptions updated for " + patient.getName();
			Toast toast = Toast.makeText(getApplicationContext(), notice, Toast.LENGTH_LONG);
			toast.show();
			Intent returnIntent = new Intent();
		    returnIntent.putExtra("patientKey", patient);
		    setResult(RESULT_OK, returnIntent);
		    finish();
		} catch (MissingPatientException e) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Error");
            dialog.setMessage(e.getMessage());
            dialog.setNeutralButton("Ok", null);
            dialog.create().show();
		} catch (EmptyFileException e) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Permission Denied");
            dialog.setMessage(e.getMessage());
            dialog.setNeutralButton("Ok", null);
            dialog.create().show();
		}
	}
}
