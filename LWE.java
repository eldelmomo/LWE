import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.LinkedHashSet;
import java.util.Set;

import utils.CipherText;
import utils.KeyPair;
import utils.Vector;

import java.util.Random;

public class LWE {

    private static final BigInteger TWO = new BigInteger("2");

    /**
     * 
     * @param n dimension, security parameter
     * @param m number of vectors in PK_a component
     * @param p p>=2 us prime number bet n^2 and 2n^2
     * @param v variance for Gaussian distribution error (0 to v)
     * @return
     */
    public static KeyPair keyGen(int n, int m, BigInteger p, int v){
        Vector[] pk_a = new Vector[m];
        Vector pk_b = new Vector(m);
        Vector sk = new Vector(n);
        Vector e = new Vector(m);
        SecureRandom sr = new SecureRandom();
        int i = 0, j = 0;
        // generate PK:a component
        for (i = 0; i<m; i++){
            pk_a[i] = new Vector(n);
            for (j = 0; j<n; j++){
                pk_a[i].setElement(j, new BigInteger(p.bitLength()-1, sr));
            }
        }
        // generate SK
        for (i = 0; i<n; i++){
            sk.setElement(i, new BigInteger(p.bitLength()-1, sr));
        }
        // generate Gaussian error vector of size m
        for (i=0; i<m; i++){
            e.setElement(i, BigInteger.valueOf(gaussianIntegerRandom(v)));
        }
        //System.out.println("\nIn keyGen, generated error:\n\t" + e.toString());
        // generate PK:b component
        pk_b = sk.mutmal(pk_a,p).add(e,p);
        return new KeyPair(pk_a, pk_b, sk);
    }

    /**
     * Encryption function of a bit
     * @param bit message to encrypt
     * @param pubKey_a A component of public Key
     * @param pubKey_b B component of public Key
     * @param p  p in Z_p of number space
     * @return encrypted bit in ciphertesxt object
     */
    public static CipherText encrypt(boolean bit, Vector[] pubKey_a, Vector pubKey_b, BigInteger p){
        SecureRandom sr = new SecureRandom();
        int m = pubKey_a.length;
        int n = pubKey_a[0].size();
        // Generate subset S
        Set<Integer> S = new LinkedHashSet<Integer>();
        int siseOfS = sr.nextInt(m-1)+1;
        while (S.size() < siseOfS){
            Integer next = sr.nextInt(m);
            S.add(next);
        }
        //System.out.println("\nIn encrypt, random subset S:\n\t"+S.toString());
        Vector sum_a = new Vector(n,BigInteger.ZERO);
        BigInteger sum_b = BigInteger.ZERO;
        if(bit){
            sum_b = p.divide(TWO);
        }
        for (int j : S){
            sum_a = sum_a.add(pubKey_a[j],p);
            sum_b = (sum_b.add(pubKey_b.getElement(j))).mod(p);
        }
        return new CipherText(sum_a, sum_b);
    }

    /**
     * Encryption function of an array of bits
     *@param bit[] array message to encrypt
     * @param pubKey_a A component of public Key
     * @param pubKey_b B component of public Key
     * @param p  p in Z_p of number space
     * @return encrypted bits in ciphertext array object
     */
    public static CipherText[] encrypt(boolean bit[], Vector[] pubKey_a, Vector pubKey_b, BigInteger p){
        int sizeBit = bit.length;
        CipherText[] ct = new CipherText[sizeBit];
        for (int i = 0; i < sizeBit; i++) {
            ct[i] = encrypt(bit[i], pubKey_a, pubKey_b, p);
        }
        return ct;
    }

    /**
     * Decryption function of a bit
     * @param ct cipher text to decrypt
     * @param privKey private key vector
     * @param p p in Z_p of number space
     * @return decrypted bit in ciphertext object
     */
    public static boolean decrypt(CipherText ct, Vector privKey, BigInteger p){
        boolean r;
        BigInteger pDiv2 = p.divide(TWO);
        Vector[] a = new Vector[1];
        a[0] = ct.getAComponent();
        Vector as = privKey.mutmal(a, p);
        BigInteger b_minus_as_mod = (ct.getBComponent().subtract(as.getElement(0))).mod(p);
        if (b_minus_as_mod.compareTo(pDiv2) > 0) {
            r = true;
        } else {
            BigInteger dtoPDiv2 = (pDiv2.subtract(b_minus_as_mod)).mod(p);
            if (b_minus_as_mod.compareTo(dtoPDiv2) < 0){
                r = false;
            } else {
                r = true;
            }
        }
        return r;
    }

    /**
     * Decryption function of an array of bits
     * @param ct cipher text array to decrypt
     * @param privKey private key vector
     * @param p p in Z_p of number space
     * @return decrypted bit in ciphertext array object
     */
    public static boolean[] decrypt(CipherText[] ct, Vector privKey, BigInteger p){
        int sizeBit = ct.length;
        boolean[] dt = new boolean[sizeBit];
        for (int i = 0; i < sizeBit; i++) {
            dt[i] = decrypt(ct[i], privKey, p);
        }
        return dt;
    }

    /**
     * a mod b
     * @param a value
     * @param b mod
     * @return
     */
    private static int mod(int a, int b){
        return (((a % b) + b) % b);
    }

    /**
     * // Random Gaussian from 0 to variance
     * media = round(variance/2)
     * @param variance
     * @return
     */
    private static int gaussianIntegerRandom(int variance){
        Random r = new Random();
        return mod((int) Math.round(r.nextGaussian() * Math.sqrt(variance) + Math.round(variance/2)),variance);
    }

}

