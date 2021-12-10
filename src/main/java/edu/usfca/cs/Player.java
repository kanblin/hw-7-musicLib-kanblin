package edu.usfca.cs;

import java.sql.*;
import java.util.Scanner;

public class Player {

    public void player() {
        boolean on = true;


        while (on){
            Scanner scan = new Scanner(System.in);
            String pick;

            System.out.println("Music Library");
            System.out.println("Would you like to " +
                    "\n 1) display all music in library " +
                    "\n 2) generate a playlist" +
                    "\n 3) edit library" +
                    "\n 4) quit" );
            pick = scan.nextLine();

            switch(Integer.parseInt(pick)){
                case 4:
                    on = false;
                    break;

                case 1:
                    printSQL();






            }
        }

    }

    public void printSQL(){
        Connection connection = null;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            ResultSet rs = statement.executeQuery("select * from songs");
//            ResultSet as , al;
            int temp;
            while (rs.next()) {
                // read the result set
                System.out.println("id = " + rs.getInt("id"));
                System.out.println("name = " + rs.getString("name"));

                temp = rs.getInt("artist");
                String currentArt = artFromSQL(temp);
                System.out.println("artist = " + currentArt);


                temp = rs.getInt("album");
                String currentAlb = albFromSQL(temp);
                System.out.println("album = " + currentAlb);


            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

    }

    public String artFromSQL(int artistID){
        Connection connection = null;
        PreparedStatement myStmt = null;
        ResultSet myRes = null;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            myStmt = connection.prepareStatement("select * from artists where id=?");
            myStmt.setInt(1, artistID);
            myRes = myStmt.executeQuery();
            return myRes.getString("name");

        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return null;
    }

    public String albFromSQL(int albID){
        Connection connection = null;
        PreparedStatement myStmt = null;
        ResultSet myRes = null;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            myStmt = connection.prepareStatement("select * from albums where id=?");
            myStmt.setInt(1, albID);
            myRes = myStmt.executeQuery();
            return myRes.getString("name");

        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return "artist not provided";
    }

    public static void main(String[] args) {
        Player play = new Player();
        play.player();

    }

}
