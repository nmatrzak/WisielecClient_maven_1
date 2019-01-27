package dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

import utils.WordCodeDecode;

/**
 * Obiekt klasy PlayeDto sluży do przenoszenie danych gry pomiedzy serwisami
 * (client server) Klasa implementuje interface Serializable (nie zawiera on
 * żadnych metod)./ The object of GameDto class carries game data between
 * processes (client and server) The class implements a tag interface
 * Serializable.This interface dosn't contain any method.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
// klasa może być mapowana na dokument XML/the class can be mapped to an XML document
@XmlRootElement
public class PlayerDto implements Serializable {

	/** stala/The Constant serialVersionUID. */
	private static final long serialVersionUID = 7526471155622776147L;

	/** nr id gracza/The player id. */
	private long playerId = -1;

	/** nazwa gracza/The name. */
	private String name = "";

	/** liczba punktow/The points. */
	private long points = 0;

	/** liczba zwyciestw/The count wins. */
	private long countWins = 0;

	/** liczba porazek/The count losts. */
	private long countLosts = 0;

	/** status gracza/The status. */
	private String status;

	/**
	 * Konstruktor klasy PlayerDto/Instantiates a new PlayerDto.
	 */
	public PlayerDto() {
	}

	/**
	 * Konstruktor klasy PlayerDto/Instantiates a new PlayerDto.
	 *
	 * @param playerId   - nr id gracza/the player id
	 * @param name       - nazwa gracza/the name
	 * @param points     - liczba punktow/the points
	 * @param countWins  - liczba zwyciestw/the count wins
	 * @param countLosts - liczba porazek/the count losts
	 * @param status     - status gracza/the status
	 */
	public PlayerDto(long playerId, String name, long points, long countWins, long countLosts, String status) {
		this.playerId = playerId;
		this.name = name;
		this.points = points;
		this.countWins = countWins;
		this.countLosts = countLosts;
		this.status = status;
	}

	/**
	 * Pobiera nr id gracza/Gets the player id.
	 *
	 * @return nr id gracza/the player id
	 */
	public long getPlayerId() {
		return playerId;
	}

	/**
	 * ustawia nr id gracza/Sets the player id.
	 *
	 * @param playerId - nowy nr id gracza/the new player id
	 */
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	/**
	 * pobiera nazwe gracza/Gets the player name.
	 *
	 * @return nazwa gracza/the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Ustawia nazwe gracza/Sets the name.
	 *
	 * @param name - nowa nazwa gracza / the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * pobiera liczbe punktow/ Gets the points.
	 *
	 * @return liczba punktow/the points
	 */
	public long getPoints() {
		return points;
	}

	/**
	 * ustawia liczbe punktow/Sets the points.
	 *
	 * @param points - nowa liczba punktow/the new points
	 */
	public void setPoints(long points) {
		this.points = points;
	}

	/**
	 * pobiera liczbe zwyciestw/ Gets the count wins.
	 *
	 * @return liczba zwyciestw/the count wins
	 */
	public long getCountWins() {
		return countWins;
	}

	/**
	 * ustawia liczbe zwyciestw/Sets the count wins.
	 *
	 * @param countWins - nowa liczba zwyciestw/the new count wins
	 */
	public void setCountWins(long countWins) {
		this.countWins = countWins;
	}

	/**
	 * pobiera liczbe porazek/Gets the count losts.
	 *
	 * @return liczba porazek/the count losts
	 */
	public long getCountLosts() {
		return countLosts;
	}

	/**
	 * ustawia liczbe porazek/Sets the count losts.
	 *
	 * @param countLosts - liczba porazek/the new count losts
	 */
	public void setCountLosts(long countLosts) {
		this.countLosts = countLosts;
	}

	/**
	 * pobiera status gracza/ Gets the player status.
	 *
	 * @return status gracza/the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * ustawia status gracza/ Sets the status.
	 *
	 * @param status - nowy status gracza/the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * metoda konwertujaca podana nazwe na polskie znaki diakrytyczne/a method that
	 * converts the given names to Polish diacritics
	 */
	public void convert() {
		this.name = WordCodeDecode.decodeWordWithSpecsToPolishWord(this.name);
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("id: %d > name: %s ", playerId, name);
	}

}
