package patterns;

public class HappyNumber {
    static int sumOfDigits(int num){
        int sum=0;
        while(num>0){
            int rem=num%10;
            sum=(sum*10)+rem;
            num=num/10;
        }
        return num;
    }
    public static void main(String [] args){
        int num=28;
        while (num!=1){
            num=sumOfDigits(num);
        }
        System.out.println("Happy Number");
    }
}
