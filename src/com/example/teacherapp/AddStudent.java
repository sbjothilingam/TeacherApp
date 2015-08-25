package com.example.teacherapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddStudent extends Activity{
	String classname="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newstudent);
		Bundle bun=getIntent().getExtras();
		classname=bun.getString("classname");
		TextView wel=(TextView)findViewById(R.id.welcomeaddstudent);
		wel.setText("Add students to class "+classname);
		SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
		try{
			Cursor c=db.rawQuery("select * from "+classname+";", null);
		}catch(Exception e){
			db.execSQL("create table "+classname+"(name VARCHAR, phone VARCHAR);");
		}
		db.close();
	}
	public void addStudent(View v){
		EditText name=(EditText)findViewById(R.id.studentname);
		EditText num=(EditText)findViewById(R.id.studentnumber);
		SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
		ContentValues c=new ContentValues();
		c.put("name", name.getText().toString());
		c.put("phone", num.getText().toString());
		db.insert(classname, null, c);
		name.setText("");
		num.setText("");
		Toast.makeText(this, "successfully added", Toast.LENGTH_SHORT).show();
		db.close();
	}
	public void finish(View v){
		Intent back=new Intent();
		back.setData(Uri.parse("finish"));
		setResult(RESULT_OK, back);
		finish();
	}
}
