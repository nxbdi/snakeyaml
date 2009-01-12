package org.pyyaml;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.scanner.ScannerImpl;
import org.yaml.snakeyaml.tokens.AliasToken;
import org.yaml.snakeyaml.tokens.AnchorToken;
import org.yaml.snakeyaml.tokens.DirectiveToken;
import org.yaml.snakeyaml.tokens.DocumentStartToken;
import org.yaml.snakeyaml.tokens.FlowEntryToken;
import org.yaml.snakeyaml.tokens.FlowMappingEndToken;
import org.yaml.snakeyaml.tokens.FlowMappingStartToken;
import org.yaml.snakeyaml.tokens.FlowSequenceEndToken;
import org.yaml.snakeyaml.tokens.FlowSequenceStartToken;
import org.yaml.snakeyaml.tokens.KeyToken;
import org.yaml.snakeyaml.tokens.ScalarToken;
import org.yaml.snakeyaml.tokens.StreamEndToken;
import org.yaml.snakeyaml.tokens.StreamStartToken;
import org.yaml.snakeyaml.tokens.TagToken;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.ValueToken;

public class CanonicalScanner implements Scanner {
    private static final String DIRECTIVE = "%YAML 1.1";
    private final static Map<Character, Integer> QUOTE_CODES = ScannerImpl.ESCAPE_CODES;

    private final static Map<Character, String> QUOTE_REPLACES = ScannerImpl.ESCAPE_REPLACEMENTS;

    private String data;
    private int index;
    public LinkedList<Token> tokens;
    private boolean scanned;

    public CanonicalScanner(String data) {
        this.data = data + "\0";
        this.index = 0;
        this.tokens = new LinkedList<Token>();
        this.scanned = false;
    }

    public boolean checkToken(List<Class<? extends Token>> choices) {
        if (!scanned) {
            scan();
        }
        if (!tokens.isEmpty()) {
            if (choices.isEmpty()) {
                return true;
            }
            Token first = this.tokens.get(0);
            for (Class<? extends Token> choice : choices) {
                if (choice.isInstance(first)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkToken(Class<? extends Token> choice) {
        List<Class<? extends Token>> list = new ArrayList<Class<? extends Token>>();
        list.add(choice);
        return checkToken(list);
    }

    public Token peekToken() {
        if (!scanned) {
            scan();
        }
        if (!tokens.isEmpty()) {
            Token first = this.tokens.get(0);
            return first;
        }
        return null;
    }

    public Token getToken() {
        if (!scanned) {
            scan();
        }
        Token token = this.tokens.poll();
        return token;
    }

    public Token getToken(Class<? extends Token> choice) {
        Token token = getToken();
        if (choice != null && !choice.isInstance(token)) {
            throw new CanonicalException("unexpected token " + token);
        }
        return token;
    }

    private void scan() {
        this.tokens.push(new StreamStartToken(null, null));
        while (true) {
            findToken();
            char ch = data.charAt(index);
            switch (ch) {
            case '\0':
                tokens.push(new StreamEndToken(null, null));
                break;

            case '%':
                tokens.push(scanDirective());
                break;

            case '-':
                if ("---".equals(data.substring(index, index + 3))) {
                    index += 3;
                    tokens.push(new DocumentStartToken(null, null));
                }
                break;

            case '[':
                index++;
                tokens.push(new FlowSequenceStartToken(null, null));
                break;

            case '{':
                index++;
                tokens.push(new FlowMappingStartToken(null, null));
                break;

            case ']':
                index++;
                tokens.push(new FlowSequenceEndToken(null, null));
                break;

            case '}':
                index++;
                tokens.push(new FlowMappingEndToken(null, null));
                break;

            case '?':
                index++;
                tokens.push(new KeyToken(null, null));
                break;

            case ':':
                index++;
                tokens.push(new ValueToken(null, null));
                break;

            case ',':
                index++;
                tokens.push(new FlowEntryToken(null, null));
                break;

            case '*':
                tokens.push(scanAlias());
                break;

            case '&':
                tokens.push(scanAlias());
                break;

            case '!':
                tokens.push(scanTag());
                break;

            case '"':
                tokens.push(scanScalar());
                break;

            default:
                throw new CanonicalException("invalid token");
            }
        }
    }

    private Token scanDirective() {
        String chunk1 = data.substring(index, index + DIRECTIVE.length());
        char chunk2 = data.charAt(index + DIRECTIVE.length());
        if (DIRECTIVE.equals(chunk1) && "\n\0".indexOf(chunk2) != -1) {
            index += DIRECTIVE.length();
            List<Integer> implicit = new ArrayList<Integer>(2);
            implicit.add(new Integer(1));
            implicit.add(new Integer(1));
            return new DirectiveToken("YAML", implicit, null, null);
        } else {
            throw new CanonicalException("invalid directive");
        }
    }

    private Token scanAlias() {
        index++;
        int start = index;
        while (", \n\0".indexOf(data.charAt(index)) == -1) {
            index++;
        }
        String value = data.substring(start, index);
        Token token;
        if (data.charAt(index) == '*') {
            token = new AliasToken(value, null, null);
        } else {
            token = new AnchorToken(value, null, null);
        }
        return token;
    }

    private Token scanTag() {
        index++;
        int start = index;
        while (" \n\0".indexOf(data.charAt(index)) == -1) {
            index++;
        }
        String value = data.substring(start, index);
        if (value.length() == 0) {
            value = "!";
        } else if (value.charAt(0) == '!') {
            value = "tag:yaml.org,2002:" + value.substring(1);
        } else if (value.charAt(0) == '<' && value.charAt(value.length() - 1) == '>') {
            value = value.substring(1, value.length() - 1);
        } else {
            value = "!" + value;
        }
        return new TagToken(new String[] { "", value }, null, null);
    }

    private Token scanScalar() {
        index++;
        StringBuffer chunks = new StringBuffer();
        int start = index;
        boolean ignoreSpaces = false;
        while (data.charAt(index) != '"') {
            if (data.charAt(index) == '\\') {
                ignoreSpaces = false;
                chunks.append(data.substring(start, index));
                index++;
                char ch = data.charAt(index);
                index++;
                if (ch == '\n') {
                    ignoreSpaces = true;
                } else if (QUOTE_CODES.keySet().contains(ch)) {
                    int length = QUOTE_CODES.get(chunks);
                    int code = Integer.parseInt(data.substring(index, index + length), 16);
                    chunks.append(String.valueOf(code));
                    index += length;
                } else {
                    if (!QUOTE_REPLACES.keySet().contains(ch)) {
                        throw new CanonicalException("invalid escape code");
                    }
                    chunks.append(QUOTE_REPLACES.get(ch));
                }
                start = index;
            } else if (data.charAt(index) == '\n') {
                chunks.append(data.substring(start, index));
                chunks.append(" ");
                index++;
                start = index;
                ignoreSpaces = true;
            } else if (ignoreSpaces && data.charAt(index) == ' ') {
                index++;
                start = index;
            } else {
                ignoreSpaces = false;
                index++;
            }
        }
        chunks.append(data.substring(start, index));
        index++;
        return new ScalarToken(chunks.toString(), null, null, false);
    }

    private void findToken() {
        boolean found = false;
        while (found) {
            while (" \t".indexOf(data.charAt(index)) != -1) {
                index++;
            }
            if (data.charAt(index) == '#') {
                while (data.charAt(index) != '\n') {
                    index++;
                }
            }
            if (data.charAt(index) == '\n') {
                index++;
            } else {
                found = true;
            }
        }
    }
}
