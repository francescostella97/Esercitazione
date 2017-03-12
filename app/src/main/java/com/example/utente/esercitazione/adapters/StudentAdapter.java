package com.example.utente.esercitazione.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.utente.esercitazione.R;
import com.example.utente.esercitazione.activities.AddStudentActivity;
import com.example.utente.esercitazione.activities.MainActivity;
import com.example.utente.esercitazione.model.Student;

import java.util.ArrayList;

/**
 * Created by ${Francesco} on 07/03/2017.
 */

public class StudentAdapter extends ArrayAdapter<Student> {
    private ArrayList<Student> dataSet;
    Context context;

    public StudentAdapter(Context context, int textViewResourceId, ArrayList<Student> dataSet){
        super(context,textViewResourceId,dataSet);
        this.dataSet = dataSet;
        this.context = context;
    }
    public void add(Student student){
        dataSet.add(student);
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater= (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.student_card,null);

        final TextView name = (TextView) convertView.findViewById(R.id.student_name);
        final TextView email = (TextView) convertView.findViewById(R.id.student_email);
        Button btnEmail = (Button) convertView.findViewById(R.id.btn_email);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent();
                emailIntent.setAction(Intent.ACTION_VIEW);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {dataSet.get(position).getEmail()});
                ((MainActivity)context).startActivity(emailIntent);
            }
        });
        Button btnGithub = (Button) convertView.findViewById(R.id.btn_github);
        btnGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse(dataSet.get(position).getGithub());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                ((MainActivity)context).startActivity(intent);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editing intent
                Intent intent = new Intent(((MainActivity)context), AddStudentActivity.class);
                intent.putExtra(AddStudentActivity.POSITION_KEY, position);
                intent.putExtra(AddStudentActivity.NAME_KEY, name.getText().toString());
                intent.putExtra(AddStudentActivity.EMAIL_KEY, email.getText().toString());
                intent.putExtra(AddStudentActivity.GITHUB_KEY, dataSet.get(position).getGithub());
                ((MainActivity) context).startActivityForResult(intent,2);
            }
        });

        Student student = dataSet.get(position);

        name.setText(student.getNome());
        email.setText(student.getEmail());


        return convertView;
    }

    public void setDataSet(ArrayList<Student> dataSet){
        this.dataSet = dataSet;
    }
}
