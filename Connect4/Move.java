package game;

public class Move {
	private int row;
	private int col;
	private int value;
	private int alpha;
	private int beta;
	
	public Move() {
		row=-1;
		col=-1;
		value=0;

	}
	
	public Move(int value) {
		row=-1;
		col=-1;
		this.value = value;
	}
	
	public Move(int row,int col) {
		this.row=row;
		this.col=col;
		value=-1;
	}
	
	public Move(int row,int col,int value) {
		this.row=row;
		this.col=col;
		this.value=value;
		
	}
	
	
	
	public Move(int row,int col,int value,int alpha,int beta) {
		this.row=row;
		this.col=col;
		this.value=value;
		this.alpha=alpha;
		this.beta=beta;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public int getBeta() {
		return beta;
	}

	public void setBeta(int beta) {
		this.beta = beta;
	}
}
