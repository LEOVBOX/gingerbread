package com.example.gingerbread;

import java.sql.*;
import java.util.ArrayList;

public class Gingerbread {

    /* TODO исправить баг: при создании нового рецепта,
        если название нового рецепта больше одного слова, то
        название таблицы создваемой для ингридиентов данного рецепта
        вмещает только последнее слово.
     */

    /*
        TODO сделать создание таблицы рецептов, если не существует
        TODO сделать создание таблицы заказов, если не существует
     */
    public static ArrayList<Resource> loadResources(String tableName) {
        Connection connection;
        ArrayList<Resource> result = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER PRIMARY KEY, name TEXT, count INTEGER, units TEXT)");
            String query = "SELECT id FROM " + tableName;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Resource resource = new Resource(id);
                result.add(resource);
            }
            resultSet.close();
            connection.close();
            for (Resource resource : result) {
                resource.load(tableName);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Resource getResourceByName(String name, String tableName) {
        ArrayList<Resource> resources = loadResources(tableName);
        for (Resource resource : resources) {
            if (resource.name.equals(name)) {
                return resource;
            }
        }
        return null; // если объект не найден
    }

    public static Recipe getRecipeByName(String name) {
        ArrayList<Recipe> recipes = loadRecipes();
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(name)) {
                return recipe;
            }
        }
        return null; // если объект не найден
    }

    public static Order getOrderByName(String name) {
        ArrayList<Order> orders = loadOrders();
        for (Order order : orders) {
            if (order.getName().equals(name)) {
                return order;
            }
        }
        return null; // если объект не найден
    }


    public static ArrayList<Recipe> loadRecipes() {
        Connection connection;
        String tableName = "recipes";
        ArrayList<Recipe> result = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();
            String query = "SELECT id FROM " + tableName;
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

    public static ArrayList<Order> loadOrders() {
        Connection connection;
        String tableName = "orders";
        ArrayList<Order> result = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();
            String query = "SELECT id FROM " + tableName;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Order order = new Order(id);
                result.add(order);
            }
            resultSet.close();
            connection.close();
            for (Order order : result) {
                order.load();
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

    public static ArrayList<OrdersRecipe> loadOrderRecipes (String tableName) {
        Connection connection;
        ArrayList<OrdersRecipe> result = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();
            String query = "SELECT recipeName FROM " + tableName;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String recipeName = resultSet.getString("recipeName");
                int count = resultSet.getInt("count");
                OrdersRecipe curRecipe = new OrdersRecipe(recipeName, count);
                result.add(curRecipe);
            }
            resultSet.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
