package com.example.survei;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.survei.models.User;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_login extends AppCompatActivity {

    private TextInputEditText txEmailLogin, txPassword;
    private Button btnLogin;
    private ProgressDialog loading;
    private Context mContext;
    private TextView belumPunya;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
//        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();
    }

    private void initComponents() {
        mApiService = UtilsApi.getApiService();
        txEmailLogin = (TextInputEditText) findViewById(R.id.txEmailLogin);
        txPassword = (TextInputEditText) findViewById(R.id.txPasswordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        belumPunya = (TextView) findViewById(R.id.belumPunya);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin();
            }
        });

        belumPunya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Activity_Register.class));
            }
        });
    }

    private void requestLogin() {
        mApiService.loginRequest(txEmailLogin.getText().toString().trim(), txPassword.getText().toString().trim()).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                BaseResponse res = response.body();
                if (res.getStatus()){
                    User U = (User) res.getData();
                    setToken(U.getApiToken());
                    Intent intent = new Intent(activity_login.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(activity_login.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                System.err.println(t.getMessage());
                Toast.makeText(activity_login.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setToken(String token){
        SharedPreferences preferences = getSharedPreferences("USER", MODE_PRIVATE);
        preferences.edit().putString("TOKEN", token).apply();
    }
}
