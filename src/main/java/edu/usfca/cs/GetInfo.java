package edu.usfca.cs;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class GetInfo {

    public void getGenre() {
        Scanner scan = new Scanner(System.in);
        String artist, song, album;
        System.out.println("--------------");
        System.out.println("Who is the artist?");
        artist = scan.nextLine();
        artist = artist.replaceAll("\\s+","%20");

        String initialURL = "https://musicbrainz.org/ws/2/artist?query="+artist+"&fmt=xml";
        /* MusicBrainz gives each element in their DB a unique ID, called an MBID. We'll use this to fecth that. */

        /* now let's parse the XML.  */
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if there's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");

            Document doc = db.parse(u.getInputStream());
            /* let's get the artist-list node */
            NodeList artists = doc.getElementsByTagName("tag");

            /* let's assume that the one we want is first. */
            int votes = 0;
            String id = null;
            for (int i = 0; i < artists.getLength(); i++) {
                Node genre = artists.item(i);


                if (genre.getNodeType() == Node.ELEMENT_NODE) {
                    Element amount = (Element) genre;

                    if (votes <= Integer.parseInt(amount.getAttribute("count"))) {
                        votes = Integer.parseInt(amount.getAttribute("count"));
                        id = genre.getTextContent();
                    }
                }
            }
            System.out.println(id + " " + votes);

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
    }


/*
ask for a song and artist and will return the album
 */
    public String songArtExample(String song, String artist) {
        Scanner scan = new Scanner(System.in);
//        System.out.println("--------------");
//        System.out.println("What is the name of the song?");
//        song = scan.nextLine();
        song = song.replaceAll("\\s+","%20");
//        System.out.println("Who is the artist?");
//        artist = scan.nextLine();
        artist = artist.replaceAll("\\s+","%20");
        String initialURL = ("https://musicbrainz.org/ws/2/recording/?query=" + song +
                "%20AND%20artist:" + artist +"&fmt=xml");
//        String initialURL = "https://musicbrainz.org/ws/2/recording/?query=dashboard%20AND%20artist:modest%20mouse&fmt=xml";
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if there's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (cbrooks@usfca.edu");

            Document doc = db.parse(u.getInputStream());
            NodeList releases = doc.getElementsByTagName("release-group");

            for (int i = 0; i < releases.getLength(); i++) {
                Node rGroup = releases.item(i);
                if (rGroup.getNodeType() == Node.ELEMENT_NODE) {
                    Element typeTag = (Element) rGroup;
                    String identifier = typeTag.getAttribute("type");
//                    System.out.println(identifier);
                    if(identifier.equals("Album")){
                        NodeList titleId = rGroup.getChildNodes();
                        return titleId.item(0).getTextContent();

                    }
                }
            }
//            System.out.println(id + " " + votes);

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        return "Single";
    }


/*
ask for an album and will return potential artists associated with the album
 */
    public String  albumGetArt(String album) {
        Scanner scan = new Scanner(System.in);
        String  userIn;
//        System.out.println("--------------");artist, song, album,
//        System.out.println("What is the name of the album?");
//        album = scan.nextLine();
        album = album.replaceAll("\\s+","%20");

        String initialURL = ("https://musicbrainz.org/ws/2/cdstub/?query=title:" + album + "&fmt=xml");
//        String initialURL = "https://musicbrainz.org/ws/2/cdstub/?query=title:We%20Were%20Dead%20Before%20the%20Ship%20Even%20Sank&fmt=xml";
        /* MusicBrainz gives each element in their DB a unique ID, called an MBID. We'll use this to fecth that. */
//        https://musicbrainz.org/ws/2/cdstub/?query=title:Stadium%20arcadium%20AND%20artist:red%20Hot%20chili%20Peppers&fmt=xml
        /* now let's parse the XML.  */
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if there's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (klin42@usfca.edu");

            Document doc = db.parse(u.getInputStream());
            /* let's get the artist-list node */
//            NodeList artists = doc.getElementsByTagName("recording");
            NodeList artist1 = doc.getElementsByTagName("artist");
            /* let's assume that the one we want is first. */

            Node artist0T = artist1.item(0);
            Node artist1T = artist1.item(1);
            Node artist2T = artist1.item(2);
//            System.out.println("name " + artist1T.getTextContent());


            System.out.println("Select one" +
                    "\n 0)"+ artist0T.getTextContent() +
                    "\n 1)"+ artist1T.getTextContent() +
                    "\n 2)"+ artist2T.getTextContent() +
                    "\n 3) None of the above");

            userIn = scan.nextLine();
            if (userIn.equals("3")) {
                return "none";
            } else {
                return artist1.item(Integer.parseInt(userIn)).getTextContent();
            }


        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        return null;
    }

//testing area as all needed user inputs.
//    public static void main(String[] args) {
//        GetInfo test = new GetInfo();
//        getGenre();
//        String out = test.albumGetArt("Californication");
//        System.out.println(out);
//        String out =  test.songArtExample("We Are The Champions" , "Queen");
//        System.out.println(out);
//    }

}
