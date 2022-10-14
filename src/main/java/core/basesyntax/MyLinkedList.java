package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> oldNode = getIndexNode(index);
        if (size == 0 || index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            newNode.next = oldNode;
            head = newNode;
        } else {
            newNode.prev = oldNode.prev;
            oldNode.prev.next = newNode;
            newNode.next = oldNode;
        }
        oldNode.prev = newNode;
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
        checkIndexGetSet(index);
        Node<T> node = getIndexNode(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexGetSet(index);
        Node<T> node = getIndexNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexGetSet(index);
        Node<T> node = getIndexNode(index);
        T removedItem = node.item;
        removeNode(node);
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        Integer index = getNodeIndex(object);
        if (index == null) {
            return false;
        }
        remove(index);
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

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index" + index + " out of bounds");
        }
    }

    private void checkIndexGetSet(int index) {
        if (index + 1 > size || index < 0) {
            throw new IndexOutOfBoundsException("Index" + index + " out of bounds");
        }
    }

    private Node<T> getIndexNode(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private Integer getNodeIndex(T element) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.item == element || node.item != null && node.item.equals(element)) {
                return i;
            }
            node = node.next;
        }
        return null;
    }

    private void removeNode(Node<T> node) {
        if (size > 1) {
            if (node.prev == null) {
                node.next.prev = null;
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
        node.prev = null;
        node.next = null;
        size--;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
