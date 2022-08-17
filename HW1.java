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
    private ConcurrentHashMap<String, Integer> hm;

    public InterfaceSongCache(){
        this.songs = new HashMap<>();
        hm = new ConcurrentHashMap<String, Integer>();
    }

    @Override
    public void recordSongPlays(String songId, int numPlays) {

        if(songs.containsKey(songId)){
            // put the numPlays of the songID
            songs.put(songId,songs.get(songId) + numPlays);
        }else{
            songs.put(songId, numPlays);
        }

        /*
        if(songId == null){
            throw new IllegalArgumentException("songId cannot be null.");
        }

        if(numPlays < 0){
            throw new IllegalArgumentException("Song cannot be played negative times.");
        }

        hm.computeIfAbsent(songId, k -> (0));
        hm.computeIfPresent(songId, (k, v)-> v + numPlays);

         */
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

        if(n == 0 || songs == null || songs.isEmpty()){
            return result;
        }
        if(n > songs.size()){
            throw new IllegalArgumentException("The input is not appropriate with the size");
        }

        PriorityQueue<String> pq = new PriorityQueue<String>((a,b)->{
            Integer val1 = songs.get(a);
            Integer val2 = songs.get(b);

            /**
             * PriorityQueue will print out the smaller val firstly
             * Subtraction will ensure the descending order
             * */
            return val2 - val1;
        });

        while(n > 0){
            result.add(pq.poll());
            n--;
        }
        return result;
    }
}


public class HW1 {

    public void cacheIsWorking() {
        SongCache cache = new InterfaceSongCache();
        cache.recordSongPlays("ID-1", 3);
        cache.recordSongPlays("ID-1", 1);
        cache.recordSongPlays("ID-2", 2);
        cache.recordSongPlays("ID-3", 5);

        /*
        assertThat(cache.getPlaysForSong("ID-1"), is(4));
        assertThat(cache.getPlaysForSong("ID-9"), is(-1));
        assertThat(cache.getTopNSongsPlayed(2), contains("ID-3",
                "ID-1"));
        assertThat(cache.getTopNSongsPlayed(0), is(empty()));
        */

        assert cache.getPlaysForSong("ID-1") == 4;
        assert cache.getPlaysForSong("ID-9") == -1;



    }

    public static void main(String[] args) {
        HW1 hw1 = new HW1();
        hw1.cacheIsWorking();

        SongCache cache = new InterfaceSongCache();
        cache.recordSongPlays("ID-1", 3);
        cache.recordSongPlays("ID-1", 1);
        cache.recordSongPlays("ID-2", 2);
        cache.recordSongPlays("ID-3", 5);
        cache.recordSongPlays("ID-33", 20);
        cache.recordSongPlays("ID-19", 12);
        cache.recordSongPlays("ID-3", 19);

        for(int i = 0; i < 5; i++){
            System.out.println(cache);
        }

        System.out.println("top 3 songs: " );
        List<String> topSongs = cache.getTopNSongsPlayed(3);

        Iterator<String> it = topSongs.iterator();

        while(it.hasNext()){
            String str = it.next();
            System.out.println(str);
        }

        System.out.println("\ntop 10 songs: ");
        List<String> topSongs2 = cache.getTopNSongsPlayed(10);

        it = topSongs2.iterator();

        while(it.hasNext()){
            String str = it.next();
            System.out.println(str);
        }

        System.out.println("\nTest getPlaysForSongs: ");
        System.out.println(cache.getPlaysForSong("ID-3"));
        System.out.println(cache.getPlaysForSong("ID-24"));
    }
}
