package Saper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class SaperWorkFile {
    public void WriteFile(String[][] field) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("file_solution.txt"))) {
            int size = field.length;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (field[i][j].equals("flag"))
                        bufferedWriter.write(field[i][j] + "\s\s");
                    else
                        bufferedWriter.write(field[i][j] + "\s\s\s\s\s");
                }
                bufferedWriter.newLine();
            }
        }
    }

    public String[][] ReadFile(String fileName) {
        List<String[]> rowsList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] cells = line.strip().split("\\s+");
                if (cells.length > 0) {
                    rowsList.add(cells);
                }
            }
            String[][] field = rowsList.toArray(new String[0][]);
            if (Check(field)) {
                return field;
            }
            return null;

        } catch (IOException e) {
            return null;
        }
    }

    private boolean Check(String[][] field) {
        int[] placeX = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] placeY = {-1, 0, 1, -1, 1, -1, 0, 1};

        int size = field.length;
        for (int i = 0; i < size; i++) {
            if (field[i].length != size) {
                System.out.println("Все строки должны иметь такуюже длинну как и столбцы");
                return false;
            }
        }

        try {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (field[i][j].equals("bomb")) {
                        continue;
                    }
                    if (Integer.parseInt(field[i][j]) >= 0 && Integer.parseInt(field[i][j]) <= 7) {
                        continue;
                    }
                    System.out.println("Найдено невозможное количество мин");
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("В данных неизвестный символ");
            return false;
        }

        boolean isExit = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j].equals("bomb")) {
                    isExit = false;
                    for (int k = 0; k < 8; k++) {
                        int otherI = i + placeX[k];
                        int otherJ = j + placeY[k];
                        if (otherI >= 0 && otherJ >= 0 && otherI < size && otherJ < size) {
                            if (!field[otherI][otherJ].equals("bomb")) {
                                isExit = true;
                                break;
                            }
                        }
                    }
                }
                if (!isExit) {
                    break;
                }
            }
            if (!isExit) {
                break;
            }
        }
        return isExit;
    }
}