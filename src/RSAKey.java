import java.math.BigInteger;

public class RSAKey {

    private Boolean publicKey = true;

    private BigInteger n;

    //e or d
    private BigInteger i;

    public RSAKey(Boolean isPublic, BigInteger n, BigInteger i){
        this.publicKey = isPublic;
        this.n = n;
        this.i = i;
    }


}
