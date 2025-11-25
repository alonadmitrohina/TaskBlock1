package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Song {
    String name;
    String singer;
    List<String> genres;

    @JsonProperty("release_year")
    int releaseYear;

    public Song(String name, String singer, List<String> genres, int releaseYear) {
        this.name = name;
        this.singer = singer;
        this.genres = genres;
        this.releaseYear = releaseYear;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSinger() { return singer; }
    public void setSinger(String singer) { this.singer = singer; }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
}
