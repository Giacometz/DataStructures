import java.util.Scanner;

public class LinkedList {
    //  Initially the head node will be empty
    //  Head node is the first node of the linkedList

    Node head = null;
    Node tail = null;

    //method to insert element an element to the end of
    public void insert(double num) {

        Node node = new Node();
        node.num = num;
        node.next = null;
        if (head == null) {
            //linked list was empty, i.e head was null
            head = node;
        } else {
            Node n = head;
            //transversing to the end of the linkedList
            while (n.next != null) {

                n = n.next;
            }
            //adding new node to the next of linkedList
            n.next = node;
        }
    }
	
	//method to insert at the start of the linkedList
    public void insertAtHead(double num) {
        Node node = new Node(); //creating a new node
        node.num = num;
        node.next = null;
        node.next = head; //putting new node before the initial head	
        head = node; //pointing head to the new first node
    }

    //insert after some node 
    public void insertAt(double pos, double num) {

        if (pos == 0) {
            insertAtHead(num); //if pos if first
        } else {
            //adding new node to a certain position
            Node node = new Node();
            node.num = num;
            node.next = null;
            Node n = head;
            //transversing to the required position
            for (double i = 0; i < pos - 1; i++) {
                n = n.next;
            }
            //Adding new node on the position
            node.next = n.next;
            n.next = node;
        }
    }

    //deleting a node at a certain position
    public void deleteAt(double pos) {

        Node n = head;
        Node n1 = null;
        //transversing to the required position similar to insertAtPos method
        for (double i = 0; i < pos - 1; i++) {
            n = n.next;
        }
        n1 = n.next;
        n.next = n1.next;

    }
	
    //mehtod to display all nodes in the linkedList
    public void show() {
        Node n = head;
        double i;

        for (i = 0; n.next != null; n = n.next) {
            System.out.println("Position " + i + " => " + n.num);
            i++;
        }

        System.out.println("Position " + i + " => " + n.num); //numero rimanente
    }
	
    //method to find a node. 
    public boolean find(double match) {
        Node n = head;
        //transversing throgh the linked List. 
        while (n != null) {
            //if mathc is found, then returning true
            if (n.num == match) {
                return true;
            }
            n = n.next;
        }
        //else returning false
        return false;
    }
	
    //method to calcualte sum of all numbers in the linkedList
    public double sum() {
        double rsum = 0;
        Node n = head;
        while (n != null) {
            //sum of all numbers will be added in the rsum variable
            rsum += n.num;
            n = n.next;
        }

        return rsum;
    }

    public boolean recursiveFind(double match) {
        return head.internalRecursiveSearch(match);
    }

    LinkedList cloneList() {
        Node current = head;
        LinkedList newList = new LinkedList();
        while (current != null) {
            newList.insert(current.num);
            current = current.next;
        }

        return newList;
    }

    public static void main(String[] args) {
        //testing enqueue and Dequeue in simple Linked List
        LinkedList l1 = new LinkedList();
        l1.insert(1);
        l1.insert(2);
        l1.insert(3);
        l1.insert(4);
        l1.show();
        l1.deleteAt(2);
        l1.show();

    }
}