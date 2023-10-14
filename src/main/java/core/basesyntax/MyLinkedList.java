package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUND_EXCEPTION = "Index: %d out of bound!! Size: %d";
    private static final int ONE = 1;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        public Node(T item) {
            this.item = item;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
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
        validateIndex(index);
        Node<T> newNode = new Node<>(value);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> current = getNodeByIndex(index);
            newNode.previous = current.previous;
            newNode.next = current;
            current.previous.next = newNode;
            current.previous = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value :
                list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> removedNode = getNodeByIndex(index);
        if (removedNode.previous != null) {
            removedNode.previous.next = removedNode.next;
        } else {
            head = removedNode.next;
        }
        if (removedNode.next != null) {
            removedNode.next.previous = removedNode.previous;
        } else {
            tail = removedNode.previous;
        }
        size--;
        return removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((current.item == object) || (current.item != null && current.item.equals(object))) {
                unlink(current);
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void unlink(Node<T> node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            tail = node.previous;
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

    private Node<T> getNodeByIndex(int index) {
        validateIndex(index + ONE);
        if (index <= size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - ONE; i > index; i--) {
                current = current.previous;
            }
            return current;
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    String.format(INDEX_OUT_OF_BOUND_EXCEPTION, index, size)
            );
        }
    }
}
