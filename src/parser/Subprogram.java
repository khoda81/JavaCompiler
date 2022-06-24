package parser;

import java.util.List;

public class Subprogram {
    List<String> arg_names;
    Program body;

    public Subprogram(List<String> args, Program body) {
        this.arg_names = args;
        this.body = body;
    }

    public MyObject run(List<MyObject> call_args, Scope parent) {
        Scope scope = new Scope(parent);
        for (int i = 0; i < arg_names.size(); i++) {
            scope.put(arg_names.get(i), call_args.get(i));
        }

        return body.execute(scope);
    }
}
