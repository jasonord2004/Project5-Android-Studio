package com.example.project4rupizzeria;
import java.util.ArrayList;

/**
 * This class contains information on the list of toppings, crust, and size of the pizza.
 * @author Ankit Mithbavkar, Jason Ordonez.
 */
public abstract class Pizza {
    /**
     * The list of toppings on the pizza.
     */
    private ArrayList<Topping> toppings;//Topping is a Enum class

    /**
     * The crust for the Pizza.
     */
    private Crust crust; //Crust is a Enum class

    /**
     * The size of the pizza.
     */
    private Size size; //Size is a Enum class

    /**
     * Abstract method to calculate the subtotal of the pizza.
     * @return the price of the pizza.
     */
    public abstract double price();

    /**
     * Creates the Pizza object.
     * @param toppings - the list of toppings on the pizza.
     * @param crust - the crust of the pizza.
     * @param size - the size of the pizza.
     */
    public Pizza (ArrayList<Topping> toppings, Crust crust, Size size) {
        this.toppings = toppings;
        this.crust = crust;
        this.size = size;
    }

    /**
     * Creates the Pizza object when size is not known.
     * @param toppings - the list of toppings on the pizza.
     * @param crust - the crust of the pizza.
     */
    public Pizza (ArrayList<Topping> toppings, Crust crust){
        this.toppings = toppings;
        this.crust = crust;
    }

    /**
     * Creates the Pizza object when size and toppings is not defined.
     * @param crust - the crust of the pizza
     */
    public Pizza (Crust crust){
        this.toppings = new ArrayList<>();
        this.crust = crust;
    }

    /**
     * Creates the default Pizza object.
     */
    public Pizza (){}

    /**
     * Adds a topping to the list of toppings.
     * @param topping - topping to be added to the pizza.
     */
    public void addTopping(Topping topping){
        toppings.add(topping);
    }

    /**
     * Removes a topping from the list of toppings.
     * @param topping - topping to be removed from the pizza.
     */
    public void removeTopping(Topping topping){
        for(int i = 0; i < toppings.size(); i++){
            if (toppings.get(i).equals(topping)){
                toppings.remove(i);
                break;
            }
        }
    }

    /**
     * Returns the list of toppings.
     * @return - the ArrayList of Topping objects.
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /**
     * The Crust type of the pizza.
     * @return the Crust enum object.
     */
    public Crust getCrust() {
        return crust;
    }

    /**
     * Sets the Crust type of the pizza.
     * @param crust - the Crust enum object to be set.
     */
    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    /**
     * The Size type of the pizza.
     * @return the Size enum object.
     */
    public Size getSize(){
        return size;
    }

    /**
     * Sets the Size type of the pizza.
     * @param size - the Size enum object to be set.
     */
    public void setSize(Size size){
        this.size = size;
    }
}
