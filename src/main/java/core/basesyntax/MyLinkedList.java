package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, Node<T> next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    private void unlink(Node<T> node) {
        // 1 - node - 3
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
    }

    private Node<T> getNode(int index) {
        int count = 0;
        Node<T> pointer = head;
        while (pointer != null) {
            if (count == index) {
                return pointer;
            }
            pointer = pointer.next;
            count++;
        }
        return null;
    }

    private void testIndex(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("index outside size: "+ index);
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNoge = new Node<>(tail, null, value);
        if (head == null) {
            head = newNoge;
        }
        if (tail != null) {
            tail.next = newNoge;
        }
        tail = newNoge;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size()) {
            add(value);
            return;
        }
        testIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, head, value);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, currentNode, value);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
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
        testIndex(index);
        Node<T> pointer = getNode(index);
        if (pointer == null) {
            return null;
        } else {
            return  pointer.getValue();
        }
    }

    @Override
    public T set(T value, int index) {
        testIndex(index);
        int count = 0;
        Node<T> pointer = head;
        T oldValue = null;
        while (pointer != null) {
            if (index == count) {
                oldValue = pointer.getValue();
                pointer.setValue(value);
                break;
            } else {
                pointer = pointer.next;
                count++;
            }
        }
        return oldValue;
    }

    @Override
    public T remove(int index) {
        testIndex(index);
        T toReturn = null;
        Node<T> pointer = getNode(index);
        if (pointer != null) {
            T result = pointer.getValue();
            unlink(pointer);
            size--;
            return result;
        }
        return toReturn;
    }

    @Override
    public boolean remove(T object) {
        Node<T> pointer = head;
        while (pointer != null) {
            if (pointer.getValue() == object || pointer.getValue() != null
                    && (pointer.getValue().equals(object))) {
                unlink(pointer);
                size--;
                return true;
            }
            pointer = pointer.next;
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
}
