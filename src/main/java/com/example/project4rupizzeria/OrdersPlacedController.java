package com.example.project4rupizzeria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is meant to manage all the event handling in the OrdersPlaced interface. It allows the user to cancel orders
 * or export them into a file that they can save on their device.
 * @author Ankit Mithbavkar, Jason Ordonez
 */
public class OrdersPlacedController {

    /**
     * Arraylist of placed orders.
     */
    public static ArrayList<Order> orders = new ArrayList<Order>();
    /**
     * Stage for the interface.
     */
    private Stage stage;
    /**
     * Scene for the interface.
     */
    private Scene scene;
    /**
     * Used for loading the interface.
     */
    private Parent root;

    /**
     * Allows the user to choose the order number for whatever order they wish to view.
     */
    @FXML
    private ComboBox orderNumberBox;
    /**
     * Displays the total order (including tax).
     */
    @FXML
    private TextField orderTotal;
    /**
     * Displays the list of all pizzas in the particular order.
     */
    @FXML
    private ListView ordersListView;
    /**
     * Cancels the chosen order when clicked.
     */
    @FXML
    private Button cancelButton;
    /**
     * Exports all the orders in a text file that the user can save when clicked.
     */
    @FXML
    private Button exportButton;

    /**
     * Removes the order number option from the ComboBox and the order from the list of orders.
     * Updates the orderNumberBox and displays the first order in the list.
     */
    @FXML
    void cancelOrder(){
        if (!orders.isEmpty()){
            int number = Integer.parseInt(String.valueOf((orderNumberBox.getValue())));
            for(Order order : orders){
                if(order.getNumber() == number) {
                    orders.remove(order);
                    break;
                }
            }
            displayDefault();
            fillOrderNumberBox();
        }
    }

    /**
     * Exports all the orders into a text file displaying the order number and pizzas ordered for each order.
     * If there are no orders, an alert is displayed, telling the user to place an order to export.
     */
    @FXML
    void exportOrder(){
        if (orders.isEmpty()){
            createAlert("No Orders", "No Orders to Save", "Please place at least 1 order before exporting.");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null){
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("RU Pizzeria Order List:\n");
                writer.write("\n");
                for (Order order : orders){
                    ArrayList<String> pizzaDescriptions = generatePizzaDescriptions(FXCollections.observableArrayList(order.getPizzas()));
                    writer.write("\nOrder Number: " + order.getNumber() + "\n");
                    for (String string : pizzaDescriptions){
                        writer.write(string + "\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        createAlert("Success", "Orders Exported", "Your orders list has been sucessfully exported.");
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
     * Initializes all the events and controllers in the scene, displaying the proper information.
     */
    public void initialize(){
        orderTotal.setEditable(false);
        fillOrderNumberBox();
        displayDefault();
        orderNumberBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!orders.isEmpty()) {
                    displayOrderTotal();
                    displayOrder();
                }
            }
        });
    }

    /**
     * Displays the default information for all textfields, listview, and combobox.
     */
    public void displayDefault(){
        if (!orders.isEmpty()){
            orderNumberBox.setValue("" + orders.getFirst().getNumber());
            displayOrderTotal();
            displayOrder();
        }
        else{
            orderTotal.setText("$0.00");
            ordersListView.setItems(null);
        }
    }

    /**
     * Fills and updates the orderNumberBox with all the order numbers for all the orders.
     */
    private void fillOrderNumberBox(){
        ArrayList<Integer> orderNumbers = new ArrayList<>();
        for (Order order : orders){
            orderNumbers.add(order.getNumber());
        }
        orderNumberBox.getItems().clear();
        if(!orderNumbers.isEmpty()) orderNumberBox.setValue(String.valueOf(orderNumbers.getFirst()));
        else orderNumberBox.setValue(null);
        orderNumberBox.getItems().addAll(orderNumbers);
    }

    /**
     * Displays the correct total price of the order for the associated chosen order number.
     */
    private void displayOrderTotal() {
            int orderNumber = Integer.parseInt(String.valueOf((orderNumberBox.getValue())));
            Order order = findOrder(orderNumber);
            orderTotal.setText("$" + String.format("%.02f", order.getTotalPrice()));
    }

    /**
     * Displays the full list of pizzas in the order for the associated chosen order number.
     */
    private void displayOrder(){
        int orderNumber = Integer.parseInt(String.valueOf((orderNumberBox.getValue())));
        Order order = findOrder(orderNumber);
        ObservableList<Pizza> ordersList = FXCollections.observableArrayList(order.getPizzas());
        ordersListView.setItems(null);
        ordersListView.setItems(FXCollections.observableArrayList(generatePizzaDescriptions(ordersList)));
    }

    /**
     * Generates the correct description for the particular pizza. This discription includes the cost (rounded up to 2 decimal places),
     * the type, the size, tbe crust, and all the toppings.
     * @param pizzas - the list of Pizza objects to generate descriptions for.
     * @return the ArrayList of Strings of pizza descriptions.
     */
    private ArrayList<String> generatePizzaDescriptions(ObservableList<Pizza> pizzas){
        ArrayList<String> pizzasDescriptions = new ArrayList<>();
        String type = "";
        for(Pizza pizza : pizzas){
            if(pizza instanceof BBQChicken) type = "BBQ Chicken Pizza";
            else if (pizza instanceof Meatzza) type = "Meatzza Pizza";
            else if (pizza instanceof Deluxe) type = "Deluxe";
            else type = "Build Your Own Pizza";
            pizzasDescriptions.add("PRICE: $" + String.format("%.02f", pizza.price()) + "  | TYPE: " + type + "  | SIZE: " + pizza.getSize() + "  | CRUST: " + pizza.getCrust() + "  | TOPPINGS: " + pizza.getToppings());
        }
        return pizzasDescriptions;
    }

    /**
     * Finds the correct order in the orders ArrayList given the order number. Returns a null object otherwise.
     * @param number - the order number
     * @return the found Order object.
     */
    private Order findOrder(int number){
        for (Order order : orders){
            if (order.getNumber() == number) return order;
        }
        return null;
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
