package com.example.myapplication;

import java.util.ArrayList;

public class OrdersList {
    private ArrayList<Order> orders;

    private OrdersList(){ orders = new ArrayList<Order>(); }

    private static OrdersList ordersList;
    public static OrdersList get(){
        if (ordersList == null){
            ordersList = new OrdersList();
        }
        return ordersList;
    }

    public void addOrder(Order order) { orders.add(order); }
    public void removeOrder(Order order){ orders.remove(order); }
    public ArrayList<Order> getOrders(){ return orders; }


}
