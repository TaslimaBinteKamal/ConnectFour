package game;

import java.util.Scanner;

public class ManageGame {

	static GameBoard board;
	static Minimax OPlayer;
	public ManageGame() {
		
	}
	public void playGame() {
		board = new GameBoard();
		int maxDepth = 2;
		OPlayer = new Minimax(maxDepth, GameBoard.playerO);
		
		
		board.init();
		board.printBoard();
	
		while(!board.isGameOver()) {
			
			if(playerMoves(board)==0) {
				
				break;
			}
			board.printBoard();
		}
		System.out.println();

		if (board.getWinner() == GameBoard.playerX) {
			System.out.println("Human player 'X' wins!");
		} else if (board.getWinner() == GameBoard.playerO) {
			System.out.println("AI computer 'O' wins!");
		} else {
			System.out.println("It's a draw!");
		}
		
		System.out.println("Game over.");
	}
	public int playerMoves(GameBoard board) {
		int XColumnPosition;
		
		if(board.getLastSymbolPlayed()==GameBoard.playerO) 
		{
			System.out.print("\nGive column (1-7): ");
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			XColumnPosition = in.nextInt();
			
			board.makeMove(XColumnPosition-1, GameBoard.playerX);
			System.out.println();
			return 1;
		}
		else if(board.getLastSymbolPlayed()==GameBoard.playerX) {
			System.out.println("AI 'O' moves.");
            
			//Move OMove = OPlayer.minimaxAlgorithm(board);
			Move OMove = OPlayer.minimaxAlgorithmABPruning(board);
			

			board.makeMove(OMove.getCol(), GameBoard.playerO);
			System.out.println();
			return -1;
			
		}
		else {
			return 0;
		}
	}

	
}
