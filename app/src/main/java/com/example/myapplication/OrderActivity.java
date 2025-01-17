package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Ankit Mithbavkar, Jason Ordonez
 */
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

    private ArrayList<Integer> orderNumbers;

    private ListView placedOrderList;

    private TextView orderTotal;
    private CustomListAdapter selectedListAdapter;

    private Button confirmOrdersBtn;

    private Button cancelOrdersBtn;

    private int selectedNumber;

    private ArrayList<Pizza> selectedPizzas;

    private ArrayList<Integer> selectedImages;

    /**
     * Starts it up
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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
        setPizzaImages(pizzas, pizzaImages);
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
                                pizzaImages.remove(position);
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

        selectedPizzas = new ArrayList<Pizza>();
        selectedImages = new ArrayList<Integer>();
        selectedListAdapter = new CustomListAdapter(getApplicationContext(), selectedPizzas, selectedImages);
        placeOrderBtn = (Button) findViewById(R.id.placeOrderBtn);
        clearOrderBtn = (Button) findViewById(R.id.clearOrderBtn);
        backBtn = findViewById(R.id.btn_back);
        placeOrderBtn.setOnClickListener(this::placeOrder);
        clearOrderBtn.setOnClickListener(this::defaultCurrentOrderDisplay);
        backBtn.setOnClickListener(this::backBtnClicked);

        placedOrderList = findViewById(R.id.placedOrderList);
        placedOrderList.setAdapter(selectedListAdapter);
        orderTotal = findViewById(R.id.orderTotal);
        setOrderTotal();
        orderNumberBox = findViewById(R.id.orderNumberBox);
        fillOrderNumberBox();
        orderNumberBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNumber = position;
                selectedPizzas = orders.get(position).getPizzas();
                selectedImages = new ArrayList<Integer>();
                setPizzaImages(selectedPizzas, selectedImages);
                selectedListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirmOrdersBtn = findViewById(R.id.confirmOrdersBtn);
        cancelOrdersBtn = findViewById(R.id.cancelOrderBtn);
        confirmOrdersBtn.setOnClickListener(this::confirmOrders);
        cancelOrdersBtn.setOnClickListener(this::cancelOrder);
    }

    /**
     * Confirms the orders
     * @param view - view of screen
     */
    public void confirmOrders(View view){
        if (orders.size() >= 1) {
            OrdersList.get().getOrders().clear();
            setOrderTotal();
            orderNumbers.clear();
            fillOrderNumberBox();
            number = 1;
            String currentOrderDisplay = "Current Order " + number;
            currentOrder.setText(currentOrderDisplay);
//            selectedListAdapter.notifyDataSetChanged();
        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
            builder.setTitle("There are no orders to confirm!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    /**
     * Cancels order
     * @param view - screen
     */
    public void cancelOrder(View view){
        orders.remove(selectedNumber);
        selectedListAdapter.notifyDataSetChanged();
        orderNumbers.remove(selectedNumber);
        setOrderTotal();
        fillOrderNumberBox();
    }

    /**
     * Empties orders list
     */
    public void emptyOrders(){
        if (orders.isEmpty()){ number = 1;}
        else {number = orders.size()+1;}
    }

    /**
     * Empty all pizzas list
     */
    public void emptyPizzas(){
        if (pizzas.isEmpty()){
            String subTotalDefault = "Subtotal: $0.00";
            String salesTaxDefault = "Sales Tax: $0.00";
            String totalDefualt = "Total: $0.00";
            subTotal.setText(subTotalDefault);
            salesTax.setText(salesTaxDefault);
            total.setText(totalDefualt);
        } else {
            setSubTotal();
            setTax();
            setTotal();
        }
    }

    /**
     * Places the order into place orders list
     * @param view - screen
     */
    public void placeOrder(View view){
        if (pizzas.size() >= 1) {
            Order order = new Order(number, pizzas);
            orders.add(order);

            defaultCurrentOrderDisplay(view);
            number += 1;
            String currentOrderDisplay = "Current Order " + number;
            currentOrder.setText(currentOrderDisplay);
            Toast toast = Toast.makeText(getApplicationContext(), "Order placed!", Toast.LENGTH_SHORT);
            toast.show();

            fillOrderNumberBox();
            setOrderTotal();
            selectedListAdapter.notifyDataSetChanged();
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

    /**
     * Default current orders
     * @param view - screen
     */
    public void defaultCurrentOrderDisplay(View view){
        PizzasList.get().getPizzas().clear();
        pizzaImages.clear();
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

    /**
     * Go back to Pizza order screen
     * @param view - screen
     */
    public void backBtnClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setPizzaImages(ArrayList<Pizza> pizzas, ArrayList<Integer> pizzaImages){
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

    private void fillOrderNumberBox(){
        orderNumbers = new ArrayList<Integer>();
        for (Order order : orders){
            orderNumbers.add(order.getNumber());
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(OrderActivity.this, android.R.layout.simple_spinner_dropdown_item, orderNumbers);
        orderNumberBox.setAdapter(adapter);
    }

    private void setOrderTotal(){
        if (orders.isEmpty()) {
            String orderTotalDisplay = "Order Total (Tax Included): $0.00";
            orderTotal.setText(orderTotalDisplay);
        }
        else {
            String orderTotalDisplay = "Order Total (Tax Included): $" + String.format("%.02f", orders.get(selectedNumber).getTotalPrice());
            orderTotal.setText(orderTotalDisplay);
        }

    }


}