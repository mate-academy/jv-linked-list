package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> prevNode = tail;
        Node<T> newNode = new Node<>(tail, value, null);
        tail = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = findNode(index);
        Node<T> prevNode = node.prev;
        Node<T> newNode = new Node<>(prevNode, value, node);
        node.prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
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
        checkIndex(index);
        return findNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unLink(findNode(index));
    }

    @Override
    public boolean remove(T value) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (compareEquality(value, node.element)) {
                unLink(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index of bound. Input index is wrong! " + index);
        }
    }

    private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (index > size) {
            node = tail;
            for (int i = index; i < size - 1; i++) {
                node = node.prev;
            }
        } else {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    private boolean compareEquality(T element1, T element2) {
        return (element2 == element1) || (element1 != null && element1.equals(element2));
    }

    private T unLink(Node<T> findNode) {
        if (findNode.prev != null) {
            findNode.prev.next = findNode.next;
        }
        if (findNode.next != null) {
            findNode.next.prev = findNode.prev;
        }
        if (findNode == head) {
            head = findNode.next;
        }
        if (findNode == tail) {
            tail = findNode.prev;
        }
        size--;
        return findNode.element;
    }
}
