package feign;

//import dto.GameDto;
import dto.PlayerDto;

/**
 * Interfejs PlayerClientEndpoint/The Interface IPlayerClientEndpoint.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
public interface IPlayerClientEndpoint {

	/**
	 * pobiera gracza wedlug nazwy/Gets the player by name.
	 *
	 * @param playerName - nazwa gracza/the player name
	 * @return nazwe gracza wedlug imienia/the player by name
	 */
	PlayerDto getPlayerByName(String playerName);

	/**
	 * pobiera nazwe gracza po nr id/Gets the player by id.
	 *
	 * @param playerId - nr id gracza/the player id
	 * @return nazwa gracza wedlug nr id/the player by id
	 */
	PlayerDto getPlayerById(long playerId);

}
