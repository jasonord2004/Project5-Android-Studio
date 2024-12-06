package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

public class CustomListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Pizza> pizzasList;
    private ArrayList<Integer> imagesList;
    private LayoutInflater inflater;
    private ImageButton btn_remove;
    public CustomListAdapter(Context ctx, ArrayList<Pizza> pizzas, ArrayList<Integer> images){
        context = ctx;
        pizzasList = pizzas;
        imagesList = images;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return pizzasList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_list_row, null);
        TextView pizzaType = (TextView) convertView.findViewById(R.id.pizza_type);
        TextView pizzaSize = (TextView) convertView.findViewById(R.id.pizza_size);
        TextView pizzaPrice = (TextView) convertView.findViewById(R.id.pizza_price);
        TextView info = (TextView) convertView.findViewById(R.id.remove_info);
        ImageView imageType = (ImageView) convertView.findViewById(R.id.im_view);

        pizzaType.setText(pizzasList.get(position));
        pizzaSize.setText(pizzasList.get(position).getSize().toString());
        pizzaPrice.setText(String.format("%.02f", pizzasList.get(position).price()));
        info.setText("(Long press item to remove)");

        imageType.setImageResource(imagesList.get(position));

        return convertView;
    }



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
