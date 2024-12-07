package com.example.myapplication;

/**
 * This class holds the necessary attributes for each item in the Recycler View in Activity_Main.xml
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public class Item {
    /**
     * The name of the item
     */
    private String itemName;
    /**
     * The int reference ID of the item's image
     */
    private int image;
    /**
     * The string of the item's description
     */
    private String itemDescription;
    /**
     * The string of the item's crust type
     */
    private String itemCrust;

    /**
     * Constructor for the Item object.
     * @param itemName - The name of the item
     * @param image - The int reference ID of the item's image
     * @param unitPrice - The string of the item's description
     * @param itemCrust - The string of the item's crust type
     */
    public Item(String itemName, int image, String unitPrice, String itemCrust){
        this.itemName = itemName;
        this.image = image;
        this.itemDescription = unitPrice;
        this.itemCrust = itemCrust;
    }

    /**
     * Returns the string name of the item
     * @return
     */
    public String getItemName(){return itemName;}

    /**
     * Returns the int reference ID of the item's image
     * @return
     */
    public int getImage(){return image;}

    /**
     * Returns the string description of the item
     * @return
     */
    public String getItemDescription(){return itemDescription;}

    /**
     * Returns the string crust type of the item
     * @return
     */
    public String getItemCrust(){return itemCrust;}


}
