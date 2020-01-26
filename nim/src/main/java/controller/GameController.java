package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.*;
import model.Board;
import model.CurrentPage;
import model.Difficulty;
import model.Player;

public class GameController {
	
	public static boolean isAgainstAI, lastPieceWins;
	public static Difficulty selectedDifficulty;
	public static CurrentPage currentPage = CurrentPage.MAIN_PAGE;
	
	@FXML
	public static RadioButton playerVsAIRB, playerVsPlayerRB, easyRB, mediumRB, hardRB, lastPieceWinRB, lastPieceLossRB;
	
	@FXML
	public static Button mainMenuSubmitBtn;
	
	@FXML
	public static Label eGamePlayer1NameLabel, eGamePlayer2NameLabel, eGamePlayer1ScoreLabel, eGamePlayer2ScoreLabel,
							mGamePlayer1NameLabel, mGamePlayer2NameLabel, mGamePlayer1ScoreLabel, mGamePlayer2ScoreLabel,
							hGamePlayer1NameLabel, hGamePlayer2NameLabel, hGamePlayer1ScoreLabel, hGamePlayer2ScoreLabel;
	
	@FXML
	public static TextField player1TF, player2TF;
	
	@FXML
	public static ImageView sprite1IV, sprite2IV, sprite3IV;
	
	// gets the info the user inputs from the mainpage
	public static void startGame() {
		isAgainstAI = playerVsAIRB.isSelected();
		lastPieceWins = lastPieceWinRB.isSelected();
		if(easyRB.isSelected()) {
			selectedDifficulty = Difficulty.EASY;
		} else if(mediumRB.isSelected()) {
			selectedDifficulty = Difficulty.MEDIUM;
		} else {
			selectedDifficulty = Difficulty.HARD;
		}
		
		currentPage = currentPage.ENTER_NAMES;
	}
	
	// keyevent for the user to hit enter on the enter name page
	public void keyPress(KeyEvent key) throws IOException {
		if(key.getCode() == KeyCode.ENTER && currentPage == CurrentPage.ENTER_NAMES) {
			enterNames();
		}else {
			
		}
	}
	
	// take all the information from name page to make the characters
	public static void enterNames() {
		Player player1 = new Player(player1TF.getText(), 1, true);
	}
	
	// game logic
	public static void playGame() {
		
	}

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
