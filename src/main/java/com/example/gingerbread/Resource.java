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

    Resource(int id) {
        this.id = id;
    }

    public void print() {
        System.out.println("id: " + id + "\nname: " + name + "\ncount: " + count + "\n");
    }

    public boolean isIdExists(int id) {
        String sql = "SELECT id FROM main.resourses WHERE id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public int setId()
    {
        Connection connection = null;
        int lastId = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM main.resourses ORDER BY id DESC LIMIT 1");
            if (rs.next()) {
                lastId = rs.getInt("id");
            }
            lastId = rs.getInt(1);
            System.out.println("Last row id = " + lastId);
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return lastId + 1;
    }

    public void save() {
        Connection connection = null;
        if (id == -1) {
            id = setId();
        }
        try {
            // Loading the driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS resourses (id INTEGER PRIMARY KEY, name TEXT, count INTEGER, units TEXT)");

            // Insert a new object to the table
            PreparedStatement preparedStatement;
            System.out.println("Name: " + this.name + " id = " + this.id + " saving");

            if (!isIdExists(this.id)) {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO resourses (id, name, count, units) VALUES (?, ?, ?, ?)");
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
            pstmt = conn.prepareStatement("SELECT * FROM main.resourses WHERE id = ?");
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
            pstmt = conn.prepareStatement("DELETE FROM main.resourses WHERE id = ?");
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
