package com.example.servigo;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ProviderListActivity extends AppCompatActivity {

    RecyclerView recyclerProviders;
    ProviderAdapter adapter;
    List<Provider> providerList;
    FirebaseFirestore db;
    TextView txtCategory, btnBack;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list);

        db = FirebaseFirestore.getInstance();
        category = getIntent().getStringExtra("category");

        txtCategory = findViewById(R.id.txtCategory);
        btnBack = findViewById(R.id.btnBack);
        recyclerProviders = findViewById(R.id.recyclerProviders);

        txtCategory.setText(category + "s near you");

        providerList = new ArrayList<>();
        adapter = new ProviderAdapter(this, providerList);
        recyclerProviders.setLayoutManager(new LinearLayoutManager(this));
        recyclerProviders.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        loadProviders();
    }

    private void loadProviders() {
        db.collection("providers")
                .whereEqualTo("skill", category)
                .get()
                .addOnSuccessListener(query -> {
                    providerList.clear();
                    for (QueryDocumentSnapshot doc : query) {
                        Provider provider = new Provider(
                                doc.getString("name"),
                                doc.getString("skill"),
                                doc.getString("price"),
                                doc.getString("rating"),
                                doc.getString("distance"),
                                doc.getString("experience"),
                                doc.getId()
                        );
                        providerList.add(provider);
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}