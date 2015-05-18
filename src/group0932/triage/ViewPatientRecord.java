package group0932.triage;

import emergencyRoom.Patient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewPatientRecord extends BaseActivity {
	/** The Patient in which visit record data will be observed. */
	private Patient patient;
	
	/** All visit record data for the Patient. */
	private String allData;

	/**
	 * Displays all the visit record information of a patient.
	 */ 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_patient_record);
		Intent intent = getIntent();
		patient = (Patient) intent.getSerializableExtra("patientKey");
		allData = (String) intent.getSerializableExtra("data");
		//Display name, dob, health card
		TextView newName = (TextView) findViewById(R.id.show_name2);
		newName.setText("Name:" + " " + patient.getName());
		TextView newDob = (TextView) findViewById(R.id.show_dob2);
		newDob.setText("DOB:" + " " + patient.getDob());
		TextView newHealthCardNum = (TextView) findViewById(R.id.show_health_card_num2);
		newHealthCardNum.setText("Health Card No:" + " " + patient.getHealthCardNum());
		//Display contents of file
		TextView visitRecord = (TextView) findViewById(R.id.visit_record_data);
		visitRecord.setText(allData);
	}
	
}
