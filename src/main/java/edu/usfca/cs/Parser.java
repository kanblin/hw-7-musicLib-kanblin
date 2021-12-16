package edu.usfca.cs;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parser {


    /* Task 1,2. I decided to put both parsers in one class as it was easier to keep track of the Maps, which
    * allowed for the parser not to add duplicates songs.
    * Noticing that all info was identical I added a couple additional songs to the JSON file for parsing so it would
    * demonstrate this fact.*/

    public static String getContent(Node n) {
        StringBuilder sb = new StringBuilder();
        Node child = n.getFirstChild();
        sb.append(child.getNodeValue());
        return sb.toString();
    }

    HashMap<Integer, Artist> artistMap = new HashMap<>();
    HashMap<Integer, Album> albumMap = new HashMap<>();
    HashMap<String, Integer> titleMap = new HashMap<>();
    Player player1 = new Player();

    public Library xmlParser() {
        Library lib = new Library();

        String filename = "C:\\Users\\kanbl\\IdeaProjects\\cs514-homework-6-kanblin\\src\\music-library.xml";
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));

            Element root = doc.getDocumentElement();
            NodeList libType = root.getChildNodes(); //vs child
            Node currentNode, subNode;

            Album currentAlbum;
            Artist currentArtist;
            Song currentSong;

            NodeList songsPortion = root.getElementsByTagName("song");
            for (int i = 0; i < songsPortion.getLength(); i++) {
                currentNode = songsPortion.item(i);
                Element num = (Element) currentNode;
                String id = num.getAttribute("id");
//                System.out.println(id);
                NodeList indSong = currentNode.getChildNodes();
                currentSong = new Song();
                currentSong.setAttributeID(Integer.parseInt(id));

                for (int j = 0; j < indSong.getLength(); j++) {
                    subNode = indSong.item(j);
                    if(subNode.getNodeType() == Node.ELEMENT_NODE){
                        Element name = (Element) subNode;
//                        System.out.println("1 " + name.getTagName() + " : " + name.getTextContent().trim());
                        if(name.getNodeName().equals("title")){

                            if(!titleMap.containsKey(getContent(subNode).trim())){
                               titleMap.put(getContent(subNode).trim(), Integer.parseInt(id));
                                currentSong.setName(getContent(subNode).trim());
                            } else {
                                currentSong.setName(getContent(subNode).trim());
                                currentSong.entityID = titleMap.get(getContent(subNode).trim());
                            }
                            currentSong.setName(getContent(subNode).trim());
                        } else if(name.getNodeName().equals("artist")){
                            id = name.getAttribute("id");
                            if(!artistMap.containsKey(Integer.parseInt(id))){
                                currentArtist = new Artist( getContent(subNode).trim());
                                currentArtist.setAttributeID(Integer.parseInt(id));
                                currentSong.setPerformer(currentArtist);
                                artistMap.put(Integer.parseInt(id), currentArtist);
                            } else {
                                currentSong.setPerformer(artistMap.get(Integer.parseInt(id)));
                            }
                        } else if(name.getNodeName().equals("album")) {
                            id = name.getAttribute("id");
                            if(!albumMap.containsKey(Integer.parseInt(id))){
                                currentAlbum = new Album(getContent(subNode).trim());
                                currentAlbum.setAttributeID(Integer.parseInt(id));
                                currentSong.setAlbum(currentAlbum);
                                albumMap.put(Integer.parseInt(id), currentAlbum);
                            } else {
                                currentSong.setAlbum(albumMap.get(Integer.parseInt(id)));
                            }
                        }
                    }
                }
                lib.addSong(currentSong);
            }
        } catch (Exception e) {
            System.out.println("Parsing error:" + e);
        }
        return lib;
    }

    public Library jsonParser() {
        Library lib = new Library();
        String s;
        Album currentAlbum;
        Artist currentArtist;
        Song currentSong;

        try {
            Scanner sc = new Scanner(new File("C:\\Users\\kanbl\\IdeaProjects\\cs514-homework-6-kanblin\\src\\music-library.json"));
            sc.useDelimiter("\\Z");
            s = sc.next();
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            Object obj = parser.parse(s);
            JSONObject jsonObject = (JSONObject)obj;

            JSONArray songArray = (JSONArray)jsonObject.get("songs");
            for (Object song : songArray) {
                JSONObject jsong = (JSONObject) song;
                int titleId = Integer.parseInt(jsong.get("id").toString());
                if(!titleMap.containsKey(jsong.get("title").toString())) {
                    currentSong = new Song();
                    currentSong.setAttributeID(Integer.parseInt(jsong.get("id").toString()));
                    titleMap.put(jsong.get("title").toString(), titleId);
                    currentSong.setName(jsong.get("title").toString());
                    Map artist = ((Map) jsong.get("artist"));
                    int artId = Integer.parseInt(artist.get("id").toString());
                    String art = artist.get("name").toString();
                    if (!artistMap.containsKey(artId)) {
                        currentArtist = new Artist(art);
                        currentArtist.setAttributeID(artId);
                        currentSong.setPerformer(currentArtist);
                        artistMap.put(artId, currentArtist);
                    } else {
                        currentSong.setPerformer(artistMap.get(artId));
                    }

                    Map album = ((Map) jsong.get("album"));
                    int albId = Integer.parseInt(album.get("id").toString());
                    String alb = album.get("name").toString();
                    if (!albumMap.containsKey(albId)) {
                        currentAlbum = new Album(alb);
                        currentAlbum.setAttributeID(albId);
                        currentSong.setAlbum(currentAlbum);
                        albumMap.put(albId, currentAlbum);
                    } else {
                        currentSong.setAlbum(albumMap.get(albId));
                    }

                    lib.addSong(currentSong);
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e);
        } catch (ParseException e1) {
            System.out.println("Parser error");
        }
        return lib;
    }


    //fromSQl take data from sql and put them  into the maps


    public Library songsFromSQL(){
        Library sqlSongLib = new Library();

        Connection connection = null;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            ResultSet rs = statement.executeQuery("select * from songs");
//            ResultSet as , al;
            int temp, id;
            String currentArt, currentAlb, currentSong;
            while (rs.next()) {

                id = rs.getInt("id");
                currentSong = rs.getString("name");
//                titleMap.containsKey(currentSong) ||
//                titleMap does not have add new song
                if (!titleMap.containsValue(id)) {
                    Song songToAdd = new Song(currentSong);
                    songToAdd.attributeID = id;
                    titleMap.put(currentSong, id);

                    temp = rs.getInt("artist");
                    //if it contains this artist then pull it out no need to create a new one
                    if (!artistMap.containsKey(temp)) {
                        currentArt = player1.artFromSQL(temp);
                        Artist artToAdd = new Artist(currentArt);
                        artistMap.put(temp, artToAdd);
                        artToAdd.setAttributeID(temp);
                        songToAdd.setPerformer(artToAdd);
                    } else {
                        songToAdd.setPerformer(artistMap.get(temp));
                    }

                    temp = rs.getInt("album");
                    //if it contains this artist then pull it out no need to create a new one
                    if (!albumMap.containsKey(temp)) {
                        currentAlb = player1.albFromSQL(temp);
                        Album albToAdd = new Album(currentAlb);
                        albumMap.put(temp, albToAdd);
                        albToAdd.setAttributeID(temp);
                        songToAdd.setAlbum(albToAdd);
                    } else {
                        songToAdd.setAlbum(albumMap.get(temp));
                    }

                    sqlSongLib.addSong(songToAdd);
                } else {
                    System.out.println("song already in library");
                }


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


        return sqlSongLib;

    }
//edges duplicates?lowercase

    public void SArtoSQL(String song, Artist artist, Album album){
        Connection connection = null;
        PreparedStatement myStmt = null;
        ResultSet myRes = null;
        //
        int artID = 0;
        int albID = 0;
        int songID = titleMap.size()+1;
//        boolean artExists = artistMap.containsValue(artist); // false if new artist
//        System.out.println(artExists);

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            artID = artist.getArtistIDfromSQL();
            albID = album.getAlbumIDfromSQL();
//            System.out.println(artID);
            //artID = 0 when it does not exsist need to make a new addition to sql library
            if (artID == 0) {
//                table artists (id INTEGER NOT NULL PRIMARY KEY, name VARCHAR(50) NOT NULL, songs INTEGER);
                artID = artistMap.size()+1;
                String artistInsert = artID + ", '" + artist.name + "'";
                statement.executeUpdate("insert into artists (id, name) values (" + artistInsert + ")");
                artistMap.put(artID, artist);

            }
            if (albID == 0) {
//              albums (id INTEGER NOT NULL PRIMARY KEY, name VARCHAR(50) NOT NULL, artist INTERGER NOT NULL, nSongs INTEGER);
                albID = albumMap.size()+1;
                String albumInsert = albID + ", '" + album.name + "'," + artID ;
                statement.executeUpdate("insert into albums (id, name, artist) values (" + albumInsert + ")");
                albumMap.put(albID, album);

            }

            if(!titleMap.containsKey(song)){
                //            songs (id integer, name string, artist integer, album integer);
                statement.executeUpdate("insert into songs (id, name, artist, album) values (" + songID + ", '"
                        + song + "'," + artID +"," + albID +")");
                Song additionalSong = new Song(song);
                additionalSong.setPerformer(artist);
                additionalSong.setAlbum(album);
                titleMap.put(song, songID);
            }

            System.out.println("insert into songs (id, name, artist, album) values (" + songID + ", '"
                    + song + "'," + artID +"," + albID +")");

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




}

//    //writes stuff from theEntity to the SQL
//    public static void writeToSQL(String theURL, Entity theEntity){
//        Connection connection = null;
//        try{
//            connection = DriverManager.getConnection(theURL);
//            Statement statement = connection.createStatement();
//            statement.setQueryTimeout(30);  // set timeout to 30 sec.
//
//            String theString = null;
//            //ONLY WORKS FOR SONGS RIGHT NOW, add others later
//            if(theEntity instanceof Song){
//                theString = "songs";
//                ResultSet rsSongs = statement.executeQuery("select * from " + theString);
//                //checking if the song is already in the db
//                while(rsSongs.next()){
//                    //if the song is already in the db, just exit that shit
//                    if(rsSongs.getString("name").equals(theEntity.getName())
//                            || rsSongs.getInt("id") == theEntity.getID()){
//                        System.out.println("not adding '" + theEntity.name + "'cos its already in the db");
//                        return;
//                    }
//                }
//                //check if the artist is in the db
//                ResultSet rsArtists = statement.executeQuery("select * from artists");
//                while(rsArtists.next()){
//                    //if the artist is in the db, get the id from artist and assign it
//                    if(rsArtists.getString("name").equals(((Song) theEntity).getArtist().name)){
//                        String selectString = "select id from artists where name = '" + ((Song) theEntity).getArtist().name + "'";
//                        ResultSet theRS = statement.executeQuery(selectString);
//                        int theArtistID = theRS.getInt("id");
//                        ((Song) theEntity).getArtist().entityID = theArtistID;
//                        System.out.println("assigned the artistID: " + theArtistID + " to " + theEntity.name);
//                        break;
//                    }
//                }
//                //check if the album is in the db
//                ResultSet rsAlbums = statement.executeQuery("select * from albums");
//                while(rsAlbums.next()){
//                    //if the album is in the db, get the id from the album and assign it
//                    //System.out.println("rsAlbums.getString('name'): " + rsAlbums.getString("name"));
//                    //System.out.println("theEntity.getAlbum().name: " + ((Song) theEntity).getAlbum().name);
//                    if(rsAlbums.getString("name").equals(((Song) theEntity).getAlbum().name)){
//                        String selectString = "select id from albums where name = '" + ((Song) theEntity).getAlbum().name + "'";
//                        ResultSet theRS = statement.executeQuery(selectString);
//                        int theAlbumID = theRS.getInt("id");
//                        ((Song) theEntity).getAlbum().entityID = theAlbumID;
//                        System.out.println("assigned the albumID: " + theAlbumID +" to " + theEntity.name);
//                        break;
//                    }
//                }
//                String addToSQLStatement = theEntity.toSQL(); //runtime polymorphism
//                statement.executeUpdate(addToSQLStatement);
//                System.out.println("executing update with: " + addToSQLStatement);
//            }
//        }
//        catch(SQLException e){
//            System.err.println(e.getMessage());
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                if(connection != null){
//                    connection.close();
//                }
//            }
//            catch (SQLException e){
//                System.err.println(e.getMessage());
//            }
//        }
//    }