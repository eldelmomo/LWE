
public class KeyPair {

    private Vector[] pubKey_a;
    private Vector pubKey_b;
    private Vector privKey;

    public KeyPair(Vector[] pubKey_a, Vector pubKey_b, Vector privKey){
        this.pubKey_a = pubKey_a;
        this.pubKey_b = pubKey_b;
        this.privKey = privKey;
    }

    public Vector[] getPubKeyA(){
        return pubKey_a;
    }

    public Vector getPubKeyB(){
        return pubKey_b;
    }

    public Vector getPrivKey(){
        return privKey;
    }

    @Override
    public String toString() {
        String r = "Public Key, A component:\n";
        for (Vector vector : pubKey_a) {
            r += "\t"+vector.toString() + "\n";
        }
        r += "Public Key, B component:\n\t"+pubKey_b.toString()+"\nPrivate Key:\n\t"+privKey.toString();
       return r;
    }
}
