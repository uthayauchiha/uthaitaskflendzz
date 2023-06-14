package com.uthai.uthaitask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class UserInfoActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{
TextView id,name,email,address,company,phoneno,website,compname;
String strName,strId,strEmail,strStreet,strSuite,strCity,strPhone,strCompanyName,strWebsite,strPincode;
    boolean net;
    private NetworkStateReceiver networkStateReceiver;
    ViewGroup viewGroup;
    AlertDialog alertDialog;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        id=findViewById(R.id.id);
        name=findViewById(R.id.nameTextView);
        email=findViewById(R.id.emailTextView);
        address=findViewById(R.id.addressTextView);
        company=findViewById(R.id.companyTextView);
        phoneno=findViewById(R.id.phoneTextView);
        website=findViewById(R.id.websiteTextView);
compname=findViewById(R.id.companyTextUi);
        viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.internet_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog = builder.create();
        startNetworkBroadcastReceiver(this);
        Intent intent = getIntent();
       strId =intent.getStringExtra("id");
        strName=intent.getStringExtra("name");
        StringBuilder camelCaseString = new StringBuilder();

        String[] words = strName.split("\\s+");

        for (String word : words) {
            if (!word.isEmpty()) {
                String firstLetter = word.substring(0, 1).toUpperCase();
                String restOfWord = word.substring(1).toLowerCase();
                camelCaseString.append(firstLetter).append(restOfWord);
            }
        }

        String camelCaseResult = camelCaseString.toString();
        strEmail=intent.getStringExtra("email");

       strStreet =intent.getStringExtra("street");
        strSuite=intent.getStringExtra("suite" );
       strCity =intent.getStringExtra("city");
       strPincode =intent.getStringExtra("zipcode");
       strPhone =intent.getStringExtra("phoneno" );
        strCompanyName=intent.getStringExtra("cname");
       strWebsite =intent.getStringExtra("website");
       id.setText("Employee Id: "+strId);
       name.setText("Name: "+camelCaseResult);
        email.setText("Email: "+strEmail.toLowerCase(Locale.ROOT));
        address.setText("Address: \n"+strSuite+","+strCity+"-"+strPincode);
        phoneno.setText("Phone Number: "+strPhone);
        company.setText("Company Name: "+strCompanyName);
        website.setText("Website: "+strWebsite);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + strEmail.toLowerCase(Locale.ROOT)));
                intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Hy uthai test mail");
               startActivity(intent);
            }
        });
phoneno.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Uri dialUri = Uri.parse("tel:" +strPhone );
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, dialUri);
        startActivity(dialIntent);
    }
});
compname.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent1=new Intent(UserInfoActivity.this,MainActivity2.class);
        startActivity(intent1);
    }
});
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void networkAvailable() {
        alertDialog.dismiss();
    }

    @Override
    public void networkUnavailable() {
        if (!((Activity) UserInfoActivity.this).isFinishing()) {
            showCustomDialog();
        }

    }
    public void startNetworkBroadcastReceiver(Context currentContext) {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener((NetworkStateReceiver.NetworkStateReceiverListener) currentContext);
        registerNetworkBroadcastReceiver(currentContext);
    }


    public void registerNetworkBroadcastReceiver(Context currentContext) {
        currentContext.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void unregisterNetworkBroadcastReceiver(Context currentContext) {
        currentContext.unregisterReceiver(networkStateReceiver);
    }

    private void showCustomDialog()
    {
        alertDialog.setCancelable(false);
        alertDialog.show();

    }
    @Override
    protected void onPause() {
        startNetworkBroadcastReceiver(this);
        unregisterNetworkBroadcastReceiver(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        startNetworkBroadcastReceiver(this);
        registerNetworkBroadcastReceiver(this);
        super.onResume();


    }
}