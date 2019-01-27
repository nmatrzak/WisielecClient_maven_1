package servlet;

import static java.util.Optional.ofNullable;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.GameDto;
import dto.PlayerDto;
import feign.IGameClientEndpoint;
import feign.IPlayerClientEndpoint;
import utils.WordCodeDecode;

/**
 * klasa obsługuje wszystkie żądania i odpowiedzi HTTP/ the class supports all
 * HTTP requests and responses
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */

public class PageDispacherServlet extends HttpServlet {

	/** stala/The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * stala_operacji_ustawienia_nazwy_gracza The Constant
	 * OPERATION_SET_PLAYER_NAME.
	 */
	private static final String OPERATION_SET_PLAYER_NAME = "setPlayerName";

	/** stala_operacji_graj_rozgrywke/The Constant OPERATION_START_GAME. */
	private static final String OPERATION_START_GAME = "playGame";

	/** stala_operacji_rozlacz/The Constant OPERATION_DISCONNECT. */
	private static final String OPERATION_DISCONNECT = "disconnect";

	/** stala_operacji_wyslij_litere/The Constant OPERATION_SEND_LETTER. */
	private static final String OPERATION_SEND_LETTER = "letter";

	/** stala_operacji_uaktualnij_slowo/The Constant OPERATION_UPDATE_WORD. */
	// private static final String OPERATION_WORD_UPDATED = "word_updated";
	private static final String OPERATION_UPDATE_WORD = "update_word";

	/** stala_operacji_idz_do_strony/The Constant OPERATION_GOTO_PAGE. */
	private static final String OPERATION_GOTO_PAGE = "goto_page";
//	private static final String OPERATION_END_GAME = "end_game";

	/** stala_operacji_strona_indeksu/The Constant PAGE_INDEX. */
	private static final String PAGE_INDEX = "index";

	/** stala_operacji_strona_listy/The Constant PAGE_LIST. */
	private static final String PAGE_LIST = "list";

	/** stala_operacji_strona_slowa/ The Constant PAGE_WORD. */
	private static final String PAGE_WORD = "word";

	/** stala_operacji_strona_zgadywania/The Constant PAGE_GUESS. */
	private static final String PAGE_GUESS = "guess";

	/**
	 * odwolanie do atrybutu HTML o nazwie:"usernmame"/reference to the HTML
	 * attribute named:"username"
	 */
	private static final String ATTR_NAME_PLAYERNAME = "username";

	/**
	 * odwolanie do atrybutu HTML o nazwie:"player"/reference to the HTML attribute
	 * named:"player"
	 */
	private static final String ATTR_NAME_PLAYER = "player";

	/**
	 * odwolanie do atrybutu HTML o nazwie:"game"/reference to the HTML attribute
	 * named:"game"
	 */
	private static final String ATTR_NAME_GAME = "game";

	/** interfejs/ interface IPlayerClientEndpoint. */
	// wstrzykniecie dostepu do interfejsu /inject interface access
	@Inject
	private IPlayerClientEndpoint playerClientEndpoint;

	/** interfejs/ interface IGameClientEndpoint. */
	/// wstrzykniecie dostepu do interfejsu /inject interface access
	@Inject
	private IGameClientEndpoint gameClientEndpoint;

	/*
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("HttpServlet::doPost");

		String operation = request.getParameter("operation");
		String data = decode(request.getParameter("data"));
		String username = request.getParameter("username");

		String nextPage = currentPageJsp(request);
		boolean setGameAttr = true;
		boolean setPlayerAttr = true;
		long playerId = getPlayerId(request);
		System.out.println(String.format("operation: %s, data: %s, username: %s", operation, data, username));

		if (OPERATION_SET_PLAYER_NAME.equals(operation)) {
			username = decode(data);
			System.out.println("HttpServlet::doPost > OPERATION_SET_PLAYER_NAME: " + username);
			request.setAttribute(ATTR_NAME_PLAYERNAME, username);
			playerId = getPlayerId(request, username);
			setPlayerAttr = false;
			nextPage = pageJsp(PAGE_LIST);
		} else {
			request.setAttribute(ATTR_NAME_PLAYERNAME, getUserName(request));
			if (OPERATION_DISCONNECT.equals(operation)) {
				request.setAttribute(ATTR_NAME_PLAYERNAME, null);
				request.setAttribute(ATTR_NAME_GAME, null);
				setGameAttr = false;
				setPlayerAttr = false;
				nextPage = pageJsp(PAGE_INDEX);
			} else if (OPERATION_START_GAME.equals(operation)) {
				nextPage = pageJsp(getPlayerPage(playerId, Long.valueOf(data)));
			} else if (OPERATION_SEND_LETTER.equals(operation)) {
				sendLetter(request, playerId, data);
				setGameAttr = false;
			} else if (OPERATION_UPDATE_WORD.equals(operation)) {
				updateWord(request, playerId, data);
				setGameAttr = false;
			} else if (OPERATION_GOTO_PAGE.equals(operation)) {

				nextPage = pageJsp(data);
			}
		
		}

		if (playerId != -1) {
			if (setPlayerAttr) {
				setPlayerAttribute(request, playerId);
			}
			if (setGameAttr) {
				setGameAttribute(request, playerId);
			}
		}

		forwardToPage(nextPage, request, response);
	}

	/**
	 * dekoduje slowo z polsimi znakami/ decodes the word with polsimi characters
	 *
	 * @param data slowo/the word
	 * @return slowo z polskimi znakami/word with polsimi characters
	 */
	private String decode(String data) {
		if (data != null && !data.isEmpty()) {
			return WordCodeDecode.decode(WordCodeDecode.decodeWordWithSpecsToPolishWord(data));
		}
		return "";
	}

	/**
	 * pobranie nr id gracza/Gets the player id.
	 *
	 * @param request zdanie/the request
	 * @return nr id gracza/the player id
	 */
	private long getPlayerId(HttpServletRequest request) {
		return (request.getParameter("playerId") != null) ? Long.valueOf(request.getParameter("playerId")) : -1;
	}

	/**
	 * pobranie nr id gracza/Gets the player id.
	 *
	 * @param request  - zadaniethe request
	 * @param username - nazwa uzykownika/the username
	 * @return nr id gracza/the player id
	 */
	private long getPlayerId(HttpServletRequest request, String username) {
		PlayerDto pdto = playerClientEndpoint.getPlayerByName(username);
		if (pdto != null) {
			request.setAttribute(ATTR_NAME_PLAYER, pdto);
			return pdto.getPlayerId();
		} else
			return -1;
	}

	/**
	 * pobranie nazwy uzytkownika/Gets the user name.
	 *
	 * @param request zdanie/the request
	 * @return nazwa uzykownika/the user name
	 */
	private String getUserName(HttpServletRequest request) {
		return decode(request.getParameter("username"));
	}

	/**
	 * ustawienie atrybutow gracza/Sets the player attribute.
	 *
	 * @param request  - zadaniethe request
	 * @param playerId - nr id gracza/the player id
	 */
	private void setPlayerAttribute(HttpServletRequest request, long playerId) {
		System.out.println("HttpServlet::setPlayerAttribute");
		PlayerDto pdto = playerClientEndpoint.getPlayerById(playerId);
		if (pdto != null) {
			System.out.println("setPlayerAttribute:PlayerDto = " + pdto);
			request.setAttribute(ATTR_NAME_PLAYER, pdto);
		}
	}

	/**
	 * ustawienie atrybutow gry/Sets the game attribute.
	 *
	 * @param request  - zadanie/the request
	 * @param playerId - nr id gracza/the player id
	 */
	private void setGameAttribute(HttpServletRequest request, long playerId) {
		System.out.println("HttpServlet::setGameAttribute");
		GameDto gdto = gameClientEndpoint.getGame(playerId);
		if (gdto != null) {
			System.out.println("setGameAttribute:GameDto = " + gdto);
			request.setAttribute(ATTR_NAME_GAME, gdto);
		}
	}

	/**
	 * przekazanie parametrow do strony/Forward parameters to page.
	 *
	 * @param pageNameJsp - nazwa strony w jsp/the page name in jsp
	 * @param request     - zadanie/the request
	 * @param response    - odpowiedz/the response
	 * @throws ServletException - wyjątek serwletu/the servlet exception
	 * @throws IOException      - Sygnalizuje wystąpienie wyjątku we-wy/Signals that
	 *                          an I/O exception has occurred.
	 */
	private void forwardToPage(String pageNameJsp, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("HttpServlet::forwardToPage > " + pageNameJsp);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(pageNameJsp);
		requestDispatcher.forward(request, response);
	}

	/**
	 * wysylanie informacji o rozpoczeciu rozgrywki/ send information about the
	 * start of the game
	 *
	 * @param playerId   - nr id gracza/the player id
	 * @param opponentId - nr id przeciwnika/the opponent id
	 * @return zwraca wartosc true, jesli zgadujesz/true, if guess
	 */
	private boolean sendToServerPlayGameReturnIfGuess(long playerId, long opponentId) {
		System.out.println("HttpServlet::sendToServerPlayGameReturnIfGuess > " + playerId + " vs " + opponentId);
		return gameClientEndpoint.createGameReturnTrueIfGuess(playerId, opponentId);
	}

	/**
	 * pobranie strony gracza/Gets the player page.
	 *
	 * @param playerId   - nr id gracza/the player id
	 * @param opponentId - nr id przeciwnika/the opponent id
	 * @return strona gracza/the player page
	 */
	private String getPlayerPage(long playerId, long opponentId) {
		if (sendToServerPlayGameReturnIfGuess(playerId, opponentId)) {
			return PAGE_GUESS;
		} else {
			return PAGE_WORD;
		}
	}

	/**
	 * wyslij litere/Send letter.
	 *
	 * @param request  - zadanie/the request
	 * @param playerId - nr id gracza/the player id
	 * @param letter   - litera/the letter
	 */
	private void sendLetter(HttpServletRequest request, long playerId, String letter) {
		System.out.println("HttpServlet::sendLetter > " + playerId + " > " + letter);
		GameDto game = gameClientEndpoint.sendLetter(playerId, letter);
		if (game != null) {
			request.setAttribute(ATTR_NAME_GAME, game);
		}
	}


	/**
	 * uaktualnij slowo/Update word.
	 *
	 * @param request  - zadanie/the request
	 * @param playerId - nr id gracza/the player id
	 * @param word     - slowo/the word
	 */
	private void updateWord(HttpServletRequest request, long playerId, String word) {
		System.out.println("HttpServlet::sendLetter > " + playerId + " > " + word);
		GameDto game = gameClientEndpoint.updateWord(playerId, word);
		if (game != null) {
			request.setAttribute(ATTR_NAME_GAME, game);
		}
	}

	/**
	 * stona w jsp/Page in jsp.
	 *
	 * @param pageName - nazwa strony/the page name
	 * @return nazwa strony w jsp/page name in jsp
	 */
	private String pageJsp(String pageName) {
		return pageName.concat(".jsp");
	}

	/**
	 * obecan strona w jsp/Current page in jsp.
	 *
	 * @param request -zadanie/the request
	 * @return nazwa strony/page name
	 */
	private String currentPageJsp(HttpServletRequest request) {
		return pageJsp(ofNullable(request.getParameter("currentPage")).orElse(""));
	}

}
