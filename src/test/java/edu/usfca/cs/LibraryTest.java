package edu.usfca.cs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LibraryTest {

    Library myLibrary, likeTestLib;
    Song s1, s2, s3, s4, s5, s6, s7 , s8;
    Album a1, a2;
    Artist b1, b2;
    @BeforeEach
    void setUp() {
        myLibrary = new Library();
        likeTestLib = new Library();
        s1 = new Song("Happy Birthday");
        s2 = new Song("Helter Skelter");
        s3 = new Song("Californication");
        s4 = new Song("Billie Jean");
        s5 = new Song("I Want it That Way");
        s6 = new Song("Billie Jean");
        s7 = new Song("I Want it That Way!");
        s8 = new Song("billie Jean");
        s3.setLike(true);
        s4.setLike(true);
        a1 = new Album("Here Comes the sun");
        s1.setAlbum(a1);
        s2.setAlbum(a1);
        s3.setAlbum(a1);
        s4.setAlbum(a1);
        s5.setAlbum(a1);
        s6.setAlbum(a1);
        s7.setAlbum(a1);
        s8.setAlbum(a1);
        b1 = new Artist("Best Of");
        b2 = new Artist("Joe");
        s1.setPerformer(b1);
        s2.setPerformer(b1);
        s3.setPerformer(b1);
        s4.setPerformer(b1);
        s5.setPerformer(b1);
        s6.setPerformer(b1);
        s7.setPerformer(b1);
        s8.setPerformer(b1);
        myLibrary.addSong(s1);
        myLibrary.addSong(s3);
        myLibrary.addSong(s4);

        //liked list to compare
        likeTestLib.addSong(s3);
        likeTestLib.addSong(s4);
    }

    @Test
    void findSong() {

        assertTrue(myLibrary.findSong(s1));
        assertFalse(myLibrary.findSong(s2));
    }

    @Test
    void addSong() {
        myLibrary.addSong(s1);
        assertTrue(myLibrary.getSongs().contains(s1));
    }

    @Test
    void getLiked() {
        System.out.println(myLibrary.getLiked(s1.isLike()));
        System.out.println(myLibrary.getLiked(s4.isLike()));
        myLibrary.getLiked(s4.isLike());


    }

    @Test
    void removeCertainDuplicates() {
//        Junit did not allow for user input to test I initially just let it run without the scanner
//
//        myLibrary.addSong(s3);
//        myLibrary.addSong(s4);
//        for (int i = 0; i < myLibrary.getSongs().size(); i++) {
//            System.out.println(myLibrary.getSongs().get(i));
//        }
//        System.out.println(myLibrary.getSongs().size());
//        myLibrary.removeCertainDuplicates();
//        System.out.println(myLibrary.getSongs().size());
//        for (int i = 0; i < myLibrary.getSongs().size(); i++) {
//            System.out.println(myLibrary.getSongs().get(i));
//        }

    }

    @Test
    void removePotentialDuplicates() {
//        myLibrary.addSong(s5);
//        myLibrary.addSong(s4);
//        myLibrary.addSong(s7);
//        myLibrary.addSong(s8);
//        for (int i = 0; i < myLibrary.getSongs().size(); i++) {
//            System.out.println(myLibrary.getSongs().get(i));
//        }
//        System.out.println(myLibrary.getSongs().size());
//        myLibrary.removePotentialDuplicates();
//        System.out.println(myLibrary.getSongs().size());
//        for (int i = 0; i < myLibrary.getSongs().size(); i++) {
//            System.out.println(myLibrary.getSongs().get(i));
//        }

    }

    @Test
    void randomLikes() {
        myLibrary.randomLikes();
    }

    @Test
    void filterLikedList() {
        myLibrary.randomLikes();

        myLibrary.filterLikedList();

    }

    @Test
    void filterByArt() {
        s5.setPerformer(b2);
        s6.setPerformer(b2);
        myLibrary.addSong(s5);
        myLibrary.addSong(s6);
        myLibrary.randomLikes();

        myLibrary.filterByArt("Best Of");
//

    }
}