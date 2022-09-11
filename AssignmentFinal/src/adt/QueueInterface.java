package adt;
import adt.Iterator;
/** 
 * QueueInterface.java An interface for the ADT queue. * 
 * @author Frank M. Carrano 
 * @version 2.0 */
public interface QueueInterface<T> {  
    public Iterator<T> getIterator();  
    public boolean enqueue(T newEntry);  
    public T dequeue();  
    public T getFront();  
    public boolean isEmpty();  
    public void clear();
    public boolean contains(Object anEntry);
} 
