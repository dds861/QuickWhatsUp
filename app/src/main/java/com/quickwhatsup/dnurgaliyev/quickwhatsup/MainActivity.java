package com.quickwhatsup.dnurgaliyev.quickwhatsup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    EditText MA_ed_address;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.btn1);
        MA_ed_address=(EditText)findViewById(R.id.MA_ed_address);

        numberPicker =(NumberPicker)findViewById(R.id.numberPicker) ;
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setWrapSelectorWheel(false);

        btn.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {


        String smsNumber = "77017774941";
        Intent sendIntent = new Intent();
        try {

            //sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Здравствуйте,\n" +
                            "можно заказать воду.\n" +
                            "АДРЕС: " + String.valueOf(MA_ed_address.getText())+"\n" +
                            "БУТЫЛЕЙ: " + String.valueOf(numberPicker.getValue())+"\n" +
                            "Спасибо.");
            sendIntent.setType("text/plain");
            sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch(Exception e) {
            Toast.makeText(this, "Error/n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}