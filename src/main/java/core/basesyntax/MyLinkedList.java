package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    public static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node(null, value, null);
        if (tail != null) {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        if (size == 0) {
            head = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index to add value!");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> curNodeByIndex = getNode(index);
        Node<T> newNode = new Node(curNodeByIndex.prev, value, curNodeByIndex);
        if (curNodeByIndex.prev != null) {
            curNodeByIndex.prev.next = newNode;
        }
        curNodeByIndex.prev = newNode;
        if (index == 0) {
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T el : list) {
            add(el);
        }
    }

    @Override
    public T get(int index) {
        Node<T> curNode = getNode(index);
        return curNode.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.item == object || (object != null && object.equals(node.item))) {
                unlink(node);
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
        return size == 0;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
    }

    private Node<T> getNode(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index to get node!");
        }
        Node<T> curNode;
        if (index <= size / 2) {
            curNode = head;
            for (int i = 0; i < index; i++) {
                curNode = curNode.next;
            }
        } else {
            curNode = tail;
            for (int i = 0; i < size - 1 - index; i++) {
                curNode = curNode.prev;
            }
        }
        return curNode;
    }

}
