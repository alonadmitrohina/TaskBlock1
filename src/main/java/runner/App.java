package runner;

import json.JSONParser;
import model.Playlist;
import statistics.Statistics;
import xml.XMLWriter;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    /**
     *
     * Парсинг json файлів та робота з різною кількістю потоків (1, 2, 4, 8)
     * @param directory
     * @param jsonParser
     * @throws InterruptedException
     */
    private void parseAllJSON(String directory, JSONParser jsonParser, int nThreads) throws InterruptedException {
        boolean finished;

        try (ExecutorService executor = Executors.newFixedThreadPool(nThreads)) {

            for (File file : Objects.requireNonNull(new File(directory).listFiles())) {
                if (!file.getName().endsWith(".json")) continue;
                executor.submit(() -> {
                    try {
                        jsonParser.parseJSON(file);
                    } catch (Exception e) {
                        System.err.println("Помилка при парсингу: " + file.getName());
                    }
                });
            }
            executor.shutdown();

            finished = executor.awaitTermination(5, java.util.concurrent.TimeUnit.MINUTES);
        }

        if (!finished) {
            throw new InterruptedException();
        }
    }

    /**
     * Запуск App
     * @param directory
     * @param attribute
     * @throws Exception
     */
    public void run(String directory, String attribute, int nThreads) throws Exception {
        JSONParser jsonParser = new JSONParser();
        Statistics statistics = new Statistics();

        try {
            parseAllJSON(directory, jsonParser, nThreads);
        } catch (InterruptedException e){
            System.err.println("Timeout: не всі завдання виконані");
        }

        Playlist playlist = jsonParser.getPlaylist();
        Map<String, Integer> stats = statistics.formStatistics(playlist, attribute);

        File directoryXML = new File("src/main/resources/xml/");
        if (!directoryXML.exists()) {
            directoryXML.mkdirs();
        }

        XMLWriter.createXML("src/main/resources/xml/", stats, attribute);
        System.out.println("Сформовано статистику за атрибутом: " + attribute);

    }
}
