package Saper;

import java.util.Objects;
import java.util.Random;

class GeneratorRandomBomb {

    public String[][] Generate() {
        Random random = new Random();

        float coefficient = 0.2F;

        int size = random.nextInt(3, 10000);
        int quantity = (int) (size * size * coefficient);

        String[][] field = CreateField(size, quantity); //Создаем поле
        AssemblyBomb(field, size); //Подсчитываем бомбы вокруг клетки

        return field;
    }

    private String[][] CreateField(int size, int quantity) {
        Random random = new Random();

        final int[] placeX = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] placeY = {-1, 0, 1, -1, 1, -1, 0, 1};

        while (true) {
            String[][] field = new String[size][size];
            int placedBomb = 0;
            int[] x = random.ints(0, size).limit(quantity).toArray();
            int[] y = random.ints(0, size).limit(quantity).toArray();

            while (placedBomb < quantity) {
                while (field[x[placedBomb]][y[placedBomb]] != null) {
                    y[placedBomb] += random.nextInt(1, 3);
                    if (y[placedBomb] >= size) {
                        y[placedBomb] = 0;
                        x[placedBomb] += 1;
                    }
                    if (x[placedBomb] == size) {
                        x[placedBomb] = 0;
                        y[placedBomb] = 0;
                    }
                }
                for (int k = 0; k < 8; k++) {
                    int i = x[placedBomb] + placeX[k];
                    int j = y[placedBomb] + placeY[k];
                    if (i >= 0 && j >= 0 && i < size && j < size) {
                        if (field[i][j] == null) {
                            field[x[placedBomb]][y[placedBomb]] = "bomb";
                            placedBomb++;
                            break;
                        }
                    }
                    if (k == 7) {
                        y[placedBomb] += random.nextInt(1, 3);
                        if (y[placedBomb] >= size) {
                            y[placedBomb] = 0;
                            x[placedBomb] += 1;
                        }
                        if (x[placedBomb] == size) {
                            x[placedBomb] = 0;
                            y[placedBomb] = 0;
                        }
                    }
                }
            }


            boolean isExit = true;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (Objects.equals(field[i][j], "bomb")) {
                        isExit = false;
                        for (int k = 0; k < 8; k++) {
                            int otherI = i + placeX[k];
                            int otherJ = j + placeY[k];
                            if (otherI >= 0 && otherJ >= 0 && otherI < size && otherJ < size) {
                                if (field[otherI][otherJ] == null) {
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
            if (isExit) {
                return field;
            }
        }
    }

    private void AssemblyBomb(String[][] field, int size) {
        int[] placeX = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] placeY = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Objects.equals(field[i][j], "bomb")) {
                    continue;
                }

                int countBomb = 0;
                for (int k = 0; k < 8; k++) {
                    int ni = i + placeX[k];
                    int nj = j + placeY[k];

                    if (ni >= 0 && nj >= 0 && ni < size && nj < size) {
                        if (Objects.equals(field[ni][nj], "bomb")) {
                            countBomb++;
                        }
                    }
                }

                field[i][j] = String.valueOf(countBomb);
            }
        }
    }
}
