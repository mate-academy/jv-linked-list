package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (isEmpty()) {
            first = node;
        } else {
            node.prev = last;
            last.next = node;
        }
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index
                    + ", last available index: " + size);
        }
        if (isEmpty()) {
            Node<T> newNode = new Node<>(null, value, null);
            first = newNode;
            last = newNode;
            size++;
            return;
        } else if (index == size) {
            Node<T> newNode = new Node<>(getNodeByIndex(size - 1), value, null);
            getNodeByIndex(size - 1).next = newNode;
            last = newNode;
            size++;
            return;
        }
        Node<T> node = getNodeByIndex(index);
        if (node.prev == null) {
            Node<T> newNode = new Node<>(null, value, node);
            node.prev = newNode;
            first = newNode;
        } else {
            Node<T> newNode = new Node<>(node.prev, value, node);
            node.prev.next = newNode;
            node.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if ((object == null && node.value == null)
                    || node.value.equals(object)) {
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

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index
                    + ", last available index: " + (size - 1));
        }
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            first = null;
            last = null;
        } else if (node.equals(first)) {
            node.next.prev = null;
            first = node.next;
        } else if (node.equals(last)) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}
