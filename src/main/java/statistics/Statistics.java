package statistics;

import model.Playlist;
import model.Song;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Statistics {

    private Map<String, Integer> stats = new ConcurrentHashMap<>();


    /**
     * Формування статистики плейлісту за певним атрибутом
     * @param playlist
     * @param attribute
     * @return
     */
    public Map<String, Integer> formStatistics(Playlist playlist, String attribute) {

        if (playlist == null || playlist.getSongs() == null || playlist.getSongs().isEmpty()) {
            throw new IllegalArgumentException("Плейліст пустий або null.");
        }

        switch(attribute) {
            case "singer":
                statisticsBySinger(playlist);
                break;
            case "year":
                statisticsByYear(playlist);
                break;
            case "genres":
                statisticsByGenres(playlist);
                break;
            default:
                throw new IllegalArgumentException("Атрибут "+attribute+" не знайдено.");
        }

        return sortedStatistics();
    }

    private void statisticsBySinger(Playlist playlist) {
        for(Song song : playlist.getSongs()) {
            stats.merge(song.getSinger(), 1, Integer::sum);
        }
    }

    private void statisticsByYear(Playlist playlist){
        for(Song song : playlist.getSongs()) {
            stats.merge(String.valueOf(song.getReleaseYear()), 1, Integer::sum);
        }
    }

    private void statisticsByGenres(Playlist playlist){
        for(Song song : playlist.getSongs()) {
            writeGenre(song);
        }
    }

    private void writeGenre(Song song){
        for(String genre : song.getGenres()) {
            stats.merge(genre, 1, Integer::sum);
        }
    }

    /**
     * Повернення відсортованої статистики
     * @return
     */
    private Map<String, Integer> sortedStatistics() {
        return stats.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .collect(java.util.stream.Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        java.util.LinkedHashMap::new
                ));
    }
}

