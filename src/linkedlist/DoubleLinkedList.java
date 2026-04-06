package linkedlist;

class Node{
    int data;
    Node next;
    Node prev;
    Node(int data){
        this.data=data;
        this.next=null;
        this.prev=null;
    }
}
public class DoubleLinkedList {
    Node head;
    Node tail;
    public void create(int data){
        Node newNode=new Node(data);
        if(head==null){
            head=newNode;
            tail=newNode;
        }
        else{
            tail.next=newNode;
            newNode.prev=tail;
            tail=newNode;
        }
    }
    public void printFromHead(){
        Node temp=head;
        System.out.print("null -> ");
        while(temp!=null){
            System.out.print(temp.data+" -> ");
            temp=temp.next;
        }
        System.out.println("null");
    }
    public void printFromTail(){
        Node temp=tail;

        System.out.print("null <- ");
        while(temp!=null){
            System.out.print(temp.data+" <- ");
            temp=temp.prev;
        }
        System.out.println("null");
    }
    public static void main(String [] args){
        DoubleLinkedList obj=new DoubleLinkedList();
        obj.create(50);
        obj.create(10);
        obj.create(5);
        obj.printFromHead();
        obj.printFromTail();
    }
}
