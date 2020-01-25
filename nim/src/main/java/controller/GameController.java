package controller;

import java.util.ArrayList;

import model.Board;
import model.Difficulty;

public class GameController {
	
	public boolean isAgainstAI;
	public boolean lastPieceWins;
	public Difficulty selectedDifficulty;

	public static void takeTurn(Board board) {
		int ones = 0;
		int twos = 0;
		int fours = 0;
		int count = 0;
		int nimSum = 0;
		ArrayList<Integer> counts = new ArrayList<Integer>();
		ArrayList<Integer> subtractedCounts = new ArrayList<Integer>();

		for (int i = 0; i < board.getBoard().length; i++) {
			for (int j = 0; j < board.getBoard()[i].length; j++) {
				if (board.getBoard()[i][j].isActive()) {
					count++;
				}
			}
			counts.add(count);
			count = 0;
		}
		
		for (int i = 0; i < counts.size(); i++) {
			if (counts.get(i)/4 > 0) {
				subtractedCounts.add((counts.get(i) - (4 * (counts.get(i)/4))));
				fours += (subtractedCounts.get(i)/4);
			}
			else {
				subtractedCounts.add(0);
			}
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
