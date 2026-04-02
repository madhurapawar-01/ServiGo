package com.example.servigo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    TextView txtGreeting, txtLocation;
    LinearLayout catPlumber, catElectrician, catCleaner,
            catPainter, catCarpenter, catMore;
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        txtGreeting = findViewById(R.id.txtGreeting);
        txtLocation = findViewById(R.id.txtLocation);

        catPlumber = findViewById(R.id.catPlumber);
        catElectrician = findViewById(R.id.catElectrician);
        catCleaner = findViewById(R.id.catCleaner);
        catPainter = findViewById(R.id.catPainter);
        catCarpenter = findViewById(R.id.catCarpenter);
        catMore = findViewById(R.id.catMore);

        loadUserName();
        setupCategories();
    }

    private void loadUserName() {
        String userId = auth.getCurrentUser().getUid();
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        String name = doc.getString("name");
                        txtGreeting.setText("hey " + name + " 👋");
                    }
                });
    }

    private void setupCategories() {
        catPlumber.setOnClickListener(v -> openCategory("plumber"));
        catElectrician.setOnClickListener(v -> openCategory("electrician"));
        catCleaner.setOnClickListener(v -> openCategory("cleaner"));
        catPainter.setOnClickListener(v -> openCategory("painter"));
        catCarpenter.setOnClickListener(v -> openCategory("carpenter"));
        catMore.setOnClickListener(v -> openCategory("other"));
    }

    private void openCategory(String category) {
        Intent intent = new Intent(this, ProviderListActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}