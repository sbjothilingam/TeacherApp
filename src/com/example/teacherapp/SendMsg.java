package com.example.teacherapp;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendMsg extends Activity{
	String numbers="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendmsg);
		Bundle bun=getIntent().getExtras();
		EditText num=(EditText)findViewById(R.id.rephone);
		numbers=bun.getString("numbers");
		num.setText(bun.getString("numbers"));
	}
	public void sendMsg(View v){
		EditText msg=(EditText)findViewById(R.id.message);
		SmsManager sms=SmsManager.getDefault();
		sms.sendTextMessage(numbers, null, msg.getText().toString(), null, null);
		Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
		finish();
	}
}
