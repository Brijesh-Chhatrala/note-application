package com.notenow.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.notenow.model.Note;

import java.util.List;

public class DBManager {
    private DBManager instance;
    private Context context;
    private NoteDBOpenHelper databaseOpenHelper;
    private SQLiteDatabase dbReader;
    private SQLiteDatabase dbWriter;

    public DBManager(Context context) {
        this.context = context;
        databaseOpenHelper = new NoteDBOpenHelper(context);

        dbReader = databaseOpenHelper.getReadableDatabase();
        dbWriter = databaseOpenHelper.getWritableDatabase();
    }

    //create instance of DBManager class
    public synchronized DBManager getInstance(Context context) {
        if (instance == null) {
            instance = new DBManager(context);
        }
        return instance;
    }

    //add data or notes to database table
    public void addToDB(String title, String content, String time, String rank) {
        ContentValues cv = new ContentValues();
        cv.put(NoteDBOpenHelper.TITLE, title);
        cv.put(NoteDBOpenHelper.CONTENT, content);
        cv.put(NoteDBOpenHelper.TIME, time);
        cv.put(NoteDBOpenHelper.RANK, rank);
        dbWriter.insert(NoteDBOpenHelper.TABLE_NAME, null, cv);
    }


    public void readFromDB(List<Note> noteList) {
        Cursor cursor = dbReader.query(NoteDBOpenHelper.TABLE_NAME,
                null, null, null, null, null, null);
        fillData(noteList, cursor);
    }

    @SuppressLint("Range")
    private void fillData(List<Note> noteList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(NoteDBOpenHelper.ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NoteDBOpenHelper.TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NoteDBOpenHelper.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(NoteDBOpenHelper.TIME)));
                note.setRank(cursor.getString(cursor.getColumnIndex(NoteDBOpenHelper.RANK)));
                noteList.add(note);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    //update method for update data in Sqlite Database table
    public void updateNote(int noteID, String title, String content, String time, String rank) {
        ContentValues cv = new ContentValues();
        cv.put(NoteDBOpenHelper.ID, noteID);
        cv.put(NoteDBOpenHelper.TITLE, title);
        cv.put(NoteDBOpenHelper.CONTENT, content);
        cv.put(NoteDBOpenHelper.TIME, time);
        cv.put(NoteDBOpenHelper.RANK, rank);
        dbWriter.update(NoteDBOpenHelper.TABLE_NAME, cv, "_id = ?", new String[]{noteID + ""});
    }

    //Delete method for delete data in Sqlite table
    public void deleteNote(int noteID) {
        dbWriter.delete(NoteDBOpenHelper.TABLE_NAME, "_id = ?", new String[]{noteID + ""});
    }

    //Delete all method that delete all note in Sqlite Database table
    public void deleteAllNote() {
        dbWriter.delete(NoteDBOpenHelper.TABLE_NAME, null, null);
    }


    //readData method for read a particular note
    @SuppressLint("Range")
    public Note readData(int noteID) {
        Cursor cursor = dbReader.rawQuery(
                "SELECT * FROM note WHERE _id = ?", new String[]{noteID + ""});
        cursor.moveToFirst();
        Note note = new Note();
        note.setId(cursor.getInt(cursor.getColumnIndex(NoteDBOpenHelper.ID)));
        note.setTitle(cursor.getString(cursor.getColumnIndex(NoteDBOpenHelper.TITLE)));
        note.setContent(cursor.getString(cursor.getColumnIndex(NoteDBOpenHelper.CONTENT)));
        note.setRank(cursor.getString(cursor.getColumnIndex(NoteDBOpenHelper.RANK)));
        return note;
    }

    //filter method for filter note based on some criteria
    public void sortBy(List<Note> noteList, String orderBy) {
        Cursor cursor = dbReader.query(NoteDBOpenHelper.TABLE_NAME,
                null, null, null, null, null, orderBy);
        fillData(noteList, cursor);
    }
}


