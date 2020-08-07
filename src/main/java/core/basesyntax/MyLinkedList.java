package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            Node<T> node = new Node<>();
            node.value = value;
            head = node;
            tail = node;

        } else {
            Node<T> node = new Node<>();
            node.value = value;
            node.previous = tail;
            tail.next = node;
            tail = node;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (index == size) {
            Node<T> previousTail = tail;
            tail = new Node<>();
            tail.previous = previousTail;
            tail.value = value;
            if (previousTail == null) {
                head = tail;
            } else {
                previousTail.next = tail;
            }
        } else {
            Node<T> nodeBeforeInput = getNode(index);
            Node<T> oldPrevious = nodeBeforeInput.previous;
            Node<T> inputNode = new Node<>();
            inputNode.previous = oldPrevious;
            inputNode.value = value;
            inputNode.next = nodeBeforeInput;
            nodeBeforeInput.previous = inputNode;
            if (oldPrevious == null) {
                head = inputNode;
            } else {
                oldPrevious.next = inputNode;
            }
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T previousValue = node.value;
        node.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        Node<T> target = getNode(index);
        removingAction(target);
        return target.value;
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == t || node.value.equals(t)) {
                removingAction(node);
                return true;
            }
            node = node.next;
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
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void removingAction(Node<T> target) {
        Node<T> previous = target.previous;
        Node<T> next = target.next;

        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            tail = previous;
        } else {
            next.previous = previous;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;
    }
}
