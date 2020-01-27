package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Board;
import model.CurrentPage;
import model.Difficulty;
import model.Player;

public class GameController {

	public static boolean isAgainstAI, lastPieceWins;
	public static Difficulty selectedDifficulty;
	public static CurrentPage currentPage = CurrentPage.MAIN_PAGE;
	public static String selectSprite;
	public static int round = 0;

	public static SceneController sc = new SceneController();

	@FXML
	public RadioButton playerVsAIRB, playerVsPlayerRB, easyRB, mediumRB, hardRB, lastPieceWinRB, lastPieceLossRB;

	@FXML
	public Button mainMenuSubmitBtn, helpBtn;

	@FXML
	public Label gamePlayer1NameLabel, gamePlayer2NameLabel, gamePlayer1ScoreLabel, gamePlayer2ScoreLabel;

	@FXML
	public TextField player1TF, player2TF;

	@FXML
	public ImageView sprite1IV, sprite2IV, sprite3IV;

	// gets the info the user inputs from the mainpage
	public void startGame() {
		isAgainstAI = playerVsAIRB.isSelected();
		lastPieceWins = lastPieceWinRB.isSelected();
		if (easyRB.isSelected()) {
			selectedDifficulty = Difficulty.EASY;
		} else if (mediumRB.isSelected()) {
			selectedDifficulty = Difficulty.MEDIUM;
		} else {
			selectedDifficulty = Difficulty.HARD;
		}

		currentPage = currentPage.ENTER_NAMES;
		sc.changeScene("/EnterNames.fxml", "");
	}

	// keyevent for the user to hit enter on the enter name page
	public void keyPress(KeyEvent key) throws IOException {
		if (key.getCode() == KeyCode.ENTER && currentPage == CurrentPage.ENTER_NAMES) {
			enterNames();
		} else {

		}
	}

	// take all the information from name page to make the characters
	public void enterNames() {
		if (!player1TF.getText().trim().isEmpty() && (isAgainstAI || !player2TF.getText().trim().isEmpty())) {
			Player player1 = new Player(player1TF.getText(), 1, true);
			Player player2 = isAgainstAI ? new Player("NPC", 2, false) : new Player(player2TF.getText(), 2, true);
			// change scene to which game mode they selected
			switch (selectedDifficulty) {
			case EASY:
				sc.changeScene("/EasyGame.fxml", "");
				break;
			case MEDIUM:
				sc.changeScene("/MediumGame.fxml", "");
				break;
			case HARD:
				sc.changeScene("/HardGame.fxml", "");
				break;
			}
		}

	}

	// game logic
	public void playGame() {
		Board board = makeNewBoard();
		do {
			round++;
			if (round % 2 != 0) {
				// player

			} else {
				// player 2 or npc
				if (isAgainstAI) {
					// npc
					takeNPCTurn(board);
				} else {
					// player 2

				}
			}
		} while (checkBoard(board));
		sc.changeScene("/PlayAgain.fxml", "");
	}

	public void takeTurn(Board board) {

	}

	public void takeNPCTurn(Board board) {
		int ones = 0;
		int twos = 0;
		int fours = 0;
		int count = 0;
		int nimSum = 0;
		int countsTotal = 0;
		ArrayList<Integer> counts = new ArrayList<Integer>();
		ArrayList<Integer> subtractedCounts = new ArrayList<Integer>();

		for (int i = 0; i < board.getBoard().length; i++) {
			for (int j = 0; j < board.getBoard()[i].length; j++) {
				if (board.getBoard()[i][j].isActive()) {
					count++;
				}
			}
			counts.add(count);
			countsTotal += count;
			count = 0;
		}

		for (int i = 0; i < counts.size(); i++) {
			if (counts.get(i) / 4 > 0) {
				subtractedCounts.add((counts.get(i) - (4 * (counts.get(i) / 4))));
				fours += (subtractedCounts.get(i) / 4);
			} else {
				subtractedCounts.add(0);
			}
		}

		for (int i = 0; i < counts.size(); i++) {
			if (counts.get(i) / 2 > 0) {
				subtractedCounts.add((counts.get(i) - (2 * (counts.get(i) / 2))));
				twos += (subtractedCounts.get(i) / 2);
			} else {
				subtractedCounts.add(0);
			}
		}

		for (int i = 0; i < counts.size(); i++) {
			if (counts.get(i) / 1 > 0) {
				subtractedCounts.add((counts.get(i) - (1 * (counts.get(i) / 1))));
				ones += (subtractedCounts.get(i) / 1);
			} else {
				subtractedCounts.add(0);
			}
		}
		boolean removeFour = false;
		boolean removeTwo = false;
		boolean removeOne = false;

		if (fours % 2 > 0) {
			nimSum += fours % 2;
			removeFour = true;
		}

		if (twos % 2 > 0) {
			nimSum += twos % 2;
			removeTwo = true;
		}

		if (ones % 2 > 0) {
			nimSum += ones % 2;
			removeOne = true;
		}

		if (nimSum == 0) {
			boolean pieceRemoved = false;

			for (int i = 0; i < board.getBoard().length && !pieceRemoved; i++) {
				for (int j = 0; j < board.getBoard()[i].length && !pieceRemoved; j++) {
					if (board.getBoard()[i][j].isActive()) {
						board.getBoard()[i][j].setActive(false);
						pieceRemoved = true;
					}
				}
			}

		}

		else if (nimSum > 0) {
			// removeOne/killing blow
			if (removeOne) {
				// +removeFour
				if (removeFour) {
					if (countsTotal == 5 && !lastPieceWins) {
						int selectedRow = -1;
						for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
							if (counts.get(i) / 4 > 0)
								selectedRow = i;
						}

						int piecesRemoved = 0;
						for (int i = 0; piecesRemoved < 4; i++) {
							if (board.getBoard()[selectedRow][i].isActive()) {
								board.getBoard()[selectedRow][i].setActive(false);
								piecesRemoved++;
							}
						}
					} else if (countsTotal == 5 && lastPieceWins) {
						int selectedRow = -1;
						for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
							if (counts.get(i) / 4 > 0)
								selectedRow = i;
						}

						int piecesRemoved = 0;
						for (int i = 0; piecesRemoved < 3; i++) {
							if (board.getBoard()[selectedRow][i].isActive()) {
								board.getBoard()[selectedRow][i].setActive(false);
								piecesRemoved++;
							}
						}

					} else {
						int selectedRow = -1;
						for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
							if (counts.get(i) / 4 > 0)
								selectedRow = i;
						}

						int piecesRemoved = 0;
						for (int i = 0; piecesRemoved < 3; i++) {
							if (board.getBoard()[selectedRow][i].isActive()) {
								board.getBoard()[selectedRow][i].setActive(false);
								piecesRemoved++;
							}
						}

					}

				}

				// +removeTwo
				else if (removeTwo) {
					if (countsTotal == 3 && !lastPieceWins) {
						int selectedRow = -1;
						for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
							if (counts.get(i) / 2 > 0)
								selectedRow = i;
						}

						int piecesRemoved = 0;
						for (int i = 0; piecesRemoved < 2; i++) {
							if (board.getBoard()[selectedRow][i].isActive()) {
								board.getBoard()[selectedRow][i].setActive(false);
								piecesRemoved++;
							}
						}

					} else if (countsTotal == 3 && lastPieceWins) {
						int selectedRow = -1;
						for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
							if (counts.get(i) / 2 > 0)
								selectedRow = i;
						}

						int piecesRemoved = 0;
						for (int i = 0; piecesRemoved < 1; i++) {
							if (board.getBoard()[selectedRow][i].isActive()) {
								board.getBoard()[selectedRow][i].setActive(false);
								piecesRemoved++;
							}
						}

					} else {
						int selectedRow = -1;
						for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
							if (counts.get(i) / 2 > 0)
								selectedRow = i;
						}

						int piecesRemoved = 0;
						for (int i = 0; piecesRemoved < 1; i++) {
							if (board.getBoard()[selectedRow][i].isActive()) {
								board.getBoard()[selectedRow][i].setActive(false);
								piecesRemoved++;
							}
						}

					}
				}

				else {
					int selectedRow = -1;
					for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
						if (counts.get(i) % 2 > 0) {
							selectedRow = i;
						}
					}

					int piecesRemoved = 0;
					for (int i = 0; piecesRemoved < 1; i++) {
						if (board.getBoard()[selectedRow][i].isActive()) {
							board.getBoard()[selectedRow][i].setActive(false);
							piecesRemoved++;
						}
					}

				}
			}

			// removeTwo
			else if (removeTwo) {
				// +removeFour
				if (removeFour) {
					int selectedRow = -1;
					for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
						if (counts.get(i) / 4 > 0)
							selectedRow = i;
					}

					int piecesRemoved = 0;
					for (int i = 0; piecesRemoved < 2; i++) {
						if (board.getBoard()[selectedRow][i].isActive()) {
							board.getBoard()[selectedRow][i].setActive(false);
							piecesRemoved++;
						}
					}
				} else if (countsTotal == 2) {
					if (lastPieceWins) {
						int selectedRow = -1;
						for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
							if (counts.get(i) / 2 > 0)
								selectedRow = i;
						}

						int piecesRemoved = 0;
						for (int i = 0; piecesRemoved < 2; i++) {
							if (board.getBoard()[selectedRow][i].isActive()) {
								board.getBoard()[selectedRow][i].setActive(false);
								piecesRemoved++;
							}
						}
					} else {
						int selectedRow = -1;
						for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
							if (counts.get(i) / 2 > 0)
								selectedRow = i;
						}

						int piecesRemoved = 0;
						for (int i = 0; piecesRemoved < 1; i++) {
							if (board.getBoard()[selectedRow][i].isActive()) {
								board.getBoard()[selectedRow][i].setActive(false);
								piecesRemoved++;
							}
						}
					}
				}

				else {
					int selectedRow = -1;
					for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
						if (counts.get(i) / 2 > 0 && (counts.get(i) / 2) % 2 == 1)
							selectedRow = i;
					}

					int piecesRemoved = 0;
					for (int i = 0; piecesRemoved < 2; i++) {
						if (board.getBoard()[selectedRow][i].isActive()) {
							board.getBoard()[selectedRow][i].setActive(false);
							piecesRemoved++;
						}
					}
				}
			}
			// removeFour
			else if (removeFour) {
				if (countsTotal == 4) {
					if (lastPieceWins) {
						int selectedRow = -1;
						for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
							if (counts.get(i) / 4 > 0)
								selectedRow = i;
						}

						int piecesRemoved = 0;
						for (int i = 0; piecesRemoved < 4; i++) {
							if (board.getBoard()[selectedRow][i].isActive()) {
								board.getBoard()[selectedRow][i].setActive(false);
								piecesRemoved++;
							}
						}
					} else {
						int selectedRow = -1;
						for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
							if (counts.get(i) / 4 > 0)
								selectedRow = i;
						}

						int piecesRemoved = 0;
						for (int i = 0; piecesRemoved < 3; i++) {
							if (board.getBoard()[selectedRow][i].isActive()) {
								board.getBoard()[selectedRow][i].setActive(false);
								piecesRemoved++;
							}
						}
					}
				} else {
					int selectedRow = -1;
					for (int i = 0; i < counts.size() && selectedRow < 0; i++) {
						if (counts.get(i) / 4 > 0)
							selectedRow = i;
					}

					int piecesRemoved = 0;
					for (int i = 0; piecesRemoved < 4; i++) {
						if (board.getBoard()[selectedRow][i].isActive()) {
							board.getBoard()[selectedRow][i].setActive(false);
							piecesRemoved++;
						}
					}
				}
			}
		}

	}

	public boolean checkBoard(Board board) {
		int count = 0;
		for (int i = 0; i < board.getBoard().length; i++) {
			for (int j = 0; j < board.getBoard()[i].length; j++) {
				if (board.getBoard()[i][j].isActive()) {
					count++;
				}
			}
		}

		if (count > 1) {
			return false;
		} else {
			return true;
		}
	}

	public Board makeNewBoard() {
		Board board;
		if (selectedDifficulty == Difficulty.EASY) {
			int[] pileNumber = { 3, 3 };
			board = new Board(pileNumber);
		} else if (selectedDifficulty == Difficulty.MEDIUM) {
			int[] pileNumber = { 2, 5, 7 };
			board = new Board(pileNumber);
		} else {
			int[] pileNumber = { 2, 3, 8, 9 };
			board = new Board(pileNumber);
		}

		return board;
	}

}
