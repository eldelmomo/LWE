package utils;

import java.math.BigInteger;

public class Vector {
    
    private BigInteger[] data;

    public Vector(){
    }

    /**
     * Empty vector with determined size
     * @param size
     */
    public Vector(int size){
        this.data = new BigInteger[size];
    }

    /**
     * Vector with only <num> in components
     * @param size
     * @param num
     */
    public Vector(int size, BigInteger num){
        data = new BigInteger[size];
        for (int i = 0; i<size; i++){
            data[i] = num;
        }
    }

    /**
     * Construct vector with determined elements
     * @param data
     */
    public Vector(BigInteger[] data){
        this.data = data;
    }

    public Vector(int[] data){
        this.data = new BigInteger[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = BigInteger.valueOf(data[i]);
        }
    }

    public void setElement(int index, BigInteger val){
        data[index] = val;
    }

    public BigInteger getElement(int index){
        return data[index];
    }

    public void setData(BigInteger[] data){
        this.data = data;
    }

    public BigInteger[] getData(){
        return data;
    }

    public int size(){
        return data.length;
    }

    public String toString(){
        String r = "[";
        for (int i = 0; i<data.length; i++){
            r = r + data[i] + ", ";
        }
        return r.substring(0, r.length()-2) + "]";
    }

    public Vector add(Vector val, BigInteger mod){
        Vector r = new Vector(data.length,BigInteger.ZERO);
        for (int i = 0; i<data.length; i++){
            r.setElement(i,(data[i].add(val.getElement(i))).mod(mod));
        }
        return r;
    }

    public Vector multiply(Vector val, BigInteger mod){
        Vector r = new Vector(data.length);
        for (int i = 0; i<data.length; i++){
            r.setElement(i,(data[i].multiply(val.getElement(i))).mod(mod));
        }
        return r;
    }

    public Vector multiply(BigInteger val, BigInteger mod){
        Vector r = new Vector(data.length);
        for (int i = 0; i<data.length; i++){
            r.setElement(i,(data[i].multiply(val)).mod(mod));
        }
        return r;
    }

    public Vector mutmal(Vector[] val, BigInteger mod){
        int n = val.length;
        int m = data.length;
        Vector r = new Vector(n,BigInteger.ZERO);
        for(int i = 0; i<n; i++){
            for(int j = 0; j<m; j++){
                r.setElement(i, (r.getElement(i).add((data[j].multiply(val[i].getElement(j))).mod(mod))).mod(mod));
            }
        }
        return r;
    }

}
