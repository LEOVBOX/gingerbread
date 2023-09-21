package com.example.gingerbread;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Resource {
    int id;
    String name;
    int count;
    String units;


    Resource(String name, int count) {
        this.count = count;
        this.name = name;
        this.id = -1;
    }

    Resource(String name) {
        this.name = name;
        count = 0;
        this.id = -1;
    }

    Resource(int id, String name, int count) {
        this.name = name;
        this.count = count;
        this.id = id;
    }

    Resource(String name, int count, String units) {
        this.name = name;
        this.count = count;
        this.units = units;
        id = -1;
    }

    Resource() {
        id = -1;
    }

    Resource(int id) {
        this.id = id;
    }

    public void print() {
        System.out.println("id: " + id + "\nname: " + name + "\ncount: " + count + "\n");
    }


    // TODO Исправить создание новой таблицы ресурсов рецепта если имя рецепта с пробелами
    public void save(String tableName) {
        Connection connection = null;
        try {
            // Loading the driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER PRIMARY KEY, name TEXT, count INTEGER, units TEXT)");

            if (id == -1) {
                id = Gingerbread.setId(tableName);
            }

            // Insert a new object to the table
            PreparedStatement preparedStatement;
            System.out.println("Name: " + this.name + " id = " + this.id + " saving");

            if (!Gingerbread.isIdExists(id, tableName)) {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO " + tableName + " (id, name, count, units) VALUES (?, ?, ?, ?)");
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setInt(3, count);
                preparedStatement.setString(4, units);
                // Execute the call
                preparedStatement.executeUpdate();
                // Closing connections
                preparedStatement.close();
            }
            else {
                preparedStatement = connection.prepareStatement(
                        "UPDATE " + tableName + " SET name = ?, count = ?, units = ? WHERE id = ?"
                );
                preparedStatement.setInt(4, id);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, count);
                preparedStatement.setString(3, units);
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

    public void load(String tableName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            String query = "SELECT * FROM " + tableName + " WHERE id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // read the values from the database and create a new instance of MyClass
                String name = rs.getString("name");
                int count = rs.getInt("count");
                String units = rs.getString("units");
                this.name = name;
                this.count = count;
                this.units = units;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) {}
            try { pstmt.close(); } catch (Exception e) {}
            try { conn.close(); } catch (Exception e) {}
        }

    }

    public void deleteResource(String tableName) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            pstmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            pstmt.setInt(1, id);
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

}
