package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            firstNode = new Node<T>(null, value, null);
            lastNode = firstNode;
        } else {
            lastNode.next = new Node<>(lastNode, value, null);
            lastNode = lastNode.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, firstNode);
            firstNode.prev = newNode;
            firstNode = newNode;
            size++;
        } else {
            Node<T> afterNode = getNode(index);
            Node<T> beforeNode = afterNode.prev;
            Node<T> newNode = new Node<T>(beforeNode, value, afterNode);
            afterNode.prev = newNode;
            if (beforeNode != null) {
                beforeNode.next = newNode;
            } else {
                firstNode = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = getNode(index).item;
        getNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removed = getNode(index);
        T value = removed.item;
        unlink(removed);
        return value;

    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        if (node == null) {
            return false;
        }
        unlink(node);
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

    private Node<T> getNode(int index) {
        checkIndex(index);
        if (index >= size) {
            throw new IndexOutOfBoundsException("No such element exists");
        }
        Node<T> sought;
        if (index < size / 2) {
            sought = firstNode;
            for (int i = 1; i <= index; i++) {
                sought = sought.next;
            }
        } else {
            sought = lastNode;
            for (int i = size - 2; i >= index; i--) {
                sought = sought.prev;
            }
        }
        return sought;
    }

    private Node<T> getNode(T value) {
        Node<T> node = firstNode;
        for (int i = 0; i < size; i++) {
            if ((node.item == value) || (node.item != null && node.item.equals(value))) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (node.next == null) {
            if (node.prev != null) {
                node.prev.next = null;
            }
            lastNode = node.prev;
        } else if (node.prev == null) {
            node.next.prev = null;
            firstNode = node.next;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Can't operate negative index");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " ot of bounds of size " + size);
        }
    }
}
