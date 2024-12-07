package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This class holds the adapter for the RecyclerView in activity_main.xml.
 * This class connects the item holders to the actual data they hold.
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder>{
    /**
     * The context of the activity
     */
    final private Context context;
    /**
     * The arraylist of pizzas listings within the RecyclerView
     */
    final private ArrayList<Item> items;

    /**
     * The constructor to create an ItemsAdapter instance
     * @param context - The context of the activity
     * @param items - The arraylist of pizzas listings within the RecyclerView
     */
    public ItemsAdapter(Context context, ArrayList<Item> items){
        this.context = context;
        this.items = items;
    }

    /**
     * Inflates the view based on the context and the returns an instance of ItemsHolder
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return - returns
     */
    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view, parent, false);
        return new ItemsHolder(view, context);
    }

    /**
     * Binds the holders of the attributes to the actual data that they should hold
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position){
        holder.pizza_name.setText(items.get(position).getItemName());
        holder.pizza_description.setText(items.get(position).getItemDescription());
        holder.pizza_crust.setText(items.get(position).getItemCrust());
        holder.im_view.setImageResource(items.get(position).getImage());
    }

    /**
     * Returns the size of the items arraylist
     * @return - returns an int
     */
    @Override
    public int getItemCount(){return items.size();}
    /**
     * This class connects the xml layout files to android widgets with interactions.
     * This class handles all of the user interactions within the RecyclerView.
     * It also creates the item holders for each item in the RecyclerView.
     * @author Jason Ordonez, Ankit Mithbavkar
     */
    public static class ItemsHolder extends RecyclerView.ViewHolder {
        /**
         * The TextView of the pizza's name, description, and type of crust respectively
         */
        final private TextView pizza_name, pizza_description, pizza_crust;
        /**
         * The ImageView of the pizza's image
         */
        final private ImageView im_view;
        /**
         * The ImageButton to open the select size alert
         */
        final private ImageButton btn_add;
        /**
         * The ConstraintLayout of the items holder
         */
        final private ConstraintLayout parentLayout;
        /**
         * The context of the activity
         */
        final private Context context;
        /**
         * The maximum amount of toppings that can be added to a pizza
         */
        final private static int MAXTOPPINGS = 7;
        /**
         * The price per topping added
         */
        final private static double TOPPINGPRICE = 1.69;

        /**
         * The constructor for itemsholder objects.
         * @param itemView - The row_view.xml layout
         * @param context - The context of the activity
         */
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

        /**
         * This method covers the interactions of the add pizza button. It contains
         * other methods that creates alerts based on what size and/or toppings wanted
         * on the user's pizza.
         * @param itemView - The row_view.xml layout
         * @param context - The context of the activity
         */
        private void setAddButtonOnClick(@NonNull View itemView, Context context){
            final String[] sizes = new String[]{"LARGE", "MEDIUM", "SMALL"};
            final int[] checkedItem = {-1};
            btn_add.setOnClickListener(new View.OnClickListener() {
                /**
                 * Constructs the pizza in the backend to be added to the currentOrder later on.
                 * Creates either the sizeAlert or the toppings Alert based on what type of pizza.
                 * @param view - The view that was clicked.
                 */
                @Override
                public void onClick(View view) {
                    Pizza pizza = constructPizza();
                    if(pizza instanceof BuildYourOwn){
                        createToppingsList(pizza);
                    } else {
                        createSizeAlert(pizza);
                    }
                }

                /**
                 * Creates the alert dialog to ask users for their desired size and to add to order
                 * @param pizza - The chosen type of pizza
                 */
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

                /**
                 * Adds the pizza to order, if a size is picked. Otherwise, prompts users to do so
                 * @param view - Which size was chosen
                 * @param alert - The size alert
                 * @param input - The TextView containing the price of the pizza
                 * @param pizza - The chosen pizza
                 */
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

                /**
                 * Creates the alert dialog to add toppings to a pizza. Only activates if the chosen pizza
                 * type is "Build Your Own".
                 * @param pizza - The Build Your Own pizza
                 */
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
                    ArrayList<Integer> checkedIndex = new ArrayList<>();
                    builder.setMultiChoiceItems(toppingsList, null, ((dialog, which, isChecked) -> {
                        if (isChecked) {
                            checkedIndex.add(which);
                        } else if (checkedIndex.contains(which)){
                            int index = checkedIndex.indexOf(which);
                            checkedIndex.remove(index);
                        }
                        //Fix magic number
                        String toppingsAdded = "Toppings Added: " + checkedIndex.size() + "\n+$" + String.format("%.02f",(checkedIndex.size()*TOPPINGPRICE));
                        input.setText(toppingsAdded);
                    }));
                    builder.setNegativeButton("Cancel", (dialog, which) -> {});
                    builder.setPositiveButton("Add to Pizza", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((which) -> {addToppings(which, alert, checkedIndex, pizza, toppingsList);});
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener((which) -> {
                        checkedItem[0] = -1;
                        alert.dismiss();
                    });
                }

                /**
                 * Adds the toppings to the pizza and opens the size alert dialog, if user chose less than 7 toppings.
                 * Otherwise, prompts user to pick at most 7 toppings.
                 * @param view - Which toppings were chosen
                 * @param alert - The topping alert
                 * @param checkedIndex - The indices of checked Toppings
                 * @param pizza - The chosen pizza
                 * @param toppingsList - The full list of toppings.
                 */
                private void addToppings(View view, AlertDialog alert, ArrayList<Integer> checkedIndex, Pizza pizza, String[] toppingsList){
                    if (checkedIndex.size() > MAXTOPPINGS){
                        Toast toast = Toast.makeText(context, "Maximum 7 Toppings!", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                    alert.dismiss();
                    for (int index : checkedIndex){
                        pizza.addTopping(Topping.valueOf(toppingsList[index]));
                    }
                    createSizeAlert(pizza);
                }

            });
        }

        /**
         * The method to construct the pizza depending on the type chosen
         * @return - returns the constructed pizza
         */
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

