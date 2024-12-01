package com.example.project4rupizzeria;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class creates the various types of pizza in Chicago.
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public class NYPizza implements PizzaFactory{

    /**
     * Creates the BBQChicken pizza with its required toppings and crust type.
     * @return the BBQChicken object.
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(new ArrayList<>(Arrays.asList(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR)), Crust.THIN);
    }

    /**
     * Creates the Deluxe pizza with its required toppings and crust type.
     * @return the Deluxe object.
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(new ArrayList<>(Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM)), Crust.BROOKLYN);
    }

    /**
     * Creates the Meatzza pizza with its required toppings and crust type.
     * @return the Meatzza object.
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(new ArrayList<>(Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM)), Crust.HAND_TOSSED);
    }

    /**
     * Creates the BuildsYourOwn pizza with its crust type.
     * @return the BuildsYourOwn object.
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(Crust.HAND_TOSSED);
    }
}
