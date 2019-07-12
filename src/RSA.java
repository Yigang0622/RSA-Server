import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class RSA {


    private int p;
    private int q;
    private int n;
    private int phi;
    private int d;
    private int e;

    private int[] publicKey = {0,0};
    private int[] privateKey = {0,0};


    public RSA(int p,int q){
        this.p = p;
        this.q = q;
        this.n = p * q;
        this.phi = (p - 1) * (q - 1);
        this.d = 157;
        this.d = generateD(phi);

        generatePublicKey();
        generatePrivateKey();

        System.out.println("p:"+p);
        System.out.println("q:"+q);
        System.out.println("n:"+n);
        System.out.println("phi:"+ phi);
        System.out.println("d:"+d);
        System.out.println("e:"+e);
        System.out.println("Public Key "+n + " " + e);
        System.out.println("Private Key "+n + " " + d);

    }

    public int generateD(int phi){
        int d = new Random().nextInt(phi/10);
        if (!isPrime(d)){
            return generateD(phi);
        }
        return d;
    }



    private void generatePublicKey(){
        e = (int) xgcd(d, phi)[1];

        if (e<=0){
            this.d = generateD(phi);
            generatePublicKey();
        }

        publicKey[0] = n;
        publicKey[1] = e;
    }

    private void generatePrivateKey(){
        privateKey[0] = n;
        privateKey[1] = d;
    }

    private static long[] xgcd(long a, long b){
        long[] retvals={0,0,0};
        long aa[]={1,0}, bb[]={0,1}, q=0;
        while(true) {
            q = a / b; a = a % b;
            aa[0] = aa[0] - q*aa[1];  bb[0] = bb[0] - q*bb[1];
            if (a == 0) {
                retvals[0] = b; retvals[1] = aa[1]; retvals[2] = bb[1];
                return retvals;
            };
            q = b / a; b = b % a;
            aa[1] = aa[1] - q*aa[0];  bb[1] = bb[1] - q*bb[0];
            if (b == 0) {
                retvals[0] = a; retvals[1] = aa[0]; retvals[2] = bb[0];
                return retvals;
            };
        }
    }


    public int getP() {
        return p;
    }

    public int getQ() {
        return q;
    }

    public int getN() {
        return n;
    }

    public int getPhi() {
        return phi;
    }

    public int getD() {
        return d;
    }

    public int getE() {
        return e;
    }

    public int[] getPublicKey() {
        return publicKey;
    }

    public int[] getPrivateKey() {
        return privateKey;
    }

    private String getFourDigitString(String a){
        if (a.length() == 4){
            return a;
        }else {
            return getFourDigitString("0" + a);
        }
    }


    public String[] decode(String message) throws NumberFormatException{


        StringBuilder log = new StringBuilder();

        int segment = message.length() / 4;

        ArrayList<BigInteger> segments = new ArrayList<>();
        BigInteger d = new BigInteger(String.valueOf(this.d));
        BigInteger n = new BigInteger(String.valueOf(this.n));

        for(int i =0;i<segment;i++){
            int startIndex = i*4;
            StringBuilder builder = new StringBuilder();
            builder.append(message.charAt(startIndex));
            builder.append(message.charAt(startIndex+1));
            builder.append(message.charAt(startIndex+2));
            builder.append(message.charAt(startIndex+3));
            segments.add(new BigInteger(builder.toString()));
        }


        ArrayList<Character> decipheredMessage = new ArrayList<>();
        for(BigInteger each: segments){

            BigInteger result = each.pow(d.intValue()).mod(n);
            char a = (char) result.intValue();
            decipheredMessage.add(a);

            log.append(getFourDigitString(String.valueOf(each.intValue()))+" -> ");
            log.append(getFourDigitString(String.valueOf(each.intValue()))+ "^" +d.intValue() +"%"+n.intValue() +"= ");
            log.append(getFourDigitString(String.valueOf(result.intValue()))+" -> ");
            log.append(a+"\n");
        }

        StringBuilder m = new StringBuilder();
        for(char a:decipheredMessage){
            m.append(a);
        }
        log.append(m);
        return new String[]{m.toString(), log.toString()};

    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        RSA a = new RSA(47,59);

    }

}
