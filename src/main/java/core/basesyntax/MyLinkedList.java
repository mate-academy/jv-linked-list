package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index != size) {
            checkIndex(index);
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            if (head == null) {
                tail = head = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            size++;
        } else if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next.prev = newNode;
            newNode.next = current.next;
            current.next = newNode;
            newNode.prev = current;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        Node<T> newNode;
        if (list != null) {
            for (T el : list) {
                newNode = new Node<>(el);
                if (head == null) {
                    head = tail = newNode;
                } else {
                    tail.next = newNode;
                    tail = newNode;
                }
                size++;
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T tmp = current.value;
        current.value = value;
        return tmp;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T tmp;
        if (index == 0) {
            tmp = head.value;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.prev = null;
            }
        } else if (index == size - 1) {
            tmp = tail.value;
            tail = tail.prev;
            if (tail == null) {
                head = null;
            } else {
                tail.next = null;
            }
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            tmp = current.value;
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return tmp;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }
        if (head.value.equals(object)) {
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.prev = null;
            }
            size--;
            return true;
        }
        Node<T> current = head;
        while (current != null) {
            if ((current.value != null && current.value.equals(object))
                    || (object == current.value)) {
                if (current == tail) {
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
        return head == null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.prev = null;
            this.value = value;
            this.next = null;
        }
    }
}
