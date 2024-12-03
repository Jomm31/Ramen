package com.example.cce104_ramen;

import java.sql.*;

public class DataBase {

    private final String url = "jdbc:mysql://localhost:3306/SamuraiRamen";
    private final String user = "root";
    private final String password = "";
    private Connection connection = null;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to get the latest Order_ID for Transactions
    public int getCurrentOrderID() {
        String query = "SELECT MAX(Order_ID) AS Order_ID FROM Orders";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("Order_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Default to 0 if no orders
    }

    public void insertOrder(String ramen, String meals, String beverages, String desserts) {
        String insertOrder = "INSERT INTO Orders (Ramen, Meals, Beverages, Desserts) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertOrder)) {
            pstmt.setString(1, ramen);
            pstmt.setString(2, meals);
            pstmt.setString(3, beverages);
            pstmt.setString(4, desserts);
            pstmt.executeUpdate();
            System.out.println("Order inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Insert order failed: " + e.getMessage());
        }
    }

    public void insertTransaction(int totalAmount, int orderId) {
        String insertTransaction = "INSERT INTO Transactions (Order_ID, TotalAmount) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertTransaction)) {
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, totalAmount);
            pstmt.executeUpdate();
            System.out.println("Transaction inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Insert transaction failed: " + e.getMessage());
        }
    }
}
