package com.example.cce104_ramen;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeController {

    @FXML
    private AnchorPane main_pane;

    @FXML
    private AnchorPane rightPane;

    @FXML
    private AnchorPane Ramen_Menu;

    @FXML
    private AnchorPane Meals_Menu;

    @FXML
    private AnchorPane Dessert_Menu;

    @FXML
    private AnchorPane Beverages_Menu;

    @FXML
    private AnchorPane View_Orders;

    @FXML
    private AnchorPane First_Appearance;

    @FXML
    private Button MisoRamen, ShoyuRamen, SpicyKimchiRamen, TonkotsuRamen, VeganRamen;
    @FXML
    private Button SamuraiSushiSet, TeriyakiRiceBowl, ChickenKatsuCurry, BeefGyudon, SalmonSpecial, UnagiDonburi;
    @FXML
    private Button MatchaIceCream, MatchaTiramisu, Mochi, Dorayaki, Yokan, Warabi;
    @FXML
    private Button MatchaLatte, BubbleTea, CocaCola, Pepsi, Sake, Sprite, IcedTea, CokeZero;

    @FXML
    private TableView<Order> OrderViewTable;

    @FXML
    private TableColumn<Order, String> ordersColumn;

    @FXML
    private TableColumn<Order, Integer> priceColumn;

    @FXML
    private Button ConfirmOrder_Button;

    @FXML
    private TextArea TotalAmountTxtArea;

    private ObservableList<Order> orderList = FXCollections.observableArrayList();
    private int totalPrice = 0;

    // Lists to store items of each category
    private List<String> ramenOrders = new ArrayList<>();
    private List<String> mealOrders = new ArrayList<>();
    private List<String> beverageOrders = new ArrayList<>();
    private List<String> dessertOrders = new ArrayList<>();

    private DataBase database = new DataBase(); // Database instance

    public void initialize() {
        database.connect(); // Establish database connection
        setupTableView();
        setButtonsOrders();
        ConfirmOrder_Button.setOnAction(e -> confirmOrder());
    }

    private void setupTableView() {
        ordersColumn.setCellValueFactory(new PropertyValueFactory<>("orderName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        OrderViewTable.setItems(orderList);
    }

    public void setButtonsOrders() {
        // Ramen Orders
        TonkotsuRamen.setOnAction(e -> addOrder("Tonkotsu Ramen", 160, ramenOrders)); // Doubled from 80 to 160 PHP
        MisoRamen.setOnAction(e -> addOrder("Miso Ramen", 160, ramenOrders)); // Doubled from 80 to 160 PHP
        ShoyuRamen.setOnAction(e -> addOrder("Shoyu Ramen", 160, ramenOrders)); // Doubled from 80 to 160 PHP
        SpicyKimchiRamen.setOnAction(e -> addOrder("Spicy Kimchi Ramen", 180, ramenOrders)); // Doubled from 90 to 180 PHP
        VeganRamen.setOnAction(e -> addOrder("Vegan Ramen", 170, ramenOrders)); // Doubled from 85 to 170 PHP

        // Meal Orders
        SamuraiSushiSet.setOnAction(e -> addOrder("Samurai Sushi Set", 160, mealOrders)); // Doubled from 80 to 160 PHP
        TeriyakiRiceBowl.setOnAction(e -> addOrder("Teriyaki Rice Bowl", 160, mealOrders)); // Doubled from 80 to 160 PHP
        ChickenKatsuCurry.setOnAction(e -> addOrder("Chicken Katsu Curry", 170, mealOrders)); // Doubled from 85 to 170 PHP
        BeefGyudon.setOnAction(e -> addOrder("Beef Gyudon", 160, mealOrders)); // Doubled from 80 to 160 PHP
        SalmonSpecial.setOnAction(e -> addOrder("Samurai Salmon Special", 180, mealOrders)); // Doubled from 90 to 180 PHP
        UnagiDonburi.setOnAction(e -> addOrder("Unagi Donburi", 170, mealOrders)); // Doubled from 85 to 170 PHP

        // Dessert Orders
        MatchaIceCream.setOnAction(e -> addOrder("Matcha Ice Cream", 140, dessertOrders)); // Doubled from 70 to 140 PHP
        MatchaTiramisu.setOnAction(e -> addOrder("Matcha Tiramisu", 140, dessertOrders)); // Doubled from 70 to 140 PHP
        Mochi.setOnAction(e -> addOrder("Mochi", 120, dessertOrders)); // Doubled from 60 to 120 PHP
        Dorayaki.setOnAction(e -> addOrder("Dorayaki", 140, dessertOrders)); // Doubled from 70 to 140 PHP
        Yokan.setOnAction(e -> addOrder("Yokan", 140, dessertOrders)); // Doubled from 70 to 140 PHP
        Warabi.setOnAction(e -> addOrder("Warabi", 140, dessertOrders)); // Doubled from 70 to 140 PHP

        // Beverage Orders
        MatchaLatte.setOnAction(e -> addOrder("Matcha Latte", 90, beverageOrders)); // Doubled from 70 to 140 PHP
        BubbleTea.setOnAction(e -> addOrder("Bubble Tea", 110, beverageOrders)); // Doubled from 80 to 160 PHP
        CocaCola.setOnAction(e -> addOrder("Coca-Cola", 50, beverageOrders)); // Doubled from 50 to 100 PHP
        Pepsi.setOnAction(e -> addOrder("Pepsi", 50, beverageOrders)); // Doubled from 50 to 100 PHP
        Sake.setOnAction(e -> addOrder("Sake", 170, beverageOrders)); // Doubled from 120 to 240 PHP
        Sprite.setOnAction(e -> addOrder("Sprite", 50, beverageOrders)); // Doubled from 50 to 100 PHP
        IcedTea.setOnAction(e -> addOrder("Iced Tea", 50, beverageOrders)); // Doubled from 50 to 100 PHP
        CokeZero.setOnAction(e -> addOrder("Coke Zero", 60, beverageOrders)); // Doubled from 50 to 100 PHP
    }

    private void addOrder(String itemName, int price, List<String> categoryList) {
        Order newOrder = new Order(itemName, price);
        orderList.add(newOrder);
        categoryList.add(itemName);
        totalPrice += price;
        TotalAmountTxtArea.setText("₱" + totalPrice);
    }

    private void confirmOrder() {
        try {
            // Combine orders from each category into a single string
            String ramen = ramenOrders.isEmpty() ? "" : String.join(", ", ramenOrders);
            String meals = mealOrders.isEmpty() ? "" : String.join(", ", mealOrders);
            String beverages = beverageOrders.isEmpty() ? "" : String.join(", ", beverageOrders);
            String desserts = dessertOrders.isEmpty() ? "" : String.join(", ", dessertOrders);

            // Insert the combined order into the Orders table
            database.insertOrder(ramen, meals, beverages, desserts);

            // Store the total amount in the Transactions table
            int currentOrderID = database.getCurrentOrderID(); // Fetch current Order_ID
            database.insertTransaction(totalPrice, currentOrderID);

            // Reset for the next customer
            orderList.clear();
            ramenOrders.clear();
            mealOrders.clear();
            beverageOrders.clear();
            dessertOrders.clear();
            TotalAmountTxtArea.clear();
            totalPrice = 0;

            // Optionally, clear the TableView
            OrderViewTable.setItems(orderList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load different menus
    public void loadFrame(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node newContent = loader.load();
            rightPane.getChildren().clear();
            rightPane.getChildren().add(newContent);
            AnchorPane.setTopAnchor(newContent, 0.0);
            AnchorPane.setBottomAnchor(newContent, 0.0);
            AnchorPane.setLeftAnchor(newContent, 0.0);
            AnchorPane.setRightAnchor(newContent, 0.0);
        } catch (IOException e) {
            System.out.println("Error loading frame: " + fxmlFile);
            e.printStackTrace();
        }
    }

    // Menu visibility functions
    public void RamenMenu() {
        setMenuVisibility(true, false, false, false, false, false);
    }

    public void MealsMenu() {
        setMenuVisibility(false, true, false, false, false, false);
    }

    public void DessertsMenu() {
        setMenuVisibility(false, false, true, false, false, false);
    }

    public void BeveragesMenu() {
        setMenuVisibility(false, false, false, true, false, false);
    }

    public void ViewOrdersMenu() {
        setMenuVisibility(false, false, false, false, true, false);
    }

    public void FirstAppearance() {
        setMenuVisibility(false, false, false, false, false, true);
    }

    private void setMenuVisibility(boolean ramen, boolean meals, boolean desserts, boolean beverages, boolean viewOrders, boolean firstAppearance) {
        Ramen_Menu.setVisible(ramen);
        Meals_Menu.setVisible(meals);
        Dessert_Menu.setVisible(desserts);
        Beverages_Menu.setVisible(beverages);
        View_Orders.setVisible(viewOrders);
        First_Appearance.setVisible(firstAppearance);
    }

    public void refresh_style() {
        if (!main_pane.getStylesheets().contains(getClass().getResource("transitioncolor.css").toExternalForm())) {
            main_pane.getStylesheets().removeAll();
            main_pane.getStylesheets().add(getClass().getResource("transitioncolor.css").toExternalForm());
        }
    }
}