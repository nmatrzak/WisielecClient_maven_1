package feign;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import dto.PlayerDto;

/**
 * Punkt dostępowy do webservice /Access point to webservice.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
@ApplicationScoped
public class PlayerClientEndpoint implements IPlayerClientEndpoint {

	/** obiekt klasy Client/The object of Client class. */
	Client client = ClientBuilder.newClient();

	/** adres URi localhost'a/ localhost adress */
	URI uri = URI.create("http://localhost:8080/HangmanServer/ep/players");

	/** obiekt klasy WebTarget/The object of WebTarget class. */
	WebTarget webTarget = client.target(uri);

	/** obiekt klasy Gson/The object of Gson class */
	Gson g = new Gson();

	/**
	 * Instantiates a new player client endpoint.
	 */
	public PlayerClientEndpoint() {
		System.out.println("PlayerClientEndpoint created");
	}

	/*
	 * @see feign.IPlayerClientEndpoint#getPlayerByName(java.lang.String)
	 */

	public PlayerDto getPlayerByName(String playerName) {
		System.out.println("HangmanClientEndpoint::getPlayerByName > " + playerName);
		WebTarget target = webTarget.path("byName/" + playerName);
		String response = target.request().accept(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println("HangmanClientEndpoint::getPlayer response = " + response);
		PlayerDto p = g.fromJson(response, PlayerDto.class);
		return p;
	}

	/*
	 * @see feign.IPlayerClientEndpoint#getPlayerById(long)
	 */
	public PlayerDto getPlayerById(long playerId) {
		System.out.println("HangmanClientEndpoint::getPlayerById > " + playerId);
		WebTarget target = webTarget.path("byId/" + playerId);
		String response = target.request().accept(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println("HangmanClientEndpoint::getPlayer response = " + response);
		PlayerDto p = g.fromJson(response, PlayerDto.class);
		System.out.println("HangmanClientEndpoint::getPlayer response = " + p.getName());

		return p;
	}

}
