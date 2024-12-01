package com.example.project4rupizzeria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is meant to manage all the event handling in the CurrentOrders interface.
 * It allows the user to remove pizzas and place the order.
 * @author Ankit Mithbavkar, Jason Ordonez
 */
public class CurrentOrderController {
    /**
     * The unique order number for the order.
     */
    private int number;
    /**
     * The tax on the order.
     */
    private double salesTax;
    /**
     * The sales tax rate in NJ.
     */
    private final double TAX_RATE = 0.06625;
    /**
     * The subtotal of the order.
     */
    private double price;
    /**
     * The total price of the order.
     */
    private double total;
    /**
     * The stage of the interface.
     */
    private Stage stage;
    /**
     * The scene of the interface.
     */
    private Scene scene;
    /**
     * Used for loading the interface.
     */
    private Parent root;
    /**
     * The list of pizzas in the current order.
     */
    public static ArrayList<Pizza> pizzas = new ArrayList<Pizza>();

    /**
     * Displays the order number for the current order.
     */
    @FXML
    private TextField orderNumber;

    /**
     * Displays the list of pizzas in the order.
     */
    @FXML
    private ListView pizzaListView;

    /**
     * Displays the subtotal of the order.
     */
    @FXML
    private TextField subtotal;

    /**
     * Displays the tax of the order.
     */
    @FXML
    private TextField tax;

    /**
     * Displays the total price of the order.
     */
    @FXML
    private TextField orderTotal;

    /**
     * Places the order when clicked.
     */
    @FXML
    private Button placeButton;

    /**
     * Removes all the pizzas from the order when clicked.
     */
    @FXML
    private Button clearButton;

    /**
     * Removes the selected pizza from the order when clicked.
     */
    @FXML
    private Button removeButton;

    /**
     * Creates the Order object using the list of pizzas and adds it to the list of Orders.
     * Pops up an alert window when the order is placed, but if there are no pizzas, informs the user to add pizzas to the order.
     */
    @FXML
    void placeOrder(){
        if (pizzas.size() >= 1){
            Order order = new Order(number, pizzas);
            OrdersPlacedController.orders.add(number-1, order);

            orderNumber.clear();
            pizzaListView.getItems().clear();
            subtotal.setText("$0.00");
            tax.setText("$0.00");
            orderTotal.setText("$0.00");

            pizzas = new ArrayList<Pizza>();
            number++;
            orderNumber.setText("" + number);
            createAlert("Order Placement Alert", "Order Placed!", "Your order has been placed.");
        }
        else{
            createAlert("Order Placement Alert", "No pizzas!", "No pizzas found in your order. Add more!");
        }

    }

    /**
     * Removes all pizzas from the order and displays default values for all controllers.
     */
    @FXML
    void clearOrder(){
        pizzaListView.setItems(null);
        pizzas.clear();
        subtotal.setText("$0.00");
        tax.setText("$0.00");
        orderTotal.setText("$0.00");
    }

    /**
     * Removes the selected pizza from the display list and ArrayList of Pizza objects.
     * Updates the subtotal, sales tax, and total order.
     */
    @FXML
    void removePizza(){
        try {
            String removePizza = (String) pizzaListView.getSelectionModel().getSelectedItem();
            if (removePizza != null) {
                price -= pizzas.get(pizzaListView.getSelectionModel().getSelectedIndex()).price();
                pizzas.remove(pizzaListView.getSelectionModel().getSelectedIndex());
                pizzaListView.getItems().remove(removePizza);
                displaySubtotal();
                displaySalesTax();
                displayOrderTotal();
            }
        } catch (NullPointerException exception) { return;}
    }

    /**
     * Initializes the scene to immediately display information for the order.
     */
    public void initialize(){
        orderTotal.setEditable(false);
        tax.setEditable(false);
        orderNumber.setEditable(false);
        subtotal.setEditable(false);

        if (OrdersPlacedController.orders.isEmpty()){ number = 1;}
        else {number = OrdersPlacedController.orders.getLast().getNumber()+1;}

        price = 0;
        for (Pizza pizza : pizzas){
            price += pizza.price();
        }

        displayOrderNumber();
        displayPizzas();
        displaySubtotal();
        displaySalesTax();
        displayOrderTotal();
    }

    /**
     * Displays the subtotal rounded to 2 decimal points.
     */
    private void displaySubtotal(){
        subtotal.setText("$" + String.format("%.02f", price));
    }

    /**
     * Displays the total price rounded to 2 decimal points.
     */
    private void displayOrderTotal() {
        total = price + salesTax;
        orderTotal.setText("$" + String.format("%.02f", total));
    }

    /**
     * Displays the tax rounded to 2 decimal points.
     */
    private void displaySalesTax() {
        salesTax = price * TAX_RATE;
        tax.setText("$" + String.format("%.02f", salesTax));
    }

    /**
     * Displays the order number for the order.
     */
    private void displayOrderNumber(){
        orderNumber.setText(number + "");
    }

    /**
     * Displays the list of Pizzas in the order with their description.
     */
    private void displayPizzas(){
        ObservableList<Pizza> pizzasList = FXCollections.observableArrayList(pizzas);
        ArrayList<String> pizzasDescriptions = new ArrayList<>();
        String type = "";
        for(Pizza pizza : pizzasList){
            if(pizza instanceof BBQChicken) type = "BBQ Chicken Pizza";
            else if (pizza instanceof Meatzza) type = "Meatzza Pizza";
            else if (pizza instanceof Deluxe) type = "Deluxe";
            else type = "Build Your Own Pizza";
            pizzasDescriptions.add("PRICE: $" + String.format("%.02f", pizza.price()) + "  | TYPE: " + type + "  | SIZE: " + pizza.getSize() + "  | CRUST: " + pizza.getCrust() + "  | TOPPINGS: " + pizza.getToppings());
        }
        pizzaListView.setItems(null);
        pizzaListView.setItems(FXCollections.observableArrayList(pizzasDescriptions));
    }

    /**
     * Creates a pop-up window alert that informs the user of any important action they should take.
     * @param title - the title of the alert shown on the window.
     * @param header - the main error/update conveyed to the user.
     * @param content - the information about the alert, giving instructions or describing the alert.
     */
    private void createAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Allows the user to return back to the main menu when the button is clicked.
     * @param event - the event of the mouse being clicked
     * @throws IOException - input/output exception when the proper file isn't received.
     */
    @FXML
    void backToMenu(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage =  (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Welcome!");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
