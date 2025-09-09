package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    public MyLinkedList() {
        head = null;
        tail = null;
    }

    @Override
    public void add(T value) {
        createNewNode(tail, null, value);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkTheIndex(index);
        int count = 0;
        Node<T> current = head;
        while (count == index && count != size()) {
            current = current.next;
            count++;
        }
        Node<T> newNode = createNewNode(current.prev, current, value);
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        size += list.size();
    }

    @Override
    public T get(int index) {
        checkTheIndex(index);
        Node<T> currentNode = head;
        int count = 0;
        while (count < index) {
            currentNode = currentNode.next;
            count++;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkTheIndex(index);
        Node<T> currentNode = head;
        int count = 0;
        while (count < size()) {
            if (count == index) {
                T oldValue = currentNode.value;
                currentNode.value = value;
                return oldValue;
            }
            currentNode = currentNode.next;
            count++;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkTheIndex(index);
        Node<T> currentNode = head;
        int count = 0;
        while (count < size()) {
            if (count == index) {
                T oldValue = currentNode.value;
                unlink(currentNode);
                return oldValue;
            }
            currentNode = currentNode.next;
            count++;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        int count = 0;
        while (count < size()) {
            if (object == null && currentNode.value == null) {
                unlink(currentNode);
                return true;
            }
            if (object != null && currentNode.value != null && object.equals(currentNode.value)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
            count++;
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

    private void unlink(Node<T> node) {
        if (node.prev != null && node.next != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        if (node.prev == null) {
            head = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        }

        size--;
    }

    private void checkTheIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> createNewNode(Node<T> prev, Node<T> next, T value) {
        Node<T> newNode = new Node<>();
        newNode.next = next;
        newNode.prev = prev;
        newNode.value = value;
        if (prev != null) {
            prev.next = newNode;
        } else {
            head = newNode;
        }

        if (next != null) {
            next.prev = newNode;
        } else {
            tail = newNode;
        }

        return newNode;
    }

    private class Node<T> {
        Node prev;
        Node next;
        T value;
    }
}
