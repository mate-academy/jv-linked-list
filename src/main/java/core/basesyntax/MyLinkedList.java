package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_ERROR_MESSAGE = "The passed index is not allowed";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index, true);
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            newNode.prev = currentNode.prev;
            newNode.next = currentNode;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index, false);
        Node<T> node = getNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index, false);
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index, false);
        Node<T> current = getNodeByIndex(index);
        unlink(current);
        size--;
        if (current == head) {
            head = current.next;
        }
        if (current == tail) {
            tail = current.prev;
        }
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNodeByValue(object);
        if (node == null) {
            return false;
        }
        unlink(node);
        size--;
        if (node == head) {
            head = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }

    private void validateIndex(int index, boolean isForAdd) {
        if (isForAdd && (index < 0 || index > size)) {
            throw new IndexOutOfBoundsException(INDEX_ERROR_MESSAGE);
        }
        if (!isForAdd && (index < 0 || index >= size)) {
            throw new IndexOutOfBoundsException(INDEX_ERROR_MESSAGE);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        validateIndex(index, false);
        if (index < size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> current = head;
        while (current != null) {
            if (value == current.value
                    || (value != null && value.equals(current.value))) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
