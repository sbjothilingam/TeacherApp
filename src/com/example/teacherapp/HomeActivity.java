package com.example.teacherapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity{
	String[] classList;
	String name="";
	int request_code=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Bundle bun=getIntent().getExtras();
		TextView wel=(TextView)findViewById(R.id.welcome);
		ListView list=(ListView)findViewById(R.id.list);
		wel.setText("Hi "+bun.getString("name"));
		name=bun.getString("name");
		SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
		try{
			Cursor c=db.rawQuery("select * from "+bun.getString("name")+"classlist;", null);
			if(c.getCount()==0){
				String[] none={"empty"};
				list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,none));
			}
			else{
				c.moveToFirst();
				classList=new String[c.getCount()];
				int i=0;
				do{
					classList[i]=c.getString(0);
					i++;
				}while(c.moveToNext());
				list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,classList));
				c.close();
				db.close();
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent in=new Intent("android.intent.action.DISPSTUDENT");
						Bundle bun=new Bundle();
						bun.putString("classname", classList[position]);
						in.putExtras(bun);
						startActivity(in);
					}
				});
			}
		}catch(Exception e){
			String[] none={"empty"};
			list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,none));
		}
	}
	
	public void add(View v){
		Intent in=new Intent("android.intent.action.CLASS");
		Bundle bun=new Bundle();
		bun.putString("name", name);
		in.putExtras(bun);
		startActivityForResult(in,request_code);
	}
	public void onActivityResult(int reqCode, int resCode, Intent data){
		if(reqCode == request_code){
			if(resCode == RESULT_OK){
				SQLiteDatabase db=openOrCreateDatabase("teacher", MODE_PRIVATE, null);
				Cursor c=db.rawQuery("select * from "+name+"classlist;", null);
				c.moveToFirst();
				classList=new String[c.getCount()];
				int i=0;
				do{
					classList[i]=c.getString(0);
					i++;
				}while(c.moveToNext());
				ListView list=(ListView)findViewById(R.id.list);
				list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,classList));
				c.close();
				db.close();
			}
		}
	}
	
}
