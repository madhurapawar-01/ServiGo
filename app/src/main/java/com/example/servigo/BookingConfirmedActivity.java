package com.example.servigo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BookingConfirmedActivity extends AppCompatActivity {

    TextView txtBookingId, txtProviderName;
    Button btnGoHome, btnTrack;
    String bookingId, providerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmed);

        bookingId = getIntent().getStringExtra("bookingId");
        providerName = getIntent().getStringExtra("providerName");

        txtBookingId = findViewById(R.id.txtBookingId);
        txtProviderName = findViewById(R.id.txtProviderName);
        btnGoHome = findViewById(R.id.btnGoHome);
        btnTrack = findViewById(R.id.btnTrack);

        txtBookingId.setText("booking id: #" + bookingId.substring(0, 6));
        txtProviderName.setText(providerName + " will be notified shortly!");

        btnGoHome.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });

        btnTrack.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrackingActivity.class);
            intent.putExtra("bookingId", bookingId);
            startActivity(intent);
        });
    }
}