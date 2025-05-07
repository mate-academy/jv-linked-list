package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    @Override
    public void add(T value) {
        Node<T> newNode;
        if (size == 0) {   
            newNode = new Node<>(null, value, null); 
            head = newNode;
        } else {
            newNode = new Node<>(tail, value, null); 
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }      
        Node<T> newNode;
        if (index == 0) {
            newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            checkIndexValidity(index);
            Node<T> currentNode = getNodeByIndex(index);
            newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++; 
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndexValidity(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexValidity(index);
        Node<T> currentNode = getNodeByIndex(index);
        T prevValue = currentNode.value;
        currentNode.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        checkIndexValidity(index);
        Node<T> currentNode = getNodeByIndex(index);
        T removedValue = currentNode.value;
        unlinkNode(currentNode);
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object 
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                unlinkNode(currentNode);;
                size--;
                return true;
            }
            currentNode = currentNode.next;
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

    private class Node<E> {
        private E value;
        private Node<E> prev;
        private Node<E> next;

        Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {           
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {           
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndexValidity(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    private void unlinkNode(Node<T> currentNode) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (currentNode.equals(head)) {
            currentNode.next.prev = null;
            head = currentNode.next;
        } else if (currentNode.equals(tail)) {
            currentNode.prev.next = null;
            tail = currentNode.prev;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
    }
}
