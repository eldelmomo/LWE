public class Vector {
    
    private int[] data;

    public Vector(){
    }

    /**
     * Empty vector with determined size
     * @param size
     */
    public Vector(int size){
        this.data = new int[size];
    }

    /**
     * Vector with only <num> in components
     * @param size
     * @param num
     */
    public Vector(int size, int num){
        data = new int[size];
        for (int i = 0; i<size; i++){
            data[i] = num;
        }
    }

    /**
     * Construct vector with determined elements
     * @param data
     */
    public Vector(int[] data){
        this.data = data;
    }

    public void setElement(int index, int val){
        data[index] = val;
    }

    public int getElement(int index){
        return data[index];
    }

    public void setData(int[] data){
        this.data = data;
    }

    public int[] getData(){
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

    public Vector add(Vector val, int mod){
        Vector r = new Vector(data.length,0);
        for (int i = 0; i<data.length; i++){
            r.setElement(i,(data[i] + val.getElement(i)) % mod);
        }
        return r;
    }

    public Vector multiply(Vector val, int mod){
        Vector r = new Vector(data.length);
        for (int i = 0; i<data.length; i++){
            r.setElement(i,data[i] * val.getElement(i) % mod);
        }
        return r;
    }

    public Vector multiply(int val, int mod){
        Vector r = new Vector(data.length);
        for (int i = 0; i<data.length; i++){
            r.setElement(i,data[i] * val % mod);
        }
        return r;
    }

    public Vector mutmal(Vector[] val, int mod){
        int n = val.length;
        int m = data.length;
        Vector r = new Vector(n,0);
        for(int i = 0; i<n; i++){
            for(int j = 0; j<m; j++){
                r.setElement(i, (r.getElement(i) + (data[j] * val[i].getElement(j) % mod)) % mod);
            }
        }
        return r;
    }

}
