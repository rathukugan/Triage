package emergencyRoom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;


public class Patient implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6360953966508432384L;
	/** This Patient's name, date of birth and health card number. */
	private String name, dob, healthCardNum;
	
	/** This Patient's urgency and age. */
	private int urgency, age;
	
	private ArrayList<String> seenByDoctor;
	
	/** This Patient's vital signs - temperature, blood pressure , heart rate */
	private VitalSigns vitalSigns;
	
	/** This Patient's arrival time,YYYY-MM-DD HH:MM format */
	private String arrival;
	
	/** This Patient's prescriptions - medication name and instructions */
	private Prescriptions prescriptions;
	
	/**
	 * A constructor that initializes the information of a patient.
	 * 
	 * @param health_card_num the health card number of a patient.
	 * @param name the name of a patient.
	 * @param dob the date of birth of a patient.
	 */	
	public Patient(String health_card_num, String name, String dob, ArrayList<String> seenByDoctor){
		this.healthCardNum = health_card_num;
		this.name = name;
		this.dob = dob;
		this.age = 0;
		this.age = this.calculateAge();
		this.vitalSigns = new VitalSigns();
		this.prescriptions = new Prescriptions();
		this.urgency = 0;
		this.calculateUrgency();
		this.seenByDoctor = seenByDoctor;
		if (this.seenByDoctor.isEmpty()) {
			this.seenByDoctor.add("No");
		}
	}
	
	/**
	 * Returns the arrival time of a Patient.
	 * @return Patient's arrival time in YYYY-MM-DD HH:MM format.
	 */	
	public String getArrival() {
		return arrival;
	}
	
	/**
	 * Sets the arrival time of a Patient.
	 * @param arrival The new arrival time of the Patient.
	 */	
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	
	/**
	 * Returns the health card number of a Patient.
	 * @return Patient's health card number.
	 */	
	public String getHealthCardNum() {
		return healthCardNum;
	}

	/**
	 * Sets the health card number of a Patient.
	 * @param healthCardNum The new health card number of this Patient.
	 */	
	public void setHealthCardNum(String healthCardNum) {
		this.healthCardNum = healthCardNum;
	}

	/**
	 * Returns the name of a Patient.
	 * @return The name of this Patient.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of a Patient.
	 * @param name The new name of the Patient.
	 */	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the date of birth of a Patient.
	 * @return The date of birth of the Patient.
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of a Patient.
	 * @param dob The new date of birth of this Patient.
	 */	
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * Returns the vital signs of a Patient.
	 * @return The Vital Signs of a Patient.
	 */	
	public VitalSigns getVitalSigns() {
		return vitalSigns;
	}

	/**
	 * Sets the vital signs of a Patient.
	 * @param vitalSigns The new vital signs of this Patient.
	 */	
	public void setVitalSigns(VitalSigns vitalSigns) {
		this.vitalSigns = vitalSigns;
		this.calculateUrgency();
	}
	
	/**
	 * Returns the prescriptions of a Patient.
	 * @return The prescriptions of a Patient.
	 */
	public Prescriptions getPrescriptions() {
		return prescriptions;
	}
	
	/**
	 * Sets the prescriptions of a Patient.
	 * @param prescriptions The new prescriptions of this Patient.
	 */	
	public void setPrescriptions(Prescriptions prescriptions) {
		this.prescriptions = prescriptions;
	}
	
	/**
	 * calculates the age
	 * @return the age of the patient as an int
	 */
	public int calculateAge() {
		int age1 = 0;
		int ageyear = Integer.valueOf(this.dob.substring(0, 4));
		int agemonth = Integer.valueOf(this.dob.substring(5, 7));
		int ageday = Integer.valueOf(this.dob.substring(8));
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int difference = year - ageyear;
		if ((month > agemonth) || ((month == agemonth) && (day < ageday))){
			age1 = difference;
		}
		else {
			age1 = difference - 1;
		}
		return age1;
	}
	
	/**
	 * Calculates the urgency value of each patient
	 */
	public void calculateUrgency() {
		String data = "";
		urgency = 0;
		if (this.age < 2) {
			urgency += 1;
		}
		if (!this.vitalSigns.getTemp().equals("None")) {
			data = this.vitalSigns.getTemp().split("\u00b0")[0];
			if (Integer.valueOf(data) >= 39) {
				urgency += 1;
			}
		}
		if (!this.vitalSigns.getHr().equals("None")) {
			data = this.vitalSigns.getHr().split(" ")[0];
			if ((Integer.valueOf(data) >= 100) || (Integer.valueOf(data) <= 50)) {
				urgency += 1;
			}
		}
		if (!this.vitalSigns.getDiastolic().equals("None")) {
			data = this.vitalSigns.getDiastolic().split(" ")[0];
			if (Integer.valueOf(data) >= 90) {
				urgency += 1;
			}
			else if (!this.vitalSigns.getSystolic().equals("None")) {
				data = this.vitalSigns.getSystolic().split(" ")[0];
				if (Integer.valueOf(data) >= 140) {
					urgency += 1;
				}
			}
		}
	}
	
	/**
	 * Return the urgency value of the patient
	 * @return urgency value of the patient as int
	 */
	public int getUrgency() {
		return urgency;
	}
	
	/**
	 * Returns a list of when the patient has been seen by the doctor
	 * @return an Arraylist of times seen by a doctor
	 */
	public ArrayList<String> getSeenByDoctor(){
		return seenByDoctor;
	}
	
	/**
	  * Sets up the seen by doctor
	  * @param time the time when the patient saw he doctor
	  */
	public void setSeenByDoctor(String time){
		if (this.seenByDoctor.size() == 1){
			this.seenByDoctor.remove(0);
			this.seenByDoctor.add("Yes");
			this.seenByDoctor.add(time);
		}
		else {
			this.seenByDoctor.add(time);
		}
	}
	
	/**
	 * Gets the age of the patient
	 * @return the age of the patient as an int
	 */
	public int getAge() {
		return this.age;
	}

}

