package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    
    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;
        
        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
    
    @Override
    public boolean add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
            size++;
            return true;
        } else {
            Node<T> currentNode = head;
            while (true) {
                if (currentNode.next == null) {
                    Node<T> newNode = new Node<>(currentNode, value, null);
                    currentNode.next = newNode;
                    tail = newNode;
                    size++;
                    return true;
                }
                currentNode = currentNode.next;
            }
        }
    }
    
    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> currentIndexedNode = getNode(index);
            Node<T> prevIndexedNode = currentIndexedNode.prev;
            Node<T> newNode = new Node<>(prevIndexedNode, value, currentIndexedNode);
            currentIndexedNode.prev = newNode;
            prevIndexedNode.next = newNode;
            size++;
        }
    }
    
    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }
    
    @Override
    public T get(int index) {
        checkIndex(index);
        if (head == null) {
            return null;
        }
        return getNode(index).value;
    }
    
    @Override
    public T set(T value, int index) {
        checkIndex(index);
        return null;
    }
    
    @Override
    public T remove(int index) {
        checkIndex(index);
        return null;
    }
    
    @Override
    public boolean remove(T object) {
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
    
    private Node<T> getNode(int index) {
        Node<T> currentNode = head;
        int i = 0;
        while (currentNode != null) {
            if (i == index) {
                return currentNode;
            }
            i++;
            currentNode = currentNode.next;
        }
        return new Node<>(null, null, null);
    }
    
    private void checkIndex(int index) {
        if (index < 0 || size < index) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
