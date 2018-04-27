package ie.wit.csgoapp30.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ie.wit.csgoapp30.models.Match;
import ie.wit.csgoapp30.models.User;

//Android Tutorials Hub. (2018). Android Login and Register with SQLite Database Tutorial - Android Tutorials Hub. [online] Available at: http://www.androidtutorialshub.com/android-login-and-register-with-sqlite-database-tutorial/ [Accessed 24 Apr. 2018].


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "csgoapp.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";


    // Matches table name
    public static final String table_matches = "matches";

    //Matches colummns
    public static final String match_id = "matchid";
    public static final String team1 = "team1";
    public static final String team2 = "team2";
    public static final String date = "date";
    public static final String time= "time";

    private static final String CREATE_TABLE_MATCHES =
            "CREATE TABLE " + table_matches + " (" +
                    match_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    team1 + " TEXT, " +
                    team2 + " TEXT, " +
                    date + " NUMERIC, " +
                    time + " NUMERIC)";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_MATCHES_TABLE = "DROP TABLE IF EXISTS " + table_matches;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TABLE_MATCHES);
        db.execSQL("insert into user values (1, 'Admin', 'admin@admin.com', 'admin')");
        db.execSQL("insert into user values (2, 'Admin2', 'admin2@admin.com', 'admin2')");
        db.execSQL("insert into user values (3, 'Admin3', 'admin3@admin.com', 'admin3')");
        db.execSQL("insert into matches values (1, 'SK', 'Cloud9', '12-5-2018', '15:50')");
        db.execSQL("insert into matches values (2, 'FaZe', 'NaVi', '15-5-2018', '15:30')");
        db.execSQL("insert into matches values (3, 'Fnatic', 'NiP', '18-5-2018', '15:40')");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_MATCHES_TABLE);
        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public Match addMatch(Match match){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values  = new ContentValues();
        values.put(team1,match.getTeam1());
        values.put(team2, match.getTeam2());
        values.put(date, match.getDate());
        values.put(time, match.getTime());
        db.insert(table_matches,null,values);
        db.close();
        return match;
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    public List<Match> getAllMatches() {

        String[] allColumns = {
                match_id,
                team1,
                team2,
                date,
                time
        };

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(table_matches, allColumns,null,null,null, null, null);

        List<Match> matches = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Match match = new Match();
                match.setMatchID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(match_id))));
                match.setTeam1(cursor.getString(cursor.getColumnIndex(team1)));
                match.setTeam2(cursor.getString(cursor.getColumnIndex(team2)));
                match.setDate(cursor.getString(cursor.getColumnIndex(date)));
                match.setTime(cursor.getString(cursor.getColumnIndex(time)));
                matches.add(match);
            }
        }
        cursor.close();
        db.close();
        // return All Employees
        return matches;
    }

    public User getUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD
        };

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                COLUMN_USER_EMAIL + "=?",        //columns for the WHERE clause
                new String[]{(email)},        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
        if (cursor != null) {
            cursor.moveToNext();

            User e = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            // return Employee
            return e;
        }else{
            return null;
        }
        }

    public Match getMatch(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String[] allColumns = {
                match_id,
                team1,
                team2,
                date,
                time
        };

        Cursor cursor = db.query(table_matches,allColumns,match_id + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null) {
            cursor.moveToNext();

            Match e = new Match(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            // return Employee
            return e;
        }else{
            return null;
        }
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public int updateMatch(Match match) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(team1, match.getTeam1());
        values.put(team2, match.getTeam2());
        values.put(date, match.getDate());
        values.put(time, match.getTime());

        // updating row
        return db.update(table_matches, values, match_id + "=?",new String[] { String.valueOf(match.getMatchID())});
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteAllMatches(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ table_matches);
        db.close();
    }

    public void removeMatch(Match match) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_matches, match_id + "=" + match.getMatchID(), null);
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}