package HW1;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


interface SongCache {
    /**
     * Record number of plays for a song.
     */
    void recordSongPlays(String songId, int
            numPlays);
    /**
     * Fetch the number of plays for a song.
     *
     * @return the number of plays, or -1 if the
    song ID is unknown.
     */
    int getPlaysForSong(String songId);
    /**
     * Return the top N songs played, in descending
     order of number of plays.
     */
    List<String> getTopNSongsPlayed(int n);
}

class InterfaceSongCache implements SongCache{

    private Map<String, Integer> songs;


    public InterfaceSongCache(){
        this.songs = new HashMap<>();
    }

    @Override
    public void recordSongPlays(String songId, int numPlays) {

        if(songs.containsKey(songId)){
            // put the numPlays of the songID
            songs.put(songId,songs.get(songId) + numPlays);
        }else{
            songs.put(songId, numPlays);
        }
    }

    @Override
    public int getPlaysForSong(String songId) {
        if(!songs.containsKey(songId)){
            return -1;
        }
        return songs.get(songId);
    }

    @Override
    public List<String> getTopNSongsPlayed(int n) {
        List<String> result = new ArrayList<>();

        if (n == 0 || songs == null || songs.isEmpty()) {
            return result;
        }
        if (n > songs.size()) {
            throw new IllegalArgumentException("The input is not appropriate with the size");
        }

        PriorityQueue<String> pq = new PriorityQueue<String>((a, b) -> {
            Integer val1 = songs.get(a);
            Integer val2 = songs.get(b);

            /**
             * PriorityQueue will print out the smaller val firstly
             * Subtraction will ensure the descending order
             * */
            return val2 - val1;
        });

        for(String str : songs.keySet()){
            pq.add(str);
            if(pq.size() > n){
                pq.poll();
            }
        }

        for(String s : pq){
            result.add(s);
        }

        List<String> descList = new ArrayList<>();
        for(int i = 0; i < result.size(); i++){
            descList.add(pq.poll());
        }
        return descList;
    }
}


public class HW1 {
    public static void main(String[] args) {

        SongCache cache = new InterfaceSongCache();
        cache.recordSongPlays("ID-1", 3);
        cache.recordSongPlays("ID-1", 1);
        cache.recordSongPlays("ID-2", 2);
        cache.recordSongPlays("ID-3", 5);
        cache.recordSongPlays("ID-4", 6);
        cache.recordSongPlays("ID-5", 7);


        System.out.println("The number of plays ID-1: " + cache.getPlaysForSong("ID-1"));
        System.out.println("The number of plays ID-3: " + cache.getPlaysForSong("ID-3"));

        System.out.println("Top 2 song played "+ cache.getTopNSongsPlayed(2));
        System.out.println("Top 4 song played "+ cache.getTopNSongsPlayed(4));


    }
}
