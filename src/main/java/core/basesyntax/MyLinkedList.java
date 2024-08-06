package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int EMPTY_LIST = 0;

    private Node<T> head;

    private Node<T> tail;

    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = EMPTY_LIST;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == EMPTY_LIST) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index >= size && index < EMPTY_LIST) {
            throw new IndexOutOfBoundsException("Index is out of list");
        }

        Node<T> newNode = new Node<>(value);

        if (index == size) {
            add(value);
        } else {
            Node<T> current = getNode(index);
            newNode.prev = current.prev;
            newNode.next = current;
            if (current.prev != null) {
                current.prev.next = newNode;
            }
            current.prev = newNode;
            if (index == 0) {
                head = newNode;
            }
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size && index < EMPTY_LIST) {
            throw new IndexOutOfBoundsException("Index is out of list");
        }

        Node<T> current = getNode(index);
        return current.item;
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private Node<T> getNode(T value) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.item == value) {
                break;
            }
            current = current.next;
        }
        return current;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size && index < EMPTY_LIST) {
            throw new IndexOutOfBoundsException("Index is out of list");
        }

        Node<T> current = getNode(index);
        current.item = value;
        return current.item;
    }

    @Override
    public T remove(int index) {
        if (index >= size && index < EMPTY_LIST) {
            throw new IndexOutOfBoundsException("Index is out of list");
        }

        Node<T> current = getNode(index);
        unlinkNode(current);
        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = getNode(object);
        unlinkNode(current);
        size--;
        return true;
    }

    private void unlinkNode(Node<T> node) {
        node.item = null;
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.next = node.prev;
        } else {
            tail = node.prev;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }
    }
}
