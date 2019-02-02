package dto;

import java.io.Serializable;

import utils.WordCodeDecode;

/**
 * Obiekt klasy GameDto sluży do przenoszenie danych gry pomiedzy serwisami
 * (client server) Klasa implementuje interface Serializable (nie zawiera on
 * żadnych metod)./ The object of GameDto class carries game data between
 * processes (client and server) The class implements a tag interface
 * Serializable.This interface dosn't contain any method.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
public class GameDto implements Serializable {

	/** stala/The constant - serialVersionUID. */
	private static final long serialVersionUID = 75264711556227767L;

	/** slowo/The theWord */
	private String theWord;

	/** niepelne slowo/The gapped word. */
	private String gappedWord;

	/** liczba blednych odpowiedzi/The count missed. */
	private int countMissed = 0;

	/** uzyte litery/The used letters. */
	private String usedLetters;

	/** status gry/The game status. */
	private String gameStatus;

	/** zwyciezca/The winner. */
	private String winner;

	/** nazwa gracza tworzacego slowo/The player word name. */
	private String playerWordName;

	/** nazwa gracza zgadujacego/The player guess name. */
	private String playerGuessName;

	/**
	 * Konstruktor klasy GameDto/ Instantiates a new GameDto.
	 */
	public GameDto() {
	}

	/**
	 * Konstruktor klasy GameDto/ Instantiates a new GameDto.
	 *
	 * @param theWord     - slowo/the word
	 * @param gappedWord  - niepelne slowo/the gap in word
	 * @param countMissed - liczba blednych odpowiedzi /the count missed
	 * @param usedLetters - uzyte litery/the used letters
	 * @param gameStatus  - status gry/the game status
	 * @param winner      - zwyciezca/the winner
	 * 
	 */
	public GameDto(String theWord, String gappedWord, int countMissed, String usedLetters, String gameStatus,
			String winner) {
		this.theWord = theWord;
		this.gappedWord = gappedWord;
		this.countMissed = countMissed;
		this.usedLetters = usedLetters;
		this.gameStatus = gameStatus;
		this.winner = gameStatus;
	}

	/**
	 * Pobiera slowo/Gets the word.
	 *
	 * @return slowo/the word
	 */
	public String getTheWord() {
		return theWord;
	}

	/**
	 * Ustawia slowo/Sets the word.
	 *
	 * @param theWord - nowe slowo/the new word
	 */
	public void setTheWord(String theWord) {
		this.theWord = theWord;
	}

	/**
	 * Pobiera niepelne slowo zgadywane/Gets the gap in word.
	 *
	 * @return niepelne slowo/gapped word
	 */
	public String getGappedWord() {
		return gappedWord;
	}

	/**
	 * Ustawia niepelne slowo zgadywane/Sets the gap in word.
	 *
	 * @param gappedWord - nowa niepelne slowo zgadywane/the new gap in guess word
	 */
	public void setGappedWord(String gappedWord) {
		this.gappedWord = gappedWord;
	}

	/**
	 * Pobiera ilosc blednych prob/Gets the count missed.
	 *
	 * @return liczba bledow/countMissed
	 */
	public int getCountMissed() {
		return countMissed;
	}

	/**
	 * Ustawia ilosc blednych prob/Sets the count missed.
	 *
	 * @param countMissed - nowa liczba prob/the new count missed
	 */
	public void setCountMissed(int countMissed) {
		this.countMissed = countMissed;
	}

	/**
	 * Pobiera uzyte litery/ Gets the used letters.
	 *
	 * @return uzyte litery/the used letters
	 */
	public String getUsedLetters() {
		return usedLetters;
	}

	/**
	 * Ustawia uzyte litery/Sets the used letters.
	 *
	 * @param usedLetters - nowy ciag liter/the new used letters
	 */
	public void setUsedLetters(String usedLetters) {
		this.usedLetters = usedLetters;
	}

	/**
	 * Pobiera stautus gry/Gets the game status.
	 *
	 * @return status gry/game status
	 */
	public String getGameStatus() {
		return gameStatus;
	}

	/**
	 * Ustawia status gry/Sets the game status.
	 *
	 * @param gameStatus - nowy status gry/the new game status
	 */
	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	/**
	 * Pobiera nazwe zwyciezcy/Gets the winner.
	 *
	 * @return zwyciezca/winner
	 */
	public String getWinner() {
		return winner;
	}

	/**
	 * Ustawia imie zwyciezcy/Sets the winner.
	 *
	 * @param winner - nowa nazwa zwyciezcy/the new winner
	 */
	public void setWinner(String winner) {
		this.winner = winner;
	}

	/**
	 * Pobiera nazwe gracza wprowadzajacego wyraz do zgadniecia/Gets the player name
	 * who write the word
	 *
	 * @return gracz wprowadzajacy slowo/player insert word name
	 */
	public String getPlayerWordName() {
		return playerWordName;
	}

	/**
	 * Ustawia nazwe gracza wprowadzajacego wyraz do zgadniecia/ Sets the player
	 * name who write the word
	 *
	 * @param playerWordName - imie gracza/the new player one name
	 */

	public void setPlayerWordName(String playerWordName) {
		this.playerWordName = playerWordName;
	}

	/**
	 * Pobiera nazwe gracza zgadujacego/Gets the player name who guess the word
	 *
	 * @return nazwa gracza zgadujacego slowo/name of player guess word
	 */
	public String getPlayerGuessName() {
		return playerGuessName;
	}

	/**
	 * Ustawia imie gracza zgadujacego/Sets the player name who guess the word
	 *
	 * @param playerGuessName - imie gracza zgadujacego/the new player two name
	 */
	public void setPlayerGuessName(String playerGuessName) {
		this.playerGuessName = playerGuessName;
	}

	/**
	 * metoda konwertujaca podane slowa (nazwy) na polskie znaki diakrytyczne/a
	 * method that converts the given words (names) to Polish diacritics
	 */
	public void convert() {
		this.theWord = WordCodeDecode.decodeWordWithSpecsToPolishWord(this.theWord);
		this.usedLetters = WordCodeDecode.decodeWordWithSpecsToPolishWord(this.usedLetters);
		this.winner = WordCodeDecode.decodeWordWithSpecsToPolishWord(this.winner);
		this.playerWordName = WordCodeDecode.decodeWordWithSpecsToPolishWord(this.playerWordName);
		this.playerGuessName = WordCodeDecode.decodeWordWithSpecsToPolishWord(this.playerGuessName);
	}
}
