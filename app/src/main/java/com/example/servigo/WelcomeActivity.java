package com.example.servigo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    Button btnGetStarted;
    TextView txtAlreadyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnGetStarted = findViewById(R.id.btnGetStarted);
        txtAlreadyAccount = findViewById(R.id.txtAlreadyAccount);

        btnGetStarted.setOnClickListener(v -> {
            startActivity(new Intent(this, RoleSelectionActivity.class));
        });

        txtAlreadyAccount.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}