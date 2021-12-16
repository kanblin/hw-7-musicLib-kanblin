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
    /**
     * GetGenre will take an input artist and return the genre.  The xml file had a number of votes for each
     * genre specification the method will return the one with the highest count.
     */
    public String getGenre(String a) {
        Scanner scan = new Scanner(System.in);

        String artist = a.replaceAll("\\s+","%20");

        String initialURL = "https://musicbrainz.org/ws/2/artist?query="+artist+"&fmt=xml";
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if there's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (klin42@usfca.edu");

            Document doc = db.parse(u.getInputStream());
            NodeList artists = doc.getElementsByTagName("tag");
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
            return id;

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        return "misc";
    }


    /**
     * songArtExample takes a song and artist will fetch album from music brainz.
     * @param song
     * @param artist
     * @return
     */
    public String songArtExample(String song, String artist) {
        Scanner scan = new Scanner(System.in);
        song = song.replaceAll("\\s+","%20");
        artist = artist.replaceAll("\\s+","%20");
        String initialURL = ("https://musicbrainz.org/ws/2/recording/?query=" + song +
                "%20AND%20artist:" + artist +"&fmt=xml");
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if there's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 klin42@usfca.edu");
            Document doc = db.parse(u.getInputStream());
            NodeList releases = doc.getElementsByTagName("release-group");

            for (int i = 0; i < releases.getLength(); i++) {
                Node rGroup = releases.item(i);
                if (rGroup.getNodeType() == Node.ELEMENT_NODE) {
                    Element typeTag = (Element) rGroup;
                    String identifier = typeTag.getAttribute("type");
                    if(identifier.equals("Album")){
                        NodeList titleId = rGroup.getChildNodes();
                        return titleId.item(0).getTextContent();

                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        return "Single";
    }


    /**
     * albumGetArt will take a song name and find the top three artists associated to a recording of that name.
     * The option to select none found is give otherwise.
     * @param album
     * @return
     */
    public String  albumGetArt(String album) {
        Scanner scan = new Scanner(System.in);
        String  userIn;
        album = album.replaceAll("\\s+","%20");

        String initialURL = ("https://musicbrainz.org/ws/2/cdstub/?query=title:" + album + "&fmt=xml");
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if there's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (klin42@usfca.edu");

            Document doc = db.parse(u.getInputStream());
            NodeList artist1 = doc.getElementsByTagName("artist");
            Node artist0T = artist1.item(0);
            Node artist1T = artist1.item(1);
            Node artist2T = artist1.item(2);

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


}
