import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *  File: TicTacToe.java
 *  Project: Games
 *  Creator: Carlos A. Caballero
 *  Purpose: Starting to relearn java with creating a quick and dirty game of TicTacToe
 */

 public class TicTacToe {
    private List<Character> grid;
    public final int gridSize = 9;
    public final int gridRows = 3;
    private Boolean player1Turn;
    // Player 1 = X , Player 2 = O

    public TicTacToe() {
        grid = new ArrayList<Character>(gridSize);
        player1Turn = true;
        reset();
    }

    public void reset() {
        for (int i = 0; i < gridSize; i++) {
           grid.add(' ');
        }
        player1Turn = true;
    }

    public int whosTurn() {
        if (player1Turn) {
            return 1;
        }
        return 2; 
    }

    public void move(int cell) {
        char token;
        if (player1Turn) {
            token = 'X';
        } else {
            token = 'O';
        }
        grid.set(cell, token);
        player1Turn = !player1Turn;
    }

    public Boolean isMovesAvailable() {
        int winner = whoWon();
        if (winner != 0) {
            return false;
        }

        for (Character cell: grid) {
            if (cell == ' ') {
                return true;
            }
        }
        return false;
    }

    // 0 = no one, 1 = Player 1, 2 = Player 2
    public int whoWon() {
        char winner = 0;
        for (int i = 0; i < gridRows; i++) {
            if (grid.get(i) != ' ' && grid.get(i) == grid.get(i + gridRows) && grid.get(i) == grid.get(i + 2 * gridRows)) {
                winner = grid.get(i);
            } else if (grid.get(i * gridRows) != ' ' && grid.get(i * gridRows) == grid.get(i * gridRows + 1) && grid.get(i * gridRows)  == grid.get(i * gridRows + 2)) {
                winner = grid.get(i * gridRows);
            }
        }
        if (grid.get(0) != ' ' && grid.get(0) == grid.get(4)  && grid.get(0) == grid.get(8)) {
            winner = grid.get(0);
        }
        if (grid.get(2) != ' ' && grid.get(2) == grid.get(4) && grid.get(2)  == grid.get(6)) {
            winner = grid.get(2);
        }
        if (winner == 'X') {
            return 1;
        }
        if (winner == 'O') {
            return 2;
        }
        return 0;
    }

    public Boolean isValidMove(int cell) {
        if (cell < 0 || cell >= gridSize) {
            return false;
        }
        return grid.get(cell) == ' ';
    }


    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder();
        for (int i = 0; i < gridSize; i++) {
            repr.append(' ');
            if (i % gridRows != 0) {
                repr.append("| ");
            }
            repr.append(grid.get(i));
            if (i % gridRows == gridRows - 1) {
                repr.append(" \n");
                if (i != gridSize - 1) {
                    repr.append("-  -   -  -\n");
                }
            } 
        }
        return repr.toString();
    }


    public static void main(String args[]) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String input;
        int trys = 0;
        int move = -1;
        Boolean alive = true;
        try{
            TicTacToe game = new TicTacToe(); 
            while (alive) {
                System.out.print(game);
                while(trys < 3 && !game.isValidMove(move)) {
                    System.out.printf("Player %d choose your move: ", game.whosTurn());
                    input = stdin.readLine();
                    move = Integer.parseInt(input);
                    trys++;
                }
                if (trys == 3) {
                    throw new Exception();
                }
                game.move(move);
                trys = 0;
                if (!game.isMovesAvailable()) {
                    alive = false;
                }
            }
            int winner = game.whoWon();
            String congrats =  String.format("Congratualtions Player %d!", winner);
            if (winner == 0) {
                congrats = "Tie game.";
            }
            System.out.print(game);
            System.out.println(congrats);
            
        } catch(IOException e) {
            System.out.println("Something went wrong with input!");
        } catch(Exception e) {
            // End game
        } finally {
            System.out.print("Good-bye!\n\n");
        }

    }
 }