package emergencyRoom;

import java.util.Comparator;

public class UrgencyComparator implements Comparator<Patient>{
	/**
	 * Comparator for comparing patients according to their urgencies.
	 * Returns -1 if p1's urgency is greater than p2's, 0 if p2's urgency is greater than
	 * p1's, 1 if they are equal.
	 * 
	 * @param p1 First patient object that is being compared.
	 * @param p2 Second patient object that is being compared.
	 * @return An integer value to denote which Patient has a greater urgency.
	 */
	public int compare(Patient p1, Patient p2) {
		if (p1.getUrgency() > p2.getUrgency()) {
			return -1;
		}
		else if (p1.getUrgency() < p2.getUrgency()) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
