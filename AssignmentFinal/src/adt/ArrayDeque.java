
package adt;

/**
 *
 * @author Fong Suk Dien
 */
public class ArrayDeque<T> implements DequeInterface<T>{
    private T[] array;
    private int lastIndex = 0;
    private int firstIndex = -1;
    private int numberOfEntries = 0;
    private static int arrayCapacity = 3;

    public ArrayDeque(){
        this(arrayCapacity);
    }
    ArrayDeque(int capacity){
        array = (T[]) new Object[capacity];
    }

    @Override
    public boolean insertFirst(T newEntry){
        if(isFull()){
            doubleArray();
        }
        if(isEmpty()){
            array[0] = newEntry;
            firstIndex = 0;
        }else{
            --firstIndex;
            if(firstIndex < 0){firstIndex+=arrayCapacity;}

            array[firstIndex] = newEntry;
            
        }
        numberOfEntries++;
        
        return true;
    }  
    @Override
    public boolean insertLast(T newEntry){
        if(isFull()){
            doubleArray();
        }
        if(isEmpty()){
            array[0] = newEntry;
            firstIndex = 0;
        }else{
            array[++lastIndex] = newEntry;    
        }
        numberOfEntries++;
        return true;
    }

    @Override
    public T deleteFirst(){
        if(isEmpty()){return null;}

        T returnData= array[firstIndex];
        array[firstIndex++]=null;
        if(firstIndex == arrayCapacity){firstIndex = 0;}
        numberOfEntries--;
        if(numberOfEntries == 0){clear();}
        return returnData;
    }

    @Override
    public T deleteLast(){
        if(isEmpty()){return null;}
        T returnData = array[lastIndex];
        array[lastIndex--] = null;
        if(lastIndex < 0){lastIndex += arrayCapacity;}
        numberOfEntries--;
        if(numberOfEntries == 0){clear();}
        return returnData;
    }

    @Override
    public T getFirst(){
        return array[firstIndex];
    }

    @Override  
    public T getLast(){
        return array[lastIndex];
    } 

    @Override  
    public boolean isEmpty(){
        return numberOfEntries == 0;
    }

    @Override  
    public boolean isFull(){
        return numberOfEntries == arrayCapacity;
    }

    @Override
    public int capacity(){
        return arrayCapacity;
    }

    @Override  
    public int size(){
        return numberOfEntries;
    }

    public void doubleArray(){
        int newCap = arrayCapacity * 2;
        T[] newArray = (T[]) new Object[newCap]; 
        int index = 0;
        Iterator it = getIterator();
        while(it.hasNext()){
            newArray[index++] = (T)it.next();
        }

        array = newArray;
        arrayCapacity = newCap;
        firstIndex = 0;
        lastIndex = size()-1;
    } 

    @Override
    public boolean contains(T anEntry){
        int currentIndex = firstIndex;
        boolean found = false;
        Iterator it = getIterator();
        while(it.hasNext()){
            if(it.next().equals(anEntry)){found = true; break;}
        }

        return found;
    }

    @Override  
    public void clear(){
        arrayCapacity = 3;
        array = (T[]) new Object[arrayCapacity];
        firstIndex = 0;
        lastIndex = 0;
        numberOfEntries = 0;
    }

    @Override  
    public Iterator<T> getIterator(){
        return new DequeIterator();
    }

    @Override  
    public Iterator<T> getDescIterator(){
        return new DequeIterator(1);
    }



    public class DequeIterator implements Iterator<T> {
        boolean stopLoop = false;
        public int currentIndex;
        public DequeIterator(){
            this(0);
        }
        public DequeIterator(int command) {
          currentIndex = (command==0)?firstIndex:lastIndex;
        }
        @Override
        public boolean hasNext() {
            if(isEmpty()){return false;}
            if(currentIndex == lastIndex+1 && stopLoop == false){return true;}
            return currentIndex != lastIndex+1 ;
        }

        @Override
        public T next() {
          if (hasNext()) {
            if(currentIndex == firstIndex){stopLoop = true;}
            if(currentIndex == arrayCapacity){currentIndex = 0;}
            T returnData = array[currentIndex++];
            return returnData;
          } else {
            return null;
          }
        }
        @Override
        public boolean hasPrev() {
            if(isEmpty()){return false;}
            if(currentIndex == firstIndex-1 && stopLoop == false){return true;}
            return currentIndex != firstIndex-1 ;
        }

        @Override
        public T prev() {
          if (hasPrev()) {
            if(currentIndex == lastIndex){stopLoop = true;}
            if(currentIndex == -1){currentIndex = arrayCapacity - 1;}
            T returnData = array[currentIndex--];
            return returnData;
          } else {
            return null;
          }
        }
    }
}
