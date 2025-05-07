package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (first == null) {
            first = newNode;
        }
        if (last != null) {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {

        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first = newNode;
            newNode.prev = null;
        } else {
            checkIndex(index);
            Node<T> next = getByIndex(index);
            Node<T> newNode = new Node<>(next.prev, value, next);
            next.prev.next = newNode;
            next.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndexForGet(index);
        return (T) getByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForGet(index);
        Node<T> newNode = getByIndex(index);
        T previousValue = newNode.value;
        newNode.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForGet(index);
        return linkChanging(getByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (node.value == object || node.value.equals(object)) {
                linkChanging(node);
                return true;
            } else {
                node = node.next;
            }
        }
        return false;
    }

    private Node<T> getByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i != index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        return current;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is not correct!");
        }
    }

    private void checkIndexForGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is not correct!");
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private T linkChanging(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;
        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            last = previous;
        } else {
            next.prev = previous;
        }
        size--;
        return node.value;
    }
}
