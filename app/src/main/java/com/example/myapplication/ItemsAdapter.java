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
        holder.im_view.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount(){return items.size();}

    public static class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView pizza_name, pizza_description;
        private ImageView im_view;
        private ImageButton btn_add;
        private ConstraintLayout parentLayout;
        private Context context;

        public ItemsHolder(@NonNull View itemView, Context context){
            super(itemView);
            pizza_name = itemView.findViewById(R.id.pizza_type);
            pizza_description = itemView.findViewById(R.id.pizza_description);
            im_view = itemView.findViewById(R.id.im_view);
            btn_add = itemView.findViewById(R.id.btn_add);
            parentLayout = itemView.findViewById(R.id.rowLayout);
            this.context = context;
            setAddButtonOnClick(itemView, context);
        }

        private void setAddButtonOnClick(@NonNull View itemView, Context context){
            final String[] sizes = new String[]{"Large", "Medium", "Small"};
            final int[] checkedItem = {-1};
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                    alert.setTitle("Select a size");
                    alert.setSingleChoiceItems(sizes, checkedItem[0], (dialog, which) -> {
                        checkedItem[0] = which;
                        //sizes[which] --> Sets price of pizza somehow
                        //Display Price of pizza
                    });
                    alert.setNegativeButton("Cancel", (dialog, which) -> {});
                    alert.setPositiveButton("Add to Order", (dialog, which) -> {
                        //Adds pizza to current order
                        CharSequence text = "Added to order!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        dialog.dismiss();
                    });

                }
            });
        }
    }

}

