
package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        first = null;
        last = null;
    }

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    public boolean checkIndex(int index) {
        if (index >= 0 && index < size) {
            return true;
        } else {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    @Override
    public void add(T value) {
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

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            if (isEmpty()) {
                last = newNode;
            } else {
                first.prev = newNode;
                newNode.next = first;
                first = newNode;
                size++;
            }
        } else {
            checkIndex(index);
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = first;
        checkIndex(index);
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = first;
        checkIndex(index);
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T prevVal = current.data;
        current.data = value;
        return prevVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        while (current.data != get(index)) {
            current = current.next;
            if (current == null) {
                return null;
            }
        }
        if (current == first) {
            first = current.next;
        } else {
            current.prev.next = current.next;
        }
        if (current == last) {
            last = current.prev;
        } else {
            current.next.prev = current.prev;
        }
        size--;
        return current.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current.next != null) {
            if (Objects.equals(current.data, object)) {
                break;
            }
            current = current.next;
        }
        if (Objects.equals(current.data, object)) {
            Node<T> prev = current.prev;
            Node<T> next = current.next;
            size--;
            if (next == null && prev == null) {
                last = null;
                first = null;
            } else if (prev == null) {
                first = next;
                next.prev = null;
            } else if (next == null) {
                last = prev;
            } else {
                prev.next = next;
                next.prev = prev;
            }
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }
}
