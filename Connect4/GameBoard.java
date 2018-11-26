package game;

import java.util.ArrayList;

public class GameBoard {
	public static final int playerX = 1;
	public static final int playerO = -1;
	public static final int empty = 0;
	public int [][] cells;
	private int lastSymbolPlayed;
	private Move lastMove;
    private int winner;
;
	
	public GameBoard(GameBoard board) {
		lastSymbolPlayed=board.lastSymbolPlayed;
		winner=board.winner;
		lastMove=board.lastMove;
		
		cells = new int[6][7];
		for(int row=0;row<6;row++) {
			for(int col=0;col<7;col++) {
				cells[row][col]=board.cells[row][col];
			}
		}
		
	}
	public GameBoard() {
		// TODO Auto-generated constructor stub
	}
	public void init() {
		lastSymbolPlayed=playerO;
		winner=0;
		lastMove=new Move();
		//isGameOver=false;
		cells = new int[6][7];
		for(int row=0;row<6;row++) {
			for(int col=0;col<7;col++) {
				cells[row][col]=empty;
			}
		}
	}
	
	public void setWinner(int winner) {
		this.winner= winner;
	}
	public int getWinner() {
		return winner;
	}
	
	public int getLastSymbolPlayed() {
		return lastSymbolPlayed;
	}
	public void setLastSymbolPlayed(int lastSymbolPlayed) {
		this.lastSymbolPlayed = lastSymbolPlayed;
	}
	public Move getLastMove() {
		return lastMove;
	}
	public void setLastMove(Move lastMove) {
		this.lastMove.setRow(lastMove.getRow());
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	public boolean isGameOver() {
		if (checkWinningState()) {
    		return true;
    	}
    	
    	// Check for an empty cell, i.e. check to find if it is a draw.
    	
    	for(int row=0; row<6; row++) {
			for(int col=0; col<7; col++) {
				if(cells[row][col] == empty) {
                    return false;
                }
            }
        }
    	// The game is in draw state, if all cells are full
    	// and nobody has won the game.
    	return true;
		
	}
	
	public boolean isColumnFull(int col) {
		if(cells[0][col]== empty)
		{
			return false;
		}
		return true;
	}
	
	public ArrayList<GameBoard> childrenBoard(int symbol) {
		ArrayList<GameBoard> children = new ArrayList<GameBoard>();
		for(int i=0;i<7;i++) {
			if(!isColumnFull(i)) {
				GameBoard board = new GameBoard(this);
				board.makeMove(i,symbol);
				children.add(board);
				
			}
		}return children;
	}
	
	public int rowPosition(int col) {
		int rowPosition = -1;
		for (int row=0; row<6; row++) {
			if (cells[row][col] == empty) {
				
				rowPosition = row;
				//System.out.println(rowPosition);
			}
		}
		return rowPosition;
	}
	
	public void makeMove(int col,int letter) {
		//System.out.println("Column value"+col);
		//if(canMove(rowPosition(col), col)) {
			this.lastMove = new Move(rowPosition(col), col);
			this.lastSymbolPlayed = letter;
			this.cells[rowPosition(col)][col] = letter;
		//}
		//else {
			//System.err.println("Column " + (col+1) + " is full!");
		//}
		
	}
	
	public boolean canMove(int row,int col) {
		if(row < 0 || row > 5 || col < 0 || col > 6)
			return false;
		return true;
	}
	
	public void printBoard() {
		System.out.println("|| 1 || 2 || 3 || 4 || 5 || 6 || 7 ||");
  		System.out.println();
  		for (int i=0; i<6; i++) {
  			for (int j=0; j<7; j++) {
  				if (j!=6) {
  					if (cells[i][j] == 1) {
  						System.out.print("|| " + "X" + " ");
  					} else if (cells[i][j] == -1) {
  						System.out.print("|| " + "O" + " ");
  					} else {
  						System.out.print("|| " + "-" + " ");
  					}
  				} else {
  					if (cells[i][j] == 1) {
  						System.out.println("|| " + "X" + " ||");
  					} else if (cells[i][j] == -1) {
  						System.out.println("|| " + "O" + " ||");
  					} else {
  						System.out.println("|| " + "-" + " ||");
  					}
  				}
  			}
  		}
		
	}
	
	public int evaluateMove() {
		int playerXPoint=0;
		int playerOPoint=0;
		
		if(checkWinningState()) {
			int winnerPlayer = getWinner();
			if(winnerPlayer == playerX) {
				playerXPoint=playerXPoint+100;
			}else {
				playerOPoint=playerOPoint-100;
			}		
		}
		
		playerXPoint = playerXPoint + check3InARow(playerX)*10 + check2InARow(playerX);
		playerOPoint = playerOPoint - check3InARow(playerO)*10 - check2InARow(playerO);
		
		return playerXPoint+playerOPoint;
	}
	
	public boolean checkWinningState() {
		
		//for horizontal win
		for(int row=5;row>=0;row--) {
			for(int col=0;col<=3;col++) {
				if(cells[row][col]==cells[row][col+1] &&
						cells[row][col+1]==cells[row][col+2] &&
						cells[row][col+2]==cells[row][col+3] &&
						cells[row][col]!= empty) {
					setWinner(cells[row][col]);
					return true;
				}
			}
		}
		

		//for vertical win
		for(int row=5;row>=3;row--) {
			for(int col=0;col<7;col++) {
				if(cells[row][col]==cells[row-1][col] &&
						cells[row-1][col]==cells[row-2][col] &&
						cells[row-2][col]==cells[row-3][col] &&
						cells[row-3][col]!= empty) {
					setWinner(cells[row][col]);
					return true;
				}
			}
		}
		

		//for descending diagonal win
		for(int row=0;row<3;row++) {
			for(int col=0;col<4;col++) {
				if(cells[row][col]==cells[row+1][col+1] &&
						cells[row][col]==cells[row+2][col+2] &&
						cells[row][col]==cells[row+3][col+3] &&
						cells[row][col]!= empty) {
					setWinner(cells[row][col]);
					return true;
				}
			}
		}
		

		//for ascending diagonal win
		for(int row=0;row<=5;row++) {
			for(int col=0;col<=6;col++) {
				if (canMove(row-3,col+3)) {
				if(cells[row][col]==cells[row-1][col+1] &&
						cells[row][col]==cells[row-2][col+2] &&
						cells[row][col]==cells[row-3][col+3] &&
						cells[row][col]!= empty) {
					setWinner(cells[row][col]);
					return true;
				}
				}
			}
				
		}
		setWinner(0);
		return false;
		
	}
	
	public int check3InARow(int playerSymbol) {
		
			int points = 0;
			
			// Check for 3 consecutive checkers in a row, horizontally.
			for (int row = 5; row >= 0; row--) {
				for (int col = 0; col < 7; col++) {
					if (canMove(row, col + 2)) {
						if (cells[row][col] == cells[row][col + 1]
								&& cells[row][col] == cells[row][col + 2]
								&& cells[row][col] == playerSymbol) {
							points++;
						}
					}
				}
			}
	
			// Check for 3 consecutive checkers in a row, vertically.
			for (int row = 5; row >= 0; row--) {
				for (int col = 0; col < 7; col++) {
					if (canMove(row - 2, col)) {
						if (cells[row][col] == cells[row - 1][col]
								&& cells[row][col] == cells[row - 2][col]
								&& cells[row][col] == playerSymbol) {
							points++;
						}
					}
				}
			}
	
			// Check for 3 consecutive checkers in a row, in descending diagonal.
			for (int row = 0; row < 6; row++) {
				for (int col = 0; col < 7; col++) {
					if (canMove(row + 2, col + 2)) {
						if (cells[row][col] == cells[row + 1][col + 1]
								&& cells[row][col] == cells[row + 2][col + 2]
								&& cells[row][col] == playerSymbol) {
							points++;
						}
					}
				}
			}
	
			// Check for 3 consecutive checkers in a row, in ascending diagonal.
			for (int row = 0; row < 6; row++) {
				for (int col = 0; col < 7; col++) {
					if (canMove(row - 2, col + 2)) {
						if (cells[row][col] == cells[row - 1][col + 1]
								&& cells[row][col] == cells[row - 2][col + 2]
								&& cells[row][col] == playerSymbol) {
							points++;
						}
					}
				}
			}
	
			return points;
					
		}
		
	    // It returns the frequency of 3 checkers in a row
	    // for the given player.
	public int check2InARow(int playerSymbol) {
			
			int points = 0;
			
			// Check for 2 consecutive checkers in a row, horizontally.
			for (int row = 5; row >= 0; row--) {
				for (int col = 0; col < 7; col++) {
					if (canMove(row, col + 1)) {
						if (cells[row][col] == cells[row][col + 1]
								&& cells[row][col] == playerSymbol) {
							points++;
						}
					}
				}
			}
	
			// Check for 3 consecutive checkers in a row, vertically.
			for (int row = 5; row >= 0; row--) {
				for (int col = 0; col < 7; col++) {
					if (canMove(row - 1, col)) {
						if (cells[row][col] == cells[row - 1][col]
								&& cells[row][col] == playerSymbol) {
							points++;
						}
					}
				}
			}
	
			// Check for 3 consecutive checkers in a row, in descending diagonal.
			for (int row = 0; row < 6; row++) {
				for (int col = 0; col < 7; col++) {
					if (canMove(row + 1, col + 1)) {
						if (cells[row][col] == cells[row + 1][col + 1]
								&& cells[row][col] == playerSymbol) {
							points++;
						}
					}
				}
			}
	
			// Check for 3 consecutive checkers in a row, in ascending diagonal.
			for (int row = 0; row < 6; row++) {
				for (int col = 0; col < 7; col++) {
					if (canMove(row - 1, col + 1)) {
						if (cells[row][col] == cells[row - 1][col + 1]
								&& cells[row][col] == playerSymbol) {
							points++;
						}
					}
				}
			}
	
			return points;
					
		}
		
		
	
}
