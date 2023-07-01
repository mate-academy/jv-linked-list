package core.basesyntax;

import java.util.Collection;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private int modCount;
    private Node<T> first;
    private Node<T> last;


    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public MyLinkedList() {
    }

    public MyLinkedList(Collection<? extends T> c) {
        this();
        addAll((List<T>) c);
    }

    @Override
    public void add(T value) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size)
            linkLast(value);
        else
            linkBefore(value, node(index));
    }

    void linkLast(T value) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        modCount++;
    }

    void linkBefore(T value, Node<T> successor) {
        final Node<T> predecessor = successor.prev;
        final Node<T> newNode = new Node<>(predecessor, value, successor);
        successor.prev = newNode;
        if (predecessor == null) {
            first = newNode;
        } else {
            predecessor.next = newNode;
        }

        size++;
        modCount++;
    }

    Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds");
        }
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void addAll(List<T> list) {
        addAll(size, list);
    }

    public void addAll(int index, Collection<? extends T> c) {
        checkPositionIndex(index);
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew != 0) {
            Node<T> predecessor, successor;
            if (index == size) {
                successor = null;
                predecessor = last;
            } else {
                successor = node(index);
                predecessor = successor.prev;
            }
            for (Object o : a) {
                T e = (T) o;
                Node<T> newNode = new Node<>(predecessor, e, null);
                if (predecessor == null)
                    first = newNode;
                else
                    predecessor.next = newNode;
                predecessor = newNode;
            }
            if (successor == null) {
                last = predecessor;
            } else {
                predecessor.next = successor;
                successor.prev = predecessor;
            }
            size += numNew;
            modCount++;
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> x = node(index);
        T oldVal = x.item;
        x.item = value;
        return oldVal;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + "is out of bounds");
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = first; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    T unlink(Node<T> x) {
        final T element = x.item;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        modCount++;
        return element;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }
}
