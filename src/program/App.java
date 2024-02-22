package program;

import java.util.Random;
import java.util.Scanner;


public class App {

    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;

    private static final int WIN_COUNT = 4;


    /**
     * Инициализация объектов игры
     */
    static void initialize() {
        System.out.println("Я хочу сыграть с тобой в одну игру, она называется\n" +
                "КРЕСТИКИ-НОЛИКИ.\n" +
                "Выбери размер поля:");


        fieldSizeX = scanner.nextInt();
        fieldSizeY = fieldSizeX;
        field = new char[fieldSizeX][fieldSizeY];

        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
        System.out.println("Чтобы выиграть, ты должен построить линию по вертикали,\n" +
                "горизонтали или диагонали, состоящую из " + WIN_COUNT + " клеток.");
    }

    /**
     * Печать текущего состояния игрового поля
     */
    static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print("-" + (i + 1));
        }
        System.out.println("-");

        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }

        for (int i = 0; i < fieldSizeX * 2 + 2; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Ход игрока (человека)
     */
    static boolean humanTurn(char dot, int win) {
        int x;
        int y;
        do {
            System.out.print("Введи координаты хода X и Y\n(от 1 до " + fieldSizeX + ") через пробел: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
            System.out.println(x + " " + y);

        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        System.out.println("Твой ход:");

        field[x][y] = DOT_HUMAN;
        return checkState(x, y, dot, win);
    }

    /**
     * Ход игрока (компьютера)
     */
    static boolean aiTurn(char dot, int win) {
        int x;
        int y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        System.out.println("Ход умнейшего компьютера:");
        field[x][y] = DOT_AI;
        return checkState(x, y, dot, win);
    }

    /**
     * Проверка, является ли ячейка игрового поля пустой
     *
     * @param x координата
     * @param y координата
     * @return результат проверки
     */
    static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка валидности координат хода
     *
     * @param x координата
     * @param y координата
     * @return результат проверки
     */
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Поверка на ничью (все ячейки игрового поля заполнены фишками человека или компьютера)
     *
     * @return
     */
    static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * TODO: Переработать в рамках домашней работы
     * Метод проверки победы
     *
     * @param dot фишка игрока
     * @return результат проверки победы
     */
    static boolean checkWin(int x, int y, char dot, int win) {
        if (
                checkHorizont(x, y, dot, win) ||
                        checkVertical(x, y, dot, win) ||
                        checkDiagonalDown(x, y, dot, win) ||
                        checkDiagonalUp(x, y, dot, win)
        ) {
            return true;
        }

        return false;
    }

    static boolean checkHorizont(int x, int y, char dot, int win) {
        int check = 0;
        for (int i = 0; i < fieldSizeX; i++) {
            if (field[x][i] == dot) {
                check += 1;
                if (check == win) {
                    return true;
                }
            } else {
                check = 0;
            }
        }
        return false;
    }

    static boolean checkVertical(int x, int y, char dot, int win) {
        int check = 0;
        for (int i = 0; i < fieldSizeX; i++) {
            if (field[i][y] == dot) {
                check += 1;
                if (check == win) {
                    return true;
                }
            } else {
                check = 0;
            }
        }
        return false;
    }

    static boolean checkDiagonalDown(int x, int y, char dot, int win) {
        int check = 0;
        int temp = 0;
        if (x > y) {
            for (int i = x - y; i <= fieldSizeX - (x - y); i++) {
                if (field[i][temp] == dot) {
                    check += 1;
                    if (check == win) {
                        return true;
                    }
                }
                temp += 1;
            }
        } else if (x < y) {
            for (int i = y - x; i <= fieldSizeX - (y - x); i++) {
                if (field[temp][i] == dot) {
                    check += 1;
                    if (check == win) {
                        return true;
                    }
                }
                temp += 1;
            }
        } else {
            for (int i = 0; i < fieldSizeX; i++) {
                if (field[i][i] == dot) {
                    check += 1;
                    if (check == win) {
                        return true;
                    }
                }
                temp += 1;
            }
        }
        return false;
    }

    static boolean checkDiagonalUp(int x, int y, char dot, int win) {
        int check = 0;
        int temp = 0;

        if (x + y < fieldSizeX - 1) {
            for (int i = 0; i < x+y+1; i++) {
                if (field[i][temp] == dot) {
                    check += 1;
                    if (check == win) {
                        return true;
                    }
                }
                temp += 1;
            }
        } else if (x + y > fieldSizeX - 1) {
            temp = (x+y) - fieldSizeX - 1;
            for(int i = fieldSizeX - 1; i < (fieldSizeX*2-1) - (x+y); i--) {
                if (field[i][temp] == dot) {
                    check += 1;
                    if (check == win) {
                        return true;
                    }
                }
                temp += 1;
            }
        } else {
            for (int i = fieldSizeX-1; i >= 0; i--) {
                if (field[i][temp] == dot) {
                    check += 1;
                    if (check == win) {
                        return true;
                    }
                }
                temp += 1;
            }
        }

        return false;
    }

    /**
     * Проверка состояния игры
     *
     * @param dot фишка игрока
     * @return состояние игры
     */
    static boolean checkState(int x, int y, char dot, int win) {
        if (checkWin(x, y, dot, win)) {
            System.out.println("Вы победили!");
            printField();
            return true;
        } else if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }
        // Игра продолжается
        return false;
    }

    public static void main(String[] args) {
        while (true) {
            initialize();
            printField();
            while (true) {
                if (
                        humanTurn(DOT_HUMAN, WIN_COUNT)
                ) {
                    break;
                } else printField();

                if (
                        aiTurn(DOT_AI, WIN_COUNT)
                ) break;
                else printField();
            }
            System.out.print("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }

}