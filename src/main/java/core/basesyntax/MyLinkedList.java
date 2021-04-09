package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<T>(head, value, null);
        if (tail == null) {
            tail = newNode;
        } else {
            head.next = newNode;
        }
        head = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = searchNodeByIndex(index);
        Node<T> previous = node.previous;
        Node<T> newNode = new Node<>(previous, value, node);
        if (previous == null) {
            tail = newNode;
        } else {
            previous.next = newNode;
        }
        node.previous = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return searchNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeToBeChanged = searchNodeByIndex(index);
        T temporaryValue = nodeToBeChanged.value;
        nodeToBeChanged.value = value;
        return temporaryValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = searchNodeByIndex(index);
        return unlink(node);
    }

    @Override
    public boolean remove(T value) {
        Node<T> node = tail;
        for (int index = 0; index < size; index++) {
            if (node.value != null && node.value.equals(value) || node.value == value) {
                remove(index);
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

    private Node<T> searchNodeByIndex(int index) {
        indexCheck(index);
        if (index > size / 2) {
            return searchFromHead(index);
        } else {
            return searchFromTail(index);
        }
    }

    private Node<T> searchFromHead(int index) {
        Node<T> node = head;
        while (index + 1 != size) {
            node = node.previous;
            index++;
        }
        return node;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> node = tail;
        while (index > 0) {
            node = node.next;
            index--;
        }
        return node;
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private T unlink(Node<T> nodeToBeRemoved) {
        Node<T> previous = nodeToBeRemoved.previous;
        Node<T> next = nodeToBeRemoved.next;
        if (size == 1) {
            tail = null;
            head = null;
        } else if (previous == null) {
            tail = nodeToBeRemoved.next;
            next.previous = nodeToBeRemoved.previous;
        } else if (next == null) {
            head = nodeToBeRemoved.previous;
            previous.next = nodeToBeRemoved.next;
        } else {
            next.previous = nodeToBeRemoved.previous;
            previous.next = nodeToBeRemoved.next;
        }
        nodeToBeRemoved.next = null;
        nodeToBeRemoved.previous = null;
        size--;
        return nodeToBeRemoved.value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
