import java.util.Vector;

public class TheParser {
    
    private Vector<TheToken> tokens;
    private int currentToken;
    
    public TheParser(Vector<TheToken> tokens) {
        this.tokens = tokens;
        currentToken = 0;
    }
    
    public void run() {
        RULE_PROGRAM();
    }
    
    private void RULE_PROGRAM() {
        System.out.println("- RULE_PROGRAM");
        
        while (currentToken < tokens.size() && !tokens.get(currentToken).getValue().equals("class")) {
            RULE_GLOBAL_VARIABLE_DECLARATION();
        }
        
        RULE_CLASS_DECLARATION();
    }
    
    private void RULE_GLOBAL_VARIABLE_DECLARATION() {
        System.out.println("- RULE_GLOBAL_VARIABLE_DECLARATION");
        if (isTypeDeclaration()) {
            String type = tokens.get(currentToken).getValue();
            currentToken++;
            System.out.println("- Global variable type: " + type);
            
            if (tokens.get(currentToken).getType().equals("IDENTIFIER")) {
                System.out.println("- Global variable name: " + tokens.get(currentToken).getValue());
                currentToken++;
            } else {
                error(15, "Expected variable name");
            }
            
            if (tokens.get(currentToken).getValue().equals("=")) {
                currentToken++;
                System.out.println("- =");
                RULE_EXPRESSION();
            }
            
            if (tokens.get(currentToken).getValue().equals(";")) {
                currentToken++;
                System.out.println("- ;");
            } else {
                error(16, "Expected ';'");
            }
        } else {
            error(17, "Expected type declaration");
        }
    }
    
    private void RULE_CLASS_DECLARATION() {
        System.out.println("- RULE_CLASS_DECLARATION");
        if (tokens.get(currentToken).getValue().equals("class")) {
            currentToken++;
            System.out.println("- class");
        } else {
            error(0, "Expected 'class' keyword");
        }
        
        if (tokens.get(currentToken).getType().equals("IDENTIFIER")) {
            System.out.println("- class name: " + tokens.get(currentToken).getValue());
            currentToken++;
        } else {
            error(0, "Expected class name (identifier)");
        }
        
        RULE_CLASS_BODY();
    }
    
    private void RULE_CLASS_BODY() {
        if (tokens.get(currentToken).getValue().equals("{")) {
            currentToken++;
            System.out.println("- {");
        } else {
            error(1, "Expected '{'");
        }
        
        while (!tokens.get(currentToken).getValue().equals("}")) {
            RULE_METHOD_DECLARATION();
        }
        
        if (tokens.get(currentToken).getValue().equals("}")) {
            currentToken++;
            System.out.println("- }");
        } else {
            error(2, "Expected '}'");
        }
    }
    
    private void RULE_METHOD_DECLARATION() {
        System.out.println("-- RULE_METHOD_DECLARATION");
        if (tokens.get(currentToken).getType().equals("IDENTIFIER") || 
            tokens.get(currentToken).getType().equals("KEYWORD")) {
            System.out.println("-- Return type: " + tokens.get(currentToken).getValue());
            currentToken++;
        } else {
            error(6, "Expected return type");
        }
        
        if (tokens.get(currentToken).getType().equals("IDENTIFIER")) {
            System.out.println("-- Method name: " + tokens.get(currentToken).getValue());
            currentToken++;
        } else {
            error(7, "Expected method name");
        }
        
        if (tokens.get(currentToken).getValue().equals("(")) {
            currentToken++;
            System.out.println("-- (");
        } else {
            error(8, "Expected '('");
        }
        
        RULE_PARAMETERS();
        
        if (tokens.get(currentToken).getValue().equals(")")) {
            currentToken++;
            System.out.println("-- )");
        } else {
            error(9, "Expected ')'");
        }
        
        RULE_METHOD_BODY();
    }
    
    private void RULE_PARAMETERS() {
        System.out.println("--- RULE_PARAMETERS");
        if (tokens.get(currentToken).getValue().equals(")")) {
            return;
        }
        
        if (tokens.get(currentToken).getType().equals("KEYWORD") || 
            tokens.get(currentToken).getType().equals("IDENTIFIER")) {
            System.out.println("--- Parameter type: " + tokens.get(currentToken).getValue());
            currentToken++;
        } else {
            error(10, "Expected parameter type");
        }
        
        if (tokens.get(currentToken).getType().equals("IDENTIFIER")) {
            System.out.println("--- Parameter name: " + tokens.get(currentToken).getValue());
            currentToken++;
        } else {
            error(11, "Expected parameter name");
        }
        
        while (tokens.get(currentToken).getValue().equals(",")) {
            currentToken++;
            System.out.println("--- ,");
            
            if (tokens.get(currentToken).getType().equals("KEYWORD") || 
                tokens.get(currentToken).getType().equals("IDENTIFIER")) {
                System.out.println("--- Parameter type: " + tokens.get(currentToken).getValue());
                currentToken++;
            } else {
                error(10, "Expected parameter type");
            }
            
            if (tokens.get(currentToken).getType().equals("IDENTIFIER")) {
                System.out.println("--- Parameter name: " + tokens.get(currentToken).getValue());
                currentToken++;
            } else {
                error(11, "Expected parameter name");
            }
        }
    }
    
    private void RULE_METHOD_BODY() {
        System.out.println("--- RULE_METHOD_BODY");
        if (tokens.get(currentToken).getValue().equals("{")) {
            currentToken++;
            System.out.println("--- {");
            
            while (!tokens.get(currentToken).getValue().equals("}")) {
                RULE_STATEMENT();
            }
            
            if (tokens.get(currentToken).getValue().equals("}")) {
                currentToken++;
                System.out.println("--- }");
            } else {
                error(13, "Expected '}'");
            }
        } else {
            RULE_STATEMENT();
        }
    }
    
    private void RULE_STATEMENT() {
        System.out.println("---- RULE_STATEMENT");
        
        if (isTypeDeclaration()) {
            RULE_VARIABLE_DECLARATION();
        }
        else if (tokens.get(currentToken).getValue().equals("return")) {
            RULE_RETURN();
        }
        else if (tokens.get(currentToken).getValue().equals("if")) {
            RULE_IF();
        }
        else if (tokens.get(currentToken).getValue().equals("while")) {
            RULE_WHILE();
        }
        else if (tokens.get(currentToken).getValue().equals("do")) {
            RULE_DO_WHILE();
        }
        else if (tokens.get(currentToken).getValue().equals("for")) {
            RULE_FOR();
        }
        else if (tokens.get(currentToken).getValue().equals("switch")) {
            RULE_SWITCH();
        }
        else if (tokens.get(currentToken).getType().equals("IDENTIFIER")) {
            String identifier = tokens.get(currentToken).getValue();
            currentToken++;
            
            if (tokens.get(currentToken).getValue().equals("(")) {
                currentToken--;
                RULE_CALL_METHOD();
            }
            else if (tokens.get(currentToken).getValue().equals("=")) {
                currentToken++;
                System.out.println("---- =");
                RULE_EXPRESSION();
                
                if (tokens.get(currentToken).getValue().equals(";")) {
                    currentToken++;
                    System.out.println("---- ;");
                } else {
                    error(3, "Expected ';'");
                }
            }
            else {
                currentToken--;
                RULE_EXPRESSION();
                
                if (tokens.get(currentToken).getValue().equals(";")) {
                    currentToken++;
                    System.out.println("---- ;");
                } else {
                    error(3, "Expected ';'");
                }
            }
        }
        else {
            RULE_EXPRESSION();
            
            if (tokens.get(currentToken).getValue().equals(";")) {
                currentToken++;
                System.out.println("---- ;");
            } else {
                error(3, "Expected ';'");
            }
        }
    }
    
    private void RULE_VARIABLE_DECLARATION() {
        System.out.println("----- RULE_VARIABLE_DECLARATION");
        String type = tokens.get(currentToken).getValue();
        currentToken++;
        System.out.println("----- Variable type: " + type);
        
        if (tokens.get(currentToken).getType().equals("IDENTIFIER")) {
            System.out.println("----- Variable name: " + tokens.get(currentToken).getValue());
            currentToken++;
        } else {
            error(14, "Expected variable name");
        }
        
        if (tokens.get(currentToken).getValue().equals("=")) {
            currentToken++;
            System.out.println("----- =");
            RULE_EXPRESSION();
        }
        
        if (tokens.get(currentToken).getValue().equals(";")) {
            currentToken++;
            System.out.println("----- ;");
        } else {
            error(18, "Expected ';'");
        }
    }
    
    private void RULE_RETURN() {
        System.out.println("----- RULE_RETURN");
        currentToken++;
        System.out.println("----- return");
        
        if (!tokens.get(currentToken).getValue().equals(";")) {
            RULE_EXPRESSION();
        }
        
        if (tokens.get(currentToken).getValue().equals(";")) {
            currentToken++;
            System.out.println("----- ;");
        } else {
            error(19, "Expected ';'");
        }
    }
    
    private void RULE_IF() {
        System.out.println("----- RULE_IF");
        currentToken++;
        System.out.println("----- if");
        
        if (tokens.get(currentToken).getValue().equals("(")) {
            currentToken++;
            System.out.println("----- (");
        } else {
            error(20, "Expected '('");
        }
        
        RULE_EXPRESSION();
        
        if (tokens.get(currentToken).getValue().equals(")")) {
            currentToken++;
            System.out.println("----- )");
        } else {
            error(21, "Expected ')'");
        }
        
        if (tokens.get(currentToken).getValue().equals("{")) {
            currentToken++;
            System.out.println("----- {");
            
            while (!tokens.get(currentToken).getValue().equals("}")) {
                RULE_STATEMENT();
            }
            
            if (tokens.get(currentToken).getValue().equals("}")) {
                currentToken++;
                System.out.println("----- }");
            } else {
                error(22, "Expected '}'");
            }
        } else {
            RULE_STATEMENT();
        }
        
        if (currentToken < tokens.size() && tokens.get(currentToken).getValue().equals("else")) {
            currentToken++;
            System.out.println("----- else");
            
            if (tokens.get(currentToken).getValue().equals("{")) {
                currentToken++;
                System.out.println("----- {");
                
                while (!tokens.get(currentToken).getValue().equals("}")) {
                    RULE_STATEMENT();
                }
                
                if (tokens.get(currentToken).getValue().equals("}")) {
                    currentToken++;
                    System.out.println("----- }");
                } else {
                    error(23, "Expected '}'");
                }
            } else if (tokens.get(currentToken).getValue().equals("if")) {
                RULE_IF();
            } else {
                RULE_STATEMENT();
            }
        }
    }
    
    private void RULE_WHILE() {
        System.out.println("----- RULE_WHILE");
        currentToken++;
        System.out.println("----- while");
        
        if (tokens.get(currentToken).getValue().equals("(")) {
            currentToken++;
            System.out.println("----- (");
        } else {
            error(24, "Expected '('");
        }
        
        RULE_EXPRESSION();
        
        if (tokens.get(currentToken).getValue().equals(")")) {
            currentToken++;
            System.out.println("----- )");
        } else {
            error(25, "Expected ')'");
        }
        
        if (tokens.get(currentToken).getValue().equals("{")) {
            currentToken++;
            System.out.println("----- {");
            
            while (!tokens.get(currentToken).getValue().equals("}")) {
                RULE_STATEMENT();
            }
            
            if (tokens.get(currentToken).getValue().equals("}")) {
                currentToken++;
                System.out.println("----- }");
            } else {
                error(26, "Expected '}'");
            }
        } else {
            RULE_STATEMENT();
        }
    }
    
    private void RULE_DO_WHILE() {
        System.out.println("----- RULE_DO_WHILE");
        currentToken++;
        System.out.println("----- do");
        
        if (tokens.get(currentToken).getValue().equals("{")) {
            currentToken++;
            System.out.println("----- {");
            
            while (!tokens.get(currentToken).getValue().equals("}")) {
                RULE_STATEMENT();
            }
            
            if (tokens.get(currentToken).getValue().equals("}")) {
                currentToken++;
                System.out.println("----- }");
            } else {
                error(27, "Expected '}'");
            }
        } else {
            RULE_STATEMENT();
        }
        
        if (tokens.get(currentToken).getValue().equals("while")) {
            currentToken++;
            System.out.println("----- while");
        } else {
            error(28, "Expected 'while'");
        }
        
        if (tokens.get(currentToken).getValue().equals("(")) {
            currentToken++;
            System.out.println("----- (");
        } else {
            error(29, "Expected '('");
        }
        
        RULE_EXPRESSION();
        
        if (tokens.get(currentToken).getValue().equals(")")) {
            currentToken++;
            System.out.println("----- )");
        } else {
            error(30, "Expected ')'");
        }
        
        if (tokens.get(currentToken).getValue().equals(";")) {
            currentToken++;
            System.out.println("----- ;");
        } else {
            error(31, "Expected ';'");
        }
    }
    
    private void RULE_FOR() {
        System.out.println("----- RULE_FOR");
        currentToken++;
        System.out.println("----- for");
        
        if (tokens.get(currentToken).getValue().equals("(")) {
            currentToken++;
            System.out.println("----- (");
        } else {
            error(32, "Expected '('");
        }
        
        if (!tokens.get(currentToken).getValue().equals(";")) {
            if (isTypeDeclaration()) {
                RULE_VARIABLE_DECLARATION();
            } else if (tokens.get(currentToken).getType().equals("IDENTIFIER")) {
                String identifier = tokens.get(currentToken).getValue();
                currentToken++;
                
                if (tokens.get(currentToken).getValue().equals("=")) {
                    currentToken++;
                    System.out.println("----- =");
                    RULE_EXPRESSION();
                    
                    if (tokens.get(currentToken).getValue().equals(";")) {
                        currentToken++;
                        System.out.println("----- ;");
                    } else {
                        error(33, "Expected ';'");
                    }
                } else {
                    currentToken--;
                    RULE_EXPRESSION();
                    
                    if (tokens.get(currentToken).getValue().equals(";")) {
                        currentToken++;
                        System.out.println("----- ;");
                    } else {
                        error(33, "Expected ';'");
                    }
                }
            } else {
                RULE_EXPRESSION();
                
                if (tokens.get(currentToken).getValue().equals(";")) {
                    currentToken++;
                    System.out.println("----- ;");
                } else {
                    error(33, "Expected ';'");
                }
            }
        } else {
            currentToken++;
            System.out.println("----- ;");
        }
        
        if (!tokens.get(currentToken).getValue().equals(";")) {
            RULE_EXPRESSION();
        }
        
        if (tokens.get(currentToken).getValue().equals(";")) {
            currentToken++;
            System.out.println("----- ;");
        } else {
            error(34, "Expected ';'");
        }
        
        if (!tokens.get(currentToken).getValue().equals(")")) {
            if (tokens.get(currentToken).getType().equals("IDENTIFIER")) {
                String identifier = tokens.get(currentToken).getValue();
                currentToken++;
                
                if (tokens.get(currentToken).getValue().equals("=")) {
                    currentToken++;
                    System.out.println("----- =");
                    RULE_EXPRESSION();
                } else {
                    currentToken--;
                    RULE_EXPRESSION();
                }
            } else {
                RULE_EXPRESSION();
            }
        }
        
        if (tokens.get(currentToken).getValue().equals(")")) {
            currentToken++;
            System.out.println("----- )");
        } else {
            error(35, "Expected ')'");
        }
        
        if (tokens.get(currentToken).getValue().equals("{")) {
            currentToken++;
            System.out.println("----- {");
            
            while (!tokens.get(currentToken).getValue().equals("}")) {
                RULE_STATEMENT();
            }
            
            if (tokens.get(currentToken).getValue().equals("}")) {
                currentToken++;
                System.out.println("----- }");
            } else {
                error(36, "Expected '}'");
            }
        } else {
            RULE_STATEMENT();
        }
    }
    
    private void RULE_SWITCH() {
        System.out.println("----- RULE_SWITCH");
        currentToken++;
        System.out.println("----- switch");
        
        if (tokens.get(currentToken).getValue().equals("(")) {
            currentToken++;
            System.out.println("----- (");
        } else {
            error(37, "Expected '('");
        }
        
        RULE_EXPRESSION();
        
        if (tokens.get(currentToken).getValue().equals(")")) {
            currentToken++;
            System.out.println("----- )");
        } else {
            error(38, "Expected ')'");
        }
        
        if (tokens.get(currentToken).getValue().equals("{")) {
            currentToken++;
            System.out.println("----- {");
        } else {
            error(39, "Expected '{'");
        }
        
        while (!tokens.get(currentToken).getValue().equals("}")) {
            if (tokens.get(currentToken).getValue().equals("case")) {
                currentToken++;
                System.out.println("----- case");
                
                if (tokens.get(currentToken).getType().equals("INTEGER") || 
                    tokens.get(currentToken).getType().equals("CHAR") ||
                    tokens.get(currentToken).getType().equals("STRING") ||
                    tokens.get(currentToken).getType().equals("IDENTIFIER")) {
                    currentToken++;
                } else {
                    error(40, "Expected case value");
                }
                
                if (tokens.get(currentToken).getValue().equals(":")) {
                    currentToken++;
                    System.out.println("----- :");
                } else {
                    error(41, "Expected ':'");
                }
            } else if (tokens.get(currentToken).getValue().equals("default")) {
                currentToken++;
                System.out.println("----- default");
                
                if (tokens.get(currentToken).getValue().equals(":")) {
                    currentToken++;
                    System.out.println("----- :");
                } else {
                    error(42, "Expected ':'");
                }
            } else {
                RULE_STATEMENT();
            }
            
            if (tokens.get(currentToken).getValue().equals("break")) {
                currentToken++;
                System.out.println("----- break");
                
                if (tokens.get(currentToken).getValue().equals(";")) {
                    currentToken++;
                    System.out.println("----- ;");
                } else {
                    error(43, "Expected ';'");
                }
            }
        }
        
        if (tokens.get(currentToken).getValue().equals("}")) {
            currentToken++;
            System.out.println("----- }");
        } else {
            error(44, "Expected '}'");
        }
    }
    
    private void RULE_CALL_METHOD() {
        System.out.println("----- RULE_CALL_METHOD");
        if (tokens.get(currentToken).getType().equals("IDENTIFIER")) {
            String identifier = tokens.get(currentToken).getValue();
            currentToken++;
            System.out.println("----- Method name: " + identifier);
            
            if (tokens.get(currentToken).getValue().equals("(")) {
                currentToken++;
                System.out.println("----- (");
                
                if (!tokens.get(currentToken).getValue().equals(")")) {
                    RULE_PARAM_VALUES();
                }
                
                if (tokens.get(currentToken).getValue().equals(")")) {
                    currentToken++;
                    System.out.println("----- )");
                } else {
                    error(45, "Expected ')'");
                }
                
                if (tokens.get(currentToken).getValue().equals(";")) {
                    currentToken++;
                    System.out.println("----- ;");
                } else {
                    error(46, "Expected ';'");
                }
            } else {
                error(47, "Expected '('");
            }
        } else {
            error(48, "Expected method name");
        }
    }
    
    private void RULE_PARAM_VALUES() {
        System.out.println("------ RULE_PARAM_VALUES");
        RULE_EXPRESSION();
        
        while (tokens.get(currentToken).getValue().equals(",")) {
            currentToken++;
            System.out.println("------ ,");
            RULE_EXPRESSION();
        }
    }
    
    public void RULE_EXPRESSION() {
        System.out.println("------ RULE_EXPRESSION");
        RULE_X();
        while (currentToken < tokens.size() && tokens.get(currentToken).getValue().equals("||")) {
            currentToken++;
            System.out.println("------ ||");
            RULE_X();
        }
    }
    
    public void RULE_X() {
        System.out.println("------- RULE_X");
        RULE_Y();
        while (currentToken < tokens.size() && tokens.get(currentToken).getValue().equals("&&")) {
            currentToken++;
            System.out.println("------- &&");
            RULE_Y();
        }
    }
    
    public void RULE_Y() {
        System.out.println("-------- RULE_Y");
        while (currentToken < tokens.size() && tokens.get(currentToken).getValue().equals("!")) {
            currentToken++;
            System.out.println("-------- !");
        }
        RULE_R();
    }
    
    public void RULE_R() {
        System.out.println("--------- RULE_R");
        RULE_E();
        while (currentToken < tokens.size() && 
              (tokens.get(currentToken).getValue().equals("<") ||
               tokens.get(currentToken).getValue().equals(">") ||
               tokens.get(currentToken).getValue().equals("==") ||
               tokens.get(currentToken).getValue().equals("!=") ||
               tokens.get(currentToken).getValue().equals("<=") ||
               tokens.get(currentToken).getValue().equals(">="))
        ) {
            currentToken++;
            System.out.println("--------- relational operator");
            RULE_E();
        }
    }
    
    public void RULE_E() {
        System.out.println("---------- RULE_E");
        RULE_A();
        while (currentToken < tokens.size() && 
              (tokens.get(currentToken).getValue().equals("-") ||
               tokens.get(currentToken).getValue().equals("+"))
        ) {
            currentToken++;
            System.out.println("---------- + or -");
            RULE_A();
        }
    }
    
    public void RULE_A() {
        System.out.println("----------- RULE_A");
        RULE_B();
        while (currentToken < tokens.size() && 
              (tokens.get(currentToken).getValue().equals("/") ||
               tokens.get(currentToken).getValue().equals("*"))
        ) {
            currentToken++;
            System.out.println("----------- * or /");
            RULE_B();
        }
    }
    
    public void RULE_B() {
        System.out.println("------------ RULE_B");
        if (currentToken < tokens.size() && tokens.get(currentToken).getValue().equals("-")) {
            currentToken++;
            System.out.println("------------ -");
        }
        RULE_C();
    }
    
    public void RULE_C() {
        System.out.println("------------- RULE_C");
        if (currentToken < tokens.size() && tokens.get(currentToken).getType().equals("IDENTIFIER")) {
            String identifier = tokens.get(currentToken).getValue();
            currentToken++;
            System.out.println("------------- IDENTIFIER: " + identifier);
            
            if (currentToken < tokens.size() && tokens.get(currentToken).getValue().equals("(")) {
                currentToken++;
                System.out.println("------------- (");
                
                if (!tokens.get(currentToken).getValue().equals(")")) {
                    RULE_ARGUMENTS();
                }
                
                if (tokens.get(currentToken).getValue().equals(")")) {
                    currentToken++;
                    System.out.println("------------- )");
                } else {
                    error(4, "Expected ')'");
                }
            }
        } else if (currentToken < tokens.size() && 
                  (tokens.get(currentToken).getType().equals("INTEGER") ||
                   tokens.get(currentToken).getType().equals("FLOAT") ||
                   tokens.get(currentToken).getType().equals("CHAR") ||
                   tokens.get(currentToken).getType().equals("STRING") ||
                   tokens.get(currentToken).getType().equals("BINARY") ||
                   tokens.get(currentToken).getType().equals("OCTAL") ||
                   tokens.get(currentToken).getType().equals("HEXADECIMAL"))) {
            currentToken++;
            System.out.println("------------- LITERAL: " + tokens.get(currentToken-1).getType());
        } else if (currentToken < tokens.size() && tokens.get(currentToken).getValue().equals("(")) {
            currentToken++;
            System.out.println("------------- (");
            RULE_EXPRESSION();
            if (tokens.get(currentToken).getValue().equals(")")) {
                currentToken++;
                System.out.println("------------- )");
            } else {
                error(4, "Expected ')'");
            }
        } else {
            error(5, "Expected identifier, literal or '('");
        }
    }
    
    public void RULE_ARGUMENTS() {
        System.out.println("------------- RULE_ARGUMENTS");
        RULE_EXPRESSION();
        
        while (tokens.get(currentToken).getValue().equals(",")) {
            currentToken++;
            System.out.println("------------- ,");
            RULE_EXPRESSION();
        }
    }
    
    private boolean isTypeDeclaration() {
        String value = tokens.get(currentToken).getValue();
        return (tokens.get(currentToken).getType().equals("KEYWORD") || 
               value.equals("int") || value.equals("boolean") || value.equals("float") || 
               value.equals("void") || value.equals("char") || value.equals("string") ||
               value.equals("double") || value.equals("long")) &&
               !value.equals("if") && !value.equals("else") && !value.equals("while") && 
               !value.equals("for") && !value.equals("do") && !value.equals("switch") && 
               !value.equals("case") && !value.equals("default") && !value.equals("break") &&
               !value.equals("return");
    }
    
    private TheToken lookAhead(int offset) {
        if (currentToken + offset < tokens.size()) {
            return tokens.get(currentToken + offset);
        }
        return tokens.get(tokens.size() - 1);
    }
    
    private void error(int errorCode, String message) {
        System.out.println("Error " + errorCode + ": " + message + 
                          " at token: " + tokens.get(currentToken).getValue() + 
                          " (Type: " + tokens.get(currentToken).getType() + ")");
        System.exit(1);
    }
}
