package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        private Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    private int size;
    private Node<T> head;
    private Node<T> tail;

    private Node<T> getNodeFromIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> searchNode = head;

        if (index < (size >> 1)) {
            for (int i = 0; i < index; i++) {
                searchNode = searchNode.next;
            }
        } else {
            searchNode = tail;
            for (int i = size() - 1; i > index; i--) {
                searchNode = searchNode.previous;
            }
        }
        return searchNode;

    }

    private void addToEmpty(T value) {

        Node<T> node = new Node<>(value, null, null);
        head = node;
        tail = node;
        size++;
    }

    private void addFirst(T value) {
        if (size == 0) {
            addToEmpty(value);
        } else {
            Node<T> node = new Node<>(value, null, head);
            head.previous = node;
            head = node;
            size++;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addToEmpty(value);
        } else {
            Node<T> newNode = new Node<>(value, tail, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size() || size() == 0) {
            add(value);
        } else {
            Node<T> prevNode = getNodeFromIndex(index - 1);
            Node<T> nextNode = prevNode.next;

            Node<T> newNode = new Node<>(value, prevNode, nextNode);
            prevNode.next = nextNode;
            newNode.previous = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNodeFromIndex(index).value;
    }

    @Override
    public void set(T value, int index) {
        getNodeFromIndex(index).value = value;

    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToDelete = getNodeFromIndex(index);
        Node<T> prevNode = nodeToDelete.previous;
        Node<T> nextNode = nodeToDelete.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.previous = prevNode;
        }
        T element = nodeToDelete.value;
        size--;
        return element;
    }

    @Override
    public T remove(T t) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(currentNode.value, t)) {
                return remove(i);
            }
            currentNode = currentNode.next;
        }
        return null;
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
