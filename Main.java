package main;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static boolean isPrimitiveRoot(BigInteger g, BigInteger x) {
        boolean[] visited = new boolean[x.intValue()];
        BigInteger result = BigInteger.ONE;
        for (int i = 0; i < x.intValue() - 1; i++) {
            result = result.multiply(g).mod(x);
            if (visited[result.intValue()]) {
                return false;
            }
            visited[result.intValue()] = true;
        }
        return true;
    }

    private static BigInteger generateRandomNumber(BigInteger maxExclusive) {
        Random random = new Random();
        BigInteger randomNumber;
        do {
            randomNumber = new BigInteger(maxExclusive.bitLength(), random);
        } while (randomNumber.compareTo(BigInteger.ONE) < 0 || randomNumber.compareTo(maxExclusive) >= 0);
        return randomNumber;
    }

  	public static void main(String[] args) {
        System.out.print(" Please enter a message: ");
        Scanner in = new Scanner(System.in);
        BigInteger message = in.nextBigInteger();
        Random R = new Random();
        int maxLength = 14;
        BigInteger q = BigInteger.probablePrime(maxLength, R);

        System.out.println(" Select q randomly is: " + q);
        System.out.println(" Primitive roots for " + q + ": ");
        
        List<BigInteger> primitiveRoots = new ArrayList<>();
        for (BigInteger g = BigInteger.valueOf(2); g.compareTo(q) < 0; g = g.add(BigInteger.ONE)) {
            if (isPrimitiveRoot(g, q)) {
                System.out.print(" " + g + ",");
                primitiveRoots.add(g);
            }
        }
        System.out.println("\n");

        if (primitiveRoots.isEmpty()) {
            System.out.println(" No primitive roots found for " + q);
        } else {
            BigInteger randomPrimitiveRoot = primitiveRoots.get(R.nextInt(primitiveRoots.size()));
            System.out.println(" Random primitive root(a): " + randomPrimitiveRoot);

            BigInteger XA = generateRandomNumber(q.subtract(BigInteger.ONE));
            System.out.println(" the value of XA is: " + XA);

            BigInteger YA = randomPrimitiveRoot.modPow(XA, q);
            System.out.println(" the value of YA is: " + YA);
            
            BigInteger k = generateRandomNumber(q.subtract(BigInteger.ONE));
            BigInteger K = YA.modPow(k, q);
            System.out.println(" the value of k is: " + K);

            BigInteger C1 = randomPrimitiveRoot.modPow(k, q);
            System.out.println(" the value of c1 is: " + C1); 

            BigInteger C2 = message.multiply(K).mod(q);
            System.out.println(" the value of c2 is: " + C2); 
            
            System.out.println(" the CipherText (C1, C2) : (" + C1 + ", " + C2 + ")");

            BigInteger KD = C1.modPow(XA, q);
            System.out.println(" the value of K decrypted  : " + KD);
            
            BigInteger base = BigInteger.valueOf(K.intValue());
            BigInteger modulus = BigInteger.valueOf(q.intValue());
            BigInteger inverse = base.modInverse(modulus);
            BigInteger MD = inverse.multiply(C2).mod(q);
            System.out.println(" Decrypted message : " + MD);
        }
    }
}
