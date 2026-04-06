package linkedlist;

class NodeT{
    int data;
    NodeT next;
    NodeT(int data){
        this.data=data;
        this.next=null;
    }
}
public class LinkedListTest {
    NodeT head;
    NodeT tail;
    public void create(int data){
        NodeT newNode=new NodeT(data);
        if(head==null){
            head=newNode;
            tail=newNode;
        }
        else{
            tail.next=newNode;
            tail=tail.next;
        }
    }
    public void print() {
        while (head != null) {
            System.out.print(head.data + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }
    public static void main(String[] args){
        LinkedListTest obj=new LinkedListTest();
        obj.create(25);
        obj.create(50);
        obj.create(90);
        obj.create(10);
        obj.print();
    }
}
