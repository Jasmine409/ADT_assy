package adt;

public class DeLinkedList<T extends Comparable> implements DeListInterface<T>{
    private int numberOfEntries = 0;
    private Node firstNode = null;
    private Node lastNode = null;

    public DeLinkedList() {
      clear();
    }

    @Override
    public boolean add(int newPosition, T newEntry) { 
      boolean isSuccessful = true;
      if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
        Node newNode = new Node(newEntry);
        if (isEmpty() || (newPosition == 1)) { 
            if(firstNode != null){
                newNode.next = firstNode;
                firstNode.prev = newNode;
            }else{
                lastNode = newNode;
            }
            firstNode = newNode;
        } else if(newPosition == numberOfEntries){
            newNode.prev = lastNode;
            lastNode.next = newNode;
            lastNode = newNode;
        } else {								
          Node beforePosition = firstNode;
          for (int i = 1; i < newPosition - 1; ++i) {
            beforePosition = beforePosition.next;	
          }
          newNode.prev = beforePosition;
          newNode.next = beforePosition.next;	
          beforePosition.next = newNode;	
        }

        numberOfEntries++;
      } else {
        isSuccessful = false;
      }

      return isSuccessful;
    }

    @Override
    public boolean insertFirst(T newEntry){
        Node newNode = new Node(newEntry);
        if(isEmpty()){
            lastNode = newNode;
        }else{
            firstNode.prev = newNode;
            newNode.next = firstNode;
        }
        firstNode = newNode;
        numberOfEntries++;
        return true;
    }
    @Override
    public boolean insertLast(T newEntry){
        Node newNode = new Node(newEntry);
        if(isEmpty()){
            firstNode = newNode;
        }else{
            newNode.prev = lastNode;
            lastNode.next = newNode;
        }
        lastNode = newNode;
        numberOfEntries++;
        return true;
    }
    @Override
    public T deleteFirst(){
        if(isEmpty()){
            return null;
        }
        Node currentNode = lastNode;

        T returnData = currentNode.data;
        firstNode = currentNode.next;
        currentNode = null;
        numberOfEntries--;

        return returnData;

    }
    @Override
    public T deleteLast(){
        if(isEmpty()){
            return null;
        }
         Node currentNode = lastNode;

        T returnData = currentNode.data;
        lastNode = currentNode.prev;
        currentNode = null;
        numberOfEntries--;

        return returnData;
    }
    @Override
    public boolean delete(Object anEntry){
        Node currentNode = firstNode;
        if(numberOfEntries==0){return false;}
        
        while(currentNode.next != null && currentNode.data.compareTo(anEntry) != 0){
            currentNode = currentNode.next;
        }
        if(currentNode.data.compareTo(anEntry) == 0){
            if(currentNode == firstNode){
                firstNode = currentNode.next;
                if(firstNode != null){
                    firstNode.prev = null;
                }
            }else if(currentNode == lastNode){
                lastNode = currentNode.prev;
                lastNode.next = null;
            }else{
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
            }
            currentNode = null;
            
            numberOfEntries--;
            if(numberOfEntries <= 0){numberOfEntries = 0;}

            return true;
        }
        return false;
    }

    @Override
    public T remove(int givenPosition) {
      T result = null;               

      if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
        Node currentNode = firstNode;    
        if (givenPosition == 1) {      
            result = currentNode.data; 
            firstNode = currentNode.next;
        } else if(givenPosition == numberOfEntries){
            currentNode = lastNode;
            result = currentNode.data; 
            lastNode = currentNode.prev;
        } else {                      
          for (int i = 1; i < givenPosition; i++) { 
            currentNode = currentNode.next;		
          }

          result = currentNode.data;  
          currentNode.prev.next = currentNode.next;
          currentNode.next.prev = currentNode.prev;
        } 																// one to be deleted (to disconnect node from chain)
        currentNode = null;
        numberOfEntries--;
      }

      return result; 
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
    public T getFirst(){
        return firstNode.data;
    }
    @Override
    public T getLast(){
        return lastNode.data;
    }

 


    @Override
    public T retrieve(Object anEntry){
        if(isEmpty()){return null;}
        Node currentNode = firstNode;
        T retrieveObject = null;
        while(currentNode.next != null && currentNode.data.compareTo(anEntry) != 0 ){
            currentNode = currentNode.next;
        }
        if(currentNode.data.compareTo(anEntry) == 0){
            retrieveObject = currentNode.data;
        }
        return retrieveObject;
    }
    @Override
    public T retrieveByPosition(int givenPosition){
        if(givenPosition >= 1 && givenPosition <= numberOfEntries){
        Node currentNode = firstNode;
        for(int i = 0; i < givenPosition-1;i++){
            currentNode = currentNode.next;
        }
        return currentNode.data;
        }
        return null;

    }

    
    public int findPosition(Object anEntry){
        if(isEmpty()){return -1;}
        Node currentNode = firstNode;
        int position = 1;
        while(currentNode.next != null && currentNode.data.compareTo(anEntry) != 0){
            currentNode = currentNode.next;
            position++;
        }
        if(currentNode.data.compareTo(anEntry) == 0){
            return position;
        }
        return -1;
    }
    @Override
    public int size(){
        return numberOfEntries;
    }
    @Override
    public void clear() {
      firstNode = null;
      numberOfEntries = 0;
    }
    @Override
    public boolean isEmpty(){
        return numberOfEntries == 0;
    }
    @Override
    public boolean isFull(){
        return false;
    }
    @Override
    public boolean contains(T anEntry) {
        if(isEmpty()){return false;}

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
    public Iterator<T> getIterator(){
        return new DequeIterator();
    }
    @Override
    public Iterator<T> getDescIterator(){
        return new DequeIterator(1);
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
        private T data; 
        private Node prev; 
        private Node next; 

        public Node(T data) {
          this.data = data;
          next = null;
        }
    }
    private class DequeIterator implements Iterator<T> {

        private Node currentNode;

        public DequeIterator(){
            this(0);
        }
        public DequeIterator(int command) {
          currentNode = (command==0)?firstNode:lastNode;
        }

        @Override
        public boolean hasNext() {
          return currentNode != null;
        }

        @Override
        public T next() {
          if (hasNext()) {
            T returnData = currentNode.data;
            currentNode = currentNode.next;
            return returnData;
          } else {
            return null;
          }
        }

        @Override 
        public boolean hasPrev(){
            return currentNode != null;
        }

        @Override
        public T prev() {
          if (hasPrev()) {
            T returnData = currentNode.data;
            currentNode = currentNode.prev;
            return returnData;
          } else {
            return null;
          }
        } 
    }
}
