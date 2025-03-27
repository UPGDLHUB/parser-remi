import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Regina Ochoa Gispert
 * @author Renata Calderon Mercado
 * @author Miguel Angel Velez Olide
 * @version 1.0
 */
public class TheLexer {
    
    private File file;
    private HashMap<String, String> table;
    private HashMap<String, String> acceptStates;
    private Vector<TheToken> tokens;
    
    public TheLexer(File file) {
        this.file = file;
        tokens = new Vector<>();
        table = new HashMap<>();
        acceptStates = new HashMap<>();
        
        initializeDFA();
    }
    
    private void initializeDFA() {
        addTransition("S0", "0", "I1 Integer");
        addTransition("I1 Integer", "b", "BS1");
        addTransition("I1 Integer", "B", "BS1");
        addTransition("BS1", "0", "BS2 Binary");
        addTransition("BS1", "1", "BS2 Binary");
        addTransition("BS2 Binary", "0", "BS2 Binary");
        addTransition("BS2 Binary", "1", "BS2 Binary");

        addTransition("I1 Integer", "0", "OS1 Octal");
        addTransition("I1 Integer", "1", "OS1 Octal");
        addTransition("I1 Integer", "2", "OS1 Octal");
        addTransition("I1 Integer", "3", "OS1 Octal");
        addTransition("I1 Integer", "4", "OS1 Octal");
        addTransition("I1 Integer", "5", "OS1 Octal");
        addTransition("I1 Integer", "6", "OS1 Octal");
        addTransition("I1 Integer", "7", "OS1 Octal");

        addTransition("OS1 Octal", "0", "OS1 Octal");
        addTransition("OS1 Octal", "1", "OS1 Octal");
        addTransition("OS1 Octal", "2", "OS1 Octal");
        addTransition("OS1 Octal", "3", "OS1 Octal");
        addTransition("OS1 Octal", "4", "OS1 Octal");
        addTransition("OS1 Octal", "5", "OS1 Octal");
        addTransition("OS1 Octal", "6", "OS1 Octal");
        addTransition("OS1 Octal", "7", "OS1 Octal");

        addTransition("I1 Integer", "x", "HS1");
        addTransition("I1 Integer", "X", "HS1");

        String hexChars = "0123456789abcdefABCDEF";
        for (char c : hexChars.toCharArray()) {
            addTransition("HS1", String.valueOf(c), "HS2 Hexa");
            addTransition("HS2 Hexa", String.valueOf(c), "HS2 Hexa");
        }

        String letters = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        for (char c : letters.toCharArray()) {
            addTransition("S0", String.valueOf(c), "IDS2 Identifier");
            addTransition("IDS2 Identifier", String.valueOf(c), "IDS2 Identifier");
        }
        
        String digits = "0123456789";
        for (char c : digits.toCharArray()) {
            addTransition("IDS2 Identifier", String.valueOf(c), "IDS2 Identifier");
        }
        addTransition("S0", "$", "IDS2 Identifier");
        
        addKeywordTransitions();
        
        addTransition("S0", "\"", "SS1");
        addTransition("SS1", "\"", "SS2 String");
        
        addTransition("S0", "'", "CS1");
        addTransition("CS2", "'", "CS3 Char");
        
        addTransition("S0", ".", "F1 Float");
        for (char c : digits.toCharArray()) {
            addTransition("F1 Float", String.valueOf(c), "F1 Float");
        }
        
        addTransition("F1 Float", "e", "ES1");
        for (char c : digits.toCharArray()) {
            addTransition("ES1", String.valueOf(c), "ES3 Exponencial");
            addTransition("ES2", String.valueOf(c), "ES3 Exponencial");
            addTransition("ES3 Exponencial", String.valueOf(c), "ES3 Exponencial");
        }
        
        addTransition("ES1", "+", "ES2");
        addTransition("ES1", "-", "ES2");
        
        for (char c : "123456789".toCharArray()) {
            addTransition("S0", String.valueOf(c), "I2 Integer");
        }
        
        for (char c : digits.toCharArray()) {
            addTransition("I2 Integer", String.valueOf(c), "I2 Integer");
        }
        
        addTransition("I2 Integer", ".", "F1 Float");
        addTransition("I2 Integer", "e", "ES1");
        
        addAcceptState("I1 Integer", "INTEGER");
        addAcceptState("BS2 Binary", "INTEGER");  
        addAcceptState("OS1 Octal", "INTEGER");   
        addAcceptState("HS2 Hexa", "INTEGER");   
        addAcceptState("IDS2 Identifier", "IDENTIFIER");
        addAcceptState("SS2 String", "STRING");
        addAcceptState("CS3 Char", "CHAR");
        addAcceptState("F1 Float", "INTEGER");    
        addAcceptState("I2 Integer", "INTEGER");
        addAcceptState("ES3 Exponencial", "INTEGER"); 
        
        addAcceptState("Bool4 Keyword", "KEYWORD");
        addAcceptState("Boolean7 Keyword", "KEYWORD");
        addAcceptState("Char4 Keyword", "KEYWORD");
        addAcceptState("Double6 Keyword", "KEYWORD");
        addAcceptState("Float5 Keyword", "KEYWORD");
        addAcceptState("Int3 Keyword", "KEYWORD");
        addAcceptState("Long4 Keyword", "KEYWORD");
        addAcceptState("String6 Keyword", "KEYWORD");
    }
    
    private void addKeywordTransitions() {
        addTransition("S0", "b", "Bool1 KW");
        addTransition("Bool1 KW", "o", "Bool2 KW");
        addTransition("Bool2 KW", "o", "Bool3 KW");
        addTransition("Bool3 KW", "l", "Bool4 Keyword");
        addTransition("Bool4 Keyword", "e", "Boolean5 KW");
        addTransition("Boolean5 KW", "a", "Boolean6 KW");
        addTransition("Boolean6 KW", "n", "Boolean7 Keyword");
        
        addTransition("S0", "c", "Char1 KW");
        addTransition("Char1 KW", "h", "Char2 KW");
        addTransition("Char2 KW", "a", "Char3 KW");
        addTransition("Char3 KW", "r", "Char4 Keyword");
        
        addTransition("S0", "d", "Double1 KW");
        addTransition("Double1 KW", "o", "Double2 KW");
        addTransition("Double2 KW", "u", "Double3 KW");
        addTransition("Double3 KW", "b", "Double4 KW");
        addTransition("Double4 KW", "l", "Double5 KW");
        addTransition("Double5 KW", "e", "Double6 Keyword");
        
        addTransition("S0", "f", "Float1 KW");
        addTransition("Float1 KW", "l", "Float2 KW");
        addTransition("Float2 KW", "o", "Float3 KW");
        addTransition("Float3 KW", "a", "Float4 KW");
        addTransition("Float4 KW", "t", "Float5 Keyword");
        
        addTransition("S0", "i", "Int1 KW");
        addTransition("Int1 KW", "n", "Int2 KW");
        addTransition("Int2 KW", "t", "Int3 Keyword");
        
        addTransition("S0", "l", "Long1 KW");
        addTransition("Long1 KW", "o", "Long2 KW");
        addTransition("Long2 KW", "n", "Long3 KW");
        addTransition("Long3 KW", "g", "Long4 Keyword");
        
        addTransition("S0", "s", "String1 KW");
        addTransition("String1 KW", "t", "String2 KW");
        addTransition("String2 KW", "r", "String3 KW");
        addTransition("String3 KW", "i", "String4 KW");
        addTransition("String4 KW", "n", "String5 KW");
        addTransition("String5 KW", "g", "String6 Keyword");
        
        String[] keywordStates = {
            "Bool1 KW", "Bool2 KW", "Bool3 KW", "Boolean5 KW", "Boolean6 KW",
            "Char1 KW", "Char2 KW", "Char3 KW",
            "Double1 KW", "Double2 KW", "Double3 KW", "Double4 KW", "Double5 KW",
            "Float1 KW", "Float2 KW", "Float3 KW", "Float4 KW",
            "Int1 KW", "Int2 KW",
            "Long1 KW", "Long2 KW", "Long3 KW",
            "String1 KW", "String2 KW", "String3 KW", "String4 KW", "String5 KW"
        };
        
        for (String state : keywordStates) {
            addAcceptState(state, "IDENTIFIER");
        }
        
        String alphabet = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
    }
    
    public void run() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            analyzeLine(line);
        }
        reader.close();
    }
    
    private void analyzeLine(String line) {
        String currentState = "S0";
        String nextState;
        String lexeme = "";
        int index = 0;
        
        while (index < line.length()) {
            char currentChar = line.charAt(index);
            
            if (!(isOperator(currentChar) || isDelimiter(currentChar) || isSpace(currentChar))) {
                nextState = getNextState(currentState, currentChar);
                if (nextState != null) {
                    lexeme = lexeme + currentChar;
                    currentState = nextState;
                } else {
                    if (isAcceptState(currentState)) {
                        String tokenType = getTokenType(currentState);
                        tokens.add(new TheToken(lexeme, tokenType));
                    } else if (!currentState.equals("S0")) {
                        tokens.add(new TheToken(lexeme, "ERROR"));
                    }
                    
                    currentState = "S0";
                    lexeme = "";
                    
                    continue;
                }
            } else {
                if (isAcceptState(currentState)) {
                    String tokenType = getTokenType(currentState);
                    tokens.add(new TheToken(lexeme, tokenType));
                } else if (!currentState.equals("S0")) {
                    tokens.add(new TheToken(lexeme, "ERROR"));
                }
                
                if (isOperator(currentChar)) {
                    if (index + 1 < line.length()) {
                        String possibleOp = String.valueOf(currentChar) + line.charAt(index + 1);
                        if ((possibleOp.equals("==") || possibleOp.equals("!=") || 
                             possibleOp.equals("<=") || possibleOp.equals(">=")) && 
                            isOperator(line.charAt(index + 1))) {
                            tokens.add(new TheToken(possibleOp, "OPERATOR"));
                            index++;
                        } else {
                            tokens.add(new TheToken(String.valueOf(currentChar), "OPERATOR"));
                        }
                    } else {
                        tokens.add(new TheToken(String.valueOf(currentChar), "OPERATOR"));
                    }
                } else if (isDelimiter(currentChar)) {
                    tokens.add(new TheToken(String.valueOf(currentChar), "DELIMITER"));
                }
                
                currentState = "S0";
                lexeme = "";
            }
            
            index++;
        }
        
        if (isAcceptState(currentState)) {
            String tokenType = getTokenType(currentState);
            tokens.add(new TheToken(lexeme, tokenType));
        } else if (!currentState.equals("S0") && !lexeme.isEmpty()) {
            tokens.add(new TheToken(lexeme, "ERROR"));
        }
    }
    
    private String getNextState(String currentState, char inputSymbol) {
        if (currentState.equals("SS1")) {
            if (inputSymbol != '"') {
                return "SS1";
            }
        }
        
        if (currentState.equals("CS1")) {
            if (inputSymbol != '\'') {
                return "CS2";
            }
        }
        
        return table.get(currentState + "/" + inputSymbol);
    }
    
    private boolean isAcceptState(String state) {
        return acceptStates.containsKey(state);
    }
    
    private String getTokenType(String state) {
        String type = acceptStates.get(state);
        
        switch (type) {
            case "BINARY":
            case "OCTAL":
            case "HEXADECIMAL":
            case "FLOAT":
            case "EXPONENCIAL":
                return "INTEGER";
            case "KEYWORD":
                return "IDENTIFIER";
            default:
                return type;
        }
    }
    
    private boolean isSpace(char c) {
        return c == ' ' || c == '\t' || c == '\n';
    }
    
    private boolean isDelimiter(char c) {
        return c == ',' || c == ';' || c == '(' || c == ')' || 
               c == '{' || c == '}' || c == '[' || c == ']';
    }
    
    private boolean isOperator(char c) {
        return c == '=' || c == '+' || c == '-' || c == '*' || c == '/' || 
               c == '%' || c == '<' || c == '>' || c == '!';
    }
    
    public void printTokens() {
        for (TheToken token : tokens) {
            System.out.printf("%10s\t|\t%s\n", token.getValue(), token.getType());
        }
    }
    
    public Vector<TheToken> getTokens() {
        return tokens;
    }
    
    private void addTransition(String currentState, String inputSymbol, String nextState) {
        table.put(currentState + "/" + inputSymbol, nextState);
    }
    
    private void addAcceptState(String state, String type) {
        acceptStates.put(state, type);
    }
}