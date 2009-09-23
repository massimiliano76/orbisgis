/* Generated By:JJTree&JavaCC: Do not edit this line. SQLEngineConstants.java */
package org.gdms.sql.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface SQLEngineConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int COMMENT_LINE = 7;
  /** RegularExpression Id. */
  int MULTI_LINE_COMMENT = 8;
  /** RegularExpression Id. */
  int COMMENT_BLOCK = 10;
  /** RegularExpression Id. */
  int ALL = 11;
  /** RegularExpression Id. */
  int AND = 12;
  /** RegularExpression Id. */
  int AS = 13;
  /** RegularExpression Id. */
  int ASC = 14;
  /** RegularExpression Id. */
  int BEGIN = 15;
  /** RegularExpression Id. */
  int BETWEEN = 16;
  /** RegularExpression Id. */
  int BY = 17;
  /** RegularExpression Id. */
  int DESC = 18;
  /** RegularExpression Id. */
  int DISTINCT = 19;
  /** RegularExpression Id. */
  int DROP = 20;
  /** RegularExpression Id. */
  int FROM = 21;
  /** RegularExpression Id. */
  int GROUP = 22;
  /** RegularExpression Id. */
  int HAVING = 23;
  /** RegularExpression Id. */
  int IN = 24;
  /** RegularExpression Id. */
  int INDEX = 25;
  /** RegularExpression Id. */
  int IS = 26;
  /** RegularExpression Id. */
  int KEY = 27;
  /** RegularExpression Id. */
  int LIKE = 28;
  /** RegularExpression Id. */
  int LIMIT = 29;
  /** RegularExpression Id. */
  int NOT = 30;
  /** RegularExpression Id. */
  int NULL = 31;
  /** RegularExpression Id. */
  int OFFSET = 32;
  /** RegularExpression Id. */
  int ON = 33;
  /** RegularExpression Id. */
  int OR = 34;
  /** RegularExpression Id. */
  int ORDER = 35;
  /** RegularExpression Id. */
  int PRIMARY = 36;
  /** RegularExpression Id. */
  int SELECT = 37;
  /** RegularExpression Id. */
  int UNION = 38;
  /** RegularExpression Id. */
  int SPACES = 39;
  /** RegularExpression Id. */
  int TABLE = 40;
  /** RegularExpression Id. */
  int VIEW = 41;
  /** RegularExpression Id. */
  int WHERE = 42;
  /** RegularExpression Id. */
  int CREATE = 43;
  /** RegularExpression Id. */
  int DELETE = 44;
  /** RegularExpression Id. */
  int EXISTS = 45;
  /** RegularExpression Id. */
  int INSERT = 46;
  /** RegularExpression Id. */
  int INTO = 47;
  /** RegularExpression Id. */
  int SET = 48;
  /** RegularExpression Id. */
  int UPDATE = 49;
  /** RegularExpression Id. */
  int VALUES = 50;
  /** RegularExpression Id. */
  int INTEGER_LITERAL = 51;
  /** RegularExpression Id. */
  int FLOATING_POINT_LITERAL = 52;
  /** RegularExpression Id. */
  int EXPONENT = 53;
  /** RegularExpression Id. */
  int STRING_LITERAL = 54;
  /** RegularExpression Id. */
  int BOOLEAN_LITERAL = 55;
  /** RegularExpression Id. */
  int ID = 56;
  /** RegularExpression Id. */
  int LETTER = 57;
  /** RegularExpression Id. */
  int DIGIT = 58;
  /** RegularExpression Id. */
  int ASSIGN = 59;
  /** RegularExpression Id. */
  int CONCAT = 60;
  /** RegularExpression Id. */
  int SEMICOLON = 61;
  /** RegularExpression Id. */
  int DOT = 62;
  /** RegularExpression Id. */
  int TILDE = 63;
  /** RegularExpression Id. */
  int LESS = 64;
  /** RegularExpression Id. */
  int LESSEQUAL = 65;
  /** RegularExpression Id. */
  int GREATER = 66;
  /** RegularExpression Id. */
  int GREATEREQUAL = 67;
  /** RegularExpression Id. */
  int EQUAL = 68;
  /** RegularExpression Id. */
  int NOTEQUAL = 69;
  /** RegularExpression Id. */
  int NOTEQUAL2 = 70;
  /** RegularExpression Id. */
  int JOINPLUS = 71;
  /** RegularExpression Id. */
  int OPENPAREN = 72;
  /** RegularExpression Id. */
  int CLOSEPAREN = 73;
  /** RegularExpression Id. */
  int ASTERISK = 74;
  /** RegularExpression Id. */
  int SLASH = 75;
  /** RegularExpression Id. */
  int PLUS = 76;
  /** RegularExpression Id. */
  int MINUS = 77;
  /** RegularExpression Id. */
  int QUESTIONMARK = 78;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int IN_LINE_COMMENT = 1;
  /** Lexical state. */
  int IN_MULTI_LINE_COMMENT = 2;
  /** Lexical state. */
  int IN_MULTILINE_COMMENT = 3;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\t\"",
    "\"--\"",
    "\"/*\"",
    "<COMMENT_LINE>",
    "\"*/\"",
    "<token of kind 9>",
    "<COMMENT_BLOCK>",
    "\"all\"",
    "\"and\"",
    "\"as\"",
    "\"asc\"",
    "\"begin\"",
    "\"between\"",
    "\"by\"",
    "\"desc\"",
    "\"distinct\"",
    "\"drop\"",
    "\"from\"",
    "\"group\"",
    "\"having\"",
    "\"in\"",
    "\"index\"",
    "\"is\"",
    "\"key\"",
    "\"like\"",
    "\"limit\"",
    "\"not\"",
    "\"null\"",
    "\"offset\"",
    "\"on\"",
    "\"or\"",
    "\"order\"",
    "\"primary\"",
    "\"select\"",
    "\"union\"",
    "\"spaces\"",
    "\"table\"",
    "\"view\"",
    "\"where\"",
    "\"create\"",
    "\"delete\"",
    "\"exists\"",
    "\"insert\"",
    "\"into\"",
    "\"set\"",
    "\"update\"",
    "\"values\"",
    "<INTEGER_LITERAL>",
    "<FLOATING_POINT_LITERAL>",
    "<EXPONENT>",
    "<STRING_LITERAL>",
    "<BOOLEAN_LITERAL>",
    "<ID>",
    "<LETTER>",
    "<DIGIT>",
    "\":=\"",
    "\"||\"",
    "\";\"",
    "\".\"",
    "\"~\"",
    "\"<\"",
    "\"<=\"",
    "\">\"",
    "\">=\"",
    "\"=\"",
    "\"!=\"",
    "\"<>\"",
    "\"(+)\"",
    "\"(\"",
    "\")\"",
    "\"*\"",
    "\"/\"",
    "\"+\"",
    "\"-\"",
    "\"?\"",
    "\",\"",
  };

}
