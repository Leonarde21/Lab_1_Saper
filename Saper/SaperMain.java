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


        SaperSolution2 solution = new SaperSolution2();
        String[][] newField = solution.Solutin(field, 0.2);

        int countTry = 0;
        while (newField == null && countTry < 1000) {
            newField = solution.Solutin(field, 0.2);
            countTry++;
        }


        SaperWorkFile saperWorkFile = new SaperWorkFile();
        saperWorkFile.WriteFile(newField);
    }
}
