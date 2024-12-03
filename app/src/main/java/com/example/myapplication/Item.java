package com.example.myapplication;

public class Item {
    private String itemName;
    private int image;
    private String unitPrice;

    public Item(String itemName, int image, String unitPrice){
        this.itemName = itemName;
        this.image = image;
        this.unitPrice = unitPrice;
    }

    public String getItemName(){return itemName;}
    public int getImage(){return image;}
    public String getUnitPrice(){return unitPrice;}

}
