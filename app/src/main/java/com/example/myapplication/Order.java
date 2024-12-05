package com.example.myapplication;
import java.util.ArrayList;

/**
 * This class holds the list of all pizzas in the order along with its unique identifier.
 * @author Ankit Mithbavkar, Jason Ordonez
 */
public class Order {
    /**
     * Unique identifier for the order.
     */
    private int number; //order number
    /**
     * List of all pizzas in the order.
     */
    private ArrayList<Pizza> pizzas;//can use List<E> instead of ArrayList
    /**
     * The standard sales tax rate in NJ.
     */
    private final double TAX_RATE = 0.06625;

    /**
     * Creates the Order object.
     * @param number - the order number for the order.
     * @param pizzas - the list of all pizzas in the order.
     */
    public Order(int number, ArrayList<Pizza> pizzas){
        this.number = number;
        this.pizzas = pizzas;
    }

    /**
     * Returns the list of pizzas in the order.
     * @return the ArrayList of Pizza objects.
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Returns the total price (including tax) for the order.
     * @return the total price
     */
    public double getTotalPrice() {
        double price = 0;
        for (Pizza pizza : pizzas){
            price += pizza.price();
        }
        return price * TAX_RATE + price;
    }

    /**
     * Returns the order number for the order.
     * @return order number.
     */
    public int getNumber(){
        return number;
    }
}
