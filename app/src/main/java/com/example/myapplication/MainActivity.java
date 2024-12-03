package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> items = new ArrayList<>();

    private int [] itemImages = {R.drawable.BBQ-CSP, R.drawable.BBQ-NY, R.drawable.Build-Your-Own-CSP, R.drawable.Build-Your-Own-NY,
            R.drawable.Deluxe-CSP, R.drawable.Deluxe-NY, R.drawable.Meatzza-CSP, R.drawable.Meatzza-NY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rcview = findViewById(R.id.recyclerView);
        setupMenuItems();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupMenuItems(){
        String [] itemNames = getResources().getStringArray(R.array.itemNames);
        for (int i = 0; i < itemNames.length; i++){
            //FIND PRICE through helper method that calculates it
            items.add(new Item(itemNames[i], itemImages[i], ""));
        }
    }

}