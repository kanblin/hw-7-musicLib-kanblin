package edu.usfca.cs;

import java.sql.*;
import java.util.ArrayList;

public class Artist extends Entity {

    protected ArrayList<Song> songs;
    protected ArrayList<Album> albums;

    public Artist(String name) {
        super(name);
    }

    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    protected ArrayList<Album> getAlbums() {
        return albums;
    }

    protected void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public void addSong(Song s) {
        songs.add(s);
    }

    public int getArtistIDfromSQL(){
        Connection connection = null;
        PreparedStatement myStmt = null;
        ResultSet myRes = null;
        int result  = 0;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            myStmt = connection.prepareStatement("select * from artists where name=?");
            myStmt.setString(1, this.name);

            myRes = myStmt.executeQuery();
            result = myRes.getInt("id");
//            System.out.println("Artist id: " + result);

        } catch (SQLException e) {
            return 0;
            // if the error message is "out of memory",
            // it probably means no database file is found
//            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

}
