package utils;

import java.math.BigInteger;

public class CipherText {

    private Vector a_component;
    private BigInteger b_component;

    public CipherText (Vector a_component, BigInteger b_component){
        this.a_component = a_component;
        this.b_component = b_component;
    }

    public Vector getAComponent(){
        return a_component;
    }

    public BigInteger getBComponent(){
        return b_component;
    }

    public String toString(){
        String r = "{[";
        for (int i = 0; i<a_component.size(); i++){
            r = r + a_component.getElement(i) + ",";
        }
        r = r.substring(0, r.length()-1) + "],";
        r = r + b_component + "}";
        return r;
    }
}
