package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

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
            throw new IndexOutOfBoundsException("There are no such index: " + index);
        }
        Node<T> newNode = new Node<>(null, value, null);
        addValueByIndex(newNode, index, value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        T previous = current.value;
        current.value = value;
        return previous;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        unlink(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (current.value == object || (current.value != null
                    && current.value.equals(object))) {
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> current;
        if (index < size * 0.5) {
            current = first;
            for (int i = 0; i < index; i++) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("There are no index: " + index
                    + " in this linked list");
        }
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            size--;
        } else if (node == first) {
            first = node.next;
            first.prev = null;
            size--;
        } else if (node == last) {
            last = node.prev;
            last.next = null;
            size--;
        } else {
            Node<T> previous = node.prev;
            Node<T> nextNode = node.next;
            previous.next = nextNode;
            nextNode.prev = previous;
            size--;
        }
    }

    private void addValueByIndex(Node<T> node, int index, T value) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            node.next = first;
            node.prev = null;
            first.prev = node;
            first = node;
            size++;
        } else {
            Node<T> current = findNodeByIndex(index);
            Node<T> previous = current.prev;
            node.next = current;
            node.prev = previous;
            previous.next = node;
            current.prev = node;
            size++;
        }
    }
}
