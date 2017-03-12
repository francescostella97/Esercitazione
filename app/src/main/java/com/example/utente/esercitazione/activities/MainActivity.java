package com.example.utente.esercitazione.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ListView;

import com.example.utente.esercitazione.R;
import com.example.utente.esercitazione.adapters.StudentAdapter;
import com.example.utente.esercitazione.db.DBHandler;
import com.example.utente.esercitazione.model.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by ${Francesco} on 07/03/2017.
 */

public class MainActivity extends AppCompatActivity {

    //list view items
    ListView listView;
    LinearLayoutManager layoutManager;
    StudentAdapter studentAdapter;
    //handler database locale
    public DBHandler db;

    //widgets
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);

        listView = (ListView) findViewById(R.id.list_view_activity_main);
        fab = (FloatingActionButton) findViewById(R.id.add_student);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddStudentActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        studentAdapter = new StudentAdapter(this,R.layout.student_card,db.getDataSet());

        listView.setAdapter(studentAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                //adding new student
                String name = data.getStringExtra(AddStudentActivity.NAME_KEY);
                String email = data.getStringExtra(AddStudentActivity.EMAIL_KEY);
                String github = data.getStringExtra(AddStudentActivity.GITHUB_KEY);
                Student student = new Student();
                student.setNome(name);
                student.setEmail(email);
                student.setGithub(github);
                System.out.println(student);
                studentAdapter.add(student);
                db.addStudent(student);
                listView.smoothScrollToPosition(studentAdapter.getCount());
            }
        }
        else if(requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                //editing a student
                int position = data.getIntExtra(AddStudentActivity.POSITION_KEY,-1);
                String name = data.getStringExtra(AddStudentActivity.NAME_KEY);
                String email = data.getStringExtra(AddStudentActivity.EMAIL_KEY);
                String github = data.getStringExtra(AddStudentActivity.GITHUB_KEY);
                Student student = studentAdapter.getItem(position);

                student.setNome(name);
                student.setEmail(email);
                student.setGithub(github);

                db.updateStudent(student);
            }
            if (resultCode == Activity.RESULT_OK) {
                if (data.getFlags() == 10) {
                    //deleting a student
                    int position = data.getIntExtra(AddStudentActivity.POSITION_KEY, -1);
                    System.out.println(position+"..");
                    studentAdapter.remove(studentAdapter.getItem(position-1));
                    db.deleteStudent(studentAdapter.getItem(position));
                }
            }
        }
    }

    public ArrayList<Student> fetchFromJSON(){
        ArrayList<Student> arrayList = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(readLocalJSON());
            for (int i = 0; i < jsonArray.length(); i++) {
                Student student = new Student(jsonArray.getJSONObject(i));
                arrayList.add(student);
            }
        }catch (JSONException jsone){
            jsone.printStackTrace();
        }
        return arrayList;
    }

    private String readLocalJSON(){
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try (InputStream is = getResources().openRawResource(R.raw.students)) {
            Reader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }
}
