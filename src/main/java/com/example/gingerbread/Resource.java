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



    public void save() {
        Connection connection = null;
        if (id == -1) {
            id = Gingerbread.setId("resources");
        }
        try {
            // Loading the driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS resources (id INTEGER PRIMARY KEY, name TEXT, count INTEGER, units TEXT)");

            // Insert a new object to the table
            PreparedStatement preparedStatement;
            System.out.println("Name: " + this.name + " id = " + this.id + " saving");

            if (!Gingerbread.isIdExists(id, "resources")) {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO resources (id, name, count, units) VALUES (?, ?, ?, ?)");
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
                        "UPDATE resources SET name = ?, count = ?, units = ? WHERE id = ?"
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

    public void load() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            pstmt = conn.prepareStatement("SELECT * FROM main.resources WHERE id = ?");
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

    public void deleteResource() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            pstmt = conn.prepareStatement("DELETE FROM main.resources WHERE id = ?");
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
