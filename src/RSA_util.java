import java.util.ArrayList;

public class RSA_util {

    // montgomery
//    public static int mode_exp(int base, int exp, int mod){
//
//        System.out.println("Calculating "+base+"^"+exp+" mod "+mod);
//
//        int current_exp = 1;
//        int current_result = base;
//        ArrayList<Integer> expList = new ArrayList<Integer>();
//        ArrayList<Integer> modList = new ArrayList<>();
//
//        while (current_exp < exp){
//            expList.add(current_exp);
//            if (current_exp != 1){
//                current_result = (int) Math.pow(current_result, 2);
//            }
//
//            System.out.print(base+"^"+current_exp+" -> ("+base+"^"+(current_exp/2)+")^2 -> ");
//
//            if (current_result > mod){
//                System.out.print(current_result+ " mod " + mod + "->");
//                current_result = current_result % mod;
//            }
//
//            System.out.println(current_result + " mod " + mod + "=" + current_result % mod);
//
//            modList.add(current_result%mod);
//            current_exp = current_exp*2;
//        }
//
//
//        String binStr = Integer.toBinaryString(exp);
//        System.out.println(binStr);
//
//        int reduced = 1;
//        for (int i=0;i<binStr.length();i++){
//            if (binStr.charAt(i) == '1'){
//                int index = binStr.length() - i - 1;
//                System.out.println(modList.get(index));
//                reduced *= modList.get(index);
//            }
//        }
//
//
//
//        return reduced%mod;
//    }


    public static void main(String[] args){
//        System.out.println(mode_exp(3, 200, 50));
    }

}
