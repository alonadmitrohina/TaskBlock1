package runner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        String directory = "src/main/resources/json";
        Scanner scanner = new Scanner(System.in);
        String attribute = null;

        while (true) {
            System.out.println("Оберіть атрибут для формування статистики:");
            System.out.println("1 - singer");
            System.out.println("2 - year");
            System.out.println("3 - genres");
            System.out.println("0 - quit");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "0":
                    System.out.println("Завершення роботи.");
                    return;
                case "1":
                    attribute = "singer";
                    break;
                case "2":
                    attribute = "year";
                    break;
                case "3":
                    attribute = "genres";
                    break;
                default:
                    System.out.println("Невідомий вибір, спробуйте ще раз.\n");
                    continue;
            }

            break;
        }


        App app = new App();
        long startTime = System.nanoTime();

        app.run(directory, attribute, 4);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Час виконання: " + duration + " нс");
    }


}
