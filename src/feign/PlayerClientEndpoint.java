package feign;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import dto.PlayerDto;

@ApplicationScoped
public class PlayerClientEndpoint implements IPlayerClientEndpoint {
	
	Client client = ClientBuilder.newClient();
	URI uri = URI.create("http://localhost:8080/HangmanServer/ep/players");
	WebTarget webTarget = client.target(uri);
	Gson g = new Gson();
	
	public PlayerClientEndpoint() {
		System.out.println("PlayerClientEndpoint created");
	}
	 
//	public boolean createGameReturnTrueIfGuess(String playerName, String opponentName) {
//		
//		System.out.println("HangmanClientEndpoint::createGameReturnTrueIfGuess");
//		WebTarget target = webTarget.path("game/"+playerName+"/"+opponentName);
//				
//		String response = target.request()
//				.accept(MediaType.TEXT_PLAIN).get(String.class);
//			
//		System.out.println("HangmanClientEndpoint::createGameReturnTrueIfGuess response = "+response);
//		
//		return "guess".equals(response);
//	}
//	
	public PlayerDto getPlayerByName(String playerName) {
		System.out.println("HangmanClientEndpoint::getPlayerByName > "+playerName);
		WebTarget target = webTarget.path("byName/"+playerName);
		String response = target.request()
				.accept(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println("HangmanClientEndpoint::getPlayer response = "+response);
		PlayerDto p = g.fromJson(response, PlayerDto.class);
		return p;
	}
	
	public PlayerDto getPlayerById(long playerId) {
		System.out.println("HangmanClientEndpoint::getPlayerById > "+playerId);
		WebTarget target = webTarget.path("byId/"+playerId);
		String response = target.request()
				.accept(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println("HangmanClientEndpoint::getPlayer response = "+response);		
		PlayerDto p = g.fromJson(response, PlayerDto.class);
		System.out.println("HangmanClientEndpoint::getPlayer response = "+p.getName());
		
		return p;
	}
	


}
