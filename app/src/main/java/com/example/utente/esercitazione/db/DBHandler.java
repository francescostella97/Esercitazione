package com.example.utente.esercitazione.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.utente.esercitazione.model.Student;

import java.util.ArrayList;

/**
 * Created by ${Francesco} on 09/03/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Esercitazione.db";
    private static final String TABLE_STUDENT = "Student";
    private static final String STUDENT_COLUMN_NAME = "Name";
    private static final String STUDENT_COLUMN_EMAIL = "Email";
    private static final String STUDENT_COLUMN_GITHUB = "Github";
    private static final String STUDENT_COLUMNP_AVATAR = "Avatar";
    private static final String STUDENT_COLUMN_ID = "id";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_STUDENT
                + "( id integer primary key, "
                + STUDENT_COLUMN_NAME + " text, "
                + STUDENT_COLUMN_EMAIL + " text, "
                + STUDENT_COLUMN_GITHUB + " text, "
                + STUDENT_COLUMNP_AVATAR + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS note");
        onCreate(db);
    }

    public boolean addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_COLUMN_NAME, student.getNome());
        values.put(STUDENT_COLUMN_EMAIL, student.getEmail());
        values.put(STUDENT_COLUMN_GITHUB, student.getGithub());
        values.put(STUDENT_COLUMNP_AVATAR, student.getAvatar());
        long id = db.insert(TABLE_STUDENT, null, values);
        db.close();
        student.setId((int) id);
        return true;
    }

    public boolean updateStudent(Student student){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_COLUMN_NAME, student.getNome());
        values.put(STUDENT_COLUMN_EMAIL, student.getEmail());
        values.put(STUDENT_COLUMN_GITHUB, student.getGithub());
        values.put(STUDENT_COLUMNP_AVATAR, student.getAvatar());
        db.update(TABLE_STUDENT,values,"id = ? ",new String[]{String.valueOf(student.getId())});
        db.close();
        return true;
    }

    public boolean deleteStudent(Student student){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_STUDENT,"id = ? ",new String[]{String.valueOf(student.getId())});
        db.close();
        return true;
    }

    public ArrayList<Student> getDataSet() {
        ArrayList<Student> dataSet = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_STUDENT, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Student student = new Student();
            student.setId(res.getInt(res.getColumnIndex(STUDENT_COLUMN_ID)));
            student.setNome(res.getString(res.getColumnIndex(STUDENT_COLUMN_NAME)));
            student.setEmail(res.getString(res.getColumnIndex(STUDENT_COLUMN_EMAIL)));
            student.setGithub(res.getString(res.getColumnIndex(STUDENT_COLUMN_GITHUB)));
            student.setAvatar(res.getString(res.getColumnIndex(STUDENT_COLUMNP_AVATAR)));
            dataSet.add(student);
            res.moveToNext();
        }
        db.close();
        return dataSet;
    }
}
