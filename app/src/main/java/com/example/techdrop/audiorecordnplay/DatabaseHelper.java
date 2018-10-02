package com.example.techdrop.audiorecordnplay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

  //Database Version
  private static final int DATABASE_VERSION = 1;

  //Database Name
  private static final String DATABASE_NAME = "record_db";

  public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(Record.CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + Record.TABLE_NAME);
    onCreate(db);
  }

  public long insertRecord(String record){

    //get writable database
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();

    values.put(Record.COLUMN_NAME,record);

    long id = db.insert(Record.TABLE_NAME,null,values);

    db.close();

    return id;
  }

  public Record getRecord(long id){

    //get readable database
    SQLiteDatabase db = this.getWritableDatabase();

    Cursor cursor = db.query(Record.TABLE_NAME,
      new String[]{Record.COLUMN_ID,Record.COLUMN_NAME,Record.COLUMN_TIMESTAMP},
      Record.COLUMN_ID + "=?",new String[] { String.valueOf(id)},null,null,null,null);

    if (cursor != null){
      cursor.moveToFirst();
    }

    //prepare record object
    Record record = new Record(cursor.getInt(cursor.getColumnIndex(Record.COLUMN_ID)),
      cursor.getString(cursor.getColumnIndex(Record.COLUMN_NAME)),
      cursor.getString(cursor.getColumnIndex(Record.COLUMN_TIMESTAMP)),
      cursor.getString(cursor.getColumnIndex(Record.COLUMN_DEL_ST)));

    cursor.close();

    return record;

  }

  private List<Record> getAllRecords(){
    List<Record> records =  new ArrayList<>();

    //select all query
    String selectQuery = "SELECT * FROM "+Record.TABLE_NAME+" ORDER BY "+Record.COLUMN_TIMESTAMP + "DESC";

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor =  db.rawQuery(selectQuery, null);

    //Looping throough all rows adding to list
    if (cursor.moveToFirst()) {
      do {
        Record reco = new Record();
        reco.setId(cursor.getColumnIndex(Record.COLUMN_ID));
        reco.setName(cursor.getString(cursor.getColumnIndex(Record.COLUMN_NAME)));
        reco.setTimestamp(cursor.getString(cursor.getColumnIndex(Record.COLUMN_TIMESTAMP)));
        reco.setDel_st(cursor.getString(cursor.getColumnIndex(Record.COLUMN_DEL_ST)));

        records.add(reco);
      } while (cursor.moveToNext());
    }
      db.close();

      return records;
  }



}
