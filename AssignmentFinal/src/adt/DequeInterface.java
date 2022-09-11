
package adt;

import adt.Iterator;

/**
 *
 * @author Fong Suk Dien
 */
public interface DequeInterface<T> {
    public boolean insertFirst(T newEntry);  
    public boolean insertLast(T newEntry);  
    public T deleteFirst();  
    public T deleteLast(); 

    public T getFirst();  
    public T getLast();  

    public boolean isEmpty();
    public boolean isFull();

    public int capacity();
    public int size();  
    public void clear();
    public boolean contains(T anEntry);


    public Iterator<T> getIterator();  
    public Iterator<T> getDescIterator();
}