package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> oldAtIndex = head;
            for (int i = 0; i < index; i++) {
                oldAtIndex = oldAtIndex.next;
            }
            final Node<T> newNode = new Node<>(oldAtIndex.prev, value, oldAtIndex);
            if (oldAtIndex.prev == null) {
                head = newNode;
            } else {
                oldAtIndex.prev.next = newNode;
            }
            oldAtIndex.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        T finalValue = current.value;
        current.value = value;
        return finalValue;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            final T temp = head.value;
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return temp;
        } else if (index == size - 1) {
            if (size == 0) {
                return null;
            } else {
                final T temp = tail.value;
                tail = tail.prev;
                tail.next = null;
                size--;
                return temp;
            }
        } else {
            Node<T> previous = head;
            for (int i = 0; i < index; i++) {
                previous = previous.next;
            }
            Node<T> current = previous.next;
            previous.next = current;
            current.prev = previous.prev;
            previous.prev.next = current;
            size--;
            return previous.value;
        }
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
            if (Objects.equals(currentNode.value, object)) {
                unlink(currentNode);
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
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }

    private void unlink(Node<T> current) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (current.prev == null) {
            head = current.next;
            head.prev = null;
        } else if (current.next == null) {
            tail = current.prev;
            tail.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Expected index is incorrect.");
        }
    }
}
