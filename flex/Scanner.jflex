
package lexer;

import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

%%

%public
%class Lexer
%cup
%implements parser.Symbols
%ignorecase
%char
%line
%column

WS = [ \t\n\r]+

Number = \d+
String = \"((\\.|[^\"\r\n])*)\" | \'((\\.|[^\'\r\n])*)\'

// Symbol = [\[\]\(\)\{\}\,\;\?\:\.\@\#\$\`]
// Keyword = (
//     "abstract" | "assert" | "boolean" | "break" | "byte" |
//     "case" | "catch" | "char" | "class" | "const" | "continue" |
//     "default" | "do" | "double" | "else" | "enum" | "extends" |
//     "final" | "finally" | "float" | "for" | "goto" | "if" |
//     "implements" | "import" | "instanceof" | "int" | "interface" |
//     "long" | "native" | "new" | "package" | "private" | "protected" |
//     "public" | "return" | "short" | "static" | "strictfp" |
//     "super" | "switch" | "synchronized" | "this" | "throw" |
//     "throws" | "transient" | "try" | "void" | "volatile" | "while"
// )

Identifier = [a-zA-Z_] [a-zA-Z_0-9]*

// AssignmentOperator = "=" | "+=" | "-=" | "*=" | "/=" | "&=" | "|=" | "^=" | "%=" | "<<=" | ">>=" | ">>>=" | "++" | "--"

Comment = "//" [^\r\n]* | "/*" [^*] ~"*/" | "/*" "*"+ "/"

%{
    ComplexSymbolFactory symbolFactory;

    public Lexer(java.io.Reader in, ComplexSymbolFactory sf) {
        this(in);
        symbolFactory = sf;
    }

    private Symbol symbol(String name, int type, Object value) {
        Location left = new Location(yyline + 1, yycolumn + 1, (int) yychar);
        Location right = new Location(yyline + 1, yycolumn + yylength() + 1, (int) yychar + yylength());
        return symbolFactory.newSymbol(name, type, left, right, value);
    }

    private Symbol symbol(String name, int type) {
        return symbol(name, type, null);
    }

    private Symbol symbol(int type, Object value) {
        return symbol(yytext(), type, value);
    }

    private Symbol symbol(int type) {
        return symbol(type, null);
    }
%}

%eofval{ 
 return symbol("EOF", EOF); 
%eofval}

%%

"if"            { return symbol(IF); }
"else"          { return symbol(ELSE); }
"while"         { return symbol(WHILE); }
"for"           { return symbol(FOR); }
"func"          { return symbol(FUNC); }
"return"        { return symbol(RETURN); }

"true"          { return symbol(TRUE); }
"false"         { return symbol(FALSE); }

{Number}        { return symbol(NUMBER, Integer.parseInt(yytext())); }
{Identifier}    { return symbol(IDENT, yytext()); }

"+"             { return symbol(PLUS); }
"-"             { return symbol(MINUS); }
"*"             { return symbol(TIMES); }
"/"             { return symbol(DIVIDE); }
"%"             { return symbol(MODULO); }
"**"            { return symbol(POWER); }

"^"             { return symbol(XOR); }
"&"             { return symbol(BITWISE_AND); }
"|"             { return symbol(BITWISE_OR); }
"!"             { return symbol(NOT); }
"~"             { return symbol(BITWISE_NOT); }
"<<"            { return symbol(SHIFT_LEFT); }
">>"            { return symbol(SHIFT_RIGHT); }
">>>"           { return symbol(UNSIGNED_SHIFT_RIGHT); }

"=="            { return symbol(EQ); }
"!="            { return symbol(NE); }
"<"             { return symbol(LT); }
">"             { return symbol(GT); }
"<="            { return symbol(LE); }
">="            { return symbol(GE); }

"&&"            { return symbol(AND); }
"||"            { return symbol(OR); }

";"             { return symbol(SEMI); }
","             { return symbol(COMMA); }
"("             { return symbol(LPAREN); }
")"             { return symbol(RPAREN); }
"{"             { return symbol(LCBRAC); }
"}"             { return symbol(RCBRAC); }

"="             { return symbol(ASSIGN); }

{Comment}       {  }
{WS}            {  }
.               { System.out.println("Unexpected character: " + yytext() + " at line " + yyline + ", column " + yycolumn); }
