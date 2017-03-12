package com.example.utente.esercitazione.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ${Francesco} on 07/03/2017.
 */

public class Student {
    private final static String NAME_KEY = "nome";
    private final static String EMAIL_KEY = "email";
    private final static String GITHUB_KEY = "github";
    private final static String AVATAR_KEY = "avatar";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String nome;
    private String email;
    private String github;
    private String avatar;
    public Student (){}
    public Student(String n, String e, String g, String a){
        nome = n;
        email = e;
        github = g;
        avatar = a;
    }
    public Student(JSONObject jsonStudent){
        try {
            this.nome = jsonStudent.getString(NAME_KEY);
            this.email = jsonStudent.getString(EMAIL_KEY);
            this.github = jsonStudent.getString(GITHUB_KEY);
            this.avatar = jsonStudent.getString(AVATAR_KEY);
        } catch (JSONException jsone){
            jsone.printStackTrace();
        }

    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", github='" + github + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
