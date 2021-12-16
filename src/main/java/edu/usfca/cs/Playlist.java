package edu.usfca.cs;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * PlayList class offers the various tools to create and manipulate a playlist.  Several features were
 * merged with Library to provide easier use.
 */


public class Playlist extends Library {
    private ArrayList<Song> listOfSongs = new ArrayList<>();

    public ArrayList<Song> getListOfSongs() {
        return listOfSongs;
    }

    public void setListOfSongs(ArrayList<Song> listOfSongs) {
        this.listOfSongs = listOfSongs;
    }

    public void deleteSong(Song s) {
        if(listOfSongs.contains(s)) {
            listOfSongs.remove(s);
            System.out.println("Removed form playlist");
        } else {
            System.out.println("Not on playlist");
        }

    }

    public void addToPlaylist(Song newSong) {
       listOfSongs.add(newSong);
    }

    public ArrayList<Song> merge(Playlist otherPlaylist) {
        this.listOfSongs.addAll(otherPlaylist.listOfSongs);
        Set<Song> hashSet = new HashSet<>(this.listOfSongs);
        this.listOfSongs = new ArrayList<>(hashSet);
        return this.listOfSongs;

    }

    public List<Song> sortList() {
        listOfSongs.sort(Comparator.comparingInt(a -> (a.isLike() ? 0 : 1)));
        return listOfSongs;
    }




    public List<Song> shuffleList() {
        Collections.shuffle(listOfSongs);
        return listOfSongs;
    }


    public List<Song> randomList(){
        List<Song> list = listOfSongs.stream().filter(s -> s.isLike()).collect(Collectors.toList());
        Collections.shuffle(list);
        return list;
    }

}
