package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else if (size == 1) {
            Node newNode = new Node(head, value, null);
            head.next = newNode;
            tail = newNode;
        } else {
            Node newNode = new Node(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexCheckForAdd(index);
        Node indexNode = indexOf(index);
        if (indexNode == null) {
            add(value);
            return;
        }
        if (index == 0) {
            Node newNode = new Node(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node newNode = new Node(indexNode.prev, value, indexNode);
        indexNode.prev.next = newNode;
        indexNode.prev = newNode;
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
        indexCheck(index);
        try {
            return (T) indexOf(index).item;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node indexNode = indexOf(index);
        Object answer = indexNode.item;
        indexNode.item = value;
        return (T) answer;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node removedElement = indexOf(index);
        unlink(removedElement);
        size--;
        return (T) removedElement.item;
    }

    @Override
    public boolean remove(T object) {
        Node loopNode = head;
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (loopNode.item == null && object == null) {
                remove(index);
                return true;
            }
            if (loopNode.item != null && loopNode.item.equals(object)) {
                remove(index);
                return true;
            }
            loopNode = loopNode.next;
            index++;
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

    private Node indexOf(int index) {
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void unlink(Node node) {
        if (size == 1) {
            node.item = null;
            return;
        }
        if (node == head) {
            node.next.prev = null;
            head = node.next;
            return;
        }
        if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
            return;
        }
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for length " + size);
        }
    }

    private void indexCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for length " + size);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
