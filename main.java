import java.math.BigInteger;
import java.security.SecureRandom;

import utils.CipherText;
import utils.KeyPair;
import utils.ObjectSize;

public class Main {

    public static void main(final String[] args) {

        /* SECURITY PARAMETER (dimension) */
        // log p
        int n = 112;
     
        // Prime number between n^2 and 2n^2 bits (2^8 to 2^16)
        SecureRandom sr = new SecureRandom();
        BigInteger p = new BigInteger(2*(int)Math.pow(n, 2), sr);

        // Rank m = 5n
        int m = 5*n;

        // Variance Gaussian distribution
        // mean = |v/2|
        int v = 3;

        // Message 
        boolean bit = true;

        KeyPair kp = LWE.keyGen(n,m,p,v);
        CipherText ct = LWE.encrypt(bit, kp.getPubKeyA(), kp.getPubKeyB(), p);
        boolean dt = LWE.decrypt(ct, kp.getPrivKey(), p);

        System.out.println("\nBit: "+bit+" ("+((bit)?1:0)+")");
        System.out.println("\nKey Pair:"+kp);
        System.out.println("\nCypher bit: "+ct);
        System.out.println("\nDecrypt bit: "+dt+" ("+((dt)?1:0)+")");
        
        /*
        // Experiment iterations
        System.out.println("\n\nExperiment iterations:\n");
        int[] EXPERIMENT = {1, 2, 4, 8, 16, 32, 64, 128};
        long encryptTime, deryptTime;
        for (int i = 0; i < EXPERIMENT.length; i++) {
            // Generate message 
            boolean[] bits = new boolean[EXPERIMENT[i]];
            for (int j = 0; j < EXPERIMENT[i]; j++) {
                bits[j] = sr.nextBoolean();
            }
            // Encrypt 
            encryptTime = System.nanoTime();
            CipherText[] cts = LWE.encrypt(bits, kp.getPubKeyA(), kp.getPubKeyB(), p);
            encryptTime  = System.nanoTime() - encryptTime;
            // Decrypt 
            deryptTime = System.nanoTime();
            boolean[] dts = LWE.decrypt(cts, kp.getPrivKey(), p);
            deryptTime = System.nanoTime() - deryptTime;

            System.out.println("\tWith " + EXPERIMENT[i] + " bits, Enc: " + (double)encryptTime/1000000 + " millis, " + "Dec: " + (double)deryptTime/1000000 + " millis, CT: " + ObjectSize.getObjectSize(cts) + " bytes.");
        }
        */
       
    }

    public static String toStringArray(boolean[] v) {
        String r = "[";
        for (int i = 0; i < v.length; i++) {
            if (v[i]) {
                r += "1, ";
            } else {
                r += "0, ";
            }
        }
        return r.substring(0, r.length()-2) + "]";
    }

    
   
}



