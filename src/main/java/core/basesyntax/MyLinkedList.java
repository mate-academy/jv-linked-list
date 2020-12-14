package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> currentNode = searchByIndex(index);
        newNode = new Node<T>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
        return;
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
        return searchByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = searchByIndex(index);
        T output = currentNode.value;
        currentNode.value = value;
        return output;
    }

    @Override
    public T remove(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = searchByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private void unlink(Node<T> node) {
        if (node.prev == null & node.next == null) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            head = node.next;
            node.next.prev = null;
            node.next = null;
        } else if (node.next == null) {
            tail = node.prev;
            node.prev.next = null;
            node.prev = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
        }
        size--;
    }

    private boolean isIndexOutOfArray(int index) {
        return index >= size || index < 0;
    }

    public Node<T> searchByIndex(int index) {
        if (isIndexOutOfArray(index)) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode;
        int startIndex;
        if (index < size / 2) {
            startIndex = 0;
            currentNode = head;
            while (startIndex < index) {
                startIndex++;
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        startIndex = size - 1;
        currentNode = tail;
        while (startIndex > index) {
            startIndex--;
            currentNode = currentNode.prev;
        }
        return currentNode;
    }
}
