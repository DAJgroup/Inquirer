public class Quizz {
    static class A {
        int i = 1;

        public static String greet() {
            return "From A";
        }

        public int order() {
            return 0;
        }
    }

    static class B extends A {
        int i = 2;
        public static String greet() {
            return "From B";
        }

        public int order() {
            return 1;
        }
    }

    public static void main(String[] args) {
        A obj = new B();
        System.out.println(String.format("%s, %d, %d", obj.greet(), obj.order(), obj.i));
    }
}