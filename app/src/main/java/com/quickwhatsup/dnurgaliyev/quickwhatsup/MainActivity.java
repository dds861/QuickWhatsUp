package com.quickwhatsup.dnurgaliyev.quickwhatsup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "myLogs";
    Button btn;
    EditText etPhoneNumber;
    ContactHelper contactHelper = new ContactHelper();
    boolean doesContactExist;

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn1);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        listView=(ListView)findViewById(R.id.listView);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Cursor cursor = getApplicationContext().getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI, null, null, null, null);
        GetRecentContacts getRecentContacts = new GetRecentContacts();
        MyAdapter adapter = new MyAdapter(getRecentContacts.getRecentContacts(cursor));


        listView.setAdapter(adapter);
        btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
//------------------------------------------------------Start: Read Contact
        String smsNumber0 = etPhoneNumber.getText().toString();
        String smsNumber1 = "8" + smsNumber0;
//------------------------------------------------------End:Read Contact
//------------------------------------------------------Start:Add contact
        //Checking if number exist in contacts
        doesContactExist = contactHelper.contactExists(getApplicationContext(), smsNumber1);
        Log.d(TAG, "Does Contact exist? :" + contactHelper.contactExists(getApplicationContext(), smsNumber1));
        if(doesContactExist==false){
            Log.d(TAG, "New contact inserted");
            contactHelper.insertContact(getContentResolver(),smsNumber1,smsNumber1);
        }




//------------------------------------------------------End:Add contact
//------------------------------------------------------Start: Send To whatsapp
        Log.d(TAG, "Going to sleep");
        try {
            Thread.sleep(5000);

            String smsNumber = "7" + smsNumber0;
            Intent sendIntent = new Intent();

            Log.d(TAG, "Opening whatsApp");
            //sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT," ");
            sendIntent.setType("text/plain");
            sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Error/n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
//------------------------------------------------------End: Send To whatsapp
//------------------------------------------------------Start: Delete contact
        if(doesContactExist==false){
            Log.d(TAG, "Deleting contact");
            contactHelper.deleteContact(getContentResolver(),smsNumber1);
        }


//------------------------------------------------------End: Delete contact
    }
//
}
