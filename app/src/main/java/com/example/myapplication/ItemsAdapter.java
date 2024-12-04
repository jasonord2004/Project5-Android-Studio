package com.example.myapplication;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder>{
}

public static class ItemsHolder extends RecyclerView.ViewHolder {
    private TextView pizza_name, pizza_description;
    private ImageView im_view;
    private ImageButton btn_add;
    private ConstraintLayout parentLayout;

    public ItemsHolder(@NonNull View itemView){
        super(itemView);
        pizza_name = itemView.findViewById(R.id.pizza_type);
        pizza_description = itemView.findViewById(R.id.pizza_description);
        im_view = itemView.findViewById(R.id.im_view);
        btn_add = itemView.findViewById(R.id.btn_add);
        parentLayout = itemView.findViewById(R.id.rowLayout);
        setAddButtonOnClick(itemView);
    }

    private void setAddButtonOnClick(@NonNull View itemView){
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
                    dialog.dismiss();
                });

            }
        });
    }
}