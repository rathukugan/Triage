package emergencyRoom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.IOException;


public class ER implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5400426442159270443L;
	/** The ER's collection of Patients. */
	private List<Patient> patients;
	/** The File corresponding to all listed Patients */
	private File dir;
	
	/**
	 * Creates an ArrayList and retrieves a file 
	 * which would be send to 'read_patients' for reading and writing.
	 * 
	 * @param dir directory of the file being read.
	 * @param fileName name of the file being read. 
	 */	
	public ER(File dir, String fileName) throws IOException { 
		//Collection of all Patient's from database file
		this.patients = new ArrayList<Patient>();
		File file = new File(dir, fileName);
		this.dir = dir;
		//Read txt file and create Patient object for each
		this.read_patients(file.getPath());
	}
	
	/**
	 * Reads from a file, creates Patients and adds to a list.
	 * 
	 * Use {@link #ER(File dir, String fileName)} to read from a txt file, create and add Patient
	 * objects to the ER.patients list.
	 * 
	 * @param file_path path of the file being read.
	 * @throws FileNotFoundException if file is not found in file path.
	 */
	public void read_patients(String file_path) throws FileNotFoundException {
		try {
		String line;
		Scanner read_txt = new Scanner(new FileInputStream(file_path));
		while (read_txt.hasNextLine()) {
			line = read_txt.nextLine();
			String[] patient_info = line.split(",");
			File x = new File(this.dir, (patient_info[0] + ".txt"));
			ArrayList<String> seenByDoctor1 = new ArrayList<String>();
			VitalSigns vitalsings = new VitalSigns();
			Prescriptions meds = new Prescriptions();
			if (x.exists()) {
				this.uploadPatientHistory(x.getPath(), seenByDoctor1);
				uploadPatientVitals(x.getPath(), vitalsings);
			}
			Patient newPatient = new Patient(patient_info[0], patient_info[1], patient_info[2], seenByDoctor1);
			newPatient.setVitalSigns(vitalsings);
			newPatient.setPrescriptions(meds);
			this.patients.add(newPatient);
		}
		read_txt.close();
		}
		catch (FileNotFoundException f3) {
			this.patients = new ArrayList<Patient>();
		}
	}
	
	/**
	 * Returns the list of patients.
	 * @return The collection of patients.
	 */
	public List<Patient> getPatients() {
		return patients;
	}
	
	/**
	 * Sets the up the patient
	 * @param name the name of the patient
	 * @param dob the birthday of the patient
	 * @param health_card_num the healthcard number of the patient
	 */
	public void setPatients(String name, String dob, String health_card_num){
		ArrayList<String> seenByDoctor = new ArrayList<String>();
		Patient p = new Patient(health_card_num, name, dob, seenByDoctor);
		patients.add(p);
	}
	
	/** 
	 * It loads the patient with their doctor visit history
	 * @param filePath the location of the where it patient's records are stored
	 * @param doctorVisit ArrayList that stores the doctor visit history
	 * @throws FileNotFoundException throws if the file is not found
	 */
	public void uploadPatientHistory(String filePath, ArrayList<String> doctorVisit) throws FileNotFoundException{
		try {
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		while (scanner.hasNextLine()){
			String[] records = scanner.nextLine().split("!");
			String[] data = records[1].split(",");
			for (String info: data){
			if (info.contains("No")){
			}
			else{
				doctorVisit.add(info);
			}
			}
		}
		scanner.close();
		}
		catch (FileNotFoundException f1) {
		}
	}
	
	/**
	 * Loads the vital signs of the patient into the patient
	 * @param filePath where the vital signs are stored
	 * @param vitals the vital sign object that takes the vital sign
	 * @throws FileNotFoundException throws if the file is not found
	 */
	public void uploadPatientVitals(String filePath, VitalSigns vitals) throws FileNotFoundException {
		try {
		Scanner scanner1 = new Scanner(new FileInputStream(filePath));
		String nextline = ""; 	
		while (scanner1.hasNextLine()){
			nextline = scanner1.nextLine();
		}
		if (nextline != "") {
		ArrayList<String> vitalsx = new ArrayList<String>();
		for (String x: nextline.split("!")[0].split(",")) {
			vitalsx.add(x);
		}
		nextline = vitalsx.get(vitalsx.size() - 1);
		String[] vitals3 = nextline.split("=")[1].split("&");
		vitals.setHr(vitals3[0]);
		vitals.setSystolic(vitals3[1]);
		vitals.setDiastolic(vitals3[2]);
		vitals.setTemp(vitals3[3]);
		}
		scanner1.close();
		}
		catch (FileNotFoundException f2) {
			vitals = new VitalSigns();
		}
	}
}
