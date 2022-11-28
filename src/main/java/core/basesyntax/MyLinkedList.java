package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()){
            addFirstNode(value);
        } else {
            addLastNode(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            addFirstNode(value);
        } else if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else if (index == size) {
            addLastNode(value);
        } else {
            newNode.next = getNode(index);
            newNode.prev = getNode(index).prev;
            getNode(index).prev.next = newNode;
            getNode(index).next.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size - 1);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        T replacedValue = getNode(index).value;
        getNode(index).value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        T removedValue = get(index);
            unlink(getNode(index));
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode.next != null || size == 1) {
            if (currentNode.value == null && object == null
                    || currentNode.value != null && currentNode.value.equals(object)) {
                    unlink(currentNode);
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
    ////////////// DELETE PRINT ////////////
    public void print() {
        Node<T> currentNode = head;
        while (currentNode != null) {
            System.out.println(currentNode.value);
            currentNode = currentNode.next;
        }
    }

    private void checkIndex(int index, int size) {
        if (index > size || index < 0) {
           throw new IndexOutOfBoundsException("Index is out of bounds " + index + "!");
        }
    }

    private void addFirstNode(T value) {
        Node<T> firstNode = new Node<>(null, value, null);
        head = tail = firstNode;
    }

    private void addLastNode(T value) {
        Node<T> lastNode = new Node<>(null, value, null);
        lastNode.prev = tail;
        tail.next = lastNode;
        tail = lastNode;
    }

    private Node<T> getNode(int index) {
        if (index == 0) {
            return head;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = tail = null;
        } else if (node.prev == null) {
            head = head.next;
        } else if (node.next == null) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
    }
}

class Node<T> {
    T value;
    Node<T> next;
    Node<T> prev;

    public Node(Node<T> prev, T value, Node<T> next) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }
}
