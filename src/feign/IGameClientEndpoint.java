package feign;

import dto.GameDto;

public interface IGameClientEndpoint {
	
//	boolean createGameReturnTrueIfGuess(String playerName, String opponentName);
//	GameDto updateWord(String playerName, String word);
//	GameDto sendLetter(String playerName, String letter);
//	GameDto getGame(String playerName);
	
	boolean createGameReturnTrueIfGuess(long playerId, long opponentId);
	GameDto updateWord(long playerId, String word);
	GameDto sendLetter(long playerId, String letter);
	GameDto getGame(long playerId);
//	void endGame(long playerId);

}
