import model.Playlist;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import statistics.Statistics;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatisticsTest {

    Playlist playlist;

    @BeforeEach
    public void setTestPlaylist() {
        playlist = new Playlist();
        List<Song> songs = new ArrayList<>();

        songs.add(new Song("A", "Singer1", Arrays.asList("Pop"), 2020));
        songs.add(new Song("B", "Singer1", Arrays.asList("Rock"), 2021));
        songs.add(new Song("C", "Singer2", Arrays.asList("Jazz"), 2022));
        songs.add(new Song("D", "Singer3", Arrays.asList("Pop", "Electronic", "Dance"), 2020));
        songs.add(new Song("E", "Singer2", Arrays.asList("Rock"), 2020));
        songs.add(new Song("F", "Singer1", Arrays.asList("Pop", "Pop"), 2023));
        songs.add(new Song("F", "Singer4", Collections.emptyList(), 2021));
        songs.add(new Song("F", "Singer2", Arrays.asList("pop", "POP", "Pop"), 2024));

        playlist.setSongs(songs);
    }


    @Test
    @DisplayName("Статистика за singer")
    void testStatisticsBySinger() {

        Statistics statistics = new Statistics();
        Map<String, Integer> result = statistics.formStatistics(playlist, "singer");

        assertEquals(3, result.get("Singer1"));
        assertEquals(3, result.get("Singer2"));
        assertEquals(1, result.get("Singer3"));
        assertEquals(1, result.get("Singer4"));
    }

    @Test
    @DisplayName("Статистика за genres")
    void testStatisticsByGenres() {

        Statistics statistics = new Statistics();
        Map<String, Integer> result = statistics.formStatistics(playlist, "genres");

        assertEquals(5, result.get("Pop"));
        assertEquals(2, result.get("Rock"));
        assertEquals(1, result.get("Jazz"));
        assertEquals(1, result.get("Electronic"));
        assertEquals(1, result.get("Dance"));
        assertEquals(1, result.get("pop"));
        assertEquals(1, result.get("POP"));
    }

    @Test
    @DisplayName("Статистика за year")
    void testStatisticsByYear() {

        Statistics statistics = new Statistics();
        Map<String, Integer> result = statistics.formStatistics(playlist, "year");

        assertEquals(3, result.get("2020"));
        assertEquals(2, result.get("2021"));
        assertEquals(1, result.get("2022"));
        assertEquals(1, result.get("2023"));
        assertEquals(1, result.get("2024"));
    }

    @Test
    @DisplayName("Викидання IllegalArgumentException при невідомому атрибуті")
    void testStatisticsByUnknown() {
        Statistics statistics = new Statistics();
        assertThrows(IllegalArgumentException.class, () ->  statistics.formStatistics(playlist, "unknown"));
    }

    @Test
    @DisplayName("Викидання IllegalArgumentException при пустій статистиці")
    void testEmptyStatistics() {
        Statistics statistics = new Statistics();
        Playlist playlist = new Playlist();
        playlist.setSongs(Collections.emptyList());

        assertThrows(IllegalArgumentException.class,
                () -> statistics.formStatistics(playlist, "singer"));
    }


}
