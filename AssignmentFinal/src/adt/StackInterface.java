package adt;

public interface StackInterface<T> {

  public boolean push(T newEntry);
  public T pop();
  public T peek();
  public boolean isEmpty();
  public void clear();
    public int size();
} 
