package group0932.triage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import emergencyRoom.ER;
import emergencyRoom.Nurse;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePatientActivity extends BaseActivity {
	
	/** A Nurse to perform action of creating new patient. */
	private Nurse nurse;
	/** An ER in which the patient will be added too. */
	private ER er;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_patient);

		Intent intent = getIntent();
		nurse = (Nurse) intent.getSerializableExtra("nurse_key");
		er = (ER) intent.getSerializableExtra("er_key");
	}
	
	/**
	 * Read all user information and determine if it is valid.
	 * Creates a new patient on click of submit button, throws
	 * FileNotFoundException if "patient_records" file is not in correct path.
	 */
	public void create_new_patient(View view) throws FileNotFoundException{
		boolean name_correct = false, 
				date_is_int = false, 
				year_four_digit = false, 
				date_month_two_digit = false,
				health_card_int = false;
		String first_name, last_name, yyyy, mm, dd, health_card_num;

		EditText et_first_name = (EditText) findViewById(R.id.first_name);
		EditText et_last_name = (EditText) findViewById(R.id.last_name);
		EditText et_yyyy = (EditText) findViewById(R.id.yyyy);
		EditText et_mm = (EditText) findViewById(R.id.mm);
		EditText et_dd = (EditText) findViewById(R.id.dd);
		EditText et_health_card_num = (EditText) findViewById(R.id.health_card_num);

		first_name = et_first_name.getText().toString();
		last_name = et_last_name.getText().toString();
		yyyy = et_yyyy.getText().toString();
		mm = et_mm.getText().toString();
		dd = et_dd.getText().toString();
		health_card_num = et_health_card_num.getText().toString();

		//Creating a new patient record
		//First Check: the input is acceptable 

		//name is not empty
		if((first_name != null && !first_name.isEmpty()) || (last_name != null && 
				!last_name.isEmpty())){
			name_correct = true;
		}

		//DOB and health card numbers are all integers
		try{
			Integer.parseInt(yyyy);
			Integer.parseInt(mm);
			Integer.parseInt(dd);
			date_is_int = true;
			Integer.parseInt(health_card_num);
			health_card_int = true;
		}
		catch(NumberFormatException e){
			//do nothing as the original boolean is set as False
		}

		//length of year must be 4 and month,day must be 2
		if(yyyy.length() == 4 && mm.length() == 2 && dd.length() == 2){
			year_four_digit = true;
			date_month_two_digit = true;
		}


		//Second Check: whether there already exists a person with the same info
		//That is, there cannot exist two of the same health card number but is possible
		//to have the same name

		if(name_correct == false || date_is_int == false || year_four_digit == false ||
				date_month_two_digit == false || health_card_int == false){
			//Display alert if input wasn't a non-number or a blank
			AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
			dialog2.setTitle("Invalid Input");
			dialog2.setMessage("Here are some suggestions:\n" +
					"- first/last names cannot be empty\n" +
					"- YYYY/MM/DD must be integer\n" +
					"- YYYY must contain 4 digits\n" +
					"- MM/DD must contain 2 digits\n" +
					"- health card number can only be integer values\n\n" +
					"Please try again!");
			dialog2.setNeutralButton("Ok", null);
			dialog2.create().show();
		}
		else if(health_card_number_exist(health_card_num) == true){
			//health card number already exists!
			AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
			dialog2.setTitle("Invalid Input");
			dialog2.setMessage("This health card number has already been previously " +
					"registered. Please try again!");
			dialog2.setNeutralButton("Ok", null);
			dialog2.create().show();
		}
		else{
			FileWriter fw;
			try {
				File file = new File(this.getApplicationContext().getFilesDir(), "patient_records.txt");
				fw = new FileWriter(file,true); //true lets fw append to file
				nurse.create_new_patient(er, fw, first_name, last_name, yyyy, mm, dd, health_card_num);
				String message = "The new patient has been created.";
				Toast toast = Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG);
				toast.show();
				Intent returnIntent = new Intent();
			    returnIntent.putExtra("erKey", er);
			    returnIntent.putExtra("nurseKey", nurse);
			    setResult(RESULT_OK, returnIntent);
			    finish();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		} //end else
	}
	
	/** 
	 * Check if Patient exists by searching for the health card number in the file.
	 * @param health_card_num the health card number of the desired Patient.
	 * @throws FileNotFoundException
	 * @return Whether or not the health card number already exists in file.
	 */
	public boolean health_card_number_exist(String health_card_num) throws FileNotFoundException{
		String line;
		File file = new File(this.getApplicationContext().getFilesDir(), "patient_records.txt");
		Scanner scanner = new Scanner(new FileInputStream(file.getPath()));
		while(scanner.hasNextLine()){
			line = scanner.nextLine();
			String[] patient_info = line.split(",");
			//If there already exists an identical health card number
			if(health_card_num.equals(patient_info[0])){
				return true;
			}
		}		
		return false;
	}

}
