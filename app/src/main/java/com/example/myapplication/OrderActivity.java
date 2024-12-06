package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private ArrayList<Pizza> pizzas;
    /**
     * The sales tax rate in NJ.
     */
    private final double TAX_RATE = 0.06625;

    /**
     * The unique order number for the order.
     */
    private int number;

    /**
     * The tax on the order.
     */
    private double tax;

    /**
     * The subtotal of the order.
     */
    private double price;

    /**
     * The total price of the order.
     */
    private double totalPrice;

    private ImageButton btn_back;

    private ListView currentOrderList;

    private TextView subTotal;

    private TextView salesTax;

    private TextView total;

    private Button placeOrderBtn;

    private Button clearOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.orders_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.orderLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pizzas = PizzasList.get().getPizzas();
        //Log.d("Pizzas: ", pizzas.toString()); //-> Shows the list of pizzas in current order
    }
}