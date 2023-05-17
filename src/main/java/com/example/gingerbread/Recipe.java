package com.example.gingerbread;

import java.sql.*;

public class Recipe {

    private int id;
    private String name;
    private String description;
    private String imagePath;

    public Recipe() {
        this.id = -1;
    }
    public Recipe(int id) {
        this.id = id;
    }

    public Recipe(String name) {this.name = name;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {return description;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void load() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            pstmt = conn.prepareStatement("SELECT * FROM main.recipes WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // read the values from the database and create a new instance of MyClass
                String name = rs.getString("name");
                String description = rs.getString("description");
                String imagePath = rs.getString("imagePath");
                this.name = name;
                this.description = description;
                this.imagePath = imagePath;
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
            id = Gingerbread.setId("recipes");
        }
        try {
            // Loading the driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS recipes (id INTEGER PRIMARY KEY, name TEXT, description TEXT, imagePath TEXT)");

            // Insert a new object to the table
            PreparedStatement preparedStatement;
            System.out.println("Name: " + this.name + " id = " + this.id + " saving");

            if (!Gingerbread.isIdExists(id, "recipes")) {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO recipes (id, name, description, imagePath) VALUES (?, ?, ?, ?)");
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, description);
                preparedStatement.setString(4, imagePath);
                // Execute the call
                preparedStatement.executeUpdate();
                // Closing connections
                preparedStatement.close();
            }
            else {
                preparedStatement = connection.prepareStatement(
                        "UPDATE recipes SET name = ?, description = ?, imagePath = ? WHERE id = ?"
                );
                preparedStatement.setInt(4, id);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, imagePath);
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

    public void deleteRecipe() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            pstmt = conn.prepareStatement("DELETE FROM main.recipes WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement("DROP TABLE IF EXISTS " + name + "_RecipeResources");
            pstmt.executeUpdate();
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
        System.out.println("id: " + id + "\nname: " + name + "\ndescription: " +  description + "\n");
    }

}



