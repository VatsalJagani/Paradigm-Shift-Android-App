package com.learnparadigmshift.vatsal.paradigmshift.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.learnparadigmshift.vatsal.paradigmshift.fragments.MainListFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by VATSAL on 02/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database
    public static final String DATABASE_NAME="accountingDB";
    private static final int DATABASE_VERSION=1;

    // Tables
    private static final String FRIEND_TABLE="friends";
    private static final String ACCOUNTING_TABLE="accountings";
    private static final String TEXT_DATATYPE=" TEXT ";

    // Common column names
    private static final String FRIEND_ID="friend_id";

    // Friends table - column names
    private static final String FRIEND_NAME="name";
    private static final String FRIEND_FULL_NAME="full_name";
    private static final String MOBILE_NO="mobile_no";
    private static final String EMAIL="email_address";

    // Accounting table - column names
    private static final String ACCOUNT_ID="ac_id";
    private static final String COMPLETE="complete";
    private static final String AMOUNT="amount";
    private static final String DATE="date";
    private static final String TIME="time";
    private static final String PURPOSE="purpose";

    // Tables creation
    private static final String CREATE_TABLE_FRIEND="CREATE TABLE " + FRIEND_TABLE + " ( "
            + FRIEND_ID + " INTEGER PRIMARY KEY, "
            + FRIEND_NAME + TEXT_DATATYPE + ","
            + FRIEND_FULL_NAME + TEXT_DATATYPE + ","
            + MOBILE_NO + TEXT_DATATYPE + ","
            + EMAIL + TEXT_DATATYPE
            + " ) ";
    private static final String CREATE_TABLE_ACCOUNTING="CREATE TABLE " + ACCOUNTING_TABLE + " ( "
            + FRIEND_ID + " INTEGER, "
            + ACCOUNT_ID + " INTEGER PRIMARY KEY, "
            + COMPLETE + " INTEGER, "
            + AMOUNT + " REAL, "
            + DATE + TEXT_DATATYPE + ","
            + TIME + TEXT_DATATYPE + ","
            + PURPOSE + TEXT_DATATYPE + ","
            + "FOREIGN KEY(" + FRIEND_ID + ") REFERENCES " + FRIEND_TABLE + "(" + FRIEND_ID + ") "
            + " ) ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FRIEND);
        db.execSQL(CREATE_TABLE_ACCOUNTING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FRIEND_TABLE);
        onCreate(db);
    }


    // Database Operations
    public int insertFriend(Friend fr){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FRIEND_NAME,fr.getName());
        values.put(FRIEND_FULL_NAME,fr.getFullName());
        values.put(MOBILE_NO,fr.getMobileNo());
        values.put(EMAIL,fr.getEmailAddress());
        return (int)db.insert(FRIEND_TABLE, null, values);
    }

    public int insertAccounting(Accounting ac){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FRIEND_ID,ac.getFriendId());
        values.put(COMPLETE,ac.getComplete());
        values.put(AMOUNT,ac.getAmount());
        values.put(DATE,ac.getDate());
        values.put(TIME,ac.getTime());
        values.put(PURPOSE,ac.getPurpose());
        return (int) db.insert(ACCOUNTING_TABLE,null,values);
    }

    public int removeFriend(int friendId){
        SQLiteDatabase db=this.getWritableDatabase();
        int noOfAc = db.delete(ACCOUNTING_TABLE, FRIEND_ID + "=?", new String[]{"" + friendId});
        return db.delete(FRIEND_TABLE, FRIEND_ID + "=?", new String[]{"" + friendId});
    }

    public int removeAccounting(int acId){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(ACCOUNTING_TABLE, ACCOUNT_ID + "=?", new String[]{"" + acId});
    }

    public ArrayList<MainListModel> getMainList(){
        ArrayList<MainListModel> arr=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();

        String q1="SELECT * FROM " + FRIEND_TABLE;
        Cursor c1=db.rawQuery(q1,null);
        if(c1!=null){
            while(c1.moveToNext()){
                MainListModel m=new MainListModel();
                int friend_id=c1.getInt(c1.getColumnIndex(FRIEND_ID));
                m.setFriendId(friend_id);
                m.setName(c1.getString(c1.getColumnIndex(FRIEND_NAME)));
                String q2="SELECT SUM(" + AMOUNT + ") AS MY_TOTAL FROM " + ACCOUNTING_TABLE + " WHERE " + FRIEND_ID + " = " + friend_id + " AND " + COMPLETE + "= 0";
                Cursor c2=db.rawQuery(q2,null);
                if(c2!=null && c2.moveToNext()){
                    m.setAmount(c2.getDouble(c2.getColumnIndex("MY_TOTAL")));
                }
                arr.add(m);
            }
        }
        return arr;
    }

    public ArrayList<Friend> getFriendList(){
        ArrayList<Friend> frds=null;
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM " + FRIEND_TABLE;
        Cursor c=db.rawQuery(query,null);
        if(c!=null){
            frds = new ArrayList<>();
            while(c.moveToNext()){
                Friend fr=new Friend();
                fr.setFriendId(c.getInt((c.getColumnIndex(FRIEND_ID))));
                fr.setName(c.getString(c.getColumnIndex(FRIEND_NAME)));
                fr.setFullName(c.getString(c.getColumnIndex(FRIEND_FULL_NAME)));
                fr.setMobileNo(c.getString(c.getColumnIndex(MOBILE_NO)));
                fr.setEmailAddress(c.getString(c.getColumnIndex(EMAIL)));
                frds.add(fr);
            }
        }
        return frds;
    }

    public HashMap<Integer,String> getFriendsNames(){
        HashMap<Integer,String> map=null;
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM " + FRIEND_TABLE;
        Cursor c=db.rawQuery(query,null);
        if(c!=null){
            map = new HashMap<>();
            while(c.moveToNext()){
                map.put(c.getInt((c.getColumnIndex(FRIEND_ID))),c.getString(c.getColumnIndex(FRIEND_NAME)));
            }
        }
        return map;
    }

    public ArrayList<Accounting> getAccountings(int friendId){
        ArrayList<Accounting> accs=null;
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM " + ACCOUNTING_TABLE + " WHERE " + FRIEND_ID + " = ? AND " + COMPLETE + " = 0";
        Cursor c=db.rawQuery(query,new String[]{""+friendId});
        if(c!=null){
            accs = new ArrayList<>();
            while(c.moveToNext()){
                Accounting ac=new Accounting();
                ac.setFriendId(friendId);
                ac.setAcId(c.getInt(c.getColumnIndex(ACCOUNT_ID)));
                ac.setAmount(c.getDouble(c.getColumnIndex(AMOUNT)));
                ac.setComplete(0);
                ac.setDate(c.getString(c.getColumnIndex(DATE)));
                ac.setTime(c.getString(c.getColumnIndex(TIME)));
                ac.setPurpose(c.getString(c.getColumnIndex(PURPOSE)));
                accs.add(ac);
            }
        }
        return accs;
    }

    public int  clearAccount(int friendId) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COMPLETE,1);
        return db.update(ACCOUNTING_TABLE,values,FRIEND_ID + "=?",new String[]{""+friendId});
    }

    /** method to add testing data into database */
    public void testing(){
        Friend fr=new Friend();
        fr.setName("Keval");
        fr.setFullName("Vatsal Jagani");
        fr.setMobileNo("8347919730");
        fr.setEmailAddress("vatsal@jagani.com");

        fr.setFriendId(insertFriend(fr));

        Accounting ac=new Accounting();
        ac.setFriendId(fr.getFriendId());
        ac.setPurpose("Cha");
        ac.setTime("No 2 11");
        ac.setDate("12/06/2016");
        ac.setComplete(0);
        ac.setAmount(-30);
        insertAccounting(ac);
    }

}

