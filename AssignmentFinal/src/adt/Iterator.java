package adt;

public interface Iterator<T> {
    public boolean hasPrev();
    public boolean hasNext();
    public T prev();
    public T next();
}
