package com.example.utente.esercitazione.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.utente.esercitazione.R;

import java.util.zip.Inflater;

/**
 * Created by ${Francesco} on 08/03/2017.
 */

public class AddStudentActivity extends AppCompatActivity {
    //intent Keys
    public static final String NAME_KEY = "NAME";
    public static final String EMAIL_KEY = "EMAIL";
    public static final String GITHUB_KEY = "GITHUB";
    public static final String POSITION_KEY = "POSITION";
    public static final int RESULT_CODE = 1;
    //widgets
    Button add;
    EditText nameET, emailET, githubET;

    int action;
    int position;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(action == 1) inflater.inflate(R.menu.edit_student_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_action_delete:
                createDeleteIntent();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.activity_addstudent);

        nameET = (EditText) findViewById(R.id.student_nameET);
        emailET = (EditText) findViewById(R.id.student_emailET);
        githubET = (EditText) findViewById(R.id.student_githubET);

        add = (Button) findViewById(R.id.btn_add_student);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(action == 0) {
                    Intent intent = new Intent();
                    intent.putExtra(NAME_KEY, nameET.getText().toString());
                    intent.putExtra(EMAIL_KEY, emailET.getText().toString());
                    intent.putExtra(GITHUB_KEY, githubET.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else if (action == 1){
                    Intent intent = new Intent();
                    intent.putExtra(POSITION_KEY,position);
                    intent.putExtra(NAME_KEY, nameET.getText().toString());
                    intent.putExtra(EMAIL_KEY, emailET.getText().toString());
                    intent.putExtra(GITHUB_KEY, githubET.getText().toString());
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
            }
        });

        Intent intent = getIntent();
        if(intent != null){
            if(intent.getStringExtra(NAME_KEY)!=null){
                //editing
                action = 1;
                position = intent.getIntExtra(POSITION_KEY,-1);
                nameET.setText(intent.getStringExtra(NAME_KEY));
                emailET.setText(intent.getStringExtra(EMAIL_KEY));
                githubET.setText(intent.getStringExtra(GITHUB_KEY));
                add.setText("Save");
            }
        }
    }

    public void createDeleteIntent(){
        Intent intent = new Intent();
        intent.putExtra(POSITION_KEY,position);
        intent.addFlags(10);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}
