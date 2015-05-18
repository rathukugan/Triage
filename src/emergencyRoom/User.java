package emergencyRoom;

public abstract class User {
	
	/**
	 * Goes through a list of patients and compares the existing health card number(s)
	 * to determine whether the patient is in the ER.
	 * 
	 * @throws MissingPatientException an exception that is thrown when patient could not
	 * be found.
	 * @param healthCardNum health card number that identifies a patient in the ER.
	 * @param data an ER object that holds information about patients.
	 * @throws MissingPatientException if Patient is not found in file (database).
	 * @return The Patient that matches user input.
	 */	
	public Patient lookup_patient(String healthCardNum, ER data) throws MissingPatientException{
		for (Patient selectedPatient: data.getPatients()){
			if (selectedPatient.getHealthCardNum().equals(healthCardNum)) {
				return selectedPatient;
			}
		}
		throw new MissingPatientException("That patient does not exist.");		
	}
}
