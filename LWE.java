import java.security.SecureRandom;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Random;

public class LWE {

    /**
     * 
     * @param n dimension, security parameter
     * @param m number of vectors in PK_a component
     * @param p p>=2 us prime number bet n^2 and 2n^2
     * @param v variance for Gaussian distribution error (0 to v)
     * @return
     */
    public static KeyPair keyGen(int n, int m, int p, int v){
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
                pk_a[i].setElement(j, sr.nextInt(p));
            }
        }
        // generate SK
        for (i = 0; i<n; i++){
            sk.setElement(i, sr.nextInt(p));
        }
        // generate Gaussian error vector of size m
        for (i=0; i<m; i++){
            e.setElement(i, gaussianIntegerRandom(v));
        }
        System.out.println(e.toString());
        // generate PK:b component
        pk_b = sk.mutmal(pk_a,p).add(e,p);
        return new KeyPair(pk_a, pk_b, sk);
    }

    public static CipherText encrypt(boolean bit, Vector[] pubKey_a, Vector pubKey_b, int p){
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
        System.out.println("Subset S: "+S.toString());
        Vector sum_a = new Vector(n,0);
        int sum_b = 0;
        if(bit){
            sum_b = (int) Math.floor(p/2);
        }
        for (int j : S){
            sum_a = sum_a.add(pubKey_a[j],p);
            sum_b = (sum_b + pubKey_b.getElement(j)) % p;
        }
        return new CipherText(sum_a, sum_b);
    }

    public static boolean decrypt(CipherText ct, Vector privKey, int p){
        boolean r;
        int pDiv2 = (int) Math.floor(p/2);
        System.out.println("|p/2|: "+pDiv2);
        Vector[] a = new Vector[1];
        a[0] = ct.getAComponent();
        Vector as = privKey.mutmal(a, p);
        int b_minus_as_mod = mod((ct.getBComponent()-as.getElement(0)),p);
        int dtoPDiv2 = pDiv2 - b_minus_as_mod;
        if (b_minus_as_mod<dtoPDiv2){
            r = false;
        } else {
            r = true;
        }
        return r;
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

