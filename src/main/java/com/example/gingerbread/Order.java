package com.example.gingerbread;

import java.sql.*;
import java.util.ArrayList;

public class Order {
    private int id;
    private String name;
    private String deadline;

    private String description;

    private double cost;
    private ArrayList<Recipe> recipes;

    public Order() {
        this.id = -1;
    }

    public Order(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeadline() {return deadline;}

    public void setDeadline(String deadline) {this.deadline = deadline;}

    public double getCost() {return cost;}

    public void setCost(double cost) {this.cost = cost;}

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public void load() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            pstmt = conn.prepareStatement("SELECT * FROM main.orders WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // read the values from the database and create a new instance of MyClass
                String name = rs.getString("name");
                String deadline = rs.getString("deadline");
                String description = rs.getString("description");
                double cost = rs.getDouble("cost");
                this.name = name;
                this.deadline = deadline;
                this.cost = cost;
                this.description = description;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) {}
            try { pstmt.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        }

    }

    public void save() {
        Connection connection = null;
        if (id == -1) {
            id = Gingerbread.setId("orders");
        }
        try {
            // Loading the driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS orders (id INTEGER PRIMARY KEY, name TEXT, description TEXT, deadline TEXT, cost REAL)");

            // Insert a new object to the table
            PreparedStatement preparedStatement;
            System.out.println("Name: " + this.name + " id = " + this.id + " saving");

            if (!Gingerbread.isIdExists(id, "orders")) {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO orders (id, name, description, deadline, cost) VALUES (?, ?, ?, ?, ?)");
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, description);
                preparedStatement.setString(4, deadline);
                preparedStatement.setDouble(5, cost);
                // Execute the call
                preparedStatement.executeUpdate();
                // Closing connections
                preparedStatement.close();
            }
            else {
                preparedStatement = connection.prepareStatement(
                        "UPDATE orders SET name = ?, description = ?, deadline = ?, cost = ? WHERE id = ?"
                );
                preparedStatement.setInt(5, this.id);
                preparedStatement.setString(1, this.name);
                preparedStatement.setString(2, this.description);
                preparedStatement.setString(3, this.deadline);
                preparedStatement.setDouble(4, this.cost);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("This is not new object");
            }

            statement.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            pstmt = conn.prepareStatement("DELETE FROM main.orders WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            //pstmt = conn.prepareStatement("DROP TABLE IF EXISTS " + name + "_OrderRecipes");
            //pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void print() {
        System.out.println("id: " + id + "\nname: " + name + "\ndeadline: " +  deadline + "\ncost" + cost);
    }

}
