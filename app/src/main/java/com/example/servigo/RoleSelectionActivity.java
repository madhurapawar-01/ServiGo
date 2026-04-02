package com.example.servigo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class RoleSelectionActivity extends AppCompatActivity {

    LinearLayout cardCustomer, cardProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        cardCustomer = findViewById(R.id.cardCustomer);
        cardProvider = findViewById(R.id.cardProvider);

        cardCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("role", "customer");
            startActivity(intent);
        });

        cardProvider.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("role", "provider");
            startActivity(intent);
        });
    }
}