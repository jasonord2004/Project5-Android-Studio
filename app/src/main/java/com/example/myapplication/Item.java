package com.example.myapplication;

public class Item {
    private String itemName;
    private int image;
    private String itemDescription;
    private String itemCrust;

    public Item(String itemName, int image, String unitPrice, String itemCrust){
        this.itemName = itemName;
        this.image = image;
        this.itemDescription = unitPrice;
        this.itemCrust = itemCrust;
    }

    public String getItemName(){return itemName;}
    public int getImage(){return image;}
    public String getItemDescription(){return itemDescription;}
    public String getItemCrust(){return itemCrust;}


}
