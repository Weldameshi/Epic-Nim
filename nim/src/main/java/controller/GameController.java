package controller;

import java.util.ArrayList;

import model.Board;

public class GameController {

	public static void takeTurn(Board board) {
		int ones;
		int twos;
		int fours;
		int count = 0;
		ArrayList<Integer> counts = new ArrayList<Integer>();

		for (int i = 0; i < board.getBoard().length; i++) {
			for (int j = 0; j < board.getBoard()[i].length; j++) {
				if (board.getBoard()[i][j].isActive()) {
					count++;
				}
			}
			counts.add(count);
			count = 0;
		}

	}
	
	public static boolean checkBoard(Board board) {
		int count = 0;
		for(int i = 0; i < board.getBoard().length; i++) {
			for(int j = 0; j < board.getBoard()[i].length; j++) {
				if(board.getBoard()[i][j].isActive()) {
					count++;
				}
			}
		}
		
		if(count > 1) {
			return false;
		} else {
			return true;
		}
	}

}
