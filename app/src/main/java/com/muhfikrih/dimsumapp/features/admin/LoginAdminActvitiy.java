package com.muhfikrih.dimsumapp.features.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muhfikrih.dimsumapp.R;
import com.muhfikrih.dimsumapp.helpers.DataHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginAdminActvitiy extends AppCompatActivity {
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnUser)
    TextView btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (username.equals("RajaDimsum") && password.equals("RajaDimsum")) {
                    DataHelper dataHelper = new DataHelper(LoginAdminActvitiy.this);
                    SharedPreferences preferences = dataHelper.getPrefs();
                    preferences.edit().putString("Username", username).apply();
                    preferences.edit().putString("Role", "Admin").apply();

                    Intent intent = new Intent(LoginAdminActvitiy.this, HomeAdminActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginAdminActvitiy.this, "Username atau Password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}