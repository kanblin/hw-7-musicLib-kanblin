package edu.usfca.cs;

import java.sql.*;
import java.util.Scanner;

/**
 * Player is the text based GUI
 * The player will populate the library from the music.db file.  From there the user can add songs with limited
 * information.  The player will use the GetInfo class and provide missing information.  It will update the SQL library
 * as information is added.
 */

public class Player {

    public void player() {
        boolean on = true;
        Library lib = new Library();
        GetInfo tool = new GetInfo();


        while (on){
            Scanner scan = new Scanner(System.in);
            Parser par = new Parser();
            String pick;

            System.out.println("-------------------");
            System.out.println("Music Library");
            System.out.println("Would you like to " +
                    "\n 1) display all music in library " +
                    "\n 2) generate a playlist" +
                    "\n 3) edit library" +
                    "\n 4) quit" );
            pick = scan.nextLine();

            lib = par.songsFromSQL();
            switch(Integer.parseInt(pick)) {
                case 4:
                    on = false;
                    break;

                case 1:
                    printSQL();
                    break;

                case 3:
                    System.out.println("Would you like to " +
                            "\n 1) Add song by name & artist " +
                            "\n 2) Add song by name & album" +
                            "\n 3) Add song with all the info");
                    pick = scan.nextLine();

                    String artist, song, album;
                    System.out.println("--------------");
                    System.out.println("What is the name of the song?");
                    song = scan.nextLine();

                    if(Integer.parseInt(pick) == 1){
                        System.out.println("Who is the artist?");
                        artist = scan.nextLine();
                        album = tool.songArtExample(song, artist);
                        par.SArtoSQL(song, artist, album);

                    } else if(Integer.parseInt(pick) == 2){
                        System.out.println("What is the name of the album?");
                        album = scan.nextLine();
                        artist = tool.albumGetArt(album);
                        par.SArtoSQL(song, artist, album);

                    } else if(Integer.parseInt(pick) == 3){
                        System.out.println("Who is the artist?");
                        artist = scan.nextLine();
                        System.out.println("What is the name of the album?");
                        album = scan.nextLine();
                        par.SArtoSQL(song, artist, album);
                    }
                    break;
                case 2:
//                    lib =  par.songsFromSQL();
                    lib.randomLikes();
                    System.out.println("Would you like to " +
                            "\n 1) Playlist by Artist " +
                            "\n 2) Liked Playlist" );
                    pick = scan.nextLine();
                    if(Integer.parseInt(pick) == 1){
                        System.out.println("Who is the artist?");
                        artist = scan.nextLine();
                        lib.filterByArt(artist);

                    } else if(Integer.parseInt(pick) == 2){
                        lib.filterLikedList();
                    }
                    System.out.println("New playlist exported to xml file");

                    break;
                default:
                    on = false;
                    break;
            }
        }

    }

    /**
     * printSQL connects to the sql database and prints out the current SQL songs table.
     * This allows the user to have an up to date library at all times.
     */
    public void printSQL(){
        Connection connection = null;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("select * from songs");
            int temp;
            while (rs.next()) {

                temp = rs.getInt("artist");
                String currentArt = artFromSQL(temp);
                temp = rs.getInt("album");
                String currentAlb = albFromSQL(temp);
                System.out.println("id:" + rs.getInt("id") + " " +  rs.getString("name")
                        + " " + "artist : " + currentArt + " " + "album : " + currentAlb);

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
