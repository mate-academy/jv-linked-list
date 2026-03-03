package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);

        // вставка в конец
        if (index == size) {
            add(value);
            return;
        }

        // вставка в начало
        if (index == 0) {
            Node<T> oldHead = head;
            Node<T> newNode = new Node<>(null, value, oldHead);
            head = newNode;

            if (oldHead != null) {
                oldHead.prev = newNode;
            } else {
                // список был пустой
                tail = newNode;
            }

            size++;
            return;
        }

        // вставка в середину: вклеиваем между left и right
        Node<T> right = findNodeByIndex(index);
        Node<T> left = right.prev;

        Node<T> newNode = new Node<>(left, value, right);
        left.next = newNode;
        right.prev = newNode;

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List must not be null");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);

        T oldValue = node.item;
        node.item = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);

        T oldValue = node.item;
        unlink(node);

        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if (areEqual(object, current.item)) {
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

    private void unlink(Node<T> node) {
        Node<T> left = node.prev;
        Node<T> right = node.next;

        if (left == null) {
            head = right;
        } else {
            left.next = right;
        }

        if (right == null) {
            tail = left;
        } else {
            right.prev = left;
        }

        node.prev = null;
        node.next = null;

        size--;
    }

    private Node<T> findNodeByIndex(int index) {
        // предполагается, что индекс уже валиден
        if (index < (size / 2)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    private boolean areEqual(T first, T second) {
        if (first == null) {
            return second == null;
        }
        return first.equals(second);
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
