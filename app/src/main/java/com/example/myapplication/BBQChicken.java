package com.example.myapplication;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class is a child of the Pizza class, containing the toppings and calculating the price.
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public class BBQChicken extends Pizza{
    /**
     * Price of a small.
     */
    private static final double SPRICE = 14.99;
    /**
     * Price of a medium.
     */
    private static final double MPRICE = 16.99;
    /**
     * Price of a large.
     */
    private static final double LPRICE = 19.99;

    /**
     * Creates the BBQChicken object.
     * @param toppings - the list of toppings for the pizza.
     * @param crust - the type of crust for the pizza.
     */
    public BBQChicken(ArrayList<Topping> toppings, Crust crust) { super(toppings, crust); }

    /**
     * Calculates the price of the pizza depending on the size.
     * @return the subtotal.
     */
    @Override
    public double price() {
        if(getSize().toString().equals("SMALL")){
            return SPRICE;
        } else if (getSize().toString().equals("MEDIUM")){
            return MPRICE;
        } else if (getSize().toString().equals("LARGE")){
            return LPRICE;
        } else {
            return 0;
        }
    }

}
