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
        if (index > 0 && index < size - 1) {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            current.next.prev = current.prev;
            current.prev.next = current.next;
            size--;
            return current.item;
        }
        T item;
        if (index == 0) {
            item = first.item;
            first = first.next;
        } else {
            item = last.item;
            last = last.prev;
        }
        size--;
        return item;
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
}
