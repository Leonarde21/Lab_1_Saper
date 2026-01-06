package Saper;

import java.util.*;

public class SaperSolution {

    public String[][] Solutin(String[][] field) {
        Random random = new Random();

        int size = field.length;
        String[][] newField = new String[size][size];

        int randomX = 3;//random.nextInt(0, size);
        int randomY = 0;//random.nextInt(0, size);

        if (field[randomX][randomY].equals("0")) {
            OpenNullCell(field, newField, randomX, randomY);

        } else if (field[randomX][randomY].equals("bomb")) {
            return null;

        } else { //if (field[randomX][randomY] > 0 && field[randomX][randomY] < 9) {
            OpenNotNullCell(field, newField, randomX, randomY);

        }

        while (!CheckEndGame(newField)) {
            if (!ClearlyCase(field, newField)) {
//                break;
            }
        }

        return newField;
    }

    private void OpenNullCell(String[][] field, String[][] newField, int cellX, int cellY) {
        final int[] placeX = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] placeY = {-1, 0, 1, -1, 1, -1, 0, 1};

        int size = field.length;

        Queue<int[]> queue = new LinkedList<>();

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
                }
            }
        }
    }

    private void OpenNotNullCell(String[][] field, String[][] newField, int cellX, int cellY) {
        final int[] placeX = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] placeY = {-1, 0, 1, -1, 1, -1, 0, 1};

        int size = newField.length;

        newField[cellX][cellY] = field[cellX][cellY];

        int countNull = 0;
        int countFlag = 0;
        for (int k = 0; k < 8; k++) {
            int i = cellX + placeX[k];
            int j = cellY + placeY[k];
            if (i >= 0 && j >= 0 && i < size && j < size) {
                if (newField[i][j] == null) {
                    countNull++;
                } else if (Objects.equals(newField[i][j], "flag")) {
                    countFlag++;
                }
            }
        }

        if (countNull == Integer.parseInt(newField[cellX][cellY]) - countFlag) {
            for (int k = 0; k < 8; k++) {
                int i = cellX + placeX[k];
                int j = cellY + placeY[k];
                if (i >= 0 && j >= 0 && i < size && j < size) {
                    if (newField[i][j] == null) {
                        newField[i][j] = "flag";
                    }
                }
            }
        }
    }

    private boolean ClearlyCase(String[][] field, String[][] newField) {
        final int[] placeX = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] placeY = {-1, 0, 1, -1, 1, -1, 0, 1};

        boolean hasClearlyCase = false;
        int size = newField.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (newField[i][j] == "bomb" || newField[i][j] == "flag")
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
                    if (countNull == Integer.parseInt(newField[i][j]) - countFlag) {
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
                    }
                }
            }
        }
        return hasClearlyCase;
    }

    private boolean IntersectionCase(String[][] newField) {
        boolean foundIntersection = false;
        int size = newField.length;

        List<int[]> numberCells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                String cell = newField[i][j];
                if (cell != null && !cell.equals("flag") && !cell.equals("0")) {
                    numberCells.add(new int[]{i, j});
                }
            }
        }

        for (int i = 0; i < numberCells.size(); i++) {
            for (int j = i + 1; j < numberCells.size(); j++) {
                int[] cell1 = numberCells.get(i);
                int[] cell2 = numberCells.get(j);

                if (analyzeCellIntersection(cell1[0], cell1[1], cell2[0], cell2[1])) {
                    foundIntersection = true;
                }
            }
        }

        return foundIntersection;
    }

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
}