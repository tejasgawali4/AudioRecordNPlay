package com.example.techdrop.audiorecordnplay;

public class Record {
  public static final String TABLE_NAME = "records";

  public static final String COLUMN_ID = "id";
  public static final String COLUMN_NAME = "name";
  public static final String COLUMN_TIMESTAMP = "timestamp";
  public static final String COLUMN_DEL_ST = "del_st";

  private int id;
  private String name;
  private String timestamp;
  private String del_st;

//  Create table SQL Query
  public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
  + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
  + COLUMN_NAME + " TEXT,"
  + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
  + COLUMN_DEL_ST + " INTEGER"
  + ")";


  public Record(){

  }


  public Record(int id, String name, String timestamp, String del_st) {
    this.id = id;
    this.name = name;
    this.timestamp = timestamp;
    this.del_st = del_st;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getDel_st() {
    return del_st;
  }

  public void setDel_st(String del_st) {
    this.del_st = del_st;
  }
}
