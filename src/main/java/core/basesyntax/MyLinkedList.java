package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node head;
    private Node tail;

    public MyLinkedList() {
        this.size = 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(T element, Node<T> next, Node<T> prev) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node node = new Node(value, null, null);
            head = node;
            tail = node;
        } else if (size > 0) {
            Node node = new Node(value, null, tail);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (index == size) {
            add(value);
        } else {
            Node tempNode = iteratorIndex(index);
            Node newNode = new Node(value, tempNode, tempNode.prev);
            tempNode.prev = newNode;
            if (index == 0) {
                head = newNode;
            }
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
        return (T) iteratorIndex(index).item;
    }

    @Override
    public void set(T value, int index) {
        Node tempNode = iteratorIndex(index);
        tempNode.item = value;
    }

    @Override
    public T remove(int index) {
        Node tempNode = iteratorIndex(index);
        final Object oldObject = tempNode.item;
        if (size == 1) {
            head = tail = null;
            size--;
            return (T) oldObject;
        }
        if (tempNode == head) {
            tempNode.next.prev = null;
            head = tempNode.next;
        } else if (tempNode == tail) {
            tempNode.prev.next = null;
            tail = tempNode.prev;
        } else {
            tempNode.prev.next = tempNode.next;
            tempNode.next.prev = tempNode.prev;
        }
        size--;
        return (T) oldObject;
    }

    @Override
    public T remove(T t) {
        if (iteratorValue(t) == size) {
            return null;
        }
        return remove(iteratorValue(t));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node iteratorIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (index < size / 2) {

            Node iteratorNode = head;
            int counter = 0;
            while (index > counter) {
                iteratorNode = iteratorNode.next;
                counter++;
            }
            return iteratorNode;
        } else {
            Node iteratorNode = tail;
            int counter = size - 1;
            while (index < counter) {
                iteratorNode = iteratorNode.prev;
                counter--;
            }
            return iteratorNode;
        }
    }

    private int iteratorValue(T t) {
        Node tempNode = head;
        int counter = 0;
        while (tempNode.next != null) {
            if (tempNode.item == t || t != null && t.equals(tempNode.item)) {
                return counter;
            }
            tempNode = tempNode.next;
            counter++;
        }
        return size;
    }
}

