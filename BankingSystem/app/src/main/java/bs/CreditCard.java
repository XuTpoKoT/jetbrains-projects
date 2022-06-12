package bs;

import java.util.Random;

public class CreditCard {
    private char[] number; // String is better?
    private char[] pin;
    private long balance;
    private static final int digitsInNumber = 15;
    private static final int digitsInPin = 4;
    private static Random random = new Random();

    public CreditCard() {
        this.number = genNumber();
        long remainder = luhnRemainder(number);
        if (remainder != 0) {
            this.number[digitsInNumber - 1] += 10 - remainder;
        }
        this.pin = genPin();
        this.balance = 0L;
    }

    public char[] getNumber() {
        return number;
    }

    public char[] getPin() {
        return pin;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    static private char[] genNumber() {
        char[] number = "4000007890123456".toCharArray();
        for (int i = 6; i < digitsInNumber; i++) {
            number[i] = (char)(random.nextInt(10) + '0');
        }

        return number;
    }

    static private char[] genPin() {
        char[] pin = "1234".toCharArray();
        for (int i = 0; i < digitsInPin; i++) {
            pin[i] = (char)(random.nextInt(10) + '0');
        }

        return pin;
    }

    static private int luhnRemainder(char[] number) {
        int result = 0;
        for (int i = 0; i < digitsInNumber; i++) {
            int digit = Character.getNumericValue(number[i]);
            if (i % 2 == 0) {
                int doubleDigit = digit * 2 > 9 ? digit * 2 - 9 : digit * 2;
                result += doubleDigit;
                continue;
            }
            result += digit;
        }

        return result % 10;
    }

    static public void checkLuhnRemainder() {
//        System.out.println(luhnRemainder(123L) == 8L);
//        System.out.println(luhnRemainder(4000008449433403L) == 0L); //sys
//        System.out.println(luhnRemainder(4000004938320895L)); //sys
//        System.out.println(luhnRemainder(4000001658179857L));
//        System.out.println(luhnRemainder(8L) == 8L);
        Long x = 4000001062014287L;
        Long y = 4000004136620337L;
        Long z = 4000000757157666L;
    }
}
