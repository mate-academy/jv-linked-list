package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> oldTail = tail;
        if (size == 0) {
            head = tail = new Node<>(oldTail,head,value);
        } else {
            tail = oldTail.next = new Node<>(null,tail,value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> current = getNode(index);
            linkBefore(value,current);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        unlink(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (current.value == object || current.value != null
                    && current.value.equals(object)) {
                unlink(current);
                return true;
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

    private class Node<T> {
        private Node<T> next;
        private Node<T> previous;
        private T value;

        public Node(Node<T> next, Node<T> previous, T value) {
            this.next = next;
            this.previous = previous;
            this.value = value;
        }
    }

    private Node<T> getNode(int index) {
        validateIndex(index);
        Node<T> current;
        int currentIndex;
        if (size / 2 > index) {
            current = tail;
            currentIndex = size - 1;
            while (currentIndex > index) {
                if (current.previous != null) {
                    current = current.previous;
                    currentIndex--;
                }
            }
        } else {
            current = head;
            currentIndex = 0;
            while (currentIndex < index) {
                if (current.next != null) {
                    current = current.next;
                    currentIndex++;
                }
            }
        }
        return current;
    }

    private void linkBefore(T value, Node<T> current) {
        if (current == head) {
            head.previous = new Node<>(head, null, value);
            head = head.previous;
        } else {
            current.previous.next = new Node<>(current, current.previous, value);
            current.previous = current.previous.next;
        }
        size++;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            if (size == 1) {
                size--;
                return;
            }
            node.next.previous = null;
            head = node.next;
        } else if (node == tail) {
            node.previous.next = null;
            tail = node.previous;
            node.previous = null;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
            node.previous = null;
        }
        size--;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of size:" + size);
        }
    }
}
