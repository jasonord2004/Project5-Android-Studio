package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private CustomListAdapter customListAdapter;
    private ArrayList<Pizza> pizzas;
    private ArrayList<Integer> pizzaImages;
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

    private ListView currentOrderList;

    private TextView currentOrder;
    private TextView subTotal;

    private TextView salesTax;

    private TextView total;

    private Button placeOrderBtn;

    private Button clearOrderBtn;

    private ImageButton backBtn;

    private ArrayList<Order> orders;

    private Spinner orderNumberBox;

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
        currentOrder = (TextView) findViewById(R.id.currentOrder);
        subTotal = (TextView) findViewById(R.id.subtotal);
        salesTax = (TextView) findViewById(R.id.salesTax);
        total = (TextView) findViewById(R.id.total);
        orders = OrdersList.get().getOrders();
        pizzas = PizzasList.get().getPizzas();
        emptyOrders();
        emptyPizzas();
        String currentOrderNumberDisplay = "Current Order " + number;
        currentOrder.setText(currentOrderNumberDisplay);
        pizzaImages = new ArrayList<Integer>();
        setPizzaImages();
        currentOrderList = (ListView) findViewById(R.id.currentOrderList);
        customListAdapter = new CustomListAdapter(getApplicationContext(), pizzas, pizzaImages);
        currentOrderList.setAdapter(customListAdapter);
        currentOrderList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(OrderActivity.this)
                    .setTitle("Do you want to remove this pizza from your order?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                price -= pizzas.remove(position).price();
                                customListAdapter.notifyDataSetChanged();
                                setSubTotal();
                                setTax();
                                setTotal();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                return false;
            }
        });

        placeOrderBtn = (Button) findViewById(R.id.placeOrderBtn);
        clearOrderBtn = (Button) findViewById(R.id.clearOrderBtn);
        backBtn = findViewById(R.id.btn_back);
        placeOrderBtn.setOnClickListener(this::placeOrder);
        clearOrderBtn.setOnClickListener(this::defaultCurrentOrderDisplay);
        backBtn.setOnClickListener(this::backBtnClicked);
    }

    public void emptyOrders(){
        if (orders.isEmpty()){ number = 1;}
        else {number = orders.size()+1;}
    }
    public void emptyPizzas(){
        if (pizzas.isEmpty()){
            subTotal.setText("Subtotal: $0.00");
            salesTax.setText("Sales Tax: $0.00");
            total.setText("Total: $0.00");
        } else {
            setSubTotal();
            setTax();
            setTotal();
        }
    }
    public void placeOrder(View view){
        if (pizzas.size() >= 1) {
            Order order = new Order(number, pizzas);
            orders.add(order);

            defaultCurrentOrderDisplay(view);
            number += 1;
            String currentOrderDisplay = "Current Order " + number;
            currentOrder.setText(currentOrderDisplay);
            currentOrderList.setAdapter(new CustomListAdapter(getApplicationContext(), pizzas, pizzaImages));
            Toast toast = Toast.makeText(getApplicationContext(), "Order placed!", Toast.LENGTH_SHORT);
            toast.show();
        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
            builder.setTitle("There are no pizzas to order!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
    }
    public void defaultCurrentOrderDisplay(View view){
        PizzasList.get().getPizzas().clear();
        String subTotalDisplay = "Subtotal: $0.00";
        price = 0;
        subTotal.setText(subTotalDisplay);
        String salesTaxDisplay = "Sales Tax: $0.00";
        tax = 0;
        salesTax.setText(salesTaxDisplay);
        String totalDisplay = "Total: $0.00";
        totalPrice = 0;
        total.setText(totalDisplay);
        if(customListAdapter == null){
            return;
        }
        currentOrderList.setAdapter(new CustomListAdapter(getApplicationContext(), pizzas, pizzaImages));

    }

    public void backBtnClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setPizzaImages(){
        for (Pizza pizza : pizzas){
            pizzaImages.add(getImage(pizza));
        }
    }

    private int getImage(Object pizza){
        if (pizza instanceof BBQChicken){
            BBQChicken p = (BBQChicken) pizza;
            if (p.getCrust() == Crust.PAN) return R.drawable.bbq_csp;
            return R.drawable.bbq_ny;
        }
        else if (pizza instanceof BuildYourOwn){
            BuildYourOwn p = (BuildYourOwn) pizza;
            if (p.getCrust() == Crust.PAN) return R.drawable.build_your_own_csp;
            return R.drawable.build_your_own_ny;
        }
        else if (pizza instanceof Deluxe){
            Deluxe p = (Deluxe) pizza;
            if (p.getCrust() == Crust.DEEP_DISH) return R.drawable.deluxe_csp;
            return R.drawable.deluxe_ny;
        }
        else if (pizza instanceof Meatzza){
            Meatzza p = (Meatzza) pizza;
            if (p.getCrust() == Crust.STUFFED) return R.drawable.meatzza_csp;
            return R.drawable.meatzza_ny;
        }
        return -1;
    }

    private void setSubTotal(){
        for (Pizza pizza : pizzas){
            price += pizza.price();
        }
        String display = "Subtotal: $" + String.format("%.02f", price);
        subTotal.setText(display);
    }

    private void setTax(){
        tax = price * TAX_RATE;
        String display = "Sales Tax: $" + String.format("%.02f", tax);
        salesTax.setText(display);
    }

    private void setTotal(){
        totalPrice = price + tax;
        String display = "Total: $" + String.format("%.02f", totalPrice);
        total.setText(display);
    }


}