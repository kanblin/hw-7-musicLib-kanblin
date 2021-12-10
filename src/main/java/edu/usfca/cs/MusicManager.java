package edu.usfca.cs;

public class MusicManager {
    static Parser xparse = new Parser();
/*
This class demos the parser, duplicate removal, and writing capabilities.
 */

    public static void main(String[] args) {
        MusicManager itunes = new MusicManager();
        Library lib1 = new Library();
        Library lib2 = new Library();

        lib1 = xparse.xmlParser();
        for (int i = 0; i < lib1.getSongs().size(); i++) {
            System.out.println(lib1.getSongs().get(i));
        }

        System.out.println("------------");
/*the parser recognizes duplicates and will not add them in */
        lib2 = xparse.jsonParser();
        for (int i = 0; i < lib2.getSongs().size(); i++) {
            System.out.println(lib2.getSongs().get(i));
            //add each song to lib1 so we can test the remove certain duplicates
            lib1.addSong(lib2.getSongs().get(i));
        }

        System.out.println("------------after adding duplicates and joining ---------");

        //add a couple duplicates
        lib1.addSong(lib2.getSongs().get(0));
        lib1.addSong(lib2.getSongs().get(1));

        lib1.addAll(lib2.getSongs());
        for (int i = 0; i < lib1.getSongs().size(); i++) {
            System.out.println(lib1.getSongs().get(i));

        }

        System.out.println("------------duplicates removed ---------");

        lib1.removeCertainDuplicates();
        for (int i = 0; i < lib1.getSongs().size(); i++) {
            System.out.println(lib1.getSongs().get(i));
        }


        System.out.println("------------potential duplicates removed ---------");

        lib1.removePotentialDuplicates();
        for (int i = 0; i < lib1.getSongs().size(); i++) {
            System.out.println(lib1.getSongs().get(i));
        }

        System.out.println("------------write to XML & JSON ---------");
        /* Task 6 & 7  I decided not to include writing individual artists and albums as
        I did not utilize those sections during the original parsing as the info is
        available in each individual song.
         */
        lib1.writeXML();
        lib1.writeJSON();


    }
}
