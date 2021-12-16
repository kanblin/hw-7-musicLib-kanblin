package edu.usfca.cs;

public class Song extends Entity {
    protected Album album;
    protected Artist performer;
    protected SongInterval duration;
    protected String genre;
    protected boolean like;

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }



    public Song(String name) {
        super(name);
        album = new Album("");
        performer = new Artist("");
        duration = new SongInterval(0);
        genre = "";
        like = false;
    }

    public Song(String name, int length) {
        super(name);
        duration = new SongInterval(length);
        genre = "";
    }

    public Song(){

    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLength(int length) {
        duration = new SongInterval(length);
   }

   public String showLength() {
        return duration.toString();
   }


    protected Album getAlbum() {
        return album;
    }

    protected void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getPerformer() {
        return performer;
    }

    public void setPerformer(Artist performer) {
        this.performer = performer;
    }

    public String toString() {
        return super.toString() + " " + this.performer + " " + this.album+ " " + this.like;

    }

    public boolean equals(Song otherSong){
        if ((this.name.equals(otherSong.name)) && this.performer.equals(otherSong.performer)){
            return true;
        } else {
            return false;
        }
    }

    private class SongInterval {
        private int length;

        public SongInterval(int length) {
            this.length = length;
        }

        public String toString() {
            int mins = length/60;
            int sec = length%60;
            return mins +":" + sec;
        }
    }

    public String toXML(){
        return"\t<song id=\"" + this.attributeID + "\">\n" +
                "\t\t<title>\n\t\t\t" + this.name + "\n\t\t</title>\n" +
                "\t\t<artist id=\"" + this.performer.attributeID + "\">\n\t\t\t" + this.performer.name + "\n\t\t</artist>\n" +
                "\t\t<album id=\"" + this.album.attributeID + "\">\n\t\t\t" + this.album.name + "\n\t\t</album> \n\t</song>";
    }

    public String toJSON(){
        return "{\n" +
                "\t\t\"id\": \"" + this.attributeID + "\",\n" +
                "\t\t\"title\": \"" + this.name + "\", \n" +
                "\t\t\"artist\": {\n" +
                "\t\t\t\"id\": \"" + this.performer.attributeID + "\",\n" +
                "\t\t\t\"name\": \"" + this.performer.name +"\"\n" +
                "\t\t}, \n" +
                "\t\t\"album\": {\n" +
                "\t\t\t\"id\": \"" + this.album.attributeID + "\", \n" +
                "\t\t\t\"name\": \"" + this.album.name + "\"\n" +
                "\t\t}\n" +
                "\t}";
    }




}
