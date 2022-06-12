package tictactoe;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class TicTacToe {

    private enum GameState {
        X_WINS,
        O_WINS,
        DRAW,
        GAME_NOT_FINISHED
    }

    private enum Players {
        HUMAN,
        EASY_AI,
        MEDIUM_AI,
        HARD_AI
    }

    class Move {
        private int index;
        private int score;

        public Move() {  }

        public Move(int score) {
            this.score = score;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    private ArrayList<Character> grid = new ArrayList<Character>(9);
    private final int cellsCount = 9;
    Scanner scanner = new Scanner(System.in);

    public TicTacToe() {
        for (int i = 0; i < cellsCount; i++)
            grid.add(' ');
    }

    public void play() {
        Players xPlayer, oPlayer;
        
        while (true) {
            System.out.println("start [user/easy/medium/hard] [...] or exit");
            System.out.println("Input command:");
            String[] command = scanner.nextLine().trim().split(" ");

            if (command[0].equals("exit"))
                break;

            if (command.length != 3 || !command[0].equals("start")) {
                System.out.println("Bad parameters!");
                continue;
            }
            
            if (!command[1].equals("user") && !command[1].equals("easy") &&
                    !command[1].equals("medium") && !command[1].equals("hard")) {
                System.out.println("Bad parameters!");
                continue;
            }

            if (!command[2].equals("user") && !command[2].equals("easy") &&
                    !command[2].equals("medium") && !command[2].equals("hard")) {
                System.out.println("Bad parameters!");
                continue;
            }

            xPlayer = definePlayer(command[1]);
            oPlayer = definePlayer(command[2]);

            this.printGrid(this.grid);

            while (this.getGameState(this.grid) == GameState.GAME_NOT_FINISHED) {
                this.move(xPlayer, true);
                this.printGrid(this.grid);
                if (this.getGameState(this.grid) == GameState.GAME_NOT_FINISHED) {
                    this.move(oPlayer, false);
                    this.printGrid(this.grid);
                }
            }

            this.printGameState();
            for (int i = 0; i < cellsCount; i++)
                grid.set(i, ' ');
        }
    }

    private void printGrid(ArrayList<Character> grid) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++)
            System.out.printf("| %c %c %c |\n", grid.get(i * 3), grid.get(i * 3 + 1), grid.get(i * 3 + 2));
        System.out.println("---------");
    }

    private GameState getGameState(ArrayList<Character> grid) {
        if (grid.get(0) == 'X' && grid.get(1) == 'X' && grid.get(2) == 'X')
            return GameState.X_WINS;
        if (grid.get(3) == 'X' && grid.get(4) == 'X' && grid.get(5) == 'X')
            return GameState.X_WINS;
        if (grid.get(6) == 'X' && grid.get(7) == 'X' && grid.get(8) == 'X')
            return GameState.X_WINS;
        if (grid.get(0) == 'X' && grid.get(3) == 'X' && grid.get(6) == 'X')
            return GameState.X_WINS;
        if (grid.get(1) == 'X' && grid.get(4) == 'X' && grid.get(7) == 'X')
            return GameState.X_WINS;
        if (grid.get(2) == 'X' && grid.get(5) == 'X' && grid.get(8) == 'X')
            return GameState.X_WINS;
        
        if (grid.get(0) == 'X' && grid.get(4) == 'X' && grid.get(8) == 'X')
            return GameState.X_WINS;
        if (grid.get(2) == 'X' && grid.get(4) == 'X' && grid.get(6) == 'X')
            return GameState.X_WINS;

        if (grid.get(0) == 'O' && grid.get(1) == 'O' && grid.get(2) == 'O')
            return GameState.O_WINS;
        if (grid.get(3) == 'O' && grid.get(4) == 'O' && grid.get(5) == 'O')
            return GameState.O_WINS;
        if (grid.get(6) == 'O' && grid.get(7) == 'O' && grid.get(8) == 'O')
            return GameState.O_WINS;
        if (grid.get(0) == 'O' && grid.get(3) == 'O' && grid.get(6) == 'O')
            return GameState.O_WINS;
        if (grid.get(1) == 'O' && grid.get(4) == 'O' && grid.get(7) == 'O')
            return GameState.O_WINS;
        if (grid.get(2) == 'O' && grid.get(5) == 'O' && grid.get(8) == 'O')
            return GameState.O_WINS;

        if (grid.get(0) == 'O' && grid.get(4) == 'O' && grid.get(8) == 'O')
            return GameState.O_WINS;
        if (grid.get(2) == 'O' && grid.get(4) == 'O' && grid.get(6) == 'O')
            return GameState.O_WINS;
        
        if (countX(grid) + countO(grid) < cellsCount)
            return GameState.GAME_NOT_FINISHED;

        return GameState.DRAW;
    }

    private void printGameState() {
        GameState gameState = this.getGameState(this.grid);

        switch (gameState) {
            case X_WINS:
                System.out.println("X wins");
                break;
            case O_WINS:
                System.out.println("O wins");
                break;
            case GAME_NOT_FINISHED:
                System.out.println("Game not finished");
                break;
            case DRAW:
                System.out.println("Draw");
                break;
        }
    }

    private Players definePlayer(String player) {
        if (player.equals("user"))
            return Players.HUMAN;

        if (player.equals("easy"))
            return Players.EASY_AI;

        if (player.equals("medium"))
            return Players.MEDIUM_AI;

        if (player.equals("hard"))
            return Players.HARD_AI;

        return Players.HUMAN;
    }

    private void move(Players player, boolean aiTurn) {
        switch (player) {
            default:
            case HUMAN:
                humanMove();
                break;
            case EASY_AI:
                easyMove();
                break;
            case MEDIUM_AI:
                mediumMove(aiTurn);
                break;
            case HARD_AI:
                hardMove(aiTurn);
                break;
        }
    }

    private void humanMove() {
        boolean entrySuccess = false;
        int i = 0, j = 0;

        while (!entrySuccess) {
            System.out.println("Enter the coordinates:");
            try {
                i = scanner.nextInt();
                j = scanner.nextInt();

                if (i > 3 || i < 1 || j > 3 || j < 1)
                    throw new ArithmeticException("Coordinates should be from 1 to 3!");

                if (grid.get((i - 1) * 3 + j - 1) != ' ')
                    throw new ArithmeticException("This cell is occupied! Choose another one!");

                entrySuccess = true;
            }
            catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("You should enter numbers!");
            }
            catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }

        if (countX(this.grid) == countO(this.grid))
            grid.set((i - 1) * 3 + j - 1, 'X');
        else
            grid.set((i - 1) * 3 + j - 1, 'O');
    }

    private void easyMove() {
        System.out.println("Making move level \"easy\"");
        ArrayList<Integer> emptyCells = emptyIndexies(this.grid);
        int i = emptyCells.get(ThreadLocalRandom.current().nextInt(0, emptyCells.size()));

        if (countX(this.grid) == countO(this.grid))
            grid.set(i, 'X');
        else
            grid.set(i, 'O');
    }

    private void mediumMove(boolean aiTurn) {
        System.out.println("Making move level \"medium\"");
        int i = aiTurn ? playerCanWin('X') : playerCanWin('O');
        if (i == -1)
            i = aiTurn ? playerCanWin('O') : playerCanWin('X');
        if (i == -1) {
            ArrayList<Integer> emptyCells = emptyIndexies(this.grid);
            i = emptyCells.get(ThreadLocalRandom.current().nextInt(0, emptyCells.size()));
        }

        if (countX(this.grid) == countO(this.grid))
            grid.set(i, 'X');
        else
            grid.set(i, 'O');
    }

    private int playerCanWin(char s) {
        // три горизонтали
        if (grid.get(0) == ' ' && grid.get(1) == s && grid.get(2) == s)
            return 0;
        if (grid.get(0) == s && grid.get(1) == ' ' && grid.get(2) == s)
            return 1;
        if (grid.get(0) == s && grid.get(1) == s && grid.get(2) == ' ')
            return 2;

        if (grid.get(3) == ' ' && grid.get(4) == s && grid.get(5) == s)
            return 3;
        if (grid.get(3) == s && grid.get(4) == ' ' && grid.get(5) == s)
            return 4;
        if (grid.get(3) == s && grid.get(4) == s && grid.get(5) == ' ')
            return 5;

        if (grid.get(6) == ' ' && grid.get(7) == s && grid.get(8) == s)
            return 6;
        if (grid.get(6) == s && grid.get(7) == ' ' && grid.get(8) == s)
            return 7;
        if (grid.get(6) == s && grid.get(7) == s && grid.get(8) == ' ')
            return 8;

        // три вертикали
        if (grid.get(0) == ' ' && grid.get(3) == s && grid.get(6) == s)
            return 0;
        if (grid.get(0) == s && grid.get(3) == ' ' && grid.get(6) == s)
            return 3;
        if (grid.get(0) == s && grid.get(3) == s && grid.get(6) == ' ')
            return 6;

        if (grid.get(1) == ' ' && grid.get(4) == s && grid.get(7) == s)
            return 1;
        if (grid.get(1) == s && grid.get(4) == ' ' && grid.get(7) == s)
            return 4;
        if (grid.get(1) == s && grid.get(4) == s && grid.get(7) == ' ')
            return 7;

        if (grid.get(2) == ' ' && grid.get(5) == s && grid.get(8) == s)
            return 2;
        if (grid.get(2) == s && grid.get(5) == ' ' && grid.get(8) == s)
            return 5;
        if (grid.get(2) == s && grid.get(5) == s && grid.get(8) == ' ')
            return 8;

        // две диагонали
        if (grid.get(0) == ' ' && grid.get(4) == s && grid.get(8) == s)
            return 0;
        if (grid.get(0) == s && grid.get(4) == ' ' && grid.get(8) == s)
            return 4;
        if (grid.get(0) == s && grid.get(4) == s && grid.get(8) == ' ')
            return 8;

        if (grid.get(2) == ' ' && grid.get(4) == s && grid.get(6) == s)
            return 2;
        if (grid.get(2) == s && grid.get(4) == ' ' && grid.get(6) == s)
            return 4;
        if (grid.get(2) == s && grid.get(4) == s && grid.get(6) == ' ')
            return 6;

        return -1;
    }

    private void hardMove(boolean aiTurn) {
        System.out.println("Making move level \"hard\"");

        if (countX(this.grid) == countO(this.grid))
            grid.set(minimax(grid, aiTurn).getIndex(), 'X');
        else
            grid.set(minimax(grid, aiTurn).getIndex(), 'O');
    }

    private Move minimax(ArrayList<Character> grid, boolean aiTurn) {
        ArrayList<Integer> emptyCells = emptyIndexies(grid);

        switch(getGameState(grid)) {
            case X_WINS:
            case O_WINS:
                return aiTurn ? new Move(-10) : new Move(10);
            case DRAW:
                return new Move(0);
            default:
                break;
        }

        Move[] moves = new Move[emptyCells.size()];

        for (int i = 0; i < emptyCells.size(); i++) {

            Move move = new Move();
            move.setIndex(emptyCells.get(i));

            if (aiTurn)
                grid.set(emptyCells.get(i), 'X');
            else
                grid.set(emptyCells.get(i), 'O');

            Move result = minimax(new ArrayList<Character>(grid), !aiTurn);
            move.setScore(result.getScore());

            grid.set(emptyCells.get(i), ' ');
            moves[i] = move;
        }

        // Если очередь хода ИИ, выбираем ход с наибольшим счётом
        int bestMove = 0;
        if (aiTurn) {
            int bestScore = -11;
            for (var i = 0; i < moves.length; i++)
                if (moves[i].getScore() > bestScore) {
                    bestScore = moves[i].getScore();
                    bestMove = i;
                }
        } else {
            // Иначе выбираем ход с наименьшим счётом
            int bestScore = 11;
            for (var i = 0; i < moves.length; i++)
                if (moves[i].getScore() < bestScore) {
                    bestScore = moves[i].getScore();
                    bestMove = i;
                }
        }

        return moves[bestMove];
    }

    private void printMoves(Move [] moves) {
        for (int i = 0; i < moves.length; i++) {
            System.out.println("Index: " + moves[i].getIndex());
            System.out.println("Score: " + moves[i].getScore());
        }
        System.out.println();
    }

    private ArrayList<Integer> emptyIndexies(ArrayList<Character> grid) {
        ArrayList<Integer> emptyIndexes = new ArrayList<>();

        for (int i = 0; i < cellsCount; i++)
            if (grid.get(i) == ' ')
                emptyIndexes.add(i);

        return emptyIndexes;
    }

    private int countX(ArrayList<Character> grid) {
        int countX = 0;

        for (int i = 0; i < cellsCount; i++)
            if (grid.get(i) == 'X')
                countX++;

        return countX;
    }

    private int countO(ArrayList<Character> grid) {
        int countO = 0;

        for (int i = 0; i < cellsCount; i++)
            if (grid.get(i) == 'O')
                countO++;

        return countO;
    }
}
