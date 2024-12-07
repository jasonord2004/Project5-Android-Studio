package com.example.myapplication;

import java.util.ArrayList;

/**
 * This Java class follows the "Singleton Design Pattern". It creates a private instance of
 * the arraylist of pizzas that can accessed in multiple activities.
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public class PizzasList {
    /**
     * The arraylist of pizzas in the current order
     */
    final private ArrayList<Pizza> pizzas;

    /**
     * The private instance of PizzasList that creates the new arraylist of pizzas
     */
    private PizzasList(){ pizzas = new ArrayList<Pizza>(); }

    /**
     * The PizzasList of pizzas
     */
    private static PizzasList pizzasList;

    /**
     * Returns the pizzaslist of pizzas. Creates one if it is null.
     * @return - Returns the PizzasList
     */
    public static PizzasList get(){
        if (pizzasList == null) {
            pizzasList = new PizzasList();
        }
        return pizzasList;
    }

    /**
     * Returns the arraylist of pizzas. This will allow users to edit the values in the list.
     * @return - Returns the arraylist of pizzas
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }
}
