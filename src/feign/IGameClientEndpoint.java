package feign;

import dto.GameDto;

/**
 * Interfejs GameClientEndpoint/The Interface IGameClientEndpoint.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
public interface IGameClientEndpoint {

//	boolean createGameReturnTrueIfGuess(String playerName, String opponentName);
//	GameDto updateWord(String playerName, String word);
//	GameDto sendLetter(String playerName, String letter);
//	GameDto getGame(String playerName);

	/**
	 * tworzy grę/Creates the game
	 *
	 * @param playerId   - id gracza/the player id
	 * @param opponentId - id przeciwnika/the opponent id
	 * @return zwraca true jesli zgadujesz/return true if guess
	 */
	boolean createGameReturnTrueIfGuess(long playerId, long opponentId);

	/**
	 * uaktualnienia slowo/ Update word.
	 *
	 * @param playerId - nr id gracza/the player id
	 * @param word     - slowo/ the word
	 * @return objekt GameDto/ the object GameDto
	 */
	GameDto updateWord(long playerId, String word);

	/**
	 * przesylanie litery/ Send letter.
	 *
	 * @param playerId - nr id gracza/the player id
	 * @param letter   - litera/the letter
	 * @return objekt GameDto/ the object GameDto
	 */
	GameDto sendLetter(long playerId, String letter);

	/**
	 * pobiera gre/Gets the game.
	 *
	 * @param playerId - nr id gracza/the player id
	 * @return objekt GameDto/ the object GameDto
	 */
	GameDto getGame(long playerId);
//	void endGame(long playerId);

}
