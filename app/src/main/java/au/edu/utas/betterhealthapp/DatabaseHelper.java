package au.edu.utas.betterhealthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.util.Log;
import java.io.Serializable;

/**
 * Created by store on 5/05/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper /*implements Serializable*/ {

    public static final String DATABASE_NAME="BetterHealth.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "entries";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "foodgroup";
    public static final String COL_3 = "foodtype";
    public static final String COL_4 = "servings";
    public static final String COL_5 = "date";

//public static final String CREATE_QUERY= "CREATE TABLE" + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, food_group TEXT, food_type TEXT, serving_size INTEGER, quantity TEXT)";
    public DatabaseHelper(Context context) {
//creating context to create database
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       SQLiteDatabase db=this.getWritableDatabase();
        Log.e("database", "Database created / opened...");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//create table
      // db.execSQL(CREATE_QUERY);
        db.execSQL("create table " + TABLE_NAME + "(" +
                COL_1 +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_2 +" TEXT,"+
                COL_3 +" TEXT,"+
                COL_4 +" INTEGER,"+
                COL_5 +" DATE"+
                ")");
        Log.e("database", "Table created ...");
    }
    //this method drops table if already exists
    @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
//to insert data in table
    public boolean insertData(String foodgroup, String foodtype, String servings, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, foodgroup);
        contentValues.put(COL_3, foodtype);
        contentValues.put(COL_4, servings);
        contentValues.put(COL_5, date);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        else{
            db.close();
            Log.e("database", "data inserted ...");
            return true;
        }
    }
    //method created to delete based on id
    public Integer deleteData(String id){

        SQLiteDatabase db= this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?",new String[]{id});
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME+" order by date ASC",null);
        return result;
    }

    public int getHistoryOf(String foodGroup){
        Log.e("database", "Attempting to count servings for "+foodGroup+" from the past 7 days");

        int returnVal = 0;
        SQLiteDatabase db=this.getWritableDatabase();
        String query = "select sum(servings) from (select foodgroup,servings from entries where date >= date('now','-7 days')) where foodgroup='"+foodGroup+"'";
        Log.e("database", "Executing the following query: "+query);
        Cursor result = db.rawQuery(query,null);
        if (result.getCount() == 0) {
            returnVal = 0;
        }
        else{
            result.moveToNext();
            if (result.getString(0) == null)
            {
                returnVal = 0;
            }
            else{
                returnVal = Integer.parseInt(result.getString(0));
            }
        }

        Log.e("database", "Query complete, returning value the value: "+returnVal);
        return returnVal;
    }

    public void testMe()
    {
        Log.e("database", "I am alive and well.");
    }

}
