import java.io.File;
import java.util.concurrent.Callable;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import parser.Parser;
import parser.Program;
import parser.Symbols;
import lexer.Lexer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(footer = "Copyright(c) 2022", description = "Lexer for the java language")
class Main implements Callable<Integer> {
    @Parameters(paramLabel = "FILE", description = "Files whose contents to display", arity = "1..*")
    File[] files;

    @Option(names = "--help", usageHelp = true, description = "display this help and exit")
    boolean help;

    @Option(names = { "-p", "--parse" }, description = "Parse the file instead of lexing it")
    boolean parse;

    @Option(names = { "-d", "--debug" }, description = "Print debugging information")
    boolean debug;

    @Override
    public Integer call() throws Exception {
        if (help) {
            CommandLine.usage(this, System.out);
            return 0;
        }

        ComplexSymbolFactory csf = new ComplexSymbolFactory();

        for (File file : files) {
            Lexer scanner = null;
            try {
                java.io.Reader reader = new java.io.FileReader(file);

                scanner = new Lexer(reader, csf);
                System.out.println("==================== " + file.getPath() + " ====================");

                if (parse) {
                    Parser parser = new Parser(scanner, csf);
                    Program program = (Program) parser.parse().value;
                    program.execute();

                } else {
                    System.out.printf("%-15s %-15s %4s:%s\n", "Type", "Value", "line", "column");
                    Symbol token = scanner.next_token();
                    while (token.sym != Symbols.EOF) {
                        if (token.value != null) {
                            System.out.printf("%s %s%n", token, token.value);
                        } else {
                            System.out.printf("%s%n", token);
                        }

                        token = scanner.next_token();
                    }
                }
            } catch (java.io.FileNotFoundException e) {
                System.out.println("File not found : \"" + file.getPath() + "\"");
            } catch (java.io.IOException e) {
                System.out.println("I/O error while scanning file \"" + file.getPath() + "\"");
                System.out.println(e);
            } catch (Exception e) {
                if (debug) {
                    System.out.println("Unexpected exception:");
                    e.printStackTrace();
                }
            }

            System.out.println();
        }

        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}