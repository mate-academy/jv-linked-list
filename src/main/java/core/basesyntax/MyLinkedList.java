package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            newNode.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> searchIndex = findIndex(index);
            Node<T> newNode = new Node<>(searchIndex.prev, value, searchIndex);
            searchIndex.prev.next = newNode;
            searchIndex.prev = newNode;
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
        checkIndex(index);
        return findIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findIndex(index);
        T currentValue = node.value;
        node.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (object == node.value || object != null && object.equals(node.value)) {
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

    private Node<T> findIndex(int index) {
        Node<T> foundNode = head;
        int i = 0;
        while (i < index) {
            foundNode = foundNode.next;
            i++;
        }
        return foundNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private T unlink(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;
        if (previous == null && next == null) {
            head = tail = null;
        } else if (previous == null) {
            head = next;
            next.prev = null;
        } else if (next == null) {
            tail = previous;
            previous.next = null;
        } else {
            previous.next = next;
            next.prev = previous;
        }
        size--;
        return node.value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev,T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
