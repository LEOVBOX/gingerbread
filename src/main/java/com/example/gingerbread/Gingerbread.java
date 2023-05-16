package com.example.gingerbread;

import java.sql.*;
import java.util.ArrayList;

public class Gingerbread {
    public static ArrayList<Resource> loadResourses() {
        Connection connection;
        ArrayList<Resource> result = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();
            String query = "SELECT id FROM main.resources";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Resource resource = new Resource(id);
                result.add(resource);
            }
            resultSet.close();
            connection.close();
            for (Resource resource : result) {
                resource.load();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Resource getResourseByName(String name) {
        ArrayList<Resource> resources = loadResourses();
        for (Resource resource : resources) {
            if (resource.name.equals(name)) {
                return resource;
            }
        }
        return null; // если объект не найден
    }


    public static ArrayList<Recipe> loadRecipes() {
        Connection connection;
        ArrayList<Recipe> result = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();
            String query = "SELECT id FROM main.recipes";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Recipe recipe = new Recipe(id);
                result.add(recipe);
            }
            resultSet.close();
            connection.close();
            for (Recipe recipe : result) {
                recipe.load();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int setId(String tableName)
    {
        Connection connection = null;
        int lastId = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + tableName + " ORDER BY id DESC LIMIT 1";
            ResultSet rs = statement.executeQuery(query);
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

    public static boolean isIdExists(int id, String tableName) {
        String query = "SELECT id FROM " + tableName + " WHERE id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


}
