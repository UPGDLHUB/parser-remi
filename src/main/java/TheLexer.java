import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Lexer class to analyze the input file
 * This one is an initial version that uses a DFA to recognize binary numbers
 *
 * @author Regina Ochoa Gispert
 * @author Renata Calderon Mercado
 * @author Miguel Angel Velez Olide
 * @version 0.1
 */
public class TheLexer {
	
	private File file;
	private Automata dfa;
	private Vector<TheToken> tokens;
	
	public TheLexer(File file) {
		this.file = file;
		tokens = new Vector<>();
		dfa = new Automata();
		dfa.addTransition("S0", "0", "I1 Integer");

		dfa.addTransition("S0", "&", "O&");
		dfa.addTransition("O&", "&", "O&&");

		dfa.addTransition("S0", "|", "O|");
		dfa.addTransition("O|", "|", "O||");

		dfa.addTransition("I1 Integer", "b", "BS1");
		dfa.addTransition("I1 Integer", "B", "BS1");

		dfa.addTransition("BS1", "0", "BS2 Binary");
		dfa.addTransition("BS1", "1", "BS2 Binary");

		dfa.addTransition("BS2 Binary", "0", "BS2 Binary");
		dfa.addTransition("BS2 Binary", "1", "BS2 Binary");

		dfa.addTransition("I1 Integer", "0", "OS1 Octal");
		dfa.addTransition("I1 Integer", "1", "OS1 Octal");
		dfa.addTransition("I1 Integer", "2", "OS1 Octal");
		dfa.addTransition("I1 Integer", "3", "OS1 Octal");
		dfa.addTransition("I1 Integer", "4", "OS1 Octal");
		dfa.addTransition("I1 Integer", "5", "OS1 Octal");
		dfa.addTransition("I1 Integer", "6", "OS1 Octal");
		dfa.addTransition("I1 Integer", "7", "OS1 Octal");

		dfa.addTransition("OS1 Octal", "0", "OS1 Octal");
		dfa.addTransition("OS1 Octal", "1", "OS1 Octal");
		dfa.addTransition("OS1 Octal", "2", "OS1 Octal");
		dfa.addTransition("OS1 Octal", "3", "OS1 Octal");
		dfa.addTransition("OS1 Octal", "4", "OS1 Octal");
		dfa.addTransition("OS1 Octal", "5", "OS1 Octal");
		dfa.addTransition("OS1 Octal", "6", "OS1 Octal");
		dfa.addTransition("OS1 Octal", "7", "OS1 Octal");

		dfa.addTransition("I1 Integer", "x", "HS1");
		dfa.addTransition("I1 Integer", "X", "HS1");

		dfa.addTransition("HS1", "0", "HS2 Hexa");
		dfa.addTransition("HS1", "1", "HS2 Hexa");
		dfa.addTransition("HS1", "2", "HS2 Hexa");
		dfa.addTransition("HS1", "3", "HS2 Hexa");
		dfa.addTransition("HS1", "4", "HS2 Hexa");
		dfa.addTransition("HS1", "5", "HS2 Hexa");
		dfa.addTransition("HS1", "6", "HS2 Hexa");
		dfa.addTransition("HS1", "7", "HS2 Hexa");
		dfa.addTransition("HS1", "8", "HS2 Hexa");
		dfa.addTransition("HS1", "9", "HS2 Hexa");
		dfa.addTransition("HS1", "a", "HS2 Hexa");
		dfa.addTransition("HS1", "b", "HS2 Hexa");
		dfa.addTransition("HS1", "c", "HS2 Hexa");
		dfa.addTransition("HS1", "d", "HS2 Hexa");
		dfa.addTransition("HS1", "e", "HS2 Hexa");
		dfa.addTransition("HS1", "f", "HS2 Hexa");
		dfa.addTransition("HS1", "A", "HS2 Hexa");
		dfa.addTransition("HS1", "B", "HS2 Hexa");
		dfa.addTransition("HS1", "C", "HS2 Hexa");
		dfa.addTransition("HS1", "D", "HS2 Hexa");
		dfa.addTransition("HS1", "E", "HS2 Hexa");
		dfa.addTransition("HS1", "F", "HS2 Hexa");

		dfa.addTransition("HS2 Hexa", "0", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "1", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "2", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "3", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "4", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "5", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "6", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "7", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "8", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "9", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "a", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "b", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "c", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "d", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "e", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "f", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "A", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "B", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "C", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "D", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "E", "HS2 Hexa");
		dfa.addTransition("HS2 Hexa", "F", "HS2 Hexa");

		dfa.addTransition("S0", "a", "IDS2 Identifier");
		dfa.addTransition("S0", "b", "Bool1 KW");
		dfa.addTransition("S0", "c", "Char1 KW");
		dfa.addTransition("S0", "d", "Double1 KW");
		dfa.addTransition("S0", "e", "IDS2 Identifier");
		dfa.addTransition("S0", "f", "Flofor KW");
		dfa.addTransition("S0", "g", "IDS2 Identifier");
		dfa.addTransition("S0", "h", "IDS2 Identifier");
		dfa.addTransition("S0", "i", "InIf KW");
		dfa.addTransition("S0", "j", "IDS2 Identifier");
		dfa.addTransition("S0", "k", "IDS2 Identifier");
		dfa.addTransition("S0", "l", "Long1 KW");
		dfa.addTransition("S0", "m", "IDS2 Identifier");
		dfa.addTransition("S0", "n", "IDS2 Identifier");
		dfa.addTransition("S0", "ñ", "IDS2 Identifier");
		dfa.addTransition("S0", "o", "IDS2 Identifier");
		dfa.addTransition("S0", "p", "IDS2 Identifier");
		dfa.addTransition("S0", "q", "IDS2 Identifier");
		dfa.addTransition("S0", "r", "IDS2 Identifier");
		dfa.addTransition("S0", "s", "String1 KW");
		dfa.addTransition("S0", "t", "IDS2 Identifier");
		dfa.addTransition("S0", "u", "IDS2 Identifier");
		dfa.addTransition("S0", "v", "Void1 KW");
		dfa.addTransition("S0", "w", "While1 KW");
		dfa.addTransition("S0", "x", "IDS2 Identifier");
		dfa.addTransition("S0", "y", "IDS2 Identifier");
		dfa.addTransition("S0", "z", "IDS2 Identifier");
		dfa.addTransition("S0", "A", "IDS2 Identifier");
		dfa.addTransition("S0", "B", "IDS2 Identifier");
		dfa.addTransition("S0", "C", "IDS2 Identifier");
		dfa.addTransition("S0", "D", "IDS2 Identifier");
		dfa.addTransition("S0", "E", "IDS2 Identifier");
		dfa.addTransition("S0", "F", "IDS2 Identifier");
		dfa.addTransition("S0", "G", "IDS2 Identifier");
		dfa.addTransition("S0", "H", "IDS2 Identifier");
		dfa.addTransition("S0", "I", "IDS2 Identifier");
		dfa.addTransition("S0", "J", "IDS2 Identifier");
		dfa.addTransition("S0", "K", "IDS2 Identifier");
		dfa.addTransition("S0", "L", "IDS2 Identifier");
		dfa.addTransition("S0", "M", "IDS2 Identifier");
		dfa.addTransition("S0", "N", "IDS2 Identifier");
		dfa.addTransition("S0", "Ñ", "IDS2 Identifier");
		dfa.addTransition("S0", "O", "IDS2 Identifier");
		dfa.addTransition("S0", "P", "IDS2 Identifier");
		dfa.addTransition("S0", "Q", "IDS2 Identifier");
		dfa.addTransition("S0", "R", "IDS2 Identifier");
		dfa.addTransition("S0", "S", "IDS2 Identifier");
		dfa.addTransition("S0", "T", "IDS2 Identifier");
		dfa.addTransition("S0", "U", "IDS2 Identifier");
		dfa.addTransition("S0", "V", "IDS2 Identifier");
		dfa.addTransition("S0", "W", "IDS2 Identifier");
		dfa.addTransition("S0", "X", "IDS2 Identifier");
		dfa.addTransition("S0", "Y", "IDS2 Identifier");
		dfa.addTransition("S0", "Z", "IDS2 Identifier");
		dfa.addTransition("S0", "$", "IDS2 Identifier");
		dfa.addTransition("S0", "_", "IDS2 Identifier");

		String noacept = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";

		dfa.addTransition("Bool1 KW", "o", "Bool2 KW");
		noacept = "abcdefghijklmnñpqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Bool1 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Bool2 KW", "o", "Bool3 KW");
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Bool2 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Bool3 KW", "l", "Bool4 Keyword");
		noacept = "abcdefghijkmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Bool3 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Bool4 Keyword", "e", "Boolean5 KW");
		noacept = "abcdfghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Bool4 Keyword", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Boolean5 KW", "a", "Boolean6 KW");
		noacept = "bcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Bool5 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Boolean6 KW", "n", "Boolean7 Keyword");
		noacept = "abcdefghijklmñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Bool6 KW", String.valueOf(c), "IDS2 Identifier");
		}

		dfa.addTransition("Char1 KW", "h", "Char2 KW");
		noacept = "abcdefgijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Char1 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Char2 KW", "a", "Char3 KW");
		noacept = "bcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Char2 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Char3 KW", "r", "Char4 Keyword");
		noacept = "abcdefghijklmnñopqstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Char3 KW", String.valueOf(c), "IDS2 Identifier");
		}


		dfa.addTransition("Void1 KW", "o", "Void2 KW");
		noacept = "abcdefghijklmnñpqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Void1 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Void2 KW", "i", "Void3 KW");
		noacept = "abcdefghjklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Void2 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Void3 KW", "d", "Void4 Keyword");
		noacept = "abcefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Void3 KW", String.valueOf(c), "IDS2 Identifier");
		}


		dfa.addTransition("Double1 KW", "o", "Double2 KW");
		noacept = "abcdefghijklmnñpqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Double1 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Double2 KW", "u", "Double3 KW");
		noacept = "abcdefghijklmnñopqrstvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Double2 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Double3 KW", "b", "Double4 KW");
		noacept = "acdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Double3 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Double4 KW", "l", "Double5 KW");
		noacept = "abcdefghijkmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Double4 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Double5 KW", "e", "Double6 Keyword");
		noacept = "abcdfghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Double5 KW", String.valueOf(c), "IDS2 Identifier");
		}

		dfa.addTransition("Flofor KW", "l", "Float2 KW");
		dfa.addTransition("Flofor KW", "o", "For2 KW");
		noacept = "abcdefghijkmnñpqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Flofor KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Float2 KW", "o", "Float3 KW");
		noacept = "abcdefghijklmnñpqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Float2 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Float3 KW", "a", "Float4 KW");
		noacept = "bcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Float3 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Float4 KW", "t", "Float5 Keyword");
		noacept = "abcdefghijklmnñopqrsuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Float4 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("For2 KW", "r", "For3 Keyword");
		noacept = "abcdefghijklmnñopqstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("For2 KW", String.valueOf(c), "IDS2 Identifier");
		}

		dfa.addTransition("While1 KW", "h", "While2 KW");
		noacept = "abcdefgijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("While1 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("While2 KW", "i", "While3 KW");
		noacept = "abcdefghjklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("While2 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("While3 KW", "l", "While4 KW");
		noacept = "abcdefghijkmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("While3 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("While4 KW", "e", "While5 Keyword");
		noacept = "abcdfghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("While4 KW", String.valueOf(c), "IDS2 Identifier");
		}

		dfa.addTransition("InIf KW", "n", "Int2 KW");
		dfa.addTransition("InIf KW", "f", "If KW");
		noacept = "abcdeghijklmñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("InIf KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Int2 KW", "t", "Int3 Keyword");
		noacept = "abcdefghijklmnñopqrsuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Int2 KW", String.valueOf(c), "IDS2 Identifier");
		}

		dfa.addTransition("Long1 KW", "o", "Long2 KW");
		noacept = "abcdefghijklmnñpqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Long1 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Long2 KW", "n", "Long3 KW");
		noacept = "abcdefghijklmñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Long2 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("Long3 KW", "g", "Long4 Keyword");
		noacept = "abcdefhijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Long3 KW", String.valueOf(c), "IDS2 Identifier");
		}

		dfa.addTransition("String1 KW", "t", "String2 KW");
		noacept = "abcdefghijklmnñopqrsuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("String1 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("String2 KW", "r", "String3 KW");
		noacept = "abcdefghijklmnñopqstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("String2 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("String3 KW", "i", "String4 KW");
		noacept = "abcdefghjklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("String3 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("String4 KW", "n", "String5 KW");
		noacept = "abcdefghijklmñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("String4 KW", String.valueOf(c), "IDS2 Identifier");
		}
		dfa.addTransition("String5 KW", "g", "String6 Keyword");
		noacept = "abcdefhijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("String5 KW", String.valueOf(c), "IDS2 Identifier");
		}

		noacept = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890_$";
		for (char c : noacept.toCharArray()) {
			dfa.addTransition("Boolean7 Keyword", String.valueOf(c), "IDS2 Identifier");
			dfa.addTransition("Char4 Keyword", String.valueOf(c), "IDS2 Identifier");
			dfa.addTransition("Double6 Keyword", String.valueOf(c), "IDS2 Identifier");
			dfa.addTransition("Float5 Keyword", String.valueOf(c), "IDS2 Identifier");
			dfa.addTransition("Int3 Keyword", String.valueOf(c), "IDS2 Identifier");
			dfa.addTransition("Long4 Keyword", String.valueOf(c), "IDS2 Identifier");
			dfa.addTransition("String6 Keyword", String.valueOf(c), "IDS2 Identifier");
			dfa.addTransition("While5 Keyword", String.valueOf(c), "IDS2 Identifier");
			dfa.addTransition("If KW", String.valueOf(c), "IDS2 Identifier");
			dfa.addTransition("For3 Keyword", String.valueOf(c), "IDS2 Identifier");
			dfa.addTransition("Void4 Keyword", String.valueOf(c), "IDS2 Identifier");
		}

		dfa.addTransition("IDS2 Identifier", "A", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "B", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "C", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "D", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "E", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "F", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "G", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "H", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "I", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "J", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "K", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "L", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "M", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "N", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "Ñ", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "O", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "P", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "Q", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "R", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "S", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "T", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "U", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "V", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "W", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "X", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "Y", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "Z", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "a", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "b", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "c", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "d", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "e", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "f", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "g", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "h", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "i", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "j", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "k", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "l", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "m", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "n", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "ñ", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "o", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "p", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "q", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "r", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "s", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "t", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "u", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "v", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "w", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "x", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "y", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "z", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "0", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "1", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "2", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "3", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "4", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "5", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "6", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "7", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "8", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "9", "IDS2 Identifier");
		dfa.addTransition("IDS2 Identifier", "_", "IDS2 Identifier");

		dfa.addTransition("S0", "\"", "SS1");
		dfa.addTransition("SS1", "\"", "SS2 String");

		dfa.addTransition("S0", "'", "CS1");
		dfa.addTransition("CS2", "'", "CS3 Char");

		dfa.addTransition("S0", ".", "F1 Float");

		dfa.addTransition("F1 Float", "0", "F1 Float");
		dfa.addTransition("F1 Float", "1", "F1 Float");
		dfa.addTransition("F1 Float", "2", "F1 Float");
		dfa.addTransition("F1 Float", "3", "F1 Float");
		dfa.addTransition("F1 Float", "4", "F1 Float");
		dfa.addTransition("F1 Float", "5", "F1 Float");
		dfa.addTransition("F1 Float", "6", "F1 Float");
		dfa.addTransition("F1 Float", "7", "F1 Float");
		dfa.addTransition("F1 Float", "8", "F1 Float");
		dfa.addTransition("F1 Float", "9", "F1 Float");

		dfa.addTransition("F1 Float", "e", "ES1");

		dfa.addTransition("ES1", "1", "ES3 Exponencial");
		dfa.addTransition("ES1", "2", "ES3 Exponencial");
		dfa.addTransition("ES1", "3", "ES3 Exponencial");
		dfa.addTransition("ES1", "4", "ES3 Exponencial");
		dfa.addTransition("ES1", "5", "ES3 Exponencial");
		dfa.addTransition("ES1", "6", "ES3 Exponencial");
		dfa.addTransition("ES1", "7", "ES3 Exponencial");
		dfa.addTransition("ES1", "8", "ES3 Exponencial");
		dfa.addTransition("ES1", "9", "ES3 Exponencial");

		dfa.addTransition("ES1", "+", "ES2");
		dfa.addTransition("ES1", "-", "ES2");

		dfa.addTransition("ES2", "1", "ES3 Exponencial");
		dfa.addTransition("ES2", "2", "ES3 Exponencial");
		dfa.addTransition("ES2", "3", "ES3 Exponencial");
		dfa.addTransition("ES2", "4", "ES3 Exponencial");
		dfa.addTransition("ES2", "5", "ES3 Exponencial");
		dfa.addTransition("ES2", "6", "ES3 Exponencial");
		dfa.addTransition("ES2", "7", "ES3 Exponencial");
		dfa.addTransition("ES2", "8", "ES3 Exponencial");
		dfa.addTransition("ES2", "9", "ES3 Exponencial");

		dfa.addTransition("ES3 Exponencial", "0", "ES3 Exponencial");
		dfa.addTransition("ES3 Exponencial", "1", "ES3 Exponencial");
		dfa.addTransition("ES3 Exponencial", "2", "ES3 Exponencial");
		dfa.addTransition("ES3 Exponencial", "3", "ES3 Exponencial");
		dfa.addTransition("ES3 Exponencial", "4", "ES3 Exponencial");
		dfa.addTransition("ES3 Exponencial", "5", "ES3 Exponencial");
		dfa.addTransition("ES3 Exponencial", "6", "ES3 Exponencial");
		dfa.addTransition("ES3 Exponencial", "7", "ES3 Exponencial");
		dfa.addTransition("ES3 Exponencial", "8", "ES3 Exponencial");
		dfa.addTransition("ES3 Exponencial", "9", "ES3 Exponencial");

		dfa.addTransition("S0", "1", "I2 Integer");
		dfa.addTransition("S0", "2", "I2 Integer");
		dfa.addTransition("S0", "3", "I2 Integer");
		dfa.addTransition("S0", "4", "I2 Integer");
		dfa.addTransition("S0", "5", "I2 Integer");
		dfa.addTransition("S0", "6", "I2 Integer");
		dfa.addTransition("S0", "7", "I2 Integer");
		dfa.addTransition("S0", "8", "I2 Integer");
		dfa.addTransition("S0", "9", "I2 Integer");

		dfa.addTransition("I2 Integer", "0", "I2 Integer");
		dfa.addTransition("I2 Integer", "1", "I2 Integer");
		dfa.addTransition("I2 Integer", "2", "I2 Integer");
		dfa.addTransition("I2 Integer", "3", "I2 Integer");
		dfa.addTransition("I2 Integer", "4", "I2 Integer");
		dfa.addTransition("I2 Integer", "5", "I2 Integer");
		dfa.addTransition("I2 Integer", "6", "I2 Integer");
		dfa.addTransition("I2 Integer", "7", "I2 Integer");
		dfa.addTransition("I2 Integer", "8", "I2 Integer");
		dfa.addTransition("I2 Integer", "9", "I2 Integer");

		dfa.addTransition("I2 Integer", ".", "F1 Float");

		dfa.addTransition("I2 Integer", "e", "ES1");

		dfa.addAcceptState("I1 Integer", "INTEGER");
		dfa.addAcceptState("BS2 Binary", "BINARY");
		dfa.addAcceptState("O&&", "OPERATOR");
		dfa.addAcceptState("O||", "OPERATOR");
		dfa.addAcceptState("OS1 Octal", "OCTAL");
		dfa.addAcceptState("HS2 Hexa", "HEXADECIMAL");
		dfa.addAcceptState("IDS2 Identifier", "IDENTIFIER");
		dfa.addAcceptState("SS2 String", "STRING");
		dfa.addAcceptState("CS3 Char", "CHAR");
		dfa.addAcceptState("F1 Float", "FLOAT");
		dfa.addAcceptState("I2 Integer", "INTEGER");
		dfa.addAcceptState("ES3 Exponencial", "EXPONENCIAL");

		dfa.addAcceptState("Bool1 KW", "IDENTIFIER");
		dfa.addAcceptState("Bool2 KW", "IDENTIFIER");
		dfa.addAcceptState("Bool3 KW", "IDENTIFIER");
		dfa.addAcceptState("Bool4 Keyword", "KEYWORD");
		dfa.addAcceptState("Boolean5 KW", "IDENTIFIER");
		dfa.addAcceptState("Boolean6 KW", "IDENTIFIER");
		dfa.addAcceptState("Boolean7 Keyword", "KEYWORD");

		dfa.addAcceptState("Char1 KW", "IDENTIFIER");
		dfa.addAcceptState("Char2 KW", "IDENTIFIER");
		dfa.addAcceptState("Char3 KW", "IDENTIFIER");
		dfa.addAcceptState("Char4 Keyword", "KEYWORD");

		dfa.addAcceptState("Void1 KW", "IDENTIFIER");
		dfa.addAcceptState("Void2 KW", "IDENTIFIER");
		dfa.addAcceptState("Void3 KW", "IDENTIFIER");
		dfa.addAcceptState("Void4 Keyword", "KEYWORD");

		dfa.addAcceptState("Double1 KW", "IDENTIFIER");
		dfa.addAcceptState("Double2 KW", "IDENTIFIER");
		dfa.addAcceptState("Double3 KW", "IDENTIFIER");
		dfa.addAcceptState("Double4 KW", "IDENTIFIER");
		dfa.addAcceptState("Double5 KW", "IDENTIFIER");
		dfa.addAcceptState("Double6 Keyword", "KEYWORD");

		dfa.addAcceptState("Flofor KW", "IDENTIFIER");
		dfa.addAcceptState("Float2 KW", "IDENTIFIER");
		dfa.addAcceptState("Float3 KW", "IDENTIFIER");
		dfa.addAcceptState("Float4 KW", "IDENTIFIER");
		dfa.addAcceptState("Float5 Keyword", "KEYWORD");
		
		dfa.addAcceptState("InIf KW", "IDENTIFIER");
		dfa.addAcceptState("Int2 KW", "IDENTIFIER");
		dfa.addAcceptState("Int3 Keyword", "KEYWORD");
		dfa.addAcceptState("If KW", "KEYWORD");

		dfa.addAcceptState("For2 KW", "IDENTIFIER");
		dfa.addAcceptState("For3 Keyword", "KEYWORD");

		dfa.addAcceptState("While1 KW", "IDENTIFIER");
		dfa.addAcceptState("While2 KW", "IDENTIFIER");
		dfa.addAcceptState("While3 KW", "IDENTIFIER");
		dfa.addAcceptState("While4 KW", "IDENTIFIER");
		dfa.addAcceptState("While5 Keyword", "KEYWORD");

		dfa.addAcceptState("Long1 KW", "IDENTIFIER");
		dfa.addAcceptState("Long2 KW", "IDENTIFIER");
		dfa.addAcceptState("Long3 KW", "IDENTIFIER");
		dfa.addAcceptState("Long4 Keyword", "KEYWORD");

		dfa.addAcceptState("String1 KW", "IDENTIFIER");
		dfa.addAcceptState("String2 KW", "IDENTIFIER");
		dfa.addAcceptState("String3 KW", "IDENTIFIER");
		dfa.addAcceptState("String4 KW", "IDENTIFIER");
		dfa.addAcceptState("String5 KW", "IDENTIFIER");
		dfa.addAcceptState("String6 Keyword", "KEYWORD");
	}
	
	public void run() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
			algorithm(line);
		}
	}
	
	private void algorithm(String line) {
		String currentState = "S0";
		String nextState;
		String string = "";
		int index = 0;
		boolean inString = false;
		
		while (index < line.length()) {
			char currentChar = line.charAt(index);
			
			if (currentState.equals("S0") && currentChar == '"') {
				inString = true;
			} else if (currentState.equals("SS1") && currentChar == '"') {
				inString = false;
			}
			
			if (currentChar == '!' || currentChar == '=' || currentChar == '<' || currentChar == '>') {
				if (!inString) {
					if (dfa.isAcceptState(currentState)) {
						String stateName = dfa.getAcceptStateName(currentState);
						tokens.add(new TheToken(string, stateName));
						currentState = "S0";
						string = "";
					} else if (currentState != "S0") {
						tokens.add(new TheToken(string, "ERROR"));
						currentState = "S0";
						string = "";
					}
					
					if (index + 1 < line.length()) {
						char nextChar = line.charAt(index + 1);
						if ((currentChar == '!' && nextChar == '=') || 
							(currentChar == '=' && nextChar == '=') ||
							(currentChar == '<' && nextChar == '=') || 
							(currentChar == '>' && nextChar == '=')) {
							tokens.add(new TheToken(currentChar + "" + nextChar, "OPERATOR"));
							index += 2;
							continue;
						}
					}
					
					tokens.add(new TheToken(currentChar + "", "OPERATOR"));
					index++;
					continue;
				}
			}
			
			if (!inString && (isOperator(currentChar) || isDelimiter(currentChar) || isSpace(currentChar))) {
				if (dfa.isAcceptState(currentState)) {
					String stateName = dfa.getAcceptStateName(currentState);
					tokens.add(new TheToken(string, stateName));
				} else if (currentState != "S0") {
					tokens.add(new TheToken(string, "ERROR"));
				}
				if (isOperator(currentChar)) {
					tokens.add(new TheToken(currentChar + "", "OPERATOR"));
				} else if (isDelimiter(currentChar)) {
					tokens.add(new TheToken(currentChar + "", "DELIMITER"));
				}
				currentState = "S0";
				string = "";
			} else {
				nextState = dfa.getNextState(currentState, currentChar);
				string = string + currentChar;
				currentState = nextState;
			}
			index++;
		}
		
		if (dfa.isAcceptState(currentState)) {
			String stateName = dfa.getAcceptStateName(currentState);
			tokens.add(new TheToken(string, stateName));
		} else if (currentState != "S0") {
			tokens.add(new TheToken(string, "ERROR"));
		}
	}
	private boolean isSpace(char c) {
		return c == ' ' || c == '\t' || c == '\n';
	}
	
	private boolean isDelimiter(char c) {
		return c == ',' || c == ';' || c == '(' || c == ')' || 
			   c == '{' || c == '}' || c == '[' || c == ']' || c == ':';
	}
	
	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || 
			   c == '%' || c == '<' || c == '>' || c == '!' || c == '=';
	}
	
	public void printTokens() {
		for (TheToken token : tokens) {
			System.out.printf("%10s\t|\t%s\n", token.getValue(), token.getType());
		}
	}
	
	public Vector<TheToken> getTokens() {
		return tokens;
	}
	
}
