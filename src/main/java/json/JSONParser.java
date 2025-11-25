package json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import model.Playlist;
import model.Song;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JSONParser {

    List<Song> songs = new CopyOnWriteArrayList<>();


    /**
     * Отримання всього списку пісень (Playlist)
     * @return
     */
    public Playlist getPlaylist(){
        Playlist playlist = new Playlist();
        playlist.setSongs(songs);
        return playlist;
    }


    /**
     * Парсинг об'єктів в json файлі зі створенням localSongs для збору даних локально у потоці
     * @param file
     * @throws IOException
     */
    public void parseJSON(File file) throws IOException {
        JsonFactory  jsonFactory = new JsonFactory();

        try (JsonParser jsonParser = jsonFactory.createParser(file)) {

            if(jsonParser.nextToken() != JsonToken.START_ARRAY){
                return;
            }

            List<Song> localSongs = new ArrayList<>();
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                Song song = extractSong(jsonParser);
                localSongs.add(song);
            }
            songs.addAll(localSongs);
        }
    }


    /**
     * Створення об'єкту Song з об'єкту в json
     * {
     *     "name": "Moonlight Echoes",
     *     "singer": "Ariana Grande",   =>  new Song("Moonlight Echoes", "Ariana Grande", "pop, rnb", 2023)
     *     "genres": "pop, rnb",
     *     "release_year": 2023
     *   }
     * @param jsonParser
     * @return
     * @throws IOException
     */
    private Song extractSong(JsonParser jsonParser) throws IOException {
        String name = null;
        String singer = null;
        List<String> genres = null;
        int releaseYear = 0;

        JsonToken token;
        while ((token = jsonParser.nextToken()) != JsonToken.END_OBJECT && token != null) {
            String fieldName = jsonParser.getCurrentName();

            switch (fieldName) {
                case "name":
                    jsonParser.nextToken();
                    name = jsonParser.getValueAsString();
                    break;

                case "singer":
                    jsonParser.nextToken();
                    singer = jsonParser.getValueAsString();
                    break;

                case "release_year":
                    jsonParser.nextToken();
                    releaseYear = jsonParser.getValueAsInt();
                    break;

                case "genres":
                    jsonParser.nextToken();
                    genres = Arrays.stream(jsonParser.getValueAsString().split(","))
                            .map(String::trim)
                            .toList();
            }
        }

        if (name == null || singer == null || releaseYear == 0) {
            throw new IOException();
        }

        return new Song(name, singer, genres, releaseYear);
    }


}

