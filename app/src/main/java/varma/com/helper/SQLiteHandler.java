/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package varma.com.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_USER = "user";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_FULLNAME = "FullName";
	private static final String KEY_ShortName = "ShortName";
	private static final String KEY_GCName = "GCName";
	private static final String KEY_Block = "Block";
	private static final String KEY_Flat = "Flat";
	private static final String KEY_Mobile = "Mobile";
	private static final String KEY_OWNTEN = "OWNTEN";
	private static final String KEY_Photo = "Photo";



	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE,"
				+ KEY_UID + " TEXT,"
				+ KEY_CREATED_AT + " TEXT,"
				+ KEY_FULLNAME + " TEXT,"
				+ KEY_ShortName + " TEXT,"
				+ KEY_GCName + " TEXT,"
				+ KEY_Block + " TEXT,"
				+ KEY_Flat + " TEXT,"
				+ KEY_Mobile + " TEXT,"
				+ KEY_OWNTEN + " TEXT,"
				+ KEY_Photo + " TEXT"
				+ ")";

		db.execSQL(CREATE_LOGIN_TABLE);

		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
//	public void addUser(String name, String email, String uid, String created_at,String FullName) {
	public void addUser(String name, String email, String uid, String created_at,String FullName,String ShortName, String GCName, String Block, String Flat,String Mobile,String OWNTEN, String Photo) {
		deleteUsers(); // This has to be removed later.
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name); // Name
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_UID, uid); // Email
		values.put(KEY_CREATED_AT, created_at); // Created At
		values.put(KEY_FULLNAME, FullName); // FullName
		values.put(KEY_ShortName, ShortName); // ShortName
		values.put(KEY_GCName, GCName); // GCName
		values.put(KEY_Block, Block); // Block
		values.put(KEY_Flat, Flat); // Flat
		values.put(KEY_Mobile, Mobile); // Mobile
		values.put(KEY_OWNTEN, OWNTEN); // OWNTEN
		values.put(KEY_Photo, Photo); // Photo

		// Inserting Row
		long id = db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("uid", cursor.getString(3));
			user.put("created_at", cursor.getString(4));
			user.put("FullName", cursor.getString(5));
			user.put("ShortName", cursor.getString(6));
			user.put("GCName", cursor.getString(7));
			user.put("Block", cursor.getString(8));
			user.put("Flat", cursor.getString(9));
			user.put("Mobile", cursor.getString(10));
			user.put("OWNTEN", cursor.getString(11));
			user.put("Photo", cursor.getString(12));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		// Create tables again
		 onCreate(db);
		db.close();
		Log.d(TAG, "Deleted all user info from sqlite");
	}

}
