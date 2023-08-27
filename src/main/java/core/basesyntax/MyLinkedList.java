package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    static class Node<T> {
        private T value;
        private Node next;
        private Node previous;

        public Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Incorrect index: " + index);
        }
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        } else if (index == size) {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> current;
            if (index < size / 2) {
                current = head;
                for (int i = 0; i < index; i++) {
                    current = current.next;
                }
            } else {
                current = tail;
                for (int i = 0; i < size - index - 1; i++) {
                    current = current.previous;
                }
            }
            newNode.next = current;
            newNode.previous = current.previous;
            current.previous.next = newNode;
            current.previous = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        outOfBoundsCheck(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = 0; i < size - index - 1; i++) {
                current = current.previous;
            }
        }
        return (T) current.value;
    }

    @Override
    public T set(T value, int index) {
        outOfBoundsCheck(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = 0; i < size - index - 1; i++) {
                current = current.previous;
            }
        }
        T removedValue = current.value;
        current.value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        outOfBoundsCheck(index);
        if (index == 0) {
            T removedElement = (T) head.value;
            head = head.next;
            size--;
            return removedElement;
        } else if (index == size - 1) {
            T removedElement = (T) tail.value;
            tail = tail.previous;
            size--;
            return removedElement;
        }
        T removedElement;
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = 0; i < size - index - 1; i++) {
                current = current.previous;
            }
        }
        removedElement = (T) current.value;
        unlink(current);
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value != null && current.value.equals(object) || current.value == object) {
                if (current == head) {
                    head = head.next;
                } else if (current == tail) {
                    tail = tail.previous;
                } else {
                    unlink(current);
                }
                size--;
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

    private void outOfBoundsCheck(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Incorrect index: " + index);
        }
    }

    private void unlink(Node node) {
        node.previous.next = node.next;
        node.next.previous = node.previous;
    }
}
