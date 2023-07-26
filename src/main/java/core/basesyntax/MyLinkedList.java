package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {

    }

    @Override
    public void add(T value) {
        linkTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            linkHead(value);
            return;
        }

        if (index == size) {
            linkTail(value);
            return;
        }

        Node<T> current = getElementOnIndex(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getElementOnIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getElementOnIndex(index);
        T prevValue = node.value;
        node.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getElementOnIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        int i = 0;
        Node<T> nodeToRemove = head;
        while (nodeToRemove != null) {
            if (nodeToRemove.value == object
                    || nodeToRemove.value != null && nodeToRemove.value.equals(object)) {
                unlink(nodeToRemove);
                return true;
            }
            nodeToRemove = nodeToRemove.next;
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

    private void linkHead(T value) {
        final Node<T> first = head;
        final Node<T> newNode = new Node<>(null, value, first);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    private void linkTail(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private Node<T> getElementOnIndex(int index) {
        checkIndex(index);
        Node<T> current = null;
        if (size / 2 >= index) {
            current = head;
            int i = 0;
            while (i != index) {
                current = current.next;
                i++;
            }
        }
        if (size / 2 < index) {
            current = tail;
            int i = size - 1;
            while (i != index) {
                current = current.prev;
                i--;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (next == null && prev == null) {
            head = null;
            tail = null;
            --size;
            return;
        }

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        --size;
    }

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
}
