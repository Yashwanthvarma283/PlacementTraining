package linkedlist;
//class Node{
//    int data;
//    Node next;
//    Node(int data){
//        this.data=data;
//        this.next=null;
//    }
//}

public class LinkedListCreation {
    Node head;
    Node tail;
    public void create(int data){
        Node newNode=new Node(data);
        if(head==null){
            head=newNode;
            tail=newNode;
            return ;
        }
        tail.next=newNode;
        tail=tail.next;
    }
    public void printing(){
        Node temp=head;
        while(temp!=null){
            System.out.print(temp.data + " -> ");
            temp=temp.next;
        }
        System.out.print ("null");
    }
    public static void main(String[] args){
        LinkedListCreation obj=new LinkedListCreation();
        obj.create(25);
        obj.create(30);
        obj.create(35);
        obj.create(40);
        obj.printing();
    }
}
