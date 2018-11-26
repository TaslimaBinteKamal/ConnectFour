package game;

import java.util.ArrayList;
import java.util.Random;

public class Minimax {
	
	private int maxDepth;
	private int playerSymbol;
	public int getMaxDepth() {
		return maxDepth;
	}
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	public int getPlayerSymbol() {
		return playerSymbol;
	}
	public void setPlayerSymbol(int playerSymbol) {
		this.playerSymbol = playerSymbol;
	}

	/*public Minimax() {
		maxDepth = 6;
		playerSymbol = GameBoard.playerX;
	}*/
	
	public Minimax(int depth,int symbol) {
		this.maxDepth=depth;
		this.playerSymbol = symbol;
	}
	

	public Move minimaxAlgorithm(GameBoard board) {
		if(playerSymbol==GameBoard.playerX) {
			return maximumScore(new GameBoard(board),0);
		}
		else {
			return minimumScore(new GameBoard(board),0);
		}
	}
	
	public Move maximumScore(GameBoard board,int depth) {
		Random rn = new Random();
		//System.out.println("Hi I'Max");
		if(board.isGameOver() || depth==maxDepth) {
			Move lastAssumedMove = new Move(board.getLastMove().getRow(),board.getLastMove().getCol(),board.evaluateMove());
			return lastAssumedMove;
			
		}
		ArrayList<GameBoard> childrenBoards = new ArrayList<GameBoard>(board.childrenBoard(GameBoard.playerX));
		Move maxiMove = new Move(Integer.MIN_VALUE);
		for(GameBoard child:childrenBoards) {
			Move minMove = minimumScore(child,depth+1);
			if(minMove.getValue()>=maxiMove.getValue()) {
				if(minMove.getValue()==maxiMove.getValue()) {
					if(rn.nextInt(2)==0) {
						maxiMove.setRow(child.getLastMove().getRow());
		                maxiMove.setCol(child.getLastMove().getCol());
		                maxiMove.setValue(minMove.getValue());
					}
				}else {
					maxiMove.setRow(child.getLastMove().getRow());
	                maxiMove.setCol(child.getLastMove().getCol());
	                maxiMove.setValue(minMove.getValue());
				}
				
			}
		}
		return maxiMove;
	}
	
	public Move minimumScore(GameBoard board,int depth) {
		Random rand = new Random();
		//System.out.println("Hi i'm Min");
		if(board.isGameOver() || depth==maxDepth) {
			/*if(board.isGameOver()) {System.out.println("They say it's over");}
			else {
			System.out.println(depth);}*/
			Move lastAssumedMove = new Move(board.getLastMove().getRow(),board.getLastMove().getCol(),board.evaluateMove());
			return lastAssumedMove;
			
		}
		ArrayList<GameBoard> childrenBoards = new ArrayList<GameBoard>(board.childrenBoard(GameBoard.playerO));
		Move miniMove = new Move(Integer.MAX_VALUE);
		for(GameBoard child:childrenBoards) {
			Move maxMove = maximumScore(child,depth+1);
			if(maxMove.getValue()<=miniMove.getValue()) {
				if(maxMove.getValue()==miniMove.getValue()) {
					if(rand.nextInt(2)==1) {
					miniMove.setRow(child.getLastMove().getRow());
	                miniMove.setCol(child.getLastMove().getCol());
	                miniMove.setValue(maxMove.getValue());
					}
				}
				else {
					miniMove.setRow(child.getLastMove().getRow());
	                miniMove.setCol(child.getLastMove().getCol());
	                miniMove.setValue(maxMove.getValue());
				}
			}
			
		}
		return miniMove;
	}
	
	
	
	//Applying alpha-beta pruning to minimax
	public Move minimaxAlgorithmABPruning(GameBoard board) {
		if(playerSymbol==GameBoard.playerX) {
			return maximumScoreABPruning(new GameBoard(board),0,Integer.MIN_VALUE,Integer.MAX_VALUE);
		}
		else {
			return minimumScoreABPruning(new GameBoard(board),0,Integer.MIN_VALUE,Integer.MAX_VALUE);
		}
	}
	
	public Move maximumScoreABPruning(GameBoard board,int depth,int alpha,int beta) {
		Random rn = new Random();
		//System.out.println("Hi I'Max");
		if(board.isGameOver() || depth==maxDepth ) {
			int boardValue = board.evaluateMove();
			Move lastAssumedMove = new Move(board.getLastMove().getRow(),board.getLastMove().getCol(),boardValue,boardValue,beta);
			return lastAssumedMove;
			
		}
		ArrayList<GameBoard> childrenBoards = new ArrayList<GameBoard>(board.childrenBoard(GameBoard.playerX));
		Move maxiMove = new Move(-1,-1,Integer.MIN_VALUE,alpha,beta);
		for(GameBoard child:childrenBoards) {
			Move minMove = minimumScoreABPruning(child,depth+1,alpha,beta);
			if(minMove.getAlpha()>=minMove.getBeta()) {
				System.out.println("Minalpha " + minMove.getAlpha());
				System.out.println("Minbeta " + minMove.getBeta());
				maxiMove.setRow(child.getLastMove().getRow());
                maxiMove.setCol(child.getLastMove().getCol());
                maxiMove.setValue(minMove.getValue());
                return maxiMove;
			}
			else{
			if(minMove.getValue()>=maxiMove.getValue()) {
				if(minMove.getValue()==maxiMove.getValue()) {
					if(rn.nextInt(2)==0) {
						maxiMove.setRow(child.getLastMove().getRow());
		                maxiMove.setCol(child.getLastMove().getCol());
		                maxiMove.setValue(minMove.getValue());
		                maxiMove.setAlpha(minMove.getBeta());
		                alpha= minMove.getBeta();
					}
				}else {
					maxiMove.setRow(child.getLastMove().getRow());
	                maxiMove.setCol(child.getLastMove().getCol());
	                maxiMove.setValue(minMove.getValue());
	                maxiMove.setAlpha(minMove.getBeta());
	                alpha= minMove.getBeta();
				}
				
			}
			}
			
		}
		return maxiMove;
	}
	
	public Move minimumScoreABPruning(GameBoard board,int depth,int alpha,int beta) {
		Random rand = new Random();

		//System.out.println("Hi i'm Min");
		if(board.isGameOver() || depth==maxDepth ) {
			int boardValue = board.evaluateMove();
			/*if(board.isGameOver()) {System.out.println("They say it's over");}
			else {
			System.out.println(depth);}*/
			Move lastAssumedMove = new Move(board.getLastMove().getRow(),board.getLastMove().getCol(),boardValue,alpha,boardValue);
			return lastAssumedMove;
			
		}
		ArrayList<GameBoard> childrenBoards = new ArrayList<GameBoard>(board.childrenBoard(GameBoard.playerO));
		Move miniMove = new Move(-1,-1,Integer.MAX_VALUE,alpha,beta);
		for(GameBoard child:childrenBoards) {
			Move maxMove = maximumScoreABPruning(child,depth+1,alpha,beta);
			if(maxMove.getAlpha()>=maxMove.getBeta()) {
				System.out.println("Maxalpha " + maxMove.getAlpha());
				System.out.println("Maxbeta " + maxMove.getBeta());
				miniMove.setRow(child.getLastMove().getRow());
                miniMove.setCol(child.getLastMove().getCol());
                miniMove.setValue(maxMove.getValue());
                return miniMove;
			}
			else { 
				if(maxMove.getValue()<=miniMove.getValue()) {
					if(maxMove.getValue()==miniMove.getValue()) {
						if(rand.nextInt(2)==1) {
							miniMove.setRow(child.getLastMove().getRow());
			                miniMove.setCol(child.getLastMove().getCol());
			                miniMove.setValue(maxMove.getValue());
			                miniMove.setBeta(maxMove.getAlpha());
			                beta=maxMove.getAlpha();
						}
					}
					else {
							miniMove.setRow(child.getLastMove().getRow());
			                miniMove.setCol(child.getLastMove().getCol());
			                miniMove.setValue(maxMove.getValue());
			                miniMove.setBeta(maxMove.getAlpha());
			                beta=maxMove.getAlpha();
					}
					
					
				}
			}
			
		}
		return miniMove;
	}
	
}
