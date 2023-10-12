package com.appnew.libmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.appnew.libmanager.DAO.UserDAO;
import com.appnew.libmanager.Model.User;
import com.appnew.libmanager.R;
import com.appnew.libmanager.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding loginBinding;
    private UserDAO userDAO;
    private List<User> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
        boolean isRemember = sharedPreferences.getBoolean("isRemember", false);

        if (isRemember) {
            String user = sharedPreferences.getString("UserName", "");
            String pass = sharedPreferences.getString("Pass", "");
            loginBinding.edtUserName.setText(user);
            loginBinding.edtPass.setText(pass);
            loginBinding.chkSavePass.setChecked(true);
        }

        userDAO = new UserDAO(this);
        list = userDAO.getListUser();
        loginBinding.btnLogin.setOnClickListener(view -> {
            String userName, pass;
            userName = loginBinding.edtUserName.getText().toString().trim();
            pass = loginBinding.edtPass.getText().toString().trim();
            String login = logIn(userName, pass);
            if (login != null){
                if (loginBinding.chkSavePass.isChecked()) {
                    saveAccount(userName, pass);
                }
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("user", login);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String logIn(String user, String pass) {
        for (User us : list) {
            if (us.getUserName().equals(user.trim())) {
                if (us.getPass().equals(pass.trim())) {
                    return us.getName();
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    private void saveAccount(String UserName, String Pass) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserName", UserName);
        editor.putString("Pass", Pass);
        editor.putBoolean("isRemember", true);
        editor.apply();
    }
}