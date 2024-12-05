package com.example.myapplication;

import java.util.ArrayList;

/**
 * This class is a child of the Pizza class, containing the toppings and calculating the price.
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public class BuildYourOwn extends Pizza{
    /**
     * Price of a small.
     */
    private static final double SPRICE = 8.99;
    /**
     * Price of a medium.
     */
    private static final double MPRICE = 10.99;
    /**
     * Price of a large.
     */
    private static final double LPRICE = 12.99;
    /**
     * The price for each additional topping.
     */
    private static final double TOPPING = 1.69;

    /**
     * Creates the BuildYourOwn object.
     * @param crust - the type of crust for the pizza.
     */
    public BuildYourOwn(Crust crust) {super(crust);}

    /**
     * Calculates the price of the pizza depending on the size.
     * @return the subtotal.
     */
    @Override
    public double price() {
        double price;
        if(getSize().toString().equals("SMALL")){ price = SPRICE;}
        else if (getSize().toString().equals("MEDIUM")){ price = MPRICE;}
        else if (getSize().toString().equals("LARGE")){ price = LPRICE;}
        else { return 0;}
        try {
            return price + (getToppings().size() * TOPPING);
        } catch (NullPointerException e) {
            return price;
        }
    }

}
