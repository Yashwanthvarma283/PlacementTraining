package practice;
import java.util.Arrays;
class A {
    public static int[] element(int[] arr) {
        int[] ans = new int[arr.length];
        int prefixSum = 0;
        for (int i = 0; i < arr.length; i++) {
            ans[i] = (i * arr[i]) - prefixSum;
            prefixSum += arr[i];
        }
        return ans;
    }
}
public class JP {
    public static void main(String[] args) {
        System.out.print(Arrays.toString(A.element(new int[]{2,1,3})));
    }
}