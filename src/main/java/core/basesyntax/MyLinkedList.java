package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }
    
    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> node = getNode(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            if (node.prev != null) {
                node.prev.next = newNode;
            } else {
                head = newNode;
            }
            node.prev = newNode;
            size++;
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
        return getNode(index).data;
    }
    
    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.data;
        node.data = value;
        return oldValue;
    }
    
    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        unlink(node);
        return node.data;
    }
    
    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (Objects.equals(object, node.data)) {
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
    
    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;
        
        private Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
    
    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + " out of bounds exception, current size: " + size);
        }
        if (index < size / 2) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }
    
    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }
}
