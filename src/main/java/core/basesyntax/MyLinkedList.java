package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            lastNode = new Node<>(null, value, null);
            firstNode = lastNode;
        } else {
            Node<T> addedElement = new Node<>(lastNode, value, null);
            lastNode.next = addedElement;
            lastNode = addedElement;
        }
        size++;
        return true;

    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> current = new Node<>(null, value, firstNode);
            firstNode.prev = current;
            firstNode = current;
        } else {
            Node<T> current = findNode(index);
            Node<T> added = new Node<>(current.prev, value, current);
            current.prev.next = added;
            current.prev = added;
        }
        size++;

    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = findNode(index);
        T previousValue = current.value;
        current.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removed = findNode(index);
        final T result = removed.value;
        if (removed.next == null) {
            lastNode = removed.prev;
        } else {
            removed.next.prev = removed.prev;
        }
        if (removed.prev == null) {
            firstNode = removed.next;
        } else {
            removed.prev.next = removed.next;
        }
        size--;
        return result;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = firstNode;
        for (int i = 0; i < size; i++) {
            if (object == current.value || object != null && object.equals(current.value)) {
                remove(i);
                return true;
            }
            current = current.next;
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
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            firstNode = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }

        if (nextNode == null) {
            lastNode = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        size--;

    }

    private Node<T> findNode(int index) {
        Node<T> current;
        int count = 0;
        if (index <= size / 2) {
            current = firstNode;
            while (count < index) {
                current = current.next;
                count++;
            }
        } else {
            current = lastNode;
            count = size - 1;
            while (count > index) {
                current = current.prev;
                count--;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

    }
}
