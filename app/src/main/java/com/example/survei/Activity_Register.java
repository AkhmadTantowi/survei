package com.example.survei;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.survei.models.User;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Register extends AppCompatActivity {

    private TextInputEditText txNama, txEmail, txPassword;
    private Button btnRegister;
    private ProgressDialog loading;
    private Context mContext;
    private BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        initComponents();
    }

    private void initComponents() {
        txNama = findViewById(R.id.txNama);
        txEmail = findViewById(R.id.txEmail);
        txPassword = findViewById(R.id.txPassword);
        btnRegister = findViewById(R.id.btnRegister);
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading =ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requstRegister();
            }
        });
    }

    private void requstRegister() {
        mApiService.register(txNama.getText().toString().trim(), txEmail.getText().toString().trim(),
                txPassword.getText().toString().trim()).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                BaseResponse res = response.body();
                if (res.getStatus()){
                    //todo masih ngebug nunggu backend
                    User U = (User) res.getData();
                    setToken(U.getApiToken());
                    Intent intent = new Intent(Activity_Register.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Activity_Register.this, "Register Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                System.err.println(t.getMessage());
                Toast.makeText(Activity_Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    
    private void setToken(String token){
        SharedPreferences preferences = getSharedPreferences("USER", MODE_PRIVATE);
        preferences.edit().putString("TOKEN", token).apply();
    }
}
