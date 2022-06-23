package parser;

import java.util.HashMap;
import java.util.LinkedList;

public class Program extends LinkedList<Statement> {
    public Integer execute(HashMap<String, Integer> globals) {
        HashMap<String, Object> locals = new HashMap<>();

        for (Statement stmt : this) {
            Integer result = stmt.execute(locals);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    public Integer execute() {
        return this.execute(new HashMap<>());
    }
}
