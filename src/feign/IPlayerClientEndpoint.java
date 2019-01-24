package feign;

import dto.GameDto;
import dto.PlayerDto;

public interface IPlayerClientEndpoint {
	
	PlayerDto getPlayerByName(String playerName);
	PlayerDto getPlayerById(long playerId);
		
}
