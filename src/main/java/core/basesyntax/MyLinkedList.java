package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int INITIAL_SIZE = 0;
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = INITIAL_SIZE;
    }

    @Override
    public boolean add(T value) {
        insertLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            insertFirst(value);
            return;
        }
        if (index == size) {
            insertLast(value);
            return;
        }
        insertBetween(value, index);
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T issue : list) {
            insertLast(issue);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (indexIsNotValid(index)) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            current = current.next;
        }
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        if (indexIsNotValid(index)) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            current = current.next;
        }
        Node<T> newNode = new Node<>(value);
        if (current == first) {
            first = newNode;
        } else {
            current.prev.next = newNode;
            newNode.prev = current.prev;
        }
        if (current == last) {
            last = newNode;
        } else {
            current.next.prev = newNode;
            newNode.next = current.next;
        }
        return current.value;
    }

    @Override
    public T remove(int index) {
        if (indexIsNotValid(index)) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            current = current.next;
        }
        stitchAfterRemove(current);
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T t) {
        Node<T> current = first;
        while (!isEqual(current.value, t)) {
            current = current.next;
            if (current == null) {
                return false;
            }
        }
        stitchAfterRemove(current);
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private void insertFirst(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            last = newNode;
        } else {
            first.prev = newNode;
        }
        newNode.next = first;
        first = newNode;
        size++;
    }

    private void insertBetween(T value, int index) {
        if (!isEmpty()) {
            Node<T> current = first;
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    break;
                }
                current = current.next;
            }
            Node<T> newNode = new Node<>(value);
            newNode.next = current;
            current.prev.next = newNode;
            newNode.prev = current.prev;
            current.prev = newNode;
            size++;
        }
    }

    private void insertLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    private void stitchAfterRemove(Node<T> removed) {
        if (removed == first) {
            first = removed.next;
        } else {
            removed.prev.next = removed.next;
        }
        if (removed == last) {
            last = removed.prev;
        } else {
            removed.next.prev = removed.prev;
        }
    }

    private boolean isEqual(T issue, T keyIssue) {
        return (issue == keyIssue) || (issue != null && issue.equals(keyIssue));
    }

    private boolean indexIsNotValid(int index) {
        return (index >= size) || (index < 0);
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }
}
