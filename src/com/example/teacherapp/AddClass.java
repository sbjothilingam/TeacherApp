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
import android.widget.Toast;

public class AddClass extends Activity{
	String name="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newclass);
		Bundle bun=getIntent().getExtras();
		name=bun.getString("name");
		SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
		try{
			Cursor c=db.rawQuery("select * from "+bun.get("name")+"classlist;", null);
		}catch(Exception e){
			db.execSQL("create table "+bun.getString("name")+"classlist(class VARCHAR);");
		}
		db.close();
	}
	public void addClass(View v){
		EditText className=(EditText)findViewById(R.id.classname);
		SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
		ContentValues c=new ContentValues();
		c.put("class", className.getText().toString());
		db.insert(name+"classlist", null, c);
		className.setText("");
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
