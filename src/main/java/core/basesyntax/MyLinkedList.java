package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAddMethod(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            newNode.next.prev = newNode;
            head = newNode;
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> previousToNew = findNodeByIndex(index - 1);
            newNode.next = previousToNew.next;
            newNode.prev = previousToNew;
            previousToNew.next = newNode;
            newNode.next.prev = newNode;
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
        indexValidation(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexValidation(index);
        Node<T> necessaryNode = findNodeByIndex(index);
        T oldValue = necessaryNode.item;
        necessaryNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexValidation(index);
        Node<T> current = findNodeByIndex(index);
        unlink(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        int index = findIndexByValue(object);
        if (index == -1) {
            return false;
        } else {
            unlink(findNodeByIndex(index));
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndexForAddMethod(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("There is no such index in the list, " + index);
        }
    }

    private void indexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("There is no such index in the list, " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private int findIndexByValue(T value) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (value == node.item || value != null && value.equals(node.item)) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    private void unlink(Node<T> node) {
        if (node.equals(head)) {
            head = head.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.equals(tail)) {
            tail = tail.prev;
        } else {
            node.next.prev = node.prev;
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
