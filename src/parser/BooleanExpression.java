package parser;

public abstract class BooleanExpression {

    public abstract Boolean eval(Scope locals);

    public static class Not extends BooleanExpression {
        public BooleanExpression expr;

        public Not(BooleanExpression e) {
            this.expr = e;
        }

        @Override
        public Boolean eval(Scope locals) {
            return !expr.eval(locals);
        }
    }

    public abstract static class Comparison extends BooleanExpression {
        public Expression left;
        public Expression right;

        public Comparison(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }
    }

    public static class Eq extends Comparison {
        public Eq(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public Boolean eval(Scope locals) {
            return left.eval(locals) == right.eval(locals);
        }
    }

    public static class Ne extends Comparison {
        public Ne(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public Boolean eval(Scope locals) {
            return left.eval(locals) != right.eval(locals);
        }
    }

    public static class Lt extends Comparison {
        public Lt(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public Boolean eval(Scope locals) {
            return (Integer) left.eval(locals).value < (Integer) right.eval(locals).value;
        }
    }

    public static class Gt extends Lt {
        public Gt(Expression left, Expression right) {
            super(right, left);
        }
    }

    public static class Le extends Comparison {
        public Le(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public Boolean eval(Scope locals) {
            return (Integer) left.eval(locals).value <= (Integer) right.eval(locals).value;
        }
    }

    public static class Ge extends Le {
        public Ge(Expression left, Expression right) {
            super(right, left);
        }
    }

    public abstract static class Logical extends BooleanExpression {
        public BooleanExpression left;
        public BooleanExpression right;

        public Logical(BooleanExpression left, BooleanExpression right) {
            this.left = left;
            this.right = right;
        }
    }

    public static class And extends Logical {
        public And(BooleanExpression left, BooleanExpression right) {
            super(left, right);
        }

        @Override
        public Boolean eval(Scope locals) {
            return left.eval(locals) && right.eval(locals);
        }
    }

    public static class Or extends Logical {
        public Or(BooleanExpression left, BooleanExpression right) {
            super(left, right);
        }

        @Override
        public Boolean eval(Scope locals) {
            return left.eval(locals) || right.eval(locals);
        }
    }

    public static class Expr extends BooleanExpression {
        public Expression expr;

        public Expr(Expression expr) {
            this.expr = expr;
        }

        @Override
        public Boolean eval(Scope locals) {
            return (Integer) expr.eval(locals).value != 0;
        }
    }

    public static class True extends Expr {

        public True() {
            super(new Expression.Number(1));
        }
    }

    public static class False extends Expr {

        public False() {
            super(new Expression.Number(0));
        }
    }
}
