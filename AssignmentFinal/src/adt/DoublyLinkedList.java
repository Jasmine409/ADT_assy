
package adt;

import entity.Customer;

public class DoublyLinkedList<T extends Comparable> implements ListInterface<T> {
    private int numberOfEntries = 0;
    private Node firstNode = null;

    public DoublyLinkedList() {
      clear();
    }

    @Override
    public int getNumberOfEntries() {
      return numberOfEntries;
    }

    @Override
    public boolean add(T newEntry) {
      Node newNode = new Node(newEntry);	
      if (isEmpty()) {
        firstNode = newNode;
      } else {                        
        Node currentNode = firstNode;	
        while (currentNode.next != null) { 
          currentNode = currentNode.next;
        }
        currentNode.next = newNode; 
        newNode.prev = currentNode;
      }

      numberOfEntries++;
      return true;
    }

    @Override
    public boolean add(int newPosition, T newEntry) { 
      boolean isSuccessful = true;

      if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
        Node newNode = new Node(newEntry);

        if (isEmpty() || (newPosition == 1)) { 
            newNode.next = firstNode;
            firstNode.prev = newNode;
            firstNode = newNode;
        } else if(newPosition == numberOfEntries){
            add(newEntry);
        } else {								
            Node nodeBefore = firstNode;
            for (int i = 1; i < newPosition - 1; ++i) {
              nodeBefore = nodeBefore.next;		
            }

            newNode.next = nodeBefore.next;	
            newNode.prev = nodeBefore;
            newNode.next.prev = newNode;
            nodeBefore.next = newNode;		
        }

        numberOfEntries++;
      } else {
        isSuccessful = false;
      }

      return isSuccessful;
    }

    @Override
    public T remove(int givenPosition) {
        T returnData = null;                 
        Node currentNode = firstNode;
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            if (givenPosition == 1) {      
                returnData = currentNode.data;     
                firstNode = currentNode.next;
            } else if(givenPosition == numberOfEntries){
                while(currentNode.next != null){
                    currentNode = currentNode.next;
                }
            }else {                      
                for (int i = 1; i < givenPosition; ++i) {
                  currentNode = currentNode.next;		
                }
                returnData = currentNode.data; 
                currentNode.prev.next = currentNode.next;	
                currentNode.next.prev = currentNode.prev;
            } 																// one to be deleted (to disconnect node from chain)
            currentNode = null;
            numberOfEntries--;
        }
       
        return returnData; 
    }

    @Override
    public final void clear() {
      firstNode = null;
      numberOfEntries = 0;
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
      boolean isSuccessful = true;

      if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
        Node currentNode = firstNode;
        for (int i = 0; i < givenPosition - 1; ++i) {
          currentNode = currentNode.next;		
        }
        currentNode.data = newEntry;	
      } else {
        isSuccessful = false;
      }

      return isSuccessful;
    }

    @Override
    public T retrieveByPosition(int givenPosition){
        if(givenPosition >= 1 && givenPosition <= numberOfEntries){
          Node currentNode = firstNode;
          for(int i = 0; i < givenPosition - 1;i++){
              currentNode = currentNode.next;
          }
          return currentNode.data;

        }
        return null;
    }

    @Override
    public boolean contains(T anEntry) {
      boolean found = false;
      Node currentNode = firstNode;

      while (!found && (currentNode != null)) {
        if (anEntry.equals(currentNode.data)) {
          found = true;
        } else {
          currentNode = currentNode.next;
        }
      }
      return found;
    }

    @Override
    public boolean delete(Object anEntry){
        Node currentNode = firstNode;
        if(isEmpty()){return false;}
        while(currentNode.next != null && currentNode.data.compareTo(anEntry) != 0){
            currentNode = currentNode.next;
        }
        if(currentNode.data.compareTo(anEntry) == 0){
            if(currentNode.prev == null){
                  firstNode = currentNode.next;
                  if(firstNode != null){
                      firstNode.prev = null;
                  }
            }
            else if(currentNode.next == null){
                currentNode.prev.next = null;
            }else{
                currentNode.next.prev = currentNode.prev;
                currentNode.prev.next = currentNode.next;
            }
            numberOfEntries--;
            if(numberOfEntries <= 0){numberOfEntries = 0;}
            return true;
        }
        return false;

    }

    @Override
    public T retrieve(Object anEntry){
        if(isEmpty()){return null;}
        Node currentNode = firstNode;
        T retrieveObject = null;
        while(currentNode != null && currentNode.data.compareTo(anEntry) != 0 ){
            currentNode = currentNode.next;
        }
        if(currentNode != null && currentNode.data.compareTo(anEntry) == 0){
            retrieveObject = currentNode.data;
        }
        return retrieveObject;
    }

    @Override
    public int findPosition(Object anEntry){
        if(isEmpty()){return -1;}
        Node currentNode = firstNode;
        int index = 0;

        while(currentNode.next != null && currentNode.data.compareTo(anEntry) != 0){
            currentNode = currentNode.next;
            index++;
        }
        if(currentNode.data.compareTo(anEntry) != 0){return -1;}
        return index+1;
    }

    public boolean isEmpty() {
      boolean result;

      result = numberOfEntries == 0;

      return result;
    }

    @Override
    public String toString() {
      String outputStr = "";
      Node currentNode = firstNode;
      while (currentNode != null) {
        outputStr += currentNode.data + "\n";
        currentNode = currentNode.next;
      }
      return outputStr;
    }

    private class Node {
        private T data ; 
        private Node prev; 
        private Node next; 

        public Node(T data) {
          this.data = data;
          next = null;
        }

        public Node(T data, Node next) {
          this.data = data;
          this.next = next;
        }
    }
}