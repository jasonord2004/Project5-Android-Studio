package com.example.myapplication;

public class Item {
    private String itemName;
    private int image;
    private String itemDescription;

    public Item(String itemName, int image, String unitPrice){
        this.itemName = itemName;
        this.image = image;
        this.itemDescription = unitPrice;
    }

    public String getItemName(){return itemName;}
    public int getImage(){return image;}
    public String getItemDescription(){return itemDescription;}

}
