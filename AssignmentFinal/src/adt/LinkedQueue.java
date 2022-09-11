/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;
import adt.Iterator;
/**
 *
 * @author JASON
 */
public class LinkedQueue<T extends Comparable> implements QueueInterface<T>{
    private Node firstNode; // references node at front of queue
    private Node lastNode;  // references node at back of queue

    public LinkedQueue(){
    firstNode = null;
    lastNode = null;
    }


    @Override
    public boolean enqueue(T newEntry) {
    Node newNode = new Node(newEntry);

        if (isEmpty()){
            firstNode = newNode;
            lastNode = newNode;
            return true;
        }else{
            lastNode.prev = lastNode;
            lastNode.next = newNode;
            lastNode = newNode;
            return true;
        }
    } 

    @Override
    public T getFront() {
    T front = null;

    if (!isEmpty()) {
      front = firstNode.data;
    }

    return front;
    } 

    @Override
    public T dequeue() {
    T front = null;

    if (!isEmpty()) {
      front = firstNode.data;
      firstNode = firstNode.next;

      if (firstNode == null) {
        lastNode = null;
      }
    }
    return front;
    }

    @Override
    public boolean isEmpty() {
    return (firstNode == null) && (lastNode == null);
    }

    @Override
    public void clear() {
        firstNode = null;
        lastNode = null;
    }

    @Override
    public boolean contains(Object anEntry) {
        Node currentNode = firstNode;   
      
        while(currentNode != null && currentNode.data.compareTo(anEntry) != 0 ){
            currentNode = currentNode.next;
        }
        if(currentNode != null && currentNode.data.compareTo(anEntry) == 0){
          return true;
        }
        return false;
    }
       
    @Override
    public Iterator<T> getIterator(){
        return new LinkedQueueIterator();
    }
    
    private class LinkedQueueIterator implements Iterator<T> {
    private Node currentNode;

    public LinkedQueueIterator() {
      currentNode = firstNode;
    }

    @Override
    public boolean hasNext() {
      return currentNode.next != null;
    }

    @Override
    public T next() {
      if (hasNext()) {
        T returnData = currentNode.next.data;
        currentNode = currentNode.next;
        return returnData;
      } else {
        return null;
      }
    }

    @Override
    public boolean hasPrev() {
        return currentNode.prev != null;
    }

    @Override
    public T prev() {
        if (hasPrev()) {
        T returnData = currentNode.prev.data;
        currentNode = currentNode.prev;
        return returnData;
      } else {
        return null;
      }
    }
}

    private class Node {
        private T data; 
        private Node next; 
        private Node prev;

        private Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        } 

        private Node(T data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    } 
}
