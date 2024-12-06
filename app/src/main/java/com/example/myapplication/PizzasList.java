package com.example.myapplication;

import java.util.ArrayList;

public class PizzasList {
    private ArrayList<Pizza> pizzas;

    private PizzasList(){ pizzas = new ArrayList<Pizza>(); }

    private static PizzasList pizzasList;
    public static PizzasList get(){
        if (pizzasList == null) {
            pizzasList = new PizzasList();
        }
        return pizzasList;
    }

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }
}
