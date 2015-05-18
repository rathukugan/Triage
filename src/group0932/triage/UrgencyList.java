package group0932.triage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import emergencyRoom.ER;
import emergencyRoom.Nurse;
import emergencyRoom.Patient;
import emergencyRoom.UrgencyComparator;
import emergencyRoom.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class UrgencyList extends BaseActivity {
	/** The ER to view information on Patients. */
	private ER er;
	
	/** The username and type of user. */
	private String user;
	
	/**
	 * Display Patients from ER sorted by urgency value and
	 * seen by doctor.
	 */ 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_urgency_list);
		Intent intent = getIntent();
		TextView newUrgencyList = (TextView) findViewById(R.id.textViewUrgency);
		er = (ER) intent.getSerializableExtra("erKey");
		user = (String) intent.getSerializableExtra("user");
		List<Patient> patientsList = er.getPatients();
		List<Patient> sorted_patients = new ArrayList<Patient>();
		int index;
		for (Patient p : patientsList) {
			index = 0;
			if (p.getSeenByDoctor().size() > 1) {
				index = p.getSeenByDoctor().size() - 2;
			}
			String doctorseen = p.getSeenByDoctor().get(index);
			if (doctorseen.equals("No")) {
				sorted_patients.add(p);
			}
		}
		String newUrgencyList1 = "List of patients haven't yet been seen by a doctor sorted by decreasing urgency:" + "\n";
		Collections.sort(sorted_patients, new UrgencyComparator());
		for (Patient p1 : sorted_patients) {
			newUrgencyList1 += ("\n" + p1.getName() + ", " + p1.getDob() + ", " + p1.getHealthCardNum() + 
					", Urgency: " + String.valueOf(p1.getUrgency()) + "\n");
			if (!p1.getVitalSigns().getDiastolic().equals("None")) {
				newUrgencyList1 += p1.getVitalSigns().getDiastolic();
			}
			if (!p1.getVitalSigns().getSystolic().equals("None")) {
				newUrgencyList1 += ", " + p1.getVitalSigns().getSystolic();
			}
			if (!p1.getVitalSigns().getHr().equals("None")) {
				newUrgencyList1 += ", " + p1.getVitalSigns().getHr();
			}
			if (!p1.getVitalSigns().getTemp().equals("None")) {
				newUrgencyList1 += ", " + p1.getVitalSigns().getTemp();
			}
			newUrgencyList1 += "\n";
		}
		newUrgencyList.setText(newUrgencyList1);
	}
	
	/**
	 * Return to Physician activity or Nurse Activity depending on
	 * the type of user operating the activity.
	 */ 
	public void onBackPressed() {
		String id = user.split(" ")[0];
		if (id.equals("Nurse")) {
			Intent intent = new Intent(this, MainActivity.class);
	    	intent.putExtra("user", user);
	    	startActivity(intent);
	    	super.onBackPressed();
		} else {
			Intent intent = new Intent(this, PhysicianMain.class);
	    	intent.putExtra("user", user);
	    	startActivity(intent);
	    	super.onBackPressed();
		}

    }
}