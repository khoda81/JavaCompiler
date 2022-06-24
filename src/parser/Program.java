package parser;

import java.util.LinkedList;

public class Program extends LinkedList<Statement> {

    public MyObject execute() {
        return this.execute(new Scope());
    }

    public MyObject execute(Scope scope) {
        for (Statement stmt : this) {
            MyObject result = stmt.execute(scope);
            if (result != null) {
                return result;
            }
        }

        return null;
    }
}
