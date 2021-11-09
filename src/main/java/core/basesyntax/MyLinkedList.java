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
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, getNodeByIndex(index));
            first = newNode;
            getNodeByIndex(index + 1).prev = newNode;
            size++;
        } else {
            getIndex(index);
            Node<T> newNode = new Node<>(getNodeByIndex(index - 1), value, getNodeByIndex(index));
            getNodeByIndex(index - 1).next = newNode;
            getNodeByIndex(index).prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T content : list) {
            add(content);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unLink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (node.value == object || object != null && node.value.equals(object)) {
                unLink(getNodeByIndex(counter));
                return true;
            } else {
                node = node.next;
                counter++;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
    }

    private void getIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> node;
        if (index <= (size / 2)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unLink(Node<T> node) {
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

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
