package edu.usfca.cs;

import java.sql.*;
import java.util.ArrayList;

public class Album extends Entity {
    protected ArrayList<Song> songs;
    protected Artist artist;

    public Album(String name) {
        super(name);
    }

    public String getName() {
        System.out.println("this is an album" + super.getName());
        return name;
    }

    public boolean equals(Album otherAlbum) {
        if ((this.artist.equals(otherAlbum.getArtist())) &&
                (this.name.equals(otherAlbum.getName()))) {
            return true;
        } else {
            return false;
        }
    }



    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public int getAlbumIDfromSQL(){
        Connection connection = null;
        PreparedStatement myStmt = null;
        ResultSet myRes = null;
        int result  = 0;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            myStmt = connection.prepareStatement("select * from albums where name=?");
            myStmt.setString(1, this.name);

            myRes = myStmt.executeQuery();
            result = myRes.getInt("id");
            System.out.println("Album id: " + result);

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
