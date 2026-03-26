package activites;

import java.util.Random;

class BankAccount{
    private Long accountNumber;
    private String accountHolderName;
    private int balance;

    void createAccount(String accountHolderName){
        Random generate=new Random();
        Long id=generate.nextLong(10000,99999);
        accountNumber=id;
        this.accountHolderName=accountHolderName;
        balance=0;
        System.out.println("Account created Successfully\n");
    }

    void deposit(int amount){
        balance+=amount;
        System.out.println("Amount "+amount+" is deposited Successfully to your account\n");
    }
    void withDrow(int amount){
        balance-=amount;
        System.out.println("Amount "+amount+" is withdrowed successfully from your account\n");
    }
    void display(){
        System.out.println("Account details : ");
        System.out.println("Name : "+accountHolderName);
        System.out.println("Account Number : "+accountNumber);
        System.out.println("Balance Amount : "+balance);
    }
}

public class Banking {
    public static void main(String [] args){
        BankAccount obj=new BankAccount();
        obj.createAccount("Yashwanth Varma");
        obj.deposit(50000);
        obj.withDrow(1000);
        obj.display();
    }
}
