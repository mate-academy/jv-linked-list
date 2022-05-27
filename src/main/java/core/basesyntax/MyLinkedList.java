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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index
                    + ", index should be in range from 0 to " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
        } else {
            Node<T> lastNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(lastNode.prev, value, lastNode);
            lastNode.prev.next = newNode;
            lastNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = getNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldItem = node.item;
        node.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        for (Node node = head; node != tail.next; node = node.next) {
            if (node.item == object
                    || (node.item != null && node.item.equals(object))) {
                unlink(node);
                return true;
            }
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

    public Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> node;
        if (index <= (size / 2)) {
            node = head;
            while (index != 0) {
                node = node.next;
                index--;
            }
            return node;
        }
        node = tail;
        while (index != size - 1) {
            node = node.prev;
            index++;
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            if (size == 1) {
                head = null;
            } else {
                node.next.prev = null;
                head = node.next;
            }
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index
                    + ", index should be in range from 0 to " + (size - 1));
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
