package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(first ,value, null);
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
        if (index == size) {
            add(value);
        } else if (index == 0) {
            newNode.next = first;
            newNode.prev = null;
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> current = findNodeByIndex(index);
            Node<T> previous = current.prev;
            newNode.next = current;
            newNode.prev = previous;
            previous.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
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
        if (size == 1) {
            size--;
        } else if (current == first) {
            first = current.next;
            first.prev = null;
            size--;
        } else if (current == last) {
            last = current.prev;
            last.next = null;
            size--;
        } else {
            Node<T> previous = current.prev;
            Node<T> nextNode = current.next;
            previous.next = nextNode;
            nextNode.prev = previous;
            size--;
        }
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (current.value == object || (current.value != null
                    && current.value.equals(object))) {
                if (size == 1) {
                    size--;
                } else if (current == first) {
                    first = current.next;
                    first.prev = null;
                    size--;
                } else if (current == last) {
                    last = current.prev;
                    last.next = null;
                    size--;
                } else {
                    Node<T> previous = current.prev;
                    Node<T> nextNode = current.next;
                    previous.next = nextNode;
                    nextNode.prev = previous;
                    size--;
                }
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
        Node<T> current = new Node<>(null, null, null);
        if (index < size * 0.5) {
            current = first;
            for (int i = 0; i < size; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = 0; i < size - index - 1; i++) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("There are no index: " + index + " in this linked list");
        }
    }
}
