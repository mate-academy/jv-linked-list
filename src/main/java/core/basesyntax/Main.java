package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        int n1 = 15;
        int n2 = 13;

        System.out.println(Integer.toBinaryString(n1));
        System.out.println(Integer.toBinaryString(n2));
        System.out.println(Integer.toBinaryString(n1 & n2));
        System.out.println(Integer.toBinaryString(n1 | n2));
        System.out.println(Integer.toBinaryString(n1 ^ 1));
        System.out.println(Integer.toBinaryString(n1 >> 1));
        System.out.println(Integer.toBinaryString(n1 << 1));
    }
}
