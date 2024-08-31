package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size()) {
            linkLast(value);
        } else {
            linkBefore(value, findNode(index));
        }
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
        Node<T> wanted = findNode(index);
        return wanted.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> wanted = findNode(index);
        T oldValue = wanted.item;
        wanted.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> wanted = first; wanted != null; wanted = wanted.next) {
                if (wanted.item == null) {
                    unlink(wanted);
                    return true;
                }
            }
        } else {
            for (Node<T> wanted = first; wanted != null; wanted = wanted.next) {
                if (object.equals(wanted.item)) {
                    unlink(wanted);
                    return true;
                }
            }
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        Node<T> current = first;
        while (current != null) {
            builder.append(current.item);
            if (current.next != null) {
                builder.append(", ");
            } else {
                builder.append("]");
            }
            current = current.next;
        }
        return builder.toString();
    }

    private void linkLast(T value) {
        final Node<T> initLast = last;
        final Node<T> newNode = new Node<>(initLast, value, null);
        last = newNode;
        if (initLast == null) {
            first = newNode;
        } else {
            initLast.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> after) {
        final Node<T> before = after.prev;
        final Node<T> newNode = new Node<>(before, value, after);
        after.prev = newNode;
        if (before == null) {
            first = newNode;
        } else {
            before.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> found) {
        final T element = found.item;
        final Node<T> next = found.next;
        final Node<T> prev = found.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            found.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            found.next = null;
        }
        found.item = null;
        size--;
        return element;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index is out of size range");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("There is no value on index: "
                    + index);
        }
    }

    private Node<T> findNode(int index) {
        if (index < (size() / 2)) {
            Node<T> wanted = first;
            for (int i = 0; i < index; i++) {
                wanted = wanted.next;
            }
            return wanted;
        } else {
            Node<T> wanted = last;
            for (int i = size - 1; i > index; i--) {
                wanted = wanted.prev;
            }
            return wanted;
        }
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
