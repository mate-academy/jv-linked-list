package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index != 0) {
            checkIndex(index - 1);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> curNode = findNode(index);
        Node<T> newNode = new Node<>(curNode.prev, value, curNode);
        if (curNode.prev == null) {
            first = newNode;
        } else {
            curNode.prev.next = newNode;
        }
        curNode.prev = newNode;
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
        checkIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> curNode = findNode(index);
        T oldItem = curNode.item;
        curNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> curNode = findNode(index);
        T removedItem = curNode.item;
        unlink(curNode);
        size--;
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> curNode = first;
        while (curNode != null) {
            if (curNode.item == object || curNode.item.equals(object)) {
                unlink(curNode);
                size--;
                return true;
            }
            curNode = curNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node<T> findNode(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        node = last;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null && node.next == null) {
            first = null;
            last = null;
        } else if (node.prev == null) {
            node.next.prev = null;
            first = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
        node.item = null;
    }
}
