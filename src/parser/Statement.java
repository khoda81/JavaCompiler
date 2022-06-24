
package parser;

import java.util.HashSet;
import java.util.List;

public abstract class Statement {

    public abstract MyObject execute(Scope locals);

    public static class Assign extends Statement {
        public String id;
        public Expression expr;

        public Assign(String id, Expression exp) {
            this.id = id;
            this.expr = exp;
        }

        @Override
        public MyObject execute(Scope locals) {
            locals.put(id, expr.eval(locals));
            return null;
        }
    }

    public static class ExpressionStatement extends Statement {
        public Expression expr;

        public ExpressionStatement(Expression exp) {
            this.expr = exp;
        }

        @Override
        public MyObject execute(Scope locals) {
            expr.eval(locals);
            return null;
        }
    }

    public static class If extends Statement {
        BooleanExpression expr;
        Program then_branch, else_branch;

        public If(BooleanExpression e, Program b1, Program b2) {
            this.expr = e;
            this.then_branch = b1;
            this.else_branch = b2;
        }

        @Override
        public MyObject execute(Scope locals) {
            if (expr.eval(locals)) {
                return then_branch.execute(locals);
            } else {
                return else_branch.execute(locals);
            }
        }
    }

    public static class Block extends Statement {
        public Program statements;

        public Block(Program sl) {
            this.statements = sl;
        }

        @Override
        public MyObject execute(Scope locals) {
            for (Statement stmt : statements) {
                MyObject result = stmt.execute(locals);
                if (result != null) {
                    return result;
                }
            }

            return null;
        }
    }

    public static class Function extends Statement {
        public String id;
        public List<String> arg_names;
        public Program body;

        public Function(String id, List<String> arg_names, Program body) {
            this.id = id;
            // check if there is any duplicate argument name
            if (arg_names.size() != new HashSet<>(arg_names).size()) {
                throw new RuntimeException("Duplicate argument name");
            }

            this.arg_names = arg_names;
            this.body = body;
        }

        @Override
        public MyObject execute(Scope locals) {
            locals.put(id, new MyObject(new Subprogram(arg_names, body)));
            return null;
        }
    }

    public static class Return extends Statement {
        public Expression expr;

        public Return(Expression exp) {
            this.expr = exp;
        }

        @Override
        public MyObject execute(Scope locals) {
            return expr.eval(locals);
        }
    }

    public static class While extends Statement {
        public BooleanExpression expr;
        public Program body;

        public While(BooleanExpression e, Program b) {
            this.expr = e;
            this.body = b;
        }

        @Override
        public MyObject execute(Scope locals) {
            while (expr.eval(locals)) {
                MyObject result = body.execute(locals);
                if (result != null) {
                    return result;
                }
            }

            return null;
        }
    }
}
