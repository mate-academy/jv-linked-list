package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> last;
    private Node<T> first;
    
    @Override
    public void add(T value) {
        linkLast(value);
    }
    
    @Override
    public void add(T value, int index) {
        if (!indexCheckAdd(index)) {
            throw new IndexOutOfBoundsException("Out of bounds exception for"
                        + " add(value, index) method");
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, findNodeByIndex(index));
        }        
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (!indexCheckSetGetRemove(index)) {
            throw new IndexOutOfBoundsException("Out of bounds exception for"
                    + " get(index) method");
        }
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (!indexCheckSetGetRemove(index)) {
            throw new IndexOutOfBoundsException("Out of bounds exception for"
                    + " set(value, index) method");
        }
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (!indexCheckSetGetRemove(index)) {
            throw new IndexOutOfBoundsException("Out of bounds exception for"
                    + " remove(index) method");
        }
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (node.item == null || node.item != null && node.item.equals(object)) {
                unlink(node);
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
    
    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
    
    private void linkLast(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }
    
    private void linkBefore(T value, Node<T> next) {
        Node<T> prev = next.prev;
        Node<T> newNode = new Node<>(prev, value, next);
        next.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }
    
    private boolean indexCheckAdd(int index) {
        return index >= 0 && index <= size;
    }
    
    private boolean indexCheckSetGetRemove(int index) {
        return index >= 0 && index < size;
    }
    
    private Node<T> findNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> foundNode = first;
            for (int i = 0; i < index; i++) {
                foundNode = foundNode.next;
            }
            return foundNode;
        } else {
            Node<T> foundNode = last;
            for (int i = size - 1; i > index; i--) {
                foundNode = foundNode.prev;
            }
            return foundNode;
        }
    }
    
    private T unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        T element = node.item;
        node.item = null;
        size--;
        return element;
    }
}
