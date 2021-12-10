package edu.usfca.cs;

import java.util.*;
import java.util.stream.Collectors;

/*Task 4
add a song to a playlist.
Merge two playlists. (remove duplicates!)
delete a song from a playlist.
Sort the playlist so that the songs that are most liked are at the front.
Randomly shuffle the playlist.
Please provide JUnit tests for this functionality.

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
        Library library1 = new Library();
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
/* Task 5 Now, let's add a function to generate a random playlist.
* Additional feature is whether the song is liked or not
* picks out liked songs and shuffles them.
* */
    public List<Song> randomList(){
        List<Song> list = listOfSongs.stream().filter(s -> s.isLike()).collect(Collectors.toList());
        Collections.shuffle(list);
        return list;
    }

}
