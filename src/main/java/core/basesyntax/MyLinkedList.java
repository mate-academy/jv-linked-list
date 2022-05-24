package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String MESSAGE = "index out of bounds";
    private Node<T> head;
    private Node tail;
    private int size = 0;

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            size++;
        } else {
            if (tail == null) {
                tail = new Node<>(head, value, null);
                head.next = tail;
                size++;
                return;
            }
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(MESSAGE);
        }
        Node<T> node;
        int count = 0;
        node = head;
        while (count != index) {
            node = node.next;
            count++;
        }
        Node<T> current;
        switch (count) {
            case 0:
                if (head == null) {
                    head = new Node<>(null, value, null);
                } else {
                    current = new Node<>(null, value, head);
                    head.prev = current;
                    head = current;
                }
                break;
            case 1:
                if (tail == null) {
                    tail = new Node<>(head, value, null);
                    head.next = tail;
                } else {
                    current = new Node<>(head, value, node);
                    node.prev = current;
                    head.next = current;
                }
                break;
            default:
                if (count == size) {
                    current = new Node<>(tail, value, null);
                    tail.next = current;
                    tail = current;
                } else {
                    current = new Node<>(node.prev, value, node);
                    node.prev.next = current;
                    node.prev = current;
                }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        Node<T> node;
        for (T value : list) {
            node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size += list.size();
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(MESSAGE);
        }
        Node<T> node = head;
        for (int i = 1; i <= index; i++) {
            node = node.next;
        }
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(MESSAGE);
        }
        Node<T> node;
        int count = 0;
        boolean directionSearchingIndex = index <= (size / 2);
        if (directionSearchingIndex) {
            node = head;
            while (count != index) {
                node = node.next;
                count++;
            }
            T current = node.value;
            node.value = value;
            return current;
        }
        node = tail;
        count = size - 1;
        while (count != index) {
            node = node.prev;
            count--;
        }
        T current = node.value;
        node.value = value;
        return current;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(MESSAGE);
        }
        int count = 0;
        Node<T> node = head;
        while (count != index) {
            node = node.next;
            count++;
        }
        T value = node.value;
        unlink(node);
        return value;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> node = head;
        while (node != null) {
            if (node.value == null && object == null) {
                unlink(node);
                return true;
            } else if (node.value.equals(object)) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private void unlink(Node node) {
        if (node == head) {
            switch (size) {
                case 1:
                    head = null;
                    break;
                case 2:
                    head = tail;
                    tail = null;
                    head.prev = null;
                    break;
                default:
                    head = node.next;
                    head.prev = null;
            }
        } else if (node.next == null && size > 2) {
            tail = node.prev;
            tail.next = null;
        } else if (node.next == null && size == 2) {
            head.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}
