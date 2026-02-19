package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private final CheckIndex indexChecker = new CheckIndex();
    private final CheckIndexForAdd indexForAddChecker = new CheckIndexForAdd();

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexForAddChecker.checkIndexForAdd(index, size);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(current.prev, value, current);

        if (current.prev == null) {
            head = newNode;
        } else {
            current.prev.next = newNode;
        }

        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }

        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        indexChecker.checkIndex(index, size);
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        indexChecker.checkIndex(index, size);

        Node<T> node = getNode(index);
        T oldValue = node.data;
        node.data = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexChecker.checkIndex(index, size);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if (object == null
                    ? current.data == null
                    : object.equals(current.data)) {
                unlink(current);
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

    private Node<T> getNode(int index) {
        Node<T> current;

        if (index < size / 2) {
            current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;

            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        return current;
    }

    private T unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }

        size--;

        return node.data;
    }

    private static class Node<E> {
        private E data;
        private Node<E> prev;
        private Node<E> next;

        Node(Node<E> prev, E data, Node<E> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}
