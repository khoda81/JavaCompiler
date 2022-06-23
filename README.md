# JavaCompiler

## Prerequisites

* [Java](https://www.java.com/en/download/)
* [JFlex](https://jflex.de/)
* [CUP](http://www2.cs.tum.edu/projects/cup/)
* [VSCode](https://code.visualstudio.com/)
* VSCode's [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

## Structure

### File Structure

```text
.
├── README.md
├── src
│   ├── lexer_rule
│   │   └── grammar.jflex       jflex grammar file
│   ├── parser_rule
│   │   └── grammar.cup         CUP grammar file
│   ├── Lexer.java:         lexer autoGenerated by JFlex
│   ├── LexerMain.java:     main class for running lexer
│   ├── Parser.java:        parser autoGenerated by CUP
│   ├── ParserMain.java:    main class for running parser
│   └── sym.java:           list of symbols autoGenerated by CUP
└── grammar.jflex:      main grammar file
```

### Classes

* ### LexerMain

    ```text
    Usage: <main class> [--help] [-e=ENCODING] FILE...
    Lexer for the java language
          FILE...               Files whose contents to display
      -e, --encoding=ENCODING   encoding of the input file
          --help                display this help and exit
    Copyright(c) 2022
    ```

* ### Token

    A struct to store token information.

    ```java
    public static class Token {
        public String value;
        public TokenType type;
        public int line;
        public int column;

        ...
    }
    ```

* ### TokenType

    An enum to store token type.

    ```java
    public enum TokenType {
        NUMBER,
        STRING,
        RESERVED,
        IDENTIFIER,
        SYMBOL,
        KEYWORD,
        OPERATOR,
    }
    ```

    | TokenType      | Regex | Examples |
    | -------------- | ----- | -------- |
    | Number | `\d+\.?\d* \| \.\d+` | `12`, `12.3`, `2.`, `.5`|
    | String | `\"((\\.\|[^\"\r\n])*)\" \| \'((\\.\|[^\'\r\n])*)\'` | `"Hello World!"`, `'Hello World!'`|
    | Reserved | `compiler\|dxt\.init\(\)\|EOF` | `compiler`, `dxt.init()`, `EOF`|
    | Identifier | `[a-zA-Z_][a-zA-Z0-9_]*` | `test`|
    | Symbol | ```[\[\]\(\)\{\}\,\;\?\:\.\@\#\$\`]``` | `(` `)` `[` `]` `{` `}` `.` `,` `;` `?` `:` `:` `@` `#` `\` `$` ``` ` ```|

    <br/>

    <details>
    <summary> <b> Keywords </b> </summary>
    <br/>

    `abstract` `assert` `boolean` `break` `byte` `case` `catch` `char` `class` `const` `continue` `default` `do` `double` `else` `enum` `extends` `final` `finally` `float` `for` `goto` `if` `implements` `import` `instanceof` `int` `interface` `long` `native` `new` `package` `private` `protected` `public` `return` `short` `static` `strictfp` `super` `switch` `synchronized` `this` `throw` `throws` `transient` `try` `void` `volatile` `while`

    </details>
    <br/>
    <details>
    <summary> <b> Operators </b> </summary>
    <br/>

    `=` `+=` `-=` `*=` `/=` `&=` `|=` `^=` `%=` `<<=` `>>=` `>>>=` \
    `==` `!=` `<` `>` `<=` `>=` \
    `&&` `||` \
    `+` `-` `*` `/` `%` `++` `--` \
    `^` `&` `|` `!` `~` `<<` `>>` `>>>`
    </details>
