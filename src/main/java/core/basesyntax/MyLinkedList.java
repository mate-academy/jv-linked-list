package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int OFFSET_ONE = 1;
    private static final int MIDPOINT_DIVISOR = 2;
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkInsertIndex(index);
        if (index == size) {
            linkLast(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = first;
            first = newNode;
            size++;
            return;
        }
        Node<T> temp = first;
        for (int i = 0; i < index - OFFSET_ONE; i++) {
            temp = temp.next;
        }
        temp.next.prev = newNode;
        newNode.next = temp.next;
        newNode.prev = temp;
        temp.next = newNode;

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
        checkAccessIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkAccessIndex(index);
        Node<T> temp = findNodeByIndex(index);
        T initialItem = temp.item;
        temp.item = value;
        return initialItem;
    }

    @Override
    public T remove(int index) {
        checkAccessIndex(index);
        Node<T> current = findNodeByIndex(index);
        unlink(current);
        return current.item;

    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = first;
        while (temp != null) {
            if (Objects.equals(temp.item, object)) {
                unlink(temp);
                return true;
            }
            temp = temp.next;
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

    private void linkLast(T value) {
        Node<T> initialLast = last;
        Node<T> newNode = new Node<>(initialLast, value, null);
        last = newNode;
        if (initialLast == null) {
            first = newNode;
        } else {
            initialLast.next = newNode;
        }
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> temp = first;
        if (index < size / MIDPOINT_DIVISOR) {
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        }
        if (index >= size / MIDPOINT_DIVISOR) {
            temp = last;
            for (int i = size - OFFSET_ONE; i > index; i--) {
                temp = temp.prev;
            }
        }
        return temp;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }

        size--;
    }

    private void checkInsertIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void checkAccessIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
