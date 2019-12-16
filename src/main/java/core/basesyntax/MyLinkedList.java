package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> newNode = tail;
        tail = new Node<>(value, null, newNode);
        if (newNode == null) {
            head = tail;
        } else {
            newNode.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkPosition(index);
            addBefore(value, findValue(index));
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
        checkPosition(index);
        return findValue(index).element;
    }

    @Override
    public void set(T value, int index) {
        checkPosition((index));
        Node<T> replaceNode = findValue(index);
        replaceNode.element = value;
    }

    @Override
    public T remove(int index) {
        checkPosition(index);
        return delete(findValue(index));
    }

    @Override
    public T remove(T value) {
        Node<T> temp;
        for (temp = head; temp != null; temp = temp.next) {
            if (temp.element == value
                    || temp.element != null && temp.element.equals(value)) {
                return delete(temp);
            }
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

    private void checkPosition(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Check your index");
        }
    }

    private T delete(Node<T> deleteNode) {
        final T element = deleteNode.element;
        Node<T> nextNode = deleteNode.next;
        Node<T> prevNode = deleteNode.previous;
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
        deleteNode.previous = null;
        deleteNode.next = null;
        size--;
        return element;
    }

    private void addBefore(T value, Node<T> afterNewNode) {
        Node<T> beforeNewNode = afterNewNode.previous;
        Node<T> newNode = new Node<>(value, afterNewNode, beforeNewNode);
        afterNewNode.previous = newNode;
        if (beforeNewNode == null) {
            head = newNode;
        } else {
            beforeNewNode.next = newNode;
        }
    }

    private Node<T> findValue(int index) {
        Node<T> value;
        if (index < (size >> 1)) {
            value = head;
            for (int i = 0; i < index; i++) {
                value = value.next;
            }
        } else {
            value = tail;
            for (int i = size - 1; i > index; i--) {
                value = value.previous;
            }
        }
        return value;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        Node(T element, Node<T> next, Node<T> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }
}
