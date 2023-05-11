package com.example.gingerbread;

import java.sql.*;
import java.util.ArrayList;

public class Gingerbread {
    public static ArrayList<Resource> loadResourses() {
        Connection connection = null;
        ResultSet rs = null;
        ArrayList<Resource> result = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            Statement statement = connection.createStatement();
            String query = "SELECT id FROM main.resourses";
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

    public static void deleteResource(int id) throws SQLException {
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

    public static Resource getResourseByName(ArrayList<Resource> resources, String name) {
        for (Resource resource : resources) {
            if (resource.name.equals(name)) {
                return resource;
            }
        }
        return null; // если объект не найден
    }

}
