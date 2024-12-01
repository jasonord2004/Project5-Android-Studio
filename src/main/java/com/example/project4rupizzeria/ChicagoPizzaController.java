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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class handles the pizza selection process for the ChicagoPizza factory and adds the pizza to the order.
 * @author Jason Ordonez, Ankit Mithbavkar
 */
public class ChicagoPizzaController {

    /**
     * Adds the Pizza to the order when clicked.
     */
    @FXML
    private Button AddToOrderButton;

    /**
     * Adds the chosen topping to the pizza when clicked.
     */
    @FXML
    private Button AddToppingsButton;

    /**
     * Sends the user back to the main menu when clicked.
     */
    @FXML
    private Button BackButton;

    /**
     * Displays the list of available toppings.
     */
    @FXML
    private ListView<Topping> AvailableToppingsListView;

    /**
     * Displays the corresponding image for the pizza type.
     */
    @FXML
    private ImageView CSPizzaImage;

    /**
     * Displays the type of crust (dependent on the pizza type).
     */
    @FXML
    private TextField CrustTextField;

    /**
     * The associated selection button for the large size.
     */
    @FXML
    private RadioButton LRadioButton;

    /**
     * The associated selection button for the medium size.
     */
    @FXML
    private RadioButton MRadioButton;

    /**
     * Removes a chosen topping from the pizza when clicked.
     */
    @FXML
    private Button RemoveToppingsButton;

    /**
     * The associated selection button for the small size.
     */
    @FXML
    private RadioButton SRadioButton;

    /**
     * Displays a list of the toppings the user selected.
     */
    @FXML
    private ListView<Topping> SelectedToppingsListView;

    /**
     * Displays the total price of the pizza (not including tax).
     */
    @FXML
    private TextField TotalPriceTextField;

    /**
     * Allows the user to choose the desired pizza type from a list of pizza types.
     */
    @FXML
    private ComboBox<String> TypeComboBox;

    /**
     * The group of toggle buttons containing the size option radio buttons.
     */
    @FXML
    private ToggleGroup ToggleSize;

    /**
     * The pizzaFactory object which contains the abstract methods used for creating the pizzas types.
     */
    private PizzaFactory pizzaFactory;

    /**
     * The Pizza object that the user customizes.
     */
    private Pizza pizza;

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
     * Initializes the scene, filling the comboboxes, initializing the PizzaFactory object and controlling the editable permissions.
     */
    public void initialize(){
        fillTypes();
        setDisable();
        TypeComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setEnable();
                CrustTextField.setEditable(false);
                TotalPriceTextField.setEditable(false);
                pizzaFactory = new ChicagoPizza();
                setCrust();
                setPicture();
                setToppings();
                setPizza();
                setTotalPrice();
            }
        });
    }

    /**
     * Sets all controllers to be unusable.
     */
    private void setDisable(){
        LRadioButton.setDisable(true);
        MRadioButton.setDisable(true);
        SRadioButton.setDisable(true);
        AddToOrderButton.setDisable(true);
        AddToppingsButton.setDisable(true);
        RemoveToppingsButton.setDisable(true);
        CrustTextField.setDisable(true);
        TotalPriceTextField.setDisable(true);
    }

    /**
     * Sets all controllers to be usable.
     */
    private void setEnable(){
        LRadioButton.setDisable(false);
        MRadioButton.setDisable(false);
        SRadioButton.setDisable(false);
        AddToOrderButton.setDisable(false);
        AddToppingsButton.setDisable(false);
        RemoveToppingsButton.setDisable(false);
        CrustTextField.setDisable(false);
        TotalPriceTextField.setDisable(false);
    }

    /**
     * Fills the TypeComboBox with all the options for pizza types.
     */
    @FXML
    void fillTypes(){
        String[] types = {"Build Your Own", "BBQ Chicken", "Deluxe", "Meatzza"};
        TypeComboBox.getItems().addAll(types);
    }

    /**
     * Sets the size of Pizza to large when the radio button is selected.
     * @param event - the mouse clicks on the button.
     */
    @FXML
    void largeSelected(MouseEvent event){
        LRadioButton.setToggleGroup(ToggleSize);
        pizza.setSize(sizeSelected());
        setTotalPrice();
    }

    /**
     * Sets the size of Pizza to medium when the radio button is selected.
     * @param event - the mouse clicks on the button.
     */
    @FXML
    void mediumSelected(MouseEvent event){
        MRadioButton.setToggleGroup(ToggleSize);
        pizza.setSize(sizeSelected());
        setTotalPrice();
    }

    /**
     * Sets the size of Pizza to small when the radio button is selected.
     * @param event - the mouse clicks on the button.
     */
    @FXML
    void smallSelected(MouseEvent event){
        SRadioButton.setToggleGroup(ToggleSize);
        pizza.setSize(sizeSelected());
        setTotalPrice();
    }

    /**
     * Returns the size enum object depending on which radio button is selected.
     * @return the Size enum object.
     */
    private Size sizeSelected(){
        if(LRadioButton.isSelected()) return Size.LARGE;
        else if(MRadioButton.isSelected()) return Size.MEDIUM;
        else return Size.SMALL;
    }

    /**
     * Displays the type of crust depending on the type of pizza selected.
     */
    void setCrust(){
        switch(TypeComboBox.getValue()){
            case "Build Your Own": CrustTextField.setText("Pan");break;
            case "BBQ Chicken": CrustTextField.setText("Pan"); break;
            case "Deluxe": CrustTextField.setText("Deep Dish"); break;
            case "Meatzza": CrustTextField.setText("Stuffed"); break;
            default: break;
        }
    }

    /**
     * Displays the image for the associated pizza type.
     */
    void setPicture(){
        try {
            switch (TypeComboBox.getValue()) {
                case "Build Your Own":
                    Image yourOwn = new Image(getClass().getResource("Build-Your-Own-CSP.jpg").openStream());
                    CSPizzaImage.setImage(yourOwn);
                    break;
                case "BBQ Chicken":
                    Image bbq = new Image(getClass().getResource("BBQ-CSP.jpg").openStream());
                    CSPizzaImage.setImage(bbq);
                    break;
                case "Deluxe":
                    Image deluxe = new Image(getClass().getResource("Deluxe-CSP.jpg").openStream());
                    CSPizzaImage.setImage(deluxe);
                    break;
                case "Meatzza":
                    Image meatzza = new Image(getClass().getResource("Meatzza-CSP.jpg").openStream());
                    CSPizzaImage.setImage(meatzza);
                    break;
                default:
                    break;
            }
        }
        catch (IOException e) { createAlert("FileNotFoundException", "Image Not Found", "Please insert a valid path to the image.");}
        catch (NullPointerException e) {createAlert("NullPointerException", "Image Not Found", "Please insert a valid path to the image.");}
    }

    /**
     * Displays the proper list of avaialble and selected toppings depending on the chosen pizza type.
     */
    void setToppings(){
        ObservableList<Topping> toppings = FXCollections.observableArrayList(Topping.values());
        switch(TypeComboBox.getValue()){
            case "Build Your Own":
                SelectedToppingsListView.setItems(null);
                AvailableToppingsListView.setItems(toppings);
                break;
            case "BBQ Chicken":
                AvailableToppingsListView.setItems(null);
                toppings = FXCollections.observableArrayList(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR);
                SelectedToppingsListView.setItems(toppings);
                SelectedToppingsListView.setEditable(false);
                break;
            case "Deluxe":
                AvailableToppingsListView.setItems(null);
                toppings = FXCollections.observableArrayList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM);
                SelectedToppingsListView.setItems(toppings);
                SelectedToppingsListView.setEditable(false);
                break;
            case "Meatzza":
                AvailableToppingsListView.setItems(null);
                toppings = FXCollections.observableArrayList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM);
                SelectedToppingsListView.setItems(toppings);
                SelectedToppingsListView.setEditable(false);
                break;
            default: break;
        }
    }

    /**
     * Moves the chosen topping from the Available Toppings list to the Selected Toppings list.
     */
    @FXML
    void addSelectedToppings(){
        if(!TypeComboBox.getValue().equals("Build Your Own")) return;
        Topping addTopping = AvailableToppingsListView.getSelectionModel().getSelectedItem();
        try{
            if (addTopping == null) return;
            else if (SelectedToppingsListView.getItems().size() >= 7) {
                createAlert("Pizza Toppings Limit", "Toppings Limit!", "Maximum of 7 Toppings on a Pizza");
                return;
            }
        }
        catch (NullPointerException e) {
            SelectedToppingsListView.setItems(FXCollections.observableArrayList(addTopping));
            AvailableToppingsListView.getItems().remove(addTopping);
            setPizza();
            pizza.addTopping(addTopping);
            setTotalPrice();
            return;
        }
        ObservableList<Topping> toppings = FXCollections.observableArrayList(SelectedToppingsListView.getItems());
        toppings.add(addTopping);
        SelectedToppingsListView.setItems(toppings);
        AvailableToppingsListView.getItems().remove(addTopping);
        pizza.addTopping(addTopping);
        setTotalPrice();
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
     * Moves the toppings from the Selected Toppings list to the Available Toppings List.
     */
    @FXML
    void removeSelectedToppings(){
        if(!TypeComboBox.getValue().equals("Build Your Own")) return;
        try {
            Topping removeTopping = SelectedToppingsListView.getSelectionModel().getSelectedItem();
            if (removeTopping != null) {
                SelectedToppingsListView.getItems().remove(removeTopping);
                AvailableToppingsListView.getItems().add(removeTopping);
                pizza.removeTopping(removeTopping);
            }
        } catch (NullPointerException exception) { return;}
        setTotalPrice();
    }

    /**
     * Creates the corresponding Pizza object for the chosen pizza type.
     */
    public void setPizza(){
        switch(TypeComboBox.getValue()){
            case "Build Your Own": pizza = pizzaFactory.createBuildYourOwn(); break;
            case "BBQ Chicken": pizza = pizzaFactory.createBBQChicken(); break;
            case "Deluxe": pizza = pizzaFactory.createDeluxe(); break;
            case "Meatzza": pizza = pizzaFactory.createMeatzza(); break;
            default: break;
        }
    }

    /**
     * Displays the total price for the Pizza.
     */
    @FXML
    void setTotalPrice(){
        pizza.setSize(sizeSelected());
        TotalPriceTextField.setText("$" + String.format("%.2f", pizza.price()));
    }

    /**
     * Adds the Pizza object to the list of orders when the button is clicked.
     * @param event - the mouse clicking on the button.
     */
    @FXML
    void addToOrderSelected(MouseEvent event){
        CurrentOrderController.pizzas.add(pizza);
        createAlert("Add to Order", "Pizza Added",TypeComboBox.getValue() + " Pizza added to current order");
    }

    /**
     * Allows the user to return back to the main menu when the button is clicked.
     * @param event - the event of the mouse being clicked
     * @throws IOException - input/output exception when the proper file isn't received.
     */
    @FXML
    void backToMenu(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage =  (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Welcome!");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

