package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> currentNode = head;
        if (head == null) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head.prev = new Node<>(value);
            head.prev.next = head;
            head = head.prev;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(value);
            newNode.prev = currentNode.prev;
            newNode.next = currentNode;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (Object element : list) {
            this.add((T) element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNode(index);
        Object oldValue = currentNode.value;
        currentNode.value = value;
        return (T)oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNode(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = getNode(object);
        if (currentNode != null) {
            unlink(currentNode);
            return true;
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

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index does not exist");
        }
        Node<T> currentNode;
        if (index < (size >> 1)) {
            currentNode = head;
            for (int i = 0; i != index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i != index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private Node<T> getNode(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == value || currentNode.value != null
                    && currentNode.value.equals(value)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private void unlink(Node<T> currentNode) {
        Node<T> prevNode = currentNode.prev;
        Node<T> nextNode = currentNode.next;
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
        size--;
    }
}
