package com.example.project4rupizzeria;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuildYourOwnPizzaTest {

    @Test
    void noToppingLPizzaPrice() {
        PizzaFactory pizzaFactory = new NYPizza();
        Pizza pizza = pizzaFactory.createBuildYourOwn();
        pizza.setSize(Size.LARGE);
        assertEquals(pizza.price(), 12.99);
    }

    @Test
    void noToppingMPizzaPrice() {
        PizzaFactory pizzaFactory = new NYPizza();
        Pizza pizza = pizzaFactory.createBuildYourOwn();
        pizza.setSize(Size.MEDIUM);
        assertEquals(pizza.price(), 10.99);
    }

    @Test
    void noToppingSPizzaPrice() {
        PizzaFactory pizzaFactory = new NYPizza();
        Pizza pizza = pizzaFactory.createBuildYourOwn();
        pizza.setSize(Size.SMALL);
        assertEquals(pizza.price(), 8.99);
    }

    @Test
    void sevenToppingLPizzaPrice() {
        PizzaFactory pizzaFactory = new NYPizza();
        Pizza pizza = pizzaFactory.createBuildYourOwn();
        pizza.setSize(Size.LARGE);
        pizza.addTopping(Topping.SAUSAGE);
        pizza.addTopping(Topping.PEPPERONI);
        pizza.addTopping(Topping.GREEN_PEPPER);
        pizza.addTopping(Topping.BBQ_CHICKEN);
        pizza.addTopping(Topping.PROVOLONE);
        pizza.addTopping(Topping.SPINACH);
        pizza.addTopping(Topping.BACON);
        assertEquals(pizza.price(), 24.82);
    }

    @Test
    void sevenToppingMPizzaPrice() {
        PizzaFactory pizzaFactory = new NYPizza();
        Pizza pizza = pizzaFactory.createBuildYourOwn();
        pizza.setSize(Size.MEDIUM);
        pizza.addTopping(Topping.SAUSAGE);
        pizza.addTopping(Topping.PEPPERONI);
        pizza.addTopping(Topping.GREEN_PEPPER);
        pizza.addTopping(Topping.BBQ_CHICKEN);
        pizza.addTopping(Topping.PROVOLONE);
        pizza.addTopping(Topping.SPINACH);
        pizza.addTopping(Topping.BACON);
        assertEquals(pizza.price(), 22.82);
    }

    @Test
    void sevenToppingSPizzaPrice() {
        PizzaFactory pizzaFactory = new ChicagoPizza();
        Pizza pizza = pizzaFactory.createBuildYourOwn();
        pizza.setSize(Size.SMALL);
        pizza.addTopping(Topping.SAUSAGE);
        pizza.addTopping(Topping.PEPPERONI);
        pizza.addTopping(Topping.GREEN_PEPPER);
        pizza.addTopping(Topping.BBQ_CHICKEN);
        pizza.addTopping(Topping.PROVOLONE);
        pizza.addTopping(Topping.SPINACH);
        pizza.addTopping(Topping.BACON);
        assertEquals(pizza.price(), 20.82);
    }

    @Test
    void fourToppingSPizzaPrice() {
        PizzaFactory pizzaFactory = new ChicagoPizza();
        Pizza pizza = pizzaFactory.createBuildYourOwn();
        pizza.setSize(Size.SMALL);
        pizza.addTopping(Topping.PROVOLONE);
        pizza.addTopping(Topping.SPINACH);
        pizza.addTopping(Topping.BACON);
        pizza.addTopping(Topping.GREEN_PEPPER);
        assertEquals(pizza.price(), 15.75);
    }

}