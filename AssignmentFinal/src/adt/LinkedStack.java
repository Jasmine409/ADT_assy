
package adt;

public class LinkedStack <T> implements StackInterface<T>{
    private Node topNode;
    private int numberOfEntries = 0;
    public LinkedStack(){
        topNode = null;
    }

    private class Node{
        private T data;
        private Node next;

        private Node(T data){
            this.data = data;
            this.next = null;
        }
        private Node(T data, Node next){
            this.data = data;
            this.next = next;
        }
    }
    @Override
    public boolean push(T newEntry){
        Node newNode = new Node(newEntry, topNode);
        topNode = newNode;
        numberOfEntries++;

        return true;
    }

    @Override
    public T pop(){
        T top = peek();
        if(topNode != null){
            topNode = topNode.next;
        }
        numberOfEntries--;
        return top;
    }

    @Override
    public T peek(){
       T top = null;
        if(topNode != null){
            top = topNode.data;
        }

        return top;
    } 

    @Override
    public boolean isEmpty(){
        return topNode == null;
    }

    @Override
    public void clear(){
        topNode = null;
    }
    @Override
    public int size(){
        return numberOfEntries;
    }

}
