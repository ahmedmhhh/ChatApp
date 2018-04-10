package com.example.ahmed.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;

import Model.MainResponse;
import Model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webServices.WebService;

public class RegisterActivity extends AppCompatActivity {
EditText username,email,password,repass;
Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.et_username);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        repass = (EditText) findViewById(R.id.et_repeat_password);
        signup = (Button) findViewById(R.id.btn_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!FUtilsValidation.isEmpty(username,"Please Enter Username")
                        && !FUtilsValidation.isEmpty(email,"Please Enter Email")
                        && FUtilsValidation.isValidEmail(email,"Please enter A valid Email")
                        && !FUtilsValidation.isEmpty(password,"Enter Password")
                        && !FUtilsValidation.isEmpty(repass,"Enter Password Again")
                        && FUtilsValidation.isPasswordEqual(password,repass,"password is not same !")){
                  final  User user = new User();
                    user.username = username.getText().toString();
                    user.email = email.getText().toString();
                    user.password = password.getText().toString();

                    WebService.getInstance().getApi().registerUser(user).enqueue(new Callback<MainResponse>() {
                        @Override
                        public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                            if(response.body().status==2){
                                Toast.makeText(RegisterActivity.this,response.body().message,Toast.LENGTH_LONG).show();
                            }else if (response.body().status==1){
                                Toast.makeText(RegisterActivity.this,response.body().message,Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(RegisterActivity.this,response.body().message,Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MainResponse> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this,t.getLocalizedMessage()+t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this,"check your data",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
