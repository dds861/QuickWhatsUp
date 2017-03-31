package com.quickwhatsup.dnurgaliyev.quickwhatsup;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by D.Nurgaliyev on 31.03.2017.
 * http://stackoverflow.com/questions/7995183/how-to-retrieve-recent-call-list-and-favourites-list-in-android
 */

public class GetRecentContacts {

        Map getRecentContacts(Cursor cursor){
            Map contactMap = new HashMap();

            Uri queryUri = android.provider.CallLog.Calls.CONTENT_URI;

            String[] projection = new String[]{
                    ContactsContract.Contacts._ID,
                    CallLog.Calls._ID,
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.CACHED_NAME,
                    CallLog.Calls.DATE};

            String sortOrder = String.format("%s limit 500 ", CallLog.Calls.DATE + "ASC");

            while (cursor.moveToNext()) {
                String phoneNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
//                Log.d("myLogs", phoneNumber);

                String title = (cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));

                if(phoneNumber==null||title==null)continue;

                String uri = "tel:" + phoneNumber ;

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                String intentUriString = intent.toUri(1);
                Log.d("myLogs", intentUriString);
                contactMap.put(title,uri);

            }
            cursor.close();
            return contactMap;
        }



}
