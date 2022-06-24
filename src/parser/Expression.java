package parser;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public abstract class Expression {

    public abstract MyObject eval(Scope locals);

    public static class Number extends Expression {
        public Integer val;

        public Number(Integer val) {
            this.val = val;
        }

        @Override
        public MyObject eval(Scope locals) {
            return new MyObject(val);
        }
    }

    public static class Ident extends Expression {
        public String id;

        public Ident(String id) {
            this.id = id;
        }

        @Override
        public MyObject eval(Scope locals) {
            try {
                return locals.get(id);
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
        public MyObject eval(Scope scope) {
            List<MyObject> arguments = expr_list
                    .stream()
                    .map(expr -> expr.eval(scope))
                    .collect(Collectors.toList());

            switch (id) {
                case "print" -> {
                    for (MyObject argument : arguments)
                        System.out.println(argument.value);
                    return null;
                }
                case "input" -> {
                    try (Scanner scanner = new Scanner(System.in)) {
                        return new MyObject(scanner.nextInt());
                    }
                }
                case "random" -> {
                    // pick a random number from start..end (exclusive)
                    assert arguments.size() == 2;

                    Integer start = (Integer) arguments.get(0).value;
                    Integer end = (Integer) arguments.get(1).value;

                    assert end > start;

                    return new MyObject(start + (int) (Math.random() * (end - start)));
                }
                default -> {
                    Subprogram subprogram = (Subprogram) scope.get(id).value;
                    return subprogram.run(arguments, scope);
                }
            }
        }
    }

    public static class UnaryMinus extends Expression {
        public Expression expr;

        public UnaryMinus(Expression expr) {
            this.expr = expr;
        }

        @Override
        public MyObject eval(Scope locals) {
            return new MyObject(-(Integer) expr.eval(locals).value);
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
        public MyObject eval(Scope locals) {
            return new MyObject((Integer) left.eval(locals).value + (Integer) right.eval(locals).value);
        }
    }

    public static class Sub extends Binary {
        public Sub(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public MyObject eval(Scope locals) {
            return new MyObject((Integer) left.eval(locals).value - (Integer) right.eval(locals).value);
        }
    }

    public static class Mul extends Binary {
        public Mul(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public MyObject eval(Scope locals) {
            return new MyObject((Integer) left.eval(locals).value * (Integer) right.eval(locals).value);
        }
    }

    public static class Div extends Binary {
        public Div(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public MyObject eval(Scope locals) {
            return new MyObject((Integer) left.eval(locals).value / (Integer) right.eval(locals).value);
        }
    }

    public static class Mod extends Binary {
        public Mod(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public MyObject eval(Scope locals) {
            return new MyObject((Integer) left.eval(locals).value % (Integer) right.eval(locals).value);
        }
    }

    public static class Pow extends Binary {
        public Pow(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public MyObject eval(Scope locals) {
            return new MyObject((Integer) left.eval(locals).value ^ (Integer) right.eval(locals).value);
        }
    }
}
