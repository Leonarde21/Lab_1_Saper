package Saper;

import java.util.*;

public class SaperSolution2 {
    private int[] placeX = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] placeY = {-1, 0, 1, -1, 1, -1, 0, 1};


    public String[][] Solutin(String[][] field, double coefficient) {
        Random random = new Random();

        int size = field.length;
        String[][] newField = new String[size][size];

        int randomX = random.nextInt(0, size);
        int randomY = random.nextInt(0, size);

        // если бомба, то взрываемся
        if (field[randomX][randomY].equals("bomb")) {
            return null;
        }

        // если ноль, то раскрываем окружающие клетки
        if (field[randomX][randomY].equals("0")) {
            newField[randomX][randomY] = "0";
            OpenNullCell(field, newField, randomX, randomY);
        } else {
            newField[randomX][randomY] = field[randomX][randomY];
        }
        int x = 0;
        while (!CheckEndGame(newField)) {
            while (!ClearlyCase(field, newField)) {
                if (CheckEndGame(newField))
                    break;
                int k = 0;
                do{
                    randomX = random.nextInt(0, size);
                    randomY = random.nextInt(0, size);
                    k++;
                    if (k == 10) {
                        LinkedList<int[]> d = SearchNullCells(newField);
                        Collections.shuffle(d);
                        int[] a = d.removeFirst();
                        randomX = a[0];
                        randomY = a[1];
                    }
                }
                while (newField[randomX][randomY] != null);

                // если бомба, то взрываемся
                if (field[randomX][randomY].equals("bomb")) {
                    return null;
                }

                // если ноль, то раскрываем окружающие клетки
                if (field[randomX][randomY].equals("0")) {
                    newField[randomX][randomY] = "0";
                    OpenNullCell(field, newField, randomX, randomY);

                } else {
                    newField[randomX][randomY] = field[randomX][randomY];

                    // если цифра равная единице, то раскрываем клетки вокруг неё
                    if (field[randomX][randomY].equals("1")) {
                        int[] CountNullBomb = CountNullBomb(newField);
                        int countNull = CountNullBomb[0];
                        int countFindBomb = CountNullBomb[1];
                        double chance = (double) Math.round((size*size*coefficient-countFindBomb)/countNull*100) / 100;

                        if (chance >= 0.2) {
                            int otherX;
                            int otherY;
                            LinkedList<Integer> randomCell = new LinkedList<>();
                            for (int i = 0; i < 8; i++) {
                                otherX = randomX + this.placeX[i];
                                otherY = randomY + this.placeY[i];
                                if (otherX >= 0 && otherY >= 0 && otherX < size && otherY < size) {
                                    if (newField[otherX][otherY] == null) {
                                        randomCell.add(i);
                                    }
                                }
                            }
                            if (1.0/randomCell.size() > chance) {
                                continue;
                            }
                            Collections.shuffle(randomCell);

                            int number;
                            String value;

                            while (1.0/randomCell.size() <= chance) {
                                number = randomCell.removeFirst();
                                otherX = randomX + this.placeX[number];
                                otherY = randomY + this.placeY[number];
                                value = field[otherX][otherY];

                                if (value.equals("bomb")) {
                                    return null;
                                }

                                if (value.equals("0")) {
                                    newField[otherX][otherY] = "0";
                                    OpenNullCell(field, newField, otherX, otherY);
                                    break;
                                } else {
                                    newField[otherX][otherY] = field[otherX][otherY];
                                    if (ClearlyCase(field, newField)) {
                                        break;
                                    }
                                    countNull--;
                                    chance = (double) Math.round((size*size*coefficient-countFindBomb)/countNull*100) / 100;
                                }
                            }
                        }
                    }
                }
            }
        }

        ClearNumber(newField);
        return newField;
    }

    /// Открытие нулевых ячеек вплоть до тех в которых есть числовые значения
    private void OpenNullCell(String[][] field, String[][] newField, int cellX, int cellY) {
        final int[] placeX = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] placeY = {-1, 0, 1, -1, 1, -1, 0, 1};

        int size = field.length;

        LinkedList<int[]> queue = new LinkedList<>();

        int[] newCell = {cellX, cellY};
        queue.add(newCell);

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();

            int x = cell[0];
            int y = cell[1];

            if (x >= 0 && y >= 0 && x < size && y < size) {
                if (newField[x][y] == null) {
                    newField[x][y] = field[x][y];
                    if (field[x][y].equals("0")) {
                        for (int k = 0; k < 8; k++) {
                            newCell = new int[]{x + placeX[k], y + placeY[k]};
                            queue.add(newCell);
                        }
                    }
                } else if (newField[x][y].equals("0")) {
                    for (int k = 0; k < 8; k++) {
                        int otherX = x + placeX[k];
                        int otherY = y + placeY[k];
                        if (otherX >= 0 && otherY >= 0 && otherX < size && otherY < size) {
                            if (newField[otherX][otherY] == null) {
                                newField[otherX][otherY] = field[otherX][otherY];
                            }
                        }
                    }
                }
            }
        }
    }

    /// Ищем простые случаи к которым относятся те когда число не известных мин равно количеству неизвестных клеток и решаем их
    private boolean ClearlyCase(String[][] field, String[][] newField) {
        final int[] placeX = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] placeY = {-1, 0, 1, -1, 1, -1, 0, 1};

        boolean hasClearlyCase = false;
        int size = newField.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (newField[i][j] == "flag")
                    continue;
                if (newField[i][j] != null) {
                    int countNull = 0;
                    int countFlag = 0;
                    for (int k = 0; k < 8; k++) {
                        int otherI = i + placeX[k];
                        int otherJ = j + placeY[k];
                        if (otherI >= 0 && otherJ >= 0 && otherI < size && otherJ < size) {
                            if (newField[otherI][otherJ] == null) {
                                countNull++;
                            } else if (newField[otherI][otherJ] == "flag") {
                                countFlag++;
                            }
                        }
                    }
                    int value = Integer.parseInt(newField[i][j]);
                    if (value - countFlag == 0) {
                        newField[i][j] = "0";
                        OpenNullCell(field, newField, i, j);
                        continue;
                    }
                    if (countNull == value - countFlag) {
                        for (int k = 0; k < 8; k++) {
                            int otherI = i + placeX[k];
                            int otherJ = j + placeY[k];
                            if (otherI >= 0 && otherJ >= 0 && otherI < size && otherJ < size) {
                                if (newField[otherI][otherJ] == null) {
                                    newField[otherI][otherJ] = "flag";
                                    hasClearlyCase = true;
                                }
                            }
                        }
                        newField[i][j] = "0";
                        OpenNullCell(field, newField, i, j);
                    }
                }
            }
        }
        return hasClearlyCase;
    }

    /// Проверяем заполнены ли все ячейки в новой таблице
    private boolean CheckEndGame(String[][] newField) {
        int size = newField.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (newField[i][j] == null)
                    return false;
            }
        }
        return true;
    }

    /// Узнаем шанс попасть на бомбу при выборе случайной клетки
    private int[] CountNullBomb(String[][] newField) {
        int countNull = 0;
        int countBomb = 0;
        int size = newField.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (newField[i][j] == null) {
                    countNull++;
                } else if (newField[i][j].equals("flag")) {
                    countBomb++;
                }
            }
        }
        return new int[]{countNull, countBomb}; //(double) Math.round((size*size*coefficient-countBomb)/countNull*100) / 100;
    }

    /// Находим неизвестные ячейки
    private LinkedList<int[]> SearchNullCells(String[][] newField) {
        LinkedList<int[]> NullCells = new LinkedList<>();
        int size = newField.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (newField[i][j] == null) {
                    NullCells.add(new int[]{i, j});
                }
            }
        }
        return NullCells;
    }

    /// Подчищаем цифры оставшиеся после работы алгоритма
    private void ClearNumber(String[][] newField) {
        int size = newField.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!newField[i][j].equals("flag")){
                    newField[i][j] = "0";
                }
            }
        }
    }
}
