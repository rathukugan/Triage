package emergencyRoom;

public class MissingPatientException extends Exception {
	/**
	 * This exception would be raised when a patient could not be found under the existing
	 * file 'patient_records.txt'. It would be caught by a try-and-catch method.
	 */
	private static final long serialVersionUID = -6434788883350559183L;
    /**
     * Constructs a MissingPatientException with 
     * message to be displayed.
     * @param message Message to be displayed.
     */
	public MissingPatientException(String message) {
	    super(message);
	}
}

