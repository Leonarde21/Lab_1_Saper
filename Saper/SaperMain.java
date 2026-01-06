package Saper;

import java.io.IOException;
import java.util.Scanner;

public class SaperMain {
    public static void main(String[] args) throws IOException {
        System.out.println(
                "1. Ввести путь к файлу с данными для Сапера\n" +
                "2. Сгенерировать входные данные");
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.out.println("Такого действия нет");
            return;
        }

        int task = sc.nextInt();
        sc.nextLine();
        String[][] field;

        if (task == 1) {
            SaperWorkFile workFile = new SaperWorkFile();

            System.out.print("Введите путь к файлу: ");
            String filename = sc.nextLine();

            field = workFile.ReadFile(filename);
            if (field == null) {
                System.out.println("Файл не найден");
                return;
            }

        } else if (task == 2) {
            GeneratorRandomBomb gr = new GeneratorRandomBomb();
            field = gr.Generate();

        } else {
            System.out.println("Такого действия нет");
            return;
        }

        String[][] exampleOne = {
                {"bomb", "bomb", "1", "0"},
                {"bomb",    "3",   "1",  "0"},
                {"2",    "2",   "0",  "0"},
                {"bomb",    "1",   "0",  "0"}
        };
        String[][] exampleTwo = {
                {"0",    "0",   "1",     "1",    "1"},
                {"0",    "1",   "2",     "bomb", "1"},
                {"0",    "1",   "bomb",  "2",    "1"},
                {"0",    "2",   "3",     "4",    "2"},
                {"0",    "1",   "bomb",  "bomb", "bomb"}
        };
        String[][] exampleThree = {
                {"bomb", "1", "1", "bomb", "1"},
                {"1", "1", "1", "1", "1"},
                {"0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0"}
        };
        String[][] exampleFour = {
                {"1",    "bomb", "3",    "bomb", "2"},
                {"1",    "2",    "4",    "bomb", "3"},
                {"0",    "1",    "bomb", "4",    "bomb"},
                {"1",    "2",    "2",    "bomb", "3"},
                {"bomb", "1",    "1",    "2",    "bomb"}
        };

        String[][] exampleFive = {
                {"1",     "bomb",  "2",     "1",     "0",     "1",     "1",     "2",     "1",     "2",     "2",     "bomb",  "2",     "bomb",  "bomb",  "1",     "0",     "0",     "1",     "bomb"},
                {"2",     "3",     "bomb",  "1",     "1",     "2",     "bomb",  "2",     "bomb",  "3",     "bomb",  "4",     "3",     "3",     "2",     "1",     "0",     "1",     "2",     "2"},
                {"1",     "bomb",  "2",     "2",     "3",     "bomb",  "3",     "2",     "1",     "3",     "bomb",  "3",     "bomb",  "1",     "0",     "0",     "1",     "3",     "bomb",  "2"},
                {"1",     "1",     "1",     "1",     "bomb",  "bomb",  "2",     "0",     "0",     "1",     "1",     "2",     "1",     "2",     "1",     "1",     "1",     "bomb",  "bomb",  "2"},
                {"0",     "1",     "2",     "3",     "3",     "2",     "2",     "2",     "2",     "1",     "0",     "0",     "0",     "1",     "bomb",  "1",     "1",     "2",     "2",     "1"},
                {"0",     "1",     "bomb",  "bomb",  "2",     "1",     "1",     "bomb",  "bomb",  "2",     "0",     "0",     "0",     "1",     "1",     "1",     "0",     "0",     "0",     "0"},
                {"0",     "2",     "3",     "4",     "bomb",  "1",     "1",     "3",     "bomb",  "2",     "1",     "2",     "2",     "1",     "0",     "0",     "1",     "2",     "2",     "1"},
                {"0",     "1",     "bomb",  "3",     "3",     "3",     "2",     "2",     "2",     "3",     "3",     "bomb",  "bomb",  "2",     "0",     "1",     "3",     "bomb",  "bomb",  "1"},
                {"0",     "1",     "2",     "bomb",  "2",     "bomb",  "bomb",  "3",     "3",     "bomb",  "bomb",  "6",     "bomb",  "3",     "0",     "1",     "bomb",  "bomb",  "4",     "2"},
                {"2",     "3",     "3",     "3",     "3",     "4",     "bomb",  "3",     "bomb",  "bomb",  "4",     "bomb",  "bomb",  "2",     "0",     "1",     "2",     "3",     "bomb",  "1"},
                {"bomb",  "bomb",  "bomb",  "3",     "bomb",  "2",     "2",     "3",     "3",     "2",     "3",     "3",     "3",     "1",     "0",     "0",     "0",     "1",     "2",     "2"},
                {"4",     "6",     "5",     "bomb",  "2",     "1",     "1",     "bomb",  "2",     "1",     "1",     "bomb",  "1",     "0",     "1",     "1",     "1",     "0",     "1",     "bomb"},
                {"bomb",  "bomb",  "bomb",  "4",     "2",     "0",     "1",     "2",     "bomb",  "1",     "1",     "1",     "1",     "0",     "1",     "bomb",  "1",     "0",     "1",     "1"},
                {"3",     "5",     "bomb",  "bomb",  "1",     "0",     "0",     "1",     "1",     "1",     "0",     "0",     "0",     "0",     "1",     "1",     "1",     "0",     "0",     "0"},
                {"bomb",  "2",     "2",     "2",     "2",     "2",     "2",     "1",     "1",     "1",     "1",     "0",     "0",     "0",     "0",     "0",     "0",     "0",     "0",     "0"},
                {"2",     "2",     "0",     "0",     "1",     "bomb",  "bomb",  "1",     "2",     "bomb",  "2",     "0",     "1",     "1",     "1",     "0",     "0",     "0",     "0",     "0"},
                {"bomb",  "1",     "0",     "0",     "1",     "2",     "2",     "1",     "2",     "bomb",  "3",     "1",     "2",     "bomb",  "3",     "1",     "1",     "0",     "0",     "0"},
                {"3",     "3",     "1",     "0",     "1",     "1",     "1",     "0",     "1",     "2",     "bomb",  "2",     "3",     "bomb",  "3",     "bomb",  "1",     "0",     "0",     "0"},
                {"bomb",  "bomb",  "2",     "0",     "1",     "bomb",  "2",     "2",     "3",     "3",     "4",     "bomb",  "4",     "3",     "4",     "2",     "1",     "0",     "0",     "0"},
                {"3",     "bomb",  "2",     "0",     "1",     "1",     "2",     "bomb",  "bomb",  "bomb",  "3",     "bomb",  "3",     "bomb",  "bomb",  "1",     "0",     "0",     "0",     "0"}
        };

//        field = exampleFive;

        int size = field.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j].equals("bomb"))
                    System.out.print(field[i][j] + "\s\s");
                else
                    System.out.print(field[i][j] + "\s\s\s\s\s");
            }
            System.out.println();
        }
        System.out.println();

        SaperSolution2 solution = new SaperSolution2();
        String[][] newField = solution.Solutin(field, 0.2);

        int countTry = 0;
        while (newField == null && countTry < 1000) {
            newField = solution.Solutin(field, 0.2);
            countTry++;
        }
        System.out.println(countTry);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (newField[i][j] == "flag")
                    System.out.print(newField[i][j] + "\s\s");
                else
                    System.out.print(newField[i][j] + "\s\s\s\s\s");
            }
            System.out.println();
        }

        SaperWorkFile saperWorkFile = new SaperWorkFile();
        saperWorkFile.WriteFile(newField);
    }
}
