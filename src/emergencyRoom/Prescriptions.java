package emergencyRoom;


import java.io.Serializable;
import java.util.TreeMap;

public class Prescriptions implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7094527232350458621L;
	/** The collection of times mapped with prescription info. */
	private TreeMap<Long, String[]> prescrip;
	
	/**
	 * Initializes the collection of prescription information.
	 */	
	public Prescriptions() {
		this.prescrip = new TreeMap<Long, String[]>();

	}
	
	/**
	 * Updates the values of the prescriptions of a Patient, where key's
	 * are time recorded and values are medication title and instructions.
	 * 
	 * @param time Current time generated by the Android device.
	 * @param title The medication name (title).
	 * @param instruction The instructions for using the medication.
	 */	
	public void update(long time, String title, String instruction){
		String[] prescripData = new String[2];
		prescripData[0] = title;
		prescripData[1] = instruction;
		this.prescrip.put(time, prescripData);
		
	}
	
	/**
	 * Returns the TreeMap object.
	 * @return The prescriptions of a Patient as a TreeMap.
	 */
	public TreeMap<Long, String[]> getPrescrip() {
		return prescrip;
	}
	
	/**
	 * Sets the TreeMap object to a given value.
	 * @param vitals The new TreeMap of all prescriptions of a Patient.
	 */
	public void setPrescrip(TreeMap<Long, String[]> prescrip) {
		this.prescrip = prescrip;
	}
	
	/**
	 * Returns a formatted string of the prescriptions
	 * @return the prescriptions of the Patient as a formated string.
	 */
	public String toString(){
		String prescription = "";
		for (Long key: this.prescrip.keySet()){
			String [] meds = this.prescrip.get(key);
			prescription += key.toString() + "=" + meds[0] + "&" + meds[1] + ",";
		}
		return prescription;
	}
}