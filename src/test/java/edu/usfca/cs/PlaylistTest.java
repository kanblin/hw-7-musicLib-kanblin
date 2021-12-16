package edu.usfca.cs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PlaylistTest {
    Playlist p1 , p2;
    Library myLibrary = new Library();
    Song s1, s2, s3, s4, s5, s6, s7 , s8;
    Album a1, a2;
    Artist b1;
//Best to each test individually.

    @BeforeEach
    void setUp() {
        s1 = new Song("Happy Birthday");
        s2 = new Song("Helter Skelter");
        s3 = new Song("Californication");
        s4 = new Song("Billie Jean");
        s5 = new Song("I Want it That Way");
        s6 = new Song("Float On");
        s7 = new Song("Space Cowboy");
        s8 = new Song("billie Jean");
        s3.setLike(true);
        s4.setLike(true);
        s5.setLike(true);
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
        s1.setPerformer(b1);
        s2.setPerformer(b1);
        s3.setPerformer(b1);
        s4.setPerformer(b1);
        s5.setPerformer(b1);
        s6.setPerformer(b1);
        s7.setPerformer(b1);
        s8.setPerformer(b1);

        p1 = new Playlist();
        p2 = new Playlist();

        p1.addToPlaylist(s1);
        p1.addToPlaylist(s2);
        p1.addToPlaylist(s3);
        p1.addToPlaylist(s4);
        p1.addToPlaylist(s5);
        p1.addToPlaylist(s6);

    }

    @Test
    void deleteSong() {
        p1.deleteSong(s1);//added sout to confirm deletion
    }

    @Test
    void merge() {
        System.out.println(p1.getListOfSongs().size());
        p2.addToPlaylist(s4);//duplicate
        p2.addToPlaylist(s5);//duplicate
        p2.addToPlaylist(s6);//duplicate
        p2.addToPlaylist(s7);//space cowboy will be added

        p1.merge(p2);
        System.out.println(p1.getListOfSongs().size());
        for (int j = 0; j < p1.getListOfSongs().size(); j++) {
            System.out.println(p1.getListOfSongs().get(j));
        }
    }

    @Test
    void sortList() {
        for (int j = 0; j < p1.getListOfSongs().size(); j++) {
            System.out.println(p1.getListOfSongs().get(j));
        }

        System.out.println("-------- unsorted above & sorted below ---------");
        p1.sortList();
        for (int j = 0; j < p1.getListOfSongs().size(); j++) {
            System.out.println(p1.getListOfSongs().get(j));
        }

        System.out.println("all liked true songs are ontop");
    }

    @Test
    void shuffleList() {

        for (int j = 0; j < p1.getListOfSongs().size(); j++) {
            System.out.println(p1.getListOfSongs().get(j));
        }
        p1.shuffleList();
        System.out.println("------------ after shuffle-----------");
        for (int j = 0; j < p1.getListOfSongs().size(); j++) {
            System.out.println(p1.getListOfSongs().get(j));
        }


    }

    @Test
    void randomList() {
        ArrayList<Song> likePlayList ;
        for (int j = 0; j < p1.getListOfSongs().size(); j++) {
            System.out.println(p1.getListOfSongs().get(j));
        }
        likePlayList = (ArrayList<Song>) p1.randomList();
        System.out.println("------------ 3,4,5 are liked-----------");
        for (int j = 0; j < likePlayList.size(); j++) {
            System.out.println(likePlayList.get(j));
        }

    }




}


