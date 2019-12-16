package core.basesyntax;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        int n1 = 15;
        int n2 = 13;
        Math.floor(n1);

        System.out.println(Integer.toBinaryString(n1));
        System.out.println(Integer.toBinaryString(n2));
        System.out.println(Integer.toBinaryString(n1 & n2));
        System.out.println(Integer.toBinaryString(n1 | n2));
        System.out.println(Integer.toBinaryString(n1 ^ 1));
        System.out.println(Integer.toBinaryString(n1 >> 1));
        System.out.println(Integer.toBinaryString(n1 << 1));
    }

}
