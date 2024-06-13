package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(oldLast, value, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkNewIndex(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> after = node(index);
            Node<T> before = after.prev;
            Node<T> newNode = new Node<>(before, value, after);
            after.prev = newNode;
            if (before == null) {
                first = newNode;
            } else {
                before.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T obj : list) {
            add(obj);
        }
    }

    @Override
    public T get(int index) {
        checkExistingIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkExistingIndex(index);
        Node<T> node = node(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkExistingIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.item || (object != null && object.equals(currentNode.item))) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
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

    private void checkExistingIndex(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkNewIndex(int idx) {
        if (idx < 0 || idx > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private T unlink(Node<T> node) {
        Node<T> before = node.prev;
        Node<T> after = node.next;

        if (before == null) {
            first = after;
        } else {
            before.next = after;
        }

        if (after == null) {
            last = before;
        } else {
            after.prev = before;
        }

        size--;

        return node.item;
    }

    private Node<T> node(int idx) {
        final int bitRightShift = 1;
        Node<T> node;
        if (idx < (size >> bitRightShift)) {
            node = first;
            for (int i = 0; i < idx; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > idx; i--) {
                node = node.prev;
            }
        }
        return node;
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
