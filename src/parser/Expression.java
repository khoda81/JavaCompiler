package parser;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public abstract class Expression {

    public abstract Integer eval(HashMap<String, Object> locals);

    public static class Number extends Expression {
        public Integer val;

        public Number(Integer val) {
            this.val = val;
        }

        @Override
        public Integer eval(HashMap<String, Object> locals) {
            return val;
        }
    }

    public static class Ident extends Expression {
        public String id;

        public Ident(String id) {
            this.id = id;
        }

        @Override
        public Integer eval(HashMap<String, Object> locals) {
            try {
                return (Integer) locals.get(id);
            } catch (ClassCastException e) {
                throw new RuntimeException("Identifier " + id + " is not an integer");
            }
        }
    }

    public static class Call extends Expression {

        public String id;
        public List<Expression> expr_list;

        public Call(String id, List<Expression> expr_list) {
            this.id = id;
            this.expr_list = expr_list;
        }

        @Override
        public Integer eval(HashMap<String, Object> locals) {
            List<Integer> values = expr_list
                    .stream()
                    .map(expr -> expr.eval(locals))
                    .collect(Collectors.toList());

            switch (id) {
                case "print":
                    for (Integer value : values)
                        System.out.println(value);

                    return null;

                case "input":
                    Scanner scanner = new Scanner(System.in); // System.in is a standard input stream
                    return scanner.nextInt();

                default:
                    // TODO: run subroutine
                    break;
            }

            return null;
        }
    }

    public static class UnaryMinus extends Expression {
        public Expression expr;

        public UnaryMinus(Expression expr) {
            this.expr = expr;
        }

        @Override
        public Integer eval(HashMap<String, Object> locals) {
            return -expr.eval(locals);
        }
    }

    public abstract static class Binary extends Expression {
        public Expression left;
        public Expression right;

        public Binary(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

    }

    public static class Add extends Binary {
        public Add(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public Integer eval(HashMap<String, Object> locals) {
            return left.eval(locals) + right.eval(locals);
        }
    }

    public static class Sub extends Binary {
        public Sub(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public Integer eval(HashMap<String, Object> locals) {
            return left.eval(locals) - right.eval(locals);
        }
    }

    public static class Mul extends Binary {
        public Mul(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public Integer eval(HashMap<String, Object> locals) {
            return left.eval(locals) * right.eval(locals);
        }
    }

    public static class Div extends Binary {
        public Div(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public Integer eval(HashMap<String, Object> locals) {
            return left.eval(locals) / right.eval(locals);
        }
    }

    public static class Mod extends Binary {
        public Mod(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public Integer eval(HashMap<String, Object> locals) {
            return left.eval(locals) % right.eval(locals);
        }
    }

    public static class Pow extends Binary {
        public Pow(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public Integer eval(HashMap<String, Object> locals) {
            return (int) Math.pow(left.eval(locals), right.eval(locals));
        }
    }
}
