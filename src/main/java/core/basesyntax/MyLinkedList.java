package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node first;
    private Node last;
    
    @Override
    public void add(T value) {
        linkLast(value);
    }
    
    @Override
    public void add(T value, int index) {
        checkPosition(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }
    
    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }
    
    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).item;
    }
    
    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> x = node(index);
        T oldVal = x.item;
        x.item = value;
        return oldVal;
    }
    
    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }
    
    @Override
    public boolean remove(T object) {
        for (Node<T> x = first; x != null; x = x.next) {
            if ((object == null && x.item == null) || (object != null && object.equals(x.item))) {
                unlink(x);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is out of array " + index);
        }
    }

    private void checkPosition(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index is out of array " + index);
        }
    }
    
    private Node<T> node(int index) {
        Node<T> x;
        if (index < (size >> 1)) {
            x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x;
    }
    
    private void linkLast(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }
    
    private void linkBefore(T value, Node<T> succ) {
        Node<T> pred = succ.prev;
        Node<T> newNode = new Node<>(pred, value, succ);
        succ.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }
    
    public T unlink(Node<T> x) {
        final T element = x.item;
        Node<T> next = x.next;
        Node<T> prev = x.prev;
    
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
        return element;
    }
    
    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;
        
        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
    
}
