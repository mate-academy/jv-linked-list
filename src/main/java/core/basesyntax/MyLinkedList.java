package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound exception");
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> tempNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(tempNode.prev, value, tempNode);
            tempNode.prev.next = newNode;
            tempNode.prev = newNode;
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
        Node<T> tempNode = getNodeByIndex(index);
        T value = tempNode.item;
        return value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = getNodeByIndex(index);
        T newValue = newNode.item;
        newNode.item = value;
        return newValue;
    }

    @Override
    public T remove(int index) {
        Node<T> tempNode = getNodeByIndex(index);
        unLink(tempNode);
        T revoveItem = tempNode.item;
        return revoveItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> tempNode = head;
        for (int i = 0; i < size; i++) {
            if (object == tempNode.item || object != null && object.equals(tempNode.item)) {
                unLink(tempNode);
                return true;
            }
            tempNode = tempNode.next;
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

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> tempNode;
        if (index < size / 2) {
            tempNode = head;
            for (int i = 0; i < index; i++) {
                tempNode = tempNode.next;
            }
        } else {
            tempNode = tail;
            for (int j = size - 1; j > index; j--) {
                tempNode = tempNode.prev;
            }
        }
        return tempNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound exception");
        }
    }

    private void unLink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (size == 1) {
            head = tail = null;
        } else if (prevNode == null) {
            head.next.prev = null;
            head = head.next;
        } else if (nextNode == null) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
