package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> items = new ArrayList<>();

    private int [] itemImages = {R.drawable.bbq_csp, R.drawable.bbq_ny, R.drawable.build_your_own_csp, R.drawable.build_your_own_ny,
            R.drawable.deluxe_csp, R.drawable.deluxe_ny, R.drawable.meatzza_csp, R.drawable.meatzza_ny};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rcview = findViewById(R.id.recyclerView);
        setupMenuItems();
        ItemsAdapter adapter = new ItemsAdapter(this, items);
        rcview.setAdapter(adapter);
        rcview.setLayoutManager(new LinearLayoutManager(this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupMenuItems(){
        String [] itemNames = getResources().getStringArray(R.array.itemNames);
        String [] itemDescriptions = getResources().getStringArray(R.array.itemDescriptions);
        String [] itemCrusts = getResources().getStringArray(R.array.itemCrusts);
        for (int i = 0; i < itemNames.length; i++){
            //FIND PRICE through helper method that calculates it
            items.add(new Item(itemNames[i], itemImages[i], itemDescriptions[i], itemCrusts[i]));
        }
    }

}