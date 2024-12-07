package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder>{
    private Context context;
    private ArrayList<Item> items;

    public ItemsAdapter(Context context, ArrayList<Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view, parent, false);
        return new ItemsHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position){
        holder.pizza_name.setText(items.get(position).getItemName());
        holder.pizza_description.setText(items.get(position).getItemDescription());
        holder.pizza_crust.setText(items.get(position).getItemCrust());
        holder.im_view.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount(){return items.size();}

    public static class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView pizza_name, pizza_description, pizza_crust;
        private ImageView im_view;
        private ImageButton btn_add;
        private ConstraintLayout parentLayout;
        private Context context;

        final private static int MAXTOPPINGS = 7;
        final private static double TOPPINGPRICE = 1.69;

        public ItemsHolder(@NonNull View itemView, Context context){
            super(itemView);
            pizza_name = itemView.findViewById(R.id.pizza_type);
            pizza_description = itemView.findViewById(R.id.pizza_description);
            pizza_crust = itemView.findViewById(R.id.pizza_crust);
            im_view = itemView.findViewById(R.id.im_view);
            btn_add = itemView.findViewById(R.id.btn_add);
            parentLayout = itemView.findViewById(R.id.rowLayout);
            this.context = context;
            setAddButtonOnClick(itemView, context);
        }

        private void setAddButtonOnClick(@NonNull View itemView, Context context){
            final String[] sizes = new String[]{"LARGE", "MEDIUM", "SMALL"};
            final int[] checkedItem = {-1};
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Pizza pizza = constructPizza();
                    if(pizza instanceof BuildYourOwn){
                        createToppingsList(pizza);
                    } else {
                        createSizeAlert(pizza);
                    }
                }

                private void createSizeAlert(Pizza pizza){
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Select a size");
                    final View customLayout = LayoutInflater.from(context).inflate(R.layout.alert_view, null);
                    builder.setView(customLayout);
                    TextView input = new TextView(context);
                    builder.setView(input);
                    builder.setSingleChoiceItems(sizes, checkedItem[0], (dialog, which) -> {
                        checkedItem[0] = which;
                        pizza.setSize(Size.valueOf(sizes[checkedItem[0]]));
                        String price = String.format("%.02f", pizza.price());
                        input.setText(String.format("\t Price: " + price));
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> {});
                    builder.setPositiveButton("Add to Order", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((which) -> {addToOrderClicked(which, alert, input, pizza);
                    });
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener((which) -> {
                        checkedItem[0] = -1;
                        alert.dismiss();
                    });
                }
                private void addToOrderClicked(View view, AlertDialog alert, TextView input, Pizza pizza){
                    //Adds pizza to current order
                    String inputText = input.getText().toString();
                    if(inputText.isBlank()) {
                        Toast toast = Toast.makeText(context, "Pick a size", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        checkedItem[0] = -1;
                        Toast toast = Toast.makeText(context, "Added to order!", Toast.LENGTH_SHORT);
                        toast.show();
                        alert.dismiss();
                        //Add pizza to order list in singleton class
                        PizzasList.get().getPizzas().add(pizza);
                    }
                }

                private void createToppingsList(Pizza pizza){
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Select Toppings");
                    final View customLayout = LayoutInflater.from(context).inflate(R.layout.alert_view, null);
                    builder.setView(customLayout);
                    TextView input = new TextView(context);
                    builder.setView(input);
                    Topping[] toppings = Topping.values();
                    String[] toppingsList = new String[toppings.length];
                    for (int i = 0; i<toppings.length; i++){
                        toppingsList[i] = toppings[i].toString();
                    }
                    ArrayList<Integer> checkedIndex = new ArrayList<Integer>();
                    builder.setMultiChoiceItems(toppingsList, null, ((dialog, which, isChecked) -> {
                        if (isChecked) {
                            checkedIndex.add(which);
                        } else if (checkedIndex.contains(which)){
                            int index = checkedIndex.indexOf(which);
                            checkedIndex.remove(index);
                        }
                        //Fix magic number
                        String toppingsAdded = String.valueOf("Toppings Added: " + checkedIndex.size() + "\n+$" + String.format("%.02f",(checkedIndex.size()*TOPPINGPRICE)));
                        input.setText(toppingsAdded);
                    }));
                    builder.setNegativeButton("Cancel", (dialog, which) -> {});
                    builder.setPositiveButton("Add to Pizza", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((which) -> {
                        if (checkedIndex.size() > 7){
                            Toast toast = Toast.makeText(context, "Maximum 7 Toppings!", Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        alert.dismiss();
                        for (int index : checkedIndex){
                            pizza.addTopping(Topping.valueOf(toppingsList[index]));
                        }
                        createSizeAlert(pizza);
                    });
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener((which) -> {
                        checkedItem[0] = -1;
                        alert.dismiss();
                    });
                }

            });
        }

        private Pizza constructPizza(){
            PizzaFactory pizzaFactory = null;
            Pizza pizza;

            String pizzaName = pizza_name.getText().toString();
            //System.out.println(pizzaName);
            if(pizzaName.contains("Chicago")){ pizzaFactory = new ChicagoPizza();}
            else if (pizzaName.contains("NY")) { pizzaFactory = new NYPizza();}

            if(pizzaName.contains("BBQ Chicken")){ pizza = pizzaFactory.createBBQChicken();}
            else if(pizzaName.contains("Deluxe")){ pizza = pizzaFactory.createDeluxe();}
            else if(pizzaName.contains("Meatzza")){ pizza = pizzaFactory.createMeatzza();}
            else{
                //Make BuildYourOwn pizza pop up first
                pizza = pizzaFactory.createBuildYourOwn();
            }
            return pizza;
        }

    }

}

