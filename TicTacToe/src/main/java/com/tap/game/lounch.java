package com.tap.game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;
import java.util.Scanner;


class TicTacToe 
{
    static char[][] board;

    public TicTacToe() 
    {
        board=new char[3][3];
        initBoard();
    }

    void initBoard() 
    {
        for(int i=0;i<board.length;i++) 
        {
            for(int j=0;j<board[i].length;j++) 
            {
                board[i][j] = ' ';
            }
        }
    }

    static void displayBoard() 
    {
        System.out.println("-------------");
        for(int i=0;i<board.length;i++) 
        {
            System.out.print("| ");
            for(int j=0;j<board[i].length;j++) 
            {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    static void placeMark(int row, int col, char mark) 
    {
        if(row>=0 && row<=2 && col>=0 && col<=2 && 
           board[row][col] == ' ') 
        {
            board[row][col]=mark;
        } 
        else
        {
            System.out.println("Invalid Position, try again!");
        }
    }

    static boolean checkColWin() 
    {
        for(int j=0;j<=2;j++) 
        {
            if(board[0][j]!=' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) 
            {
                return true;
            }
        }
        return false;
    }

    static boolean checkRowWin() 
    {
        for(int i=0;i<=2;i++) 
        {
            if(board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) 
            {
                return true;
            }
        }
        return false;
    }

    static boolean checkDiagWin() 
    {
        return (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            || (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]);
    }

    static boolean checkDraw() 
    {
        for(int i=0;i<=2;i++) 
        {
            for(int j=0;j<=2;j++) 
            {
                if(board[i][j] == ' ') 
                {
                    return false;
                }
            }
        }
        return true;
    }
}

abstract class Player 
{
    String name;
    char mark;

    abstract void makeMove();

    boolean isValidMove(int row, int col) 
    {
        return row >= 0 && row <= 2 && col >= 0 && col <= 2 
        		&& TicTacToe.board[row][col] == ' ';
    }
}

class HumanPlayer extends Player 
{
    Scanner sc = new Scanner(System.in);

    public HumanPlayer(String name, char mark) 
    {
        this.name = name;
        this.mark = mark;
    }

    void makeMove() 
    {
        int row, col;
        do 
        {
            System.out.print("Enter row (1-3): ");
            row = sc.nextInt();
            System.out.print("Enter col (1-3): ");
            col = sc.nextInt();
        } while (!isValidMove(row, col));
        TicTacToe.placeMark(row, col, mark);
    }
}

class AiPlayer extends Player 
{
    public AiPlayer(String name, char mark) 
    {
        this.name = name;
        this.mark = mark;
    }

    void makeMove() 
    {
        Random r = new Random();
        int row, col;
        do 
        {
            row = r.nextInt(3);
            col = r.nextInt(3);
        } while (!isValidMove(row, col));
        System.out.println("AI placed at row " + row + ", col " + col);
        TicTacToe.placeMark(row, col, mark);
    }
}



public class lounch 
{
public static void main(String[] args) 
{
        TicTacToe t = new TicTacToe();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Player 1 name (X): ");
        String playerX = sc.nextLine();

        System.out.println("Enter Player 2 name (O): ");
        String playerO = sc.nextLine();

        HumanPlayer p1 = new HumanPlayer(playerX, 'X');
        HumanPlayer p2 = new HumanPlayer(playerX, 'O');
//        AiPlayer p2 = new AiPlayer(playerO, 'O');

        Player currentPlayer = p1;

        String winnerName = null;
        boolean draw = false;

        while (true) 
        {
            System.out.println(currentPlayer.name + "'s turn (" + currentPlayer.mark + ")");
            currentPlayer.makeMove();
            TicTacToe.displayBoard();

            if(TicTacToe.checkColWin() || TicTacToe.checkRowWin() || TicTacToe.checkDiagWin()) 
            {
                System.out.println("🏆 " + currentPlayer.name + " has won!");
                winnerName = currentPlayer.name;
                break;
            } 
            else if(TicTacToe.checkDraw()) 
            {
                System.out.println("🤝 Game is a draw!");
                draw = true;
                break;
            } 
            else 
            {
                currentPlayer = (currentPlayer == p1) ? p2 : p1;
            }
        }

        // Save result to database
        try (Connection con = connector_factory.requestConnection()) 
        {
            String sql = "insert into game_results (player_x, player_o, winner) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, playerX);
            ps.setString(2, playerO);
            ps.setString(3, draw ? "Draw" : winnerName);
            ps.executeUpdate();
            System.out.println("✅ Game result saved to database successfully!");
        } 
        catch (Exception e) 
        {
            System.out.println("❌ Error saving to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

