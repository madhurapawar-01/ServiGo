package com.example.servigo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    Button btnLogin;
    TextView txtSignup, txtForgotPassword;
    FirebaseAuth auth;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        role = getIntent().getStringExtra("role");

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignup = findViewById(R.id.txtSignup);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);

        btnLogin.setOnClickListener(v -> loginUser());

        txtSignup.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            intent.putExtra("role", role);
            startActivity(intent);
        });

        txtForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "coming soon!", Toast.LENGTH_SHORT).show();
        });
    }

    private void loginUser() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("enter your email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("enter your password");
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "welcome back!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "oops! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}