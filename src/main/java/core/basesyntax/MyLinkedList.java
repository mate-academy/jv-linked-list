package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> last;
    private Node<T> first;
    
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
    
    void linkLast(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }
    
    void linkBefore(T value, Node<T> succ) {
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
    
    @Override
    public void add(T value) {
        linkLast(value);
    }
    
    @Override
    public void add(T value, int index) {
        if (indexCheckAdd(index)) {
            if (index == size) {
                linkLast(value);
            } else {
                linkBefore(value, findNodeByIndex(index));
            }
        } else {
            throw new IndexOutOfBoundsException("Out of bounds exception for"
                        + " add(value, index) method");
        }
    }
    
    private boolean indexCheckAdd(int index) {
        return index >= 0 && index <= size;
    }
    
    private boolean indexCheckSetGetRemove(int index) {
        return index >= 0 && index < size;
    }
    
    Node<T> findNodeByIndex(int index) {
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

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (indexCheckSetGetRemove(index)) {
            return findNodeByIndex(index).item;
        } else {
            throw new IndexOutOfBoundsException("Out of bounds exception for"
                    + " get(index) method");
        }
    }

    @Override
    public T set(T value, int index) {
        if (indexCheckSetGetRemove(index)) {
            Node<T> node = findNodeByIndex(index);
            T oldValue = node.item;
            node.item = value;
            return oldValue;
        } else {
            throw new IndexOutOfBoundsException("Out of bounds exception for"
                    + " set(value, index) method");
        }
    }
    
    T unlink(Node<T> node) {
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

    @Override
    public T remove(int index) {
        if (indexCheckSetGetRemove(index)) {
            return unlink(findNodeByIndex(index));
        } else {
            throw new IndexOutOfBoundsException("Out of bounds exception for"
                    + " remove(index) method");
        }
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = first; node != null; node = node.next) {
                if (node.item == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = first; node != null; node = node.next) {
                if (object.equals(node.item)) {
                    unlink(node);
                    return true;
                }
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
}
