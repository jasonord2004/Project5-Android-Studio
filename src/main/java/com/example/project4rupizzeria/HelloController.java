package com.example.project4rupizzeria;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is meant to manage all the event handling in the main menu interface.
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public class HelloController {

    /**
     * The stage for the interface.
     */
    private Stage stage;

    /**
     * The scene for the interface.
     */
    private Scene scene;

    /**
     * Used for loading the interface.
     */
    private Parent root;

    /**
     * The picture for the Chicago Style Pizza option.
     */
    @FXML
    private ImageView Chicago_Style_Pizza;

    /**
     * The picture for the Current Order option.
     */
    @FXML
    private ImageView Current_Order;

    /**
     * The picture for the NY Style Pizza option.
     */
    @FXML
    private ImageView NY_Style_Pizza;

    /**
     * The picture for the Orders Placed option.
     */
    @FXML
    private ImageView Orders_Placed;

    /**
     * Sends the user to Chicago Pizza customization screen.
     * @param event - the mouse clicking on the image.
     * @throws IOException - input/output exception when file isn't properly received.
     */
    @FXML
    public void onChicagoPizzaClicked(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("chicago-pizza-view.fxml"));
        stage =  (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Chicago Style Pizza Editor");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sends the user to current order placement view.
     * @param event - the mouse clicking on the image.
     * @throws IOException - input/output exception when file isn't properly received.
     */
    @FXML
    void onCurrentOrderClicked(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("current-order-view.fxml"));
        stage =  (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Current Order");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sends the user to NY Pizza customization screen.
     * @param event - the mouse clicking on the image.
     * @throws IOException - input/output exception when file isn't properly received.
     */
    @FXML
    void onNYPizzaClicked(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("ny-pizza-view.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("NY Style Pizza Editor");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sends the user to orders placed view.
     * @param event - the mouse clicking on the image.
     * @throws IOException - input/output exception when file isn't properly received.
     */
    @FXML
    void onOrdersPlacedClicked(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("orders-placed-view.fxml"));
        stage =  (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Orders Placed");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
