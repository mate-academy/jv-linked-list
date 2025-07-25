package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index); // Ensures index is in [0, size]
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            if (head == null) { // Empty list: set head and tail
                head = newNode;
                tail = newNode;
            } else { // Insert at head: update links
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size) { // Insert at tail
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else { // Insert in the middle (index > 0 and index < size)
            Node<T> current = getNodeAt(index); // Current node at index
            Node<T> previous = current.prev; // Safe: index > 0, so current is not head
            newNode.next = current;
            newNode.prev = previous;
            previous.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    private Node<T> getNodeAt(int ind) {
        checkElementIndex(ind);
        Node<T> current = head;
        for (int i = 0; i < ind; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index for add: " + index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        Node<T> current = getNodeAt(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        checkElementIndex(index);
        Node<T> current = getNodeAt(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index for remove: " + index);
        }
        Node<T> nodeToRemove = getNodeAt(index);
        if (size == 1) {
            head = null;
            tail = null;
        } else if (index == 0) {
            head = head.next;
            if (head != null) { // Додаємо перевірку, щоб уникнути NullPointerException
                head.prev = null;
            }
        } else if (index == size - 1) {
            tail = tail.prev;
            tail.next = null;
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
        }
        size--;

        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.value, object)) {
                if (size == 1) {
                    head = null;
                    tail = null;
                } else if (current == head) {
                    head = head.next;
                    if (head != null) {
                        head.prev = null;
                    }
                } else if (current == tail) {
                    tail = tail.prev;
                    tail.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
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
}
