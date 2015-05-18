package emergencyRoom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VisitRecords {
	
	/**
	 * Constructs a VisitRecords manager that retrieves a file under a 
	 * directory and name, if the file does not exist, it will create one
	 * @throws IOException an exception that is thrown if file cannot not be opened for any reason.
	 * @param dir directory of the file being read/written.
	 * @param fileName name of the file being read/written.
	 */	
	public VisitRecords(File dir, String fileName) throws IOException {
		File file = new File(dir, fileName);
		//If file is not exists create file
		if (!file.exists()) {
			file.createNewFile();
		}
	}
	
	/**
	 * Append Patient's information into an existing file.
	 * @throws IOException an exception that is thrown if file cannot not be opened for any reason.
	 * @param fw A file writer with a given location that is used to append to specified files.
	 * @param patient A Patient.
	 */	
	public void saveToFile(FileWriter fw, Patient patient) {
		try {
			fw.write(patient.getArrival() + (patient.getVitalSigns().toString()) + ((patient.getSeenByDoctor().toString()).replace("[", "!")).replace("]", "") + "!" + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**Returns a formatted string of patient's vital information and visit time
	 * 
	 * @param vitalsInfo the information of the patient's vitals
	 * @param visitNum the visit number
	 * @return One string that returns the formatted  date and time of visit with all the vital information
	 */
	public String readVitals(String vitalsInfo, Integer visitNum){
		String returnRecord = "";
		String[] records = vitalsInfo.split(",");
		returnRecord =  "Visit " + visitNum.toString() + ": " + records[0] + "\n";
		List<String> list = new ArrayList<String>(Arrays.asList(records));
		list.remove(0);
		for (String part: list){
			String[] vitals = part.split("=|&");
			returnRecord = returnRecord + (longToString(vitals[0]) + "\n" + "Heart Rate: " + vitals[1] + "\n" + "Blood Pressure: " + vitals[2] + "/" + vitals[3] + "\n" + "Temperature: " + vitals[4] + "\n");
		}
		return returnRecord;
	}
	
	/**
	 * Returns a formatted string of a patient's doctor visit
	 * @param doctorInfo the unformatted string of patient's doctor visit
	 * @return One string that is formatted containing the patient's doctor visit
	 */
	public String readDoctor(String doctorInfo){
		String returnInfo = "";
		String[] info = doctorInfo.split(",");
		if (info.length > 1){
			returnInfo = info[0] + " at ";
			List<String> list = new ArrayList<String>(Arrays.asList(info));
			list.remove(0);
			for (String part: list){
				returnInfo = returnInfo + part + ", ";
			}
			return (returnInfo.substring(0, (returnInfo.length()- 2)) + "\n");
		}
		else {
			return (doctorInfo + "\n");
		}
	}
	
	/**
	 * Returns a formatted string of the patient's prescriptions
	 * @param medication the unformatted string of patient's prescriptions
	 * @return formatted string of the patient's prescriptions
	 */
	public String readMeds(String medication){
		String meds = "";
		String[] medical = medication.split(",");
		if (medical.length == 1){
			if (medical[0].contentEquals("None")){
				return medication;
			}
			else{
				for (String part: medical){
				String[] part2 = medical[0].split("=|&");
				meds += longToString(part2[0]) + ": " + part2[1] + " - " + part2[2] + "\n";
				}
				return meds;
			}
		}
		else{
		for (String part: medical){
			String[] part2 = part.split("=|&");
			meds += longToString(part2[0]) + ": " + part2[1] + " - " + part2[2] + "\n";
		}
		return meds;
		}
	}
	
	/**
	 * Read the file that contains the vital sign information of a patient, doctor visits and increment
	 * the values into a String.
	 * 
	 * @throws FileNotFoundException an exception that is thrown when a file could not
	 * be found.
	 * @param filePath The file path of the file that would be read/written.
	 * @return One string to that holds all information from the file, correctly formatted for display.
	 */	
	public String readFile(String filePath) throws FileNotFoundException{
		String returnInfo = "";
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		Integer visitNum = 1;
		while (scanner.hasNextLine()){
			String[] records = scanner.nextLine().split("!");
			if (records.length > 2){
			returnInfo += readVitals(records[0], visitNum) + "Seen by Doctor: " + readDoctor(records[1]) + "Prescriptions: " + readMeds(records[2]);
			}
			else{
				returnInfo += readVitals(records[0], visitNum) + "Seen by Doctor: " + readDoctor(records[1]) + "Prescriptions: None";
			}
			visitNum += 1;
			returnInfo += "\n" + "\n";
		}
		scanner.close();
		returnInfo = "Patient Record History" + "\n" + "\n" + returnInfo;
		return returnInfo;
	}
	
	/**
	 * Convert the date into a shorter, readable value.
	 * 
	 * @param date The current time generated by the Android device.
	 * @return A date that is correctly formatted, YYYY-MM-DD at HH:MM:SS
	 */	
	public String longToString(String date){
		String readableDate = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8) + " at " + date.substring(8,10) + ":" + date.substring(10,12) + ":" + date.substring(12,14);
		return readableDate;
	}  
	
}
