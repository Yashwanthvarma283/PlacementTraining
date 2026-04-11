package linkedlist;

import java.util.Scanner;

class LLNode{
    int data;
    LLNode next;
    LLNode(int data){
        this.data=data;
        next=null;
    }
}
public class InsertionDeletionLL {
    LLNode head;
    LLNode tail;
    public void insert(int data){
        LLNode newNode=new LLNode(data);
        if(head==null){
            head=tail=newNode;
        }
        else{
            tail.next=newNode;
            tail=tail.next;
        }
    }
    public void print(){
        LLNode temp=head;
        while(temp!=null){
            System.out.print(temp.data+" -> ");
            temp=temp.next;
        }
        System.out.print("null");
        System.out.println();
    }
    public void insertAt(int pos,int data){
        LLNode newNode=new LLNode(data);
        LLNode temp=head;
        if(pos==1){
            newNode.next=head;
            head=newNode;
            return;
        }
        for(int i=0;i<pos-2;i++){
            temp=temp.next;
        }
        newNode.next=temp.next;
        temp.next=newNode;
    }
    public void deleteHead(){
        LLNode temp=head;
        head=head.next;
    }
    public void deleteAt(int pos){
        if(pos==1){
            head=head.next;
            return;
        }
        LLNode temp=head;
        while(pos>0 && temp.next!=null){
            temp=temp.next;
            pos--;
        }
        temp.next=temp.next.next;
    }
    public void deleteBy(int data){
        LLNode temp=head;
        if(head.data==data){
            head.next=head;
            return;
        }
        while (temp.next!=null){
            if(temp.next.data == data){
                temp.next=temp.next.next;
            }
            temp=temp.next;
        }
    }
    public void deleteTail(){
        LLNode temp=head;
        while(temp.next!=tail){
            temp=temp.next;
        }
        tail=temp;
        tail.next=null;
    }
    public static void main(String[] args) {
        InsertionDeletionLL obj=new InsertionDeletionLL();
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int nodes[]=new int[n];
        for(int i=0;i<n;i++){
            nodes[i]=sc.nextInt();
            obj.insert(nodes[i]);
        }
        obj.print();
        obj.insertAt(2,60);
        obj.print();
        obj.insertAt(5,80);
        obj.print();
        obj.deleteAt(1);
        obj.print();
        obj.deleteHead();
        obj.print();
        obj.deleteTail();
        obj.print();
        obj.insert(90);
        obj.print();
        obj.insertAt(1,100);
        obj.print();
        obj.deleteBy(80);
        obj.print();
    }
}
