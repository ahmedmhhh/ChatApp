package com.example.ahmed.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;

import Model.LoginResponse;
import Model.MainResponse;
import Model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Session;
import webServices.WebService;

public class LoginActivity extends AppCompatActivity {
EditText email,password;
Button loginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        loginbtn = (Button) findViewById(R.id.btn_login);

        loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!FUtilsValidation.isEmpty(email,"Please Enter Email")
                        && FUtilsValidation.isValidEmail(email,"Please enter A valid Email")
                        && !FUtilsValidation.isEmpty(password,"Enter Password")){
                   final User user = new User();
                    user.email = email.getText().toString();
                    user.password = password.getText().toString();

                    WebService.getInstance().getApi().loginUser(user).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if(response.body().status==2){
                                Toast.makeText(LoginActivity.this,response.body().message,Toast.LENGTH_LONG).show();
                            }else if (response.body().status==1){
                                Toast.makeText(LoginActivity.this,response.body().message,Toast.LENGTH_LONG).show();
                                user.username = response.body().user.user_name;
                                user.id=Integer.parseInt(response.body().user.id);
                                user.isAdmin = response.body().user.is_user_admin.equals("1");
                                Session.getInstance().loginUser(user);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this,response.body().message,Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(LoginActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        });

    }
}
