package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            Node<T> node = new Node<>(value, null, null);
            head = node;
            tail = node;
            size++;
        } else {
            Node<T> node = new Node<>(value, tail, null);
            tail.next = node;
            tail = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> indexNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(value, indexNode.prev, indexNode);
            indexNode.prev.next = newNode;
            indexNode.prev = newNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkBounds(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public void set(T value, int index) {
        checkBounds(index);
        getNodeByIndex(index).value = value;
    }
    
    @Override
    public T remove(int index) {
        checkBounds(index);
        Node<T> indexNode = getNodeByIndex(index);
        Node<T> prevNode = indexNode.prev;
        Node<T> nextNode = indexNode.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        indexNode.prev = null;
        indexNode.next = null;
        size--;
        return indexNode.value;
    }

    @Override
    public T remove(T t) {
        for (int i = 0; i < size; i++) {
            if (getNodeByIndex(i).value.equals(t)) {
                T removedValue = getNodeByIndex(i).value;
                remove(i);
                return removedValue;
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

    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node(T value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
