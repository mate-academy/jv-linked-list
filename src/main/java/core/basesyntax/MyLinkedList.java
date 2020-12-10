package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        this.add(value, size);
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (size == 0 && index == 0) {
            newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else if (index == size) {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        } else {
            isValidIndex(index);
            Node<T> iterationNode = head;
            for (int i = 0; i < index; i++) {
                iterationNode = iterationNode.next;
            }
            if (iterationNode == head) {
                newNode = new Node<>(null, value, iterationNode);
                iterationNode.prev = newNode;
                head = newNode;
            } else {
                newNode = new Node<>(iterationNode.prev, value, iterationNode);
                iterationNode.prev.next = newNode;
                iterationNode.prev = newNode;
            }
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            this.add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        isValidIndex(index);
        Node<T> iterationNode = head;
        for (int i = 0; i < index; i++) {
            iterationNode = iterationNode.next;
        }
        return iterationNode.value;
    }

    @Override
    public T set(T value, int index) {
        isValidIndex(index);
        Node<T> iterationNode = head;
        for (int i = 0; i < index; i++) {
            iterationNode = iterationNode.next;
        }
        T oldValue = iterationNode.value;
        iterationNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isValidIndex(index);
        Node<T> iterationNode = head;
        for (int i = 0; i < index; i++) {
            iterationNode = iterationNode.next;
        }
        reassignReferences(iterationNode);
        size--;
        return iterationNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> iterationNode = head;
        while (iterationNode != null) {
            if ((object == null && iterationNode.value == null)
                    || (iterationNode.value != null && iterationNode.value.equals(object))) {
                reassignReferences(iterationNode);
                size--;
                return true;
            }
            iterationNode = iterationNode.next;
        }
        return false;
    }

    private void reassignReferences(Node<T> node) {
        if (node == tail) {
            tail.next = null;
            tail = node.prev;
        } else if (node == head) {
            head = node.next;
            head.prev = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private void isValidIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index for LinkedList with size " + size);
        }
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
        Node<T> prev;
        T value;
        Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
