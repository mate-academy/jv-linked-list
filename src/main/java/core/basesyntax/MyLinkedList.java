package core.basesyntax;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        private T velue;
        private Node<T> next;
        private Node<T> prev;

        public Node(T velue) {
            this.velue = velue;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index >= size + 1) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> prevCurrent = getNodeByIndex(index - 1);
            newNode.next = prevCurrent.next;
            prevCurrent.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNodeByIndex(index).velue;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = getNodeByIndex(index);
        T nodeReturn = node.velue;
        node.velue = value;
        return nodeReturn;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T removedElement;
        if (index == 0) {
            removedElement = head.velue;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            removedElement = prev.next.velue;
            prev.next = prev.next.next;
            if (index == size - 1) {
                tail = prev;
            }
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        int count = 0;
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.velue == null) {
                    unlink(x);
                    return true;
                }
                count++;
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.velue)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private T unlink(Node<T> x) {
        // assert x != null;
        final T element = x.velue;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.velue = null;
        size--;
        return element;
    }
}
