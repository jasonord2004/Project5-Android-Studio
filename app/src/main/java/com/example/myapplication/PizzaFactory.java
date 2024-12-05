package com.example.myapplication;

/**
 * This interface holds all the abstract methods used by ChicagoPizza and NYPizza.
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public interface PizzaFactory {

    /**
     * Abstract method for creating Deluxe Pizzas.
     * @return a Deluxe Pizza
     */
    Pizza createDeluxe();

    /**
     * Abstract method for creating Meatzza Pizzas.
     * @return a Meatzza Pizza
     */
    Pizza createMeatzza();

    /**
     * Abstract method for creating BBQ Chicken Pizzas.
     * @return a BBQChicken Pizza
     */
    Pizza createBBQChicken();

    /**
     * Abstract method for creating BuildYourOwn Pizzas.
     * @return a BuildYourOwn Pizza
     */
    Pizza createBuildYourOwn();
}

