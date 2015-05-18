package group0932.triage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import emergencyRoom.MySQLiteHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	/** File name that contains user name and password credentials. */
	public static final String FILENAME = "passwords.txt";

	private MySQLiteHelper db;
	private boolean tableEmpty;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().hide();

		db = new MySQLiteHelper(this);

		tableEmpty = db.isEmpty();

		if(tableEmpty == true){
			try {
				//add info from file into database
				addToDatabase(db);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Reads from a file that contains login and job information of medical staff and
	 * transfer them into the database.
	 * 
	 * @param db MySQLiteHelper object that facilitates the use of a database.
	 * @throws FileNotFoundException file that contains the information of medical stuff 
	 */	
	public void addToDatabase(MySQLiteHelper db) throws FileNotFoundException{
		File file = new File(this.getApplicationContext().getFilesDir(), 
				FILENAME);

		String line;
		Scanner scanner = new Scanner(new FileInputStream(file.getPath()));
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			String[] login_info = line.split(",");

			db.addLoginInfo(login_info[0], login_info[1], login_info[2]);
		} //end while (scanner.hasNextLine())
		scanner.close();
	}

	/**
	 * Check credentials by comparing to text file passwords.txt,
	 * and moves to Nurse or Physician activity depending on the credentials.
	 * Otherwise, alert dialog is displayed for incorrect credentials.
	 * Throws FileNotFoundException if passwords.txt is not in correct path.
	 */	
	public void login(View view) throws FileNotFoundException{
		String str_username, str_password;

		EditText username = (EditText) findViewById(R.id.username);
		EditText password = (EditText) findViewById(R.id.password);

		str_username = username.getText().toString();
		str_password = password.getText().toString();

		String result = db.compareCredentials(str_username, str_password);

		if(result.equals("Not Found")){
			String warning = "Wrong credentials, please try again!";
			Toast toast = Toast.makeText(getApplicationContext(), warning, Toast.LENGTH_SHORT);
			toast.show();			
		}
		else{

			//split String result as it contains "username,nurse"
			String[] login_info = result.split(",");

			if(login_info[1].equals("nurse")){
				Intent intent = new Intent(this, MainActivity.class);
				String userID = "Nurse " + login_info[0];
				intent.putExtra("user", userID);
				startActivity(intent);				
			}
			else{
				Intent intent = new Intent(this, PhysicianMain.class);
				String userID = "Physician " + login_info[0];
				intent.putExtra("user", userID);
				startActivity(intent);				
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
