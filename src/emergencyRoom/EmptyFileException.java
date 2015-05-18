package emergencyRoom;

public class EmptyFileException extends Exception {

	/**
	 * This exception would be raised when a file is empty, so a physician
	 * cant record the prescription unless the Nurse creates a visit record.
	 */
	private static final long serialVersionUID = -7732135860870256774L;

    /**
     * Constructs a EmptyFileException with 
     * message to be displayed.
     * @param message Message to be displayed.
     */
	public EmptyFileException(String message) {
	    super(message);
	}
}

