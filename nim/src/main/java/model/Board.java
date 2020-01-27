package model;

public class Board {
	private Piece[][] board;
	
	
	public Board(int[] pileNumber){
		board = new Piece[pileNumber.length][];
		for(int i = 0; i< pileNumber.length; i++) {
			for(int j = 0; j < pileNumber[i]; j++) {
				Piece piece = new Piece();
				board[i][j] = piece;
			}
		}
	}

	public Piece[][] getBoard() {
		return board;
	}

	public void setBoard(Piece[][] board) {
		this.board = board;
	}

	
	public String toString() {
		String boardString = "";
		int counter = 0;
		return null;
	}
}
