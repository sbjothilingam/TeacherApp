package com.example.teacherapp;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newuser);
	}
	public void submit(View v){
		EditText uname=(EditText)findViewById(R.id.uname);
		EditText pass=(EditText)findViewById(R.id.passNew);
		EditText confirmPass=(EditText)findViewById(R.id.confirmPass);
		EditText school=(EditText)findViewById(R.id.sName);
		if(pass.getText().toString().equals(confirmPass.getText().toString())){
			SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
			ContentValues c=new ContentValues();
			c.put("username", uname.getText().toString());
			c.put("password", pass.getText().toString());
			c.put("school", school.getText().toString());
			db.insert("teacherdetails", null, c);
			db.close();
			Toast.makeText(this, "Registration Success", Toast.LENGTH_LONG).show();
			finish();
		}
		else{
			Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show();
		}
	}
}
