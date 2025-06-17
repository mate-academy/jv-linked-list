package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    
    public static class Node<T> {
        
        private T value;
        private Node<T> next;
        private Node<T> prev;
        
        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
    
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    
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
    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (index == size) {
            add(value);
            return;
        }
        
        Node<T> nextNode = getNode(index);
        Node<T> prevNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        nextNode.prev = newNode;
        size++;
    }
    
    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new RuntimeException("Input list is null");
        }
        
        for (T element : list) {
            add(element);
        }
    }
    
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        
        return current.value;
    }
    
    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node<T> current = getNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }
    
    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node<T> node = getNode(index);
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        
        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }
        
        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }
        
        size--;
        return node.value;
    }
    
    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if ((object == null && current.value == null)
                    || (object != null && object.equals(current.value))) {
                remove(i);
                return true;
            }
            current = current.next;
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
    
    private Node<T> getNode(int index) {
        if (index < (size / 2)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }
}
