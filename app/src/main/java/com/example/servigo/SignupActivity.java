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
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText inputName, inputEmail, inputPhone, inputPassword;
    Button btnSignup;
    TextView txtLogin;
    FirebaseAuth auth;
    FirebaseFirestore db;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        role = getIntent().getStringExtra("role");

        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhone = findViewById(R.id.inputPhone);
        inputPassword = findViewById(R.id.inputPassword);
        btnSignup = findViewById(R.id.btnSignup);
        txtLogin = findViewById(R.id.txtLogin);

        btnSignup.setOnClickListener(v -> signupUser());

        txtLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void signupUser() {
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String phone = inputPhone.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            inputName.setError("enter your name");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("enter your email");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            inputPhone.setError("enter your phone");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("enter a password");
            return;
        }
        if (password.length() < 6) {
            inputPassword.setError("password must be 6 characters");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(task -> {
                    String userId = auth.getCurrentUser().getUid();

                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name);
                    user.put("email", email);
                    user.put("phone", phone);
                    user.put("role", role);

                    db.collection("users").document(userId)
                            .set(user)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(this, "account created!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "oops! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}