public class main {
    /**
     * @param args
     */
    public static void main(final String[] args) {
     
        // prime number between n^2 and 2n^2 bits (2^8 to 2^16)
        // p>=2
        int p = 1024;

        //security parameter (dimension)
        // log p
        int n = (int) Math.round(Math.log(p));

        // rank
        // m = 5n
        int m = 5*n;

        // variance Gaussian distribution
        int v = 3;

        // message 
        boolean bit = false;
     
        KeyPair kp = LWE.keyGen(n,m,p,v);

        System.out.println(kp);

        CipherText ct = LWE.encrypt(bit, kp.getPubKeyA(), kp.getPubKeyB(), p);

        System.out.println("Bit: "+bit+" ("+((bit)?1:0)+")");
        System.out.println("Cypher bit: "+ct);

        boolean dt = LWE.decrypt(ct, kp.getPrivKey(), p);

        System.out.println("Decrypt bit: "+dt+" ("+((dt)?1:0)+")");

    }
}
