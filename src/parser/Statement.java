
package parser;

import java.util.HashMap;

public abstract class Statement {

    public abstract Integer execute(HashMap<String, Object> locals);

    public static class Assign extends Statement {
        public String id;
        public Expression expr;

        public Assign(String id, Expression exp) {
            this.id = id;
            this.expr = exp;
        }

        @Override
        public Integer execute(HashMap<String, Object> locals) {
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
        public Integer execute(HashMap<String, Object> locals) {
            expr.eval(locals);
            return null;
        }
    }

    public static class If extends Statement {
        BooleanExpression expr;
        Statement then_branch;
        Statement else_branch;

        public If(BooleanExpression e, Statement b1, Statement b2) {
            this.expr = e;
            this.then_branch = b1;
            this.else_branch = b2;
        }

        @Override
        public Integer execute(HashMap<String, Object> locals) {
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
        public Integer execute(HashMap<String, Object> locals) {
            for (Statement stmt : statements) {
                Integer result = stmt.execute(locals);
                if (result != null) {
                    return result;
                }
            }

            return null;
        }
    }
}
