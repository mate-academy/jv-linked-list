package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list size "
                    + size);
        }
        Node<T> newNode;
        if (index == 0) {
            Node<T> nodeByIndex = getNodeByIndex(index);
            newNode = new Node<>(null, value, nodeByIndex);
            first = newNode;
            if (size > 0) {
                nodeByIndex.prev = newNode;
            } else {
                last = newNode;
            }
            size++;
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> n = getNodeByIndex(index);
        newNode = new Node<>(n.prev, value, n);
        n.prev.next = (size == 0) ? null : newNode;
        n.prev = (size == 0) ? null : newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        var node = getNodeByIndex(index);
        T currentValue = node.item;
        node.item = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removeResult = getNodeByIndex(index);
        if (index == 0) {
            return unlinkFirst(removeResult);
        }
        if (index == size) {
            return unlinkLast(removeResult);
        }
        return unlink(removeResult);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(current.item, object)) {
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

    private static class Node<T> {
        private T item;
        private MyLinkedList.Node<T> next;
        private MyLinkedList.Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, MyLinkedList.Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list size "
                    + size);
        }
        if (index < (size >> 1)) {
            var x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            var x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list size "
                    + size);
        }
    }

    private T unlinkFirst(MyLinkedList.Node<T> first) {
        final T element = first.item;
        final var next = first.next;
        first.item = null;
        first.next = null;
        this.first = next;
        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }
        size--;
        return element;
    }

    private T unlinkLast(MyLinkedList.Node<T> last) {
        final T element = last.item;
        final var prev = last.prev;
        last.item = null;
        last.prev = null;
        this.last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        return element;
    }

    private T unlink(MyLinkedList.Node<T> x) {
        final T element = x.item;
        final var next = x.next;
        final var prev = x.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }
}
