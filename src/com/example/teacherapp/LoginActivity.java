package com.example.teacherapp;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
		db.execSQL("create table if not exists teacherdetails(username VARCHAR, password VARCHAR, school VARCHAR);");
		Cursor c=db.rawQuery("select * from teacherdetails;", null);
		c.moveToFirst();
		if(c.getCount()==1){
			Intent in = new Intent("android.intent.action.HOME");
			Bundle bun=new Bundle();
			bun.putString("name", c.getString(0));
			in.putExtras(bun);
			startActivity(in);
			c.close();
			db.close();
			finish();
		}
		else{
			setContentView(R.layout.activity_login);
		}
	}
	public void navigateToNewUser(View v){
		Intent in=new Intent("android.intent.action.NEW");
		startActivity(in);
	}
	public void doValidate(View v){
		EditText uname=(EditText)findViewById(R.id.uname);
		EditText password=(EditText)findViewById(R.id.passNew);
		SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
		//Cursor c=db.rawQuery("select * from teacherdetails where uname="+uname.getText().toString()+" and password="+password.getText().toString(), null);
		try{
		Cursor c=db.rawQuery("select * from teacherdetails;", null);
		c.moveToFirst();
		if(c.getCount()==1){
			if(c.getString(0).equals(uname.getText().toString())&&c.getString(1).equals(password.getText().toString())){
				Intent in = new Intent("android.intent.action.HOME");
				Bundle bun=new Bundle();
				bun.putString("name", uname.getText().toString());
				in.putExtras(bun);
				startActivity(in);
				finish();
				c.close();
				db.close();
			}
			else{
				Toast.makeText(this, uname.getText().toString()+" doesn't exist", Toast.LENGTH_LONG).show();
				c.close();
				db.close();
			}
		}else{
			Toast.makeText(this, uname.getText().toString()+" doesn't exist", Toast.LENGTH_LONG).show();
			c.close();
			db.close();
		}
		}catch(Exception e){
			Toast.makeText(this, uname.getText().toString()+" doesn't exist", Toast.LENGTH_LONG).show();
		}
	}
}
