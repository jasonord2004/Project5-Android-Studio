package com.example.myapplication;

import java.util.ArrayList;
/**
 * This Java class follows the "Singleton Design Pattern". It creates a private instance of
 * the arraylist of orders that can accessed in multiple activities.
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public class OrdersList {
    /**
     * The arraylist of orders in the OrdersList
     */
    final private ArrayList<Order> orders;
    /**
     * The private instance of OrdersList that creates the new arraylist of orders
     */
    private OrdersList(){ orders = new ArrayList<Order>(); }
    /**
     * The OrdersList of orders
     */
    private static OrdersList ordersList;
    /**
     * Returns the orderslist of pizzas. Creates one if it is null.
     * @return - Returns the OrdersList
     */
    public static OrdersList get(){
        if (ordersList == null){
            ordersList = new OrdersList();
        }
        return ordersList;
    }
    /**
     * Returns the arraylist of orders. This will allow users to edit the values in the list.
     * @return - Returns the arraylist of orders
     */
    public ArrayList<Order> getOrders(){ return orders; }


}
