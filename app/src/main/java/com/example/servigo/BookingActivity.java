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

public class BookingActivity extends AppCompatActivity {

    TextView txtProviderName, txtProviderSkill, btnBack;
    EditText inputDate, inputTime, inputAddress, inputProblem;
    Button btnConfirm;
    FirebaseAuth auth;
    FirebaseFirestore db;
    String providerId, providerName, providerSkill, providerPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        providerId = getIntent().getStringExtra("providerId");
        providerName = getIntent().getStringExtra("providerName");
        providerSkill = getIntent().getStringExtra("providerSkill");
        providerPrice = getIntent().getStringExtra("providerPrice");

        txtProviderName = findViewById(R.id.txtProviderName);
        txtProviderSkill = findViewById(R.id.txtProviderSkill);
        inputDate = findViewById(R.id.inputDate);
        inputTime = findViewById(R.id.inputTime);
        inputAddress = findViewById(R.id.inputAddress);
        inputProblem = findViewById(R.id.inputProblem);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnBack = findViewById(R.id.btnBack);

        txtProviderName.setText(providerName);
        txtProviderSkill.setText(providerSkill + " • ₹" + providerPrice + "/hr");

        btnBack.setOnClickListener(v -> finish());
        btnConfirm.setOnClickListener(v -> confirmBooking());
    }

    private void confirmBooking() {
        String date = inputDate.getText().toString().trim();
        String time = inputTime.getText().toString().trim();
        String address = inputAddress.getText().toString().trim();
        String problem = inputProblem.getText().toString().trim();

        if (TextUtils.isEmpty(date)) {
            inputDate.setError("pick a date");
            return;
        }
        if (TextUtils.isEmpty(time)) {
            inputTime.setError("pick a time");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            inputAddress.setError("enter your address");
            return;
        }
        if (TextUtils.isEmpty(problem)) {
            inputProblem.setError("describe the problem");
            return;
        }

        String customerId = auth.getCurrentUser().getUid();

        Map<String, Object> booking = new HashMap<>();
        booking.put("customerId", customerId);
        booking.put("providerId", providerId);
        booking.put("providerName", providerName);
        booking.put("providerSkill", providerSkill);
        booking.put("date", date);
        booking.put("time", time);
        booking.put("address", address);
        booking.put("problem", problem);
        booking.put("status", "pending");

        db.collection("bookings")
                .add(booking)
                .addOnSuccessListener(ref -> {
                    Toast.makeText(this, "booking confirmed! 🎉", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, BookingConfirmedActivity.class);
                    intent.putExtra("bookingId", ref.getId());
                    intent.putExtra("providerName", providerName);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "oops! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}