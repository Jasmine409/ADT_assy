package adt;
import adt.Iterator;

public interface DeListInterface<T> {  
    public boolean add(int newPosition, T newEntry);
    public boolean insertFirst(T newEntry);
    public boolean insertLast(T newEntry);
    public T deleteFirst();
    public T deleteLast();
    public boolean delete(Object anEntry);
    public T remove(int givenPosition);
    public boolean replace(int givenPosition, T newEntry);
    public T getFirst();
    public T getLast();
    public T retrieve(Object anEntry);
    public T retrieveByPosition(int givenPosition);
    public int size();
    public void clear();
    public boolean isEmpty();
    public boolean isFull();
    public boolean contains(T anEntry);
    public Iterator<T> getIterator();
    public Iterator<T> getDescIterator();
} 
