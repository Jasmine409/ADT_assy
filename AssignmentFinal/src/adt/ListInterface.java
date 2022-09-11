
package adt;


public interface ListInterface<T> {
  public int getNumberOfEntries();

  public boolean add(T newEntry);

  public boolean add(int newPosition, T newEntry);

  public T remove(int givenPosition);

  public void clear();

  public boolean replace(int givenPosition, T newEntry);
 
  public T retrieveByPosition(int givenPosition);
 
  public boolean contains(T anEntry);

  public boolean delete(Object anEntry);

  public T retrieve(Object anEntry);

  public int findPosition(Object anEntry);
}