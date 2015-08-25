package com.example.teacherapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayStudents extends Activity{
	int request_code=1;
	String classname="";
	String classnameModified="";
	String[] studentslist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_displaystudent);
		TextView wel=(TextView)findViewById(R.id.welcomeclass);
		Bundle bun=getIntent().getExtras();
		classname=bun.getString("classname");
		if(classname.contains("-"))
			classnameModified=classname.substring(0,classname.lastIndexOf("-"))+classname.substring(classname.indexOf("-")+1,classname.length());
		wel.setText("Class list for "+classname);
		ListView list=(ListView)findViewById(R.id.liststudents);
		SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
		try{
			Cursor c=db.rawQuery("select * from "+classnameModified+";", null);
			c.moveToFirst();
			if(c.getCount()==0){
				String none[]={"empty"};
				list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, none));
				c.close();
				db.close();
			}else{
				studentslist=new String[c.getCount()];
				int i=0;
				do{
					studentslist[i]=c.getString(0);
					i++;
				}while(c.moveToNext());
				list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, studentslist));
				c.close();
				db.close();
			}
		}catch(Exception e){
			String none[]={"empty"};
			list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, none));
			db.close();
		}
	}
	public void sendMsg(View v){
		ListView list=(ListView)findViewById(R.id.liststudents);
		//find how to find which item has been clicked
		ArrayList<String> names=new ArrayList<String>();
		String numbers="";
		for(int i=0;i<list.getCount();i++){
			if(list.isItemChecked(i)){
				names.add(list.getItemAtPosition(i).toString());
			}
		}
		SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
		for(int i=0;i<names.size();i++){
			Cursor c=db.rawQuery("select * from "+classnameModified+" where name='"+names.get(i)+"';",null);
			c.moveToFirst();
			numbers+=c.getString(1)+";";
			c.close();
		}
		db.close();
		Intent in = new Intent("android.intent.action.SENDMSG");
		Bundle bun=new Bundle();
		bun.putString("numbers", numbers);
		in.putExtras(bun);
		startActivity(in);
	}
	public void addStudent(View v){
		Intent in=new Intent("android.intent.action.STUDENT");
		Bundle bun=new Bundle();
		bun.putString("classname", classnameModified);
		in.putExtras(bun);
		startActivityForResult(in, request_code);
	}
	public void onActivityResult(int reqCode, int resCode, Intent data){
		ListView list=(ListView)findViewById(R.id.liststudents);
		if(reqCode==request_code){
			if(resCode==RESULT_OK){
				SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
				Cursor c=db.rawQuery("select * from "+classnameModified+";", null);
				c.moveToFirst();
				studentslist=new String[c.getCount()];
				int i=0;
				do{
					studentslist[i]=c.getString(0);
					i++;
				}while(c.moveToNext());
				list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, studentslist));
				c.close();
				db.close();
			}
		}
	}
}
