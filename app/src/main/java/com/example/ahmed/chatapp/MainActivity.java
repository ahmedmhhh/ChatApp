package com.example.ahmed.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Model.User;
import utils.Session;

public class MainActivity extends AppCompatActivity {
    TextView textView ;
    Button login,register,logout,chatrooms;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        logout = (Button) findViewById(R.id.logout);
        chatrooms = (Button) findViewById(R.id.chatroombtn);

        chatrooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ChatRoomsActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.getInstance().logoutAndGoTologin(MainActivity.this);
            }
        });

        user = Session.getInstance().getUser();

            if(user!=null){
                textView.setText(user.email);
            }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(getBaseContext(),LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(new Intent(getBaseContext(),RegisterActivity.class));
            }
        });
    }
}
