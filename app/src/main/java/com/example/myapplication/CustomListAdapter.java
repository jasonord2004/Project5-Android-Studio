package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class holds the adapter for each item in the List View in the Orders_View.xml layout
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public class CustomListAdapter extends BaseAdapter {
    /**
     * The context of the activity
     */
    final private Context context;
    /**
     * The arraylist of pizzas in the current order, to be displayed
     */
    final private ArrayList<Pizza> pizzasList;
    /**
     * The arraylist of image ID references for each pizza in the current order
     */
    final private ArrayList<Integer> imagesList;
    /**
     * The inflater for the view
     */
    final private LayoutInflater inflater;
    /**
     * The remove ImageButton
     */
    private ImageButton btn_remove;

    /**
     * The constructor for the CustomListAdapter instance.
     * @param ctx - The context of the activity
     * @param pizzas - The arraylist of pizzas
     * @param images - The arraylist of images
     */
    public CustomListAdapter(Context ctx, ArrayList<Pizza> pizzas, ArrayList<Integer> images){
        context = ctx;
        pizzasList = pizzas;
        imagesList = images;
        inflater = LayoutInflater.from(ctx);
    }

    /**
     * Returns the int size of the pizzaslist.
     * @return - Size of pizzas list
     */
    @Override
    public int getCount() {
        return pizzasList.size();
    }

    /**
     * Returns the item in the given position within the adapter
     * @param position Position of the item whose data we want within the adapter's
     * data set.
     * @return - returns null
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Returns the item id of the item in the given position within the adapter
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return - returns 0
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Sets the items in the listview with the actual data and returns the view
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return - Returns the updated view with appropriate data
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_list_row, null);
        TextView pizzaType = convertView.findViewById(R.id.pizza_type);
        TextView pizzaSize = convertView.findViewById(R.id.pizza_size);
        TextView pizzaPrice = convertView.findViewById(R.id.pizza_price);
        TextView info = convertView.findViewById(R.id.remove_info);
        ImageView imageType = convertView.findViewById(R.id.im_view);
        pizzaType.setText(getPizzaName(pizzasList.get(position)));
        pizzaSize.setText(pizzasList.get(position).getSize().toString());
        pizzaPrice.setText(String.format("%.02f", pizzasList.get(position).price()));
        info.setText("(Long press item to remove)");

        imageType.setImageResource(imagesList.get(position));

        return convertView;
    }


    /**
     * Gets the type of the pizza an returns the string of that type.
     * @param pizza - The chosen pizza
     * @return - The string of the chosen pizza's type
     */
    private String getPizzaName(Object pizza){
        if (pizza instanceof BBQChicken){
            BBQChicken p = (BBQChicken) pizza;
            if (p.getCrust() == Crust.PAN) return "BBQ Chicken Chicago-Style";
            return "BBQ Chicken NY-Style";
        }
        else if (pizza instanceof BuildYourOwn){
            BuildYourOwn p = (BuildYourOwn) pizza;
            if (p.getCrust() == Crust.PAN) return "Build-Your-Own Chicago-Style" ;
            return "Build-Your-Own NY-Style";
        }
        else if (pizza instanceof Deluxe){
            Deluxe p = (Deluxe) pizza;
            if (p.getCrust() == Crust.DEEP_DISH) return "Deluxe Chicago-Style";
            return "Deluxe NY-Style";
        }
        else if (pizza instanceof Meatzza){
            Meatzza p = (Meatzza) pizza;
            if (p.getCrust() == Crust.STUFFED) return "Meatzza Chicago-Style";
            return "Meatzza NY-Style";
        }
        return "";
    }
}
