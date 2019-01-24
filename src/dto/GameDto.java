package dto;

import java.io.Serializable;

import utils.WordCodeDecode;

public class GameDto implements Serializable {
	
	private static final long serialVersionUID = 75264711556227767L;
	
	private String theWord;
	private String gappedWord;	
	private int countMissed = 0;
	private String usedLetters;	
	private String gameStatus;
	private String winner;
	private String playerWordName;
	private String playerGuessName;	
	
	public GameDto() {		
	}

	public GameDto(String theWord, String gappedWord, int countMissed, String usedLetters, String gameStatus, String winner) {		
		this.theWord = theWord;
		this.gappedWord = gappedWord;
		this.countMissed = countMissed;
		this.usedLetters = usedLetters;
		this.gameStatus = gameStatus;
		this.winner = gameStatus;
	}

	public String getTheWord() {
		return theWord;
	}

	public void setTheWord(String theWord) {
		this.theWord = theWord;
	}

	public String getGappedWord() {
		return gappedWord;
	}

	public void setGappedWord(String gappedWord) {
		this.gappedWord = gappedWord;
	}

	public int getCountMissed() {
		return countMissed;
	}

	public void setCountMissed(int countMissed) {
		this.countMissed = countMissed;
	}

	public String getUsedLetters() {
		return usedLetters;
	}

	public void setUsedLetters(String usedLetters) {
		this.usedLetters = usedLetters;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}
	
	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}	
		
	public String getPlayerWordName() {
		return playerWordName;
	}

	public void setPlayerWordName(String playerWordName) {
		this.playerWordName = playerWordName;
	}

	public String getPlayerGuessName() {
		return playerGuessName;
	}

	public void setPlayerGuessName(String playerGuessName) {
		this.playerGuessName = playerGuessName;
	}

	public void convert() {
		this.theWord = WordCodeDecode.decodeWordWithSpecsToPolishWord(this.theWord);
		this.usedLetters = WordCodeDecode.decodeWordWithSpecsToPolishWord(this.usedLetters);
		this.winner = WordCodeDecode.decodeWordWithSpecsToPolishWord(this.winner);
		this.playerWordName = WordCodeDecode.decodeWordWithSpecsToPolishWord(this.playerWordName);
		this.playerGuessName = WordCodeDecode.decodeWordWithSpecsToPolishWord(this.playerGuessName);
	}
}
