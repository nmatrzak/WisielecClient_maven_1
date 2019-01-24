package utils;

import java.util.Arrays;
import java.util.List;

public class WordCodeDecode {

	private final static String SEP = "@";
	private final static String specPolishChars = "ĄĆĘŁŃÓŚŹŻąćęłńóśźż";

	private final static List<Integer> coded = Arrays.asList(260, 262, 280, 321, 323, 0, 346, 379, 377, 261, 263, 281,
			322, 324, 0, 347, 380);

	public static String decode(String word) {
		if (word != null && !word.isEmpty()) {
			for (int i = 0; i < coded.size(); i++) {
				word = word.replace("&#" + coded.get(i) + ";", String.valueOf(specPolishChars.charAt(i)));
			}
		}
		return word;
	}

	public static String code(String word) {
		if (word != null && !word.isEmpty()) {
			for (int i = 0; i < coded.size(); i++) {
				word = word.replace(String.valueOf(specPolishChars.charAt(i)), "&#" + coded.get(i) + ";");
			}
		}
		return word;
	}

	private static String charToSpec(char c) {
		for (int i = 0; i < specPolishChars.length(); i++) {
			if (c == specPolishChars.charAt(i)) {
				return SEP + i + SEP;
			}
		}
		return String.valueOf(c);
	}

	public static String codePolishWordToWordWithSpecs(String word) {
		if (word != null && !word.isEmpty()) {
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < word.length(); i++) {
				result.append(charToSpec(word.charAt(i)));
			}

			return result.toString();
		}
		return word;
	}

	public static String decodeWordWithSpecsToPolishWord(String word) {
		if (word != null && !word.isEmpty()) {
			for (int i = 0; i < specPolishChars.length(); i++) {
				word = word.replace(SEP + i + SEP, specPolishChars.substring(i, i + 1));
			}
		}
		return word;
	}

}
