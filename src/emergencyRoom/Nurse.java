package emergencyRoom;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;


public class Nurse extends User implements Serializable {

	private static final long serialVersionUID = -2067270713118288940L;

	/**
	 * Whenever a patient arrives to the ER, a visit record must be made with the 
	 * corresponding time and a VitalSigns object must be created to keep track of the
	 * patient's vital signs.
	 * 
	 * @param p Patient object.
	 * @param currentDateTime the current time generated by the Android device. 
	 */	
	public void create_visit_record(Patient p, String currentDateTime) {
		p.setArrival(currentDateTime);
		//reset vital signs to insure its empty for next new visit
		p.setVitalSigns(new VitalSigns());
	}
	
	/**
	 * Updating the vital signs of a particular patient by retrieving a VitalSigns object
	 * and using the dot notation to access the update method.
	 * 
	 * @param p A patient object.
	 * @param time the current time generated by the Android device.
	 * @param systolic the systolic blood pressure of the patient
	 * @param diastolic the diastolic blood pressure of the patient
	 * @param heartRate the heartRate of the patient.
	 * @param temp the temperature of the patient. 
	 */	
	public void update_visit_record(Patient p,long time, String systolic, String diastolic, String heartRate, String temp) {
		p.getVitalSigns().update(time, systolic, diastolic, heartRate, temp);
	}
	
	/**
	 * Updating the vital signs of a particular patient by retrieving a VitalSigns object
	 * and using the dot notation to access the update method.
	 * 
	 * @param er Patient collection for which Patient will be added. 
	 * @param fw Location and textfile writer to add to patient_records.txt
	 * @param first_name the first name of the patient
	 * @param last_name the last name of the patient
	 * @param yyyy the year of birth of the patient.
	 * @param mm the month of birth of the patient. 
	 * @param dd the day of birth of the patient.
	 * @param health_card_num the health card number of the patient.
	 */	
	public void create_new_patient(ER er, FileWriter fw, String first_name, String last_name, 
			String yyyy, String mm, String dd, String health_card_num) {
		//CreatePatientActivity calls this method after verifying the input of new patient
		String name = first_name + " " + last_name;
		String dob = yyyy + "-" + mm + "-" + dd;
		
		String new_line = health_card_num + "," + name + "," + dob;
		
		try {
			//newline sequence is system dependent so don't use /n
			fw.append(System.getProperty("line.separator") + new_line);
			fw.close();
			
			er.setPatients(name, dob, health_card_num);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
		
}