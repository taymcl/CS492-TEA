import java.io.*;
    public class TeaImplementation
    {
        static int sum = 0x0;
        static int delta = 0x9e3779b9;
        static int L = 0x0FCA4567; // <-- Enter left plaintext
        static int R = 0x0CABCDEF; // <-- Enter right plaintext
        static int K[] = {0xBF6BABCD, 0xEF00F000, 0xFEAFAFAF, 0xACCDEF01}; // <-- Enter key
        public static void main(String[] args)
        {
            System.out.println(String.format("Plaintext is: "+"0x%08x", L) + "" + String.format("%08x  ", R));
            int[] ciphertext = teaEncrypt(L, R);
            String plaintext = teaDecrypt(ciphertext[0], ciphertext[1]);
            L = Integer.parseInt(plaintext.substring(String.valueOf(L).length()), 16);
            R = Integer.parseInt(plaintext.substring(String.valueOf(R).length()), 16);
            System.out.println(String.format("Key used: 0x%08x%08x%08x%08x ", K[0], K[1], K[2], K[3]));
        }
        //Part 1
        public static int[] teaEncrypt (int L, int R)
        {
            sum = 0;
            for(int i =0; i  <32; i++)
            {
                sum  += delta;
                L += (R << 4 & 0xfffffff0 ) + K[0] ^ R + sum ^ (R >>> 5 & 0x7ffffff) + K[1];
                R += (L << 4 & 0xfffffff0 ) + K[2] ^ L + sum ^ (L >>> 5 & 0x7ffffff) + K[3];
            }
            System.out.println(String.format("Cipher Text is: "+"0x%08x", L) + "" + String.format("%08x  ", R));
            int[] ciphertext = {L, R};
            return ciphertext;
        }
        //Part 2
        public static String teaDecrypt (int L, int R)
        {
            sum = delta << 5;
            for ( int i=0; i<32; i++)
            {
                R -= (L << 4 & 0xfffffff0) + K[2] ^ L + sum ^ (L >>> 5 & 0x7ffffff) + K[3];
                L -= (R << 4 & 0xfffffff0) + K[0] ^ R + sum ^ (R >>> 5 & 0x7ffffff) + K[1];
                sum -= delta;
            }
            System.out.println(String.format("Decrypted Text is: "+"0x%08x", L) + "" + String.format("%08x    ", R));
            String plaintext = Integer.toString(L, 16) + Integer.toString(R, 16);
            return plaintext;
        }
    }

//Part 3
//An initialization vector will need to be generated randomly to start the encryption process that would be
//XOERed with the first plaintext block before the rest can be encrypted. Then the rest
//of the plaintext blocks would have to be XOR'ed with the previous ciphertext block.
//You could put L and R into an array P{L,R} and at beginning of each round,
//XOR the P{L,R} with the previos C{L,R} then continue the algorithm normally.

