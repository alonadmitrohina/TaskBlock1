import json.JSONParser;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JSONParserTest {
    private JSONParser parser;

    @BeforeEach
    void updateParser() {
        parser = new JSONParser();
    }

    @Test
    @DisplayName("Парсинг невеликого файлу")
    void testParsing() throws IOException {
        File file = new File("src/test/resources/json/jsonTest1.json");

        parser.parseJSON(file);
        List<Song> songs = parser.getPlaylist().getSongs();


        assertNotNull(songs);
        assertEquals(3, songs.size());

        Song firstSong = songs.getFirst();
        assertEquals("Moonlight Echoes", firstSong.getName());
        assertEquals("Ariana Grande", firstSong.getSinger());
        assertEquals(2023, firstSong.getReleaseYear());
        assertTrue(firstSong.getGenres().contains("pop"));
        assertTrue(firstSong.getGenres().contains("rnb"));
        assertEquals(2, firstSong.getGenres().size());

        Song secondSong = songs.get(1);
        assertEquals("Shadow Dancer", secondSong.getName());
        assertEquals("Parfenuk", secondSong.getSinger());
        assertEquals(2024, secondSong.getReleaseYear());
        assertTrue(secondSong.getGenres().contains("pop"));
        assertTrue(secondSong.getGenres().contains("soft music"));
        assertTrue(secondSong.getGenres().contains("ukrainian"));
        assertEquals(3, secondSong.getGenres().size());

        Song thirdSong = songs.get(2);
        assertEquals("Electric Heartbeat", thirdSong.getName());
        assertEquals("The Weekend", thirdSong.getSinger());
        assertEquals(2022, thirdSong.getReleaseYear());
        assertTrue(thirdSong.getGenres().contains("pop"));
        assertTrue(thirdSong.getGenres().contains("synthwave"));
        assertEquals(2, thirdSong.getGenres().size());
    }

    @Test
    @DisplayName("Парсинг порожнього масиву повертає порожній список")
    void handleEmptyJSON() throws IOException {
        File file = new File("src/test/resources/json/empty.json");

        parser.parseJSON(file);
        List<Song> songs = parser.getPlaylist().getSongs();

        assertNotNull(songs);
        assertTrue(songs.isEmpty(), "Список пісень має бути порожнім");
    }

    @Test
    @DisplayName("Кидає IOException при неправильноми типі даних")
    void handleWrongFormat(){
        File file = new File("src/test/resources/json/wrong.json");
        assertThrows(IOException.class, () -> parser.parseJSON(file));
    }
}
