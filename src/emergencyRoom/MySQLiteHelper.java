package emergencyRoom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper{

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "TriageDB";

	// Table name
	private static final String TABLE_TRIAGE = "triage";

	// Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_USER = "user";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_JOB = "job";

	/**
	 * Constructor that sets up a database's name and its version.
	 * 
	 * @param context Interface to global information about an application environment.
	 */	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Create a new database 
		String CREATE_TABLE = "CREATE TABLE triage ( " +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				"user TEXT, "+
				"password TEXT, "+
				"job TEXT )";
		db.execSQL(CREATE_TABLE);
	}

	/**
	 * Checks whether the database table is empty.
	 * 
	 * @return true/false to whether a database table is empty.
	 */	
	public boolean isEmpty(){
		SQLiteDatabase db = this.getWritableDatabase();
		
		String query = "SELECT count(*) FROM " + TABLE_TRIAGE;
		Cursor cursor = db.rawQuery(query, null);
		
		//Since a cursor object is used, it must be positioned initially or it will
		//cause an IndexOutOfBoundsException
		cursor.moveToFirst(); 
		
		if(cursor.getInt(0) > 0){
			//there exist rows in the table
			return false;
		}
		return true;
	}
	
	/**
	 * Validate a user's login credentials.
	 * 
	 * @param user Username the user typed in.
	 * @param password Password the user typed in. 
	 * @return String that indicates whether the credentials existed
	 */	
	public String compareCredentials(String user, String password){
		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_TRIAGE + " WHERE " +
				"user='" + user + "' and password='" + password + "'";
			
		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3.
		if(!cursor.moveToFirst()){
			//Credentials NOT found
			return "Not Found";
		}
		else{
			//Credentials are found
			if(cursor.moveToFirst()){
				//returns whether the user is a nurse/physician
				return cursor.getString(1) + ',' + cursor.getString(3);
				//getString(int columnIndex); columnIndex based on the table
				//e.g. returns "username,nurse"
			}
		}
		
		return "Ignore this return statement";
	}

	/**
	 * Reads from a file containing information of medical staff and move those information
	 * into the database table.
	 * 
	 * @param user User's username.
	 * @param password User's password.
	 * @param job User's job title. 
	 */	
	public void addLoginInfo(String user, String password, String job){
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_USER, user); 
		values.put(KEY_PASSWORD, password); 
		values.put(KEY_JOB, job);

		// 3. insert
		db.insert(TABLE_TRIAGE, // table
				null, //nullColumnHack
				values); // key/value -> keys = column names/ values = column values

		// 4. close
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older books table if existed
		db.execSQL("DROP TABLE IF EXISTS triage");

		// create fresh books table
		this.onCreate(db);
	}

}
