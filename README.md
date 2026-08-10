# JavaCompiler

A small compiler front end built with Java, JFlex, and CUP. It lexes and parses a simple imperative language with variables, expressions, control flow, functions, and nested scopes.

## Prerequisites

* Java
* JFlex
* CUP

## Structure

```text
.
├── README.md
├── cup
│   └── Parser.cup                  CUP parser specification
├── flex
│   └── Scanner.flex                JFlex scanner specification
└── src
    ├── lexer
    │   └── Lexer.java              generated lexer
    ├── parser
    │   ├── BooleanExpression.java
    │   ├── Expression.java
    │   ├── MyObject.java
    │   ├── Parser.java             generated parser
    │   ├── Program.java
    │   ├── Scope.java
    │   ├── Statement.java
    │   ├── Subprogram.java
    │   ├── Symbols.java            generated symbol table
    └── Main.java
```

## Language

### Keywords

`if` `else` `while` `for` `func` `return`

### Operators

`=` `==` `!=` `<` `>` `<=` `>=`  
`&&` `||`  
`+` `-` `*` `/` `%` `++` `--`

## Usage

```text
Usage: <main class> [-dp] [--help] FILE...
  FILE...       source files
  -d, --debug   print debugging information
  -p, --parse   parse the file instead of only lexing it
      --help    display help and exit
```

Built by [Mahdi Khodabandeh](https://github.com/khoda81).
