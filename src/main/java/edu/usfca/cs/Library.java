package edu.usfca.cs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Library extends ArrayList{
    private ArrayList<Song> songs;
//    protected ArrayList<Song> likedSongs;
    private boolean liked;

    public boolean findSongs(Song s) {
        return songs.contains(s);
    }

    public boolean getLiked(boolean liked) {
        if (liked == true) {
            return true;
        }
        return false;
    }


    public Library() {
        songs = new ArrayList<Song>();
    }
    public boolean findSong(Song s) {
        return songs.contains(s);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void addSong(Song s) {
        songs.add(s);
    }

    //Task 3 Removing duplicates I tested initally in Library test then moved to music manager for demonstration
    // purposes.

    public ArrayList<Song> removeCertainDuplicates(){
        Set<Song> hashSet = new HashSet<>(songs);
        if(songs.size() > hashSet.size()){
            Scanner msc = new Scanner(System.in);
            String choice;
            System.out.println("duplicates found, remove y/n?");
            choice = msc.nextLine();
            if(choice.equals("y")){
                songs = new ArrayList<>(hashSet);
            }

        } else {
            System.out.println("No certain duplicates");
        }
        return songs;
    }
    /*The have the same name and either artist or album is the same.
They have the same artist and album, and the names are the same if 
converted to lower case and punctuation is ignored.
Seems very inefficent to do it this way, is there something better ?

     */
    public ArrayList<Song> removePotentialDuplicates(){
        Scanner msc = new Scanner(System.in);
        String choice;
        ArrayList<Song> result = new ArrayList<>(songs);

        for (int i = 0; i < songs.size(); i++) {
            Song curS = songs.get(i);

            for (int j = i+1; j < songs.size(); j++) {
                Song otherS = songs.get(j);
                if(curS.equals(otherS) &&
                        (curS.getPerformer() == otherS.getPerformer() || curS.getAlbum() == otherS.getAlbum())){
                    System.out.println("duplicate found, " + curS.name + " remove y/n?");
                    choice = msc.nextLine();
                    if(choice.equals("y")){
                        result.remove(otherS);
                    }

                } else if ((curS.getName().toLowerCase().replaceAll("\\p{Punct}", ""))
                        .equals(otherS.getName().toLowerCase().replaceAll("\\p{Punct}", ""))
                        && (curS.getPerformer() == otherS.getPerformer() || curS.getAlbum() == otherS.getAlbum()))
                        {
                    System.out.println("duplicate found due to spelling " + curS.name +" vs " + otherS.name +  " remove y/n?");
                    choice = msc.nextLine();
                    if(choice.equals("y")){
                        result.remove(otherS);
                    }
                }
            }
        }
        songs = result;
        return songs;
    }

    public void writeXML(){
        try{
            FileWriter write = new FileWriter("library.xml");
            write.write("<?xml version=\"1.0\"  ?>\n");
            write.write("<Library>\n");
            write.write("<songs>\n");
            for(Song s: songs){
                write.write(s.toXML() + "\n");
            }
            write.write("</songs>\n");
            write.write("</Library>");
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeJSON(){
        try{
            FileWriter writer2 = new FileWriter("library.json");
            writer2.write("{\n");
            writer2.write("\t\"songs\": [\n");
            for (int i = 0; i < songs.size(); i++) {
                writer2.write("\t" + songs.get(i).toJSON() + "\n");
                if(i < songs.size() - 1){
                    writer2.write(",\n");
                }
            }
            writer2.write("\t]\n}");
            writer2.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void randomLikes() {
        Random random = new Random();
        boolean likeTF = false;
        for (int i = 0; i < songs.size(); i++) {
            likeTF = random.nextBoolean();
            songs.get(i).setLike(likeTF);
        }
    }

    public void filterLikedList() {
        List<Song> list = songs.stream().filter(s -> s.isLike()).collect(Collectors.toList());
        Library newPlaylist = new Library();
        newPlaylist.getSongs().addAll(list);
        newPlaylist.writeXML();
        System.out.println("------------------");
        for (int j = 0; j < list.size(); j++) {
            System.out.println(list.get(j));
        }
    }

    public void filterByArt(String artist) {
        List<Song> list = songs.stream().filter(s -> s.performer.name.equals(artist)).collect(Collectors.toList());
        Library newPlaylist = new Library();
        newPlaylist.getSongs().addAll(list);
        newPlaylist.writeXML();
        System.out.println("------------------");
        for (int j = 0; j < list.size(); j++) {
            System.out.println(list.get(j));
        }
    }


}
