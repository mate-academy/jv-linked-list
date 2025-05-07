package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = getNodeByIndex(index);
        Node<T> addedNode = new Node<>(current.prev, value, current);
        if (current.prev == null) {
            head = addedNode;
        } else {
            current.prev.next = addedNode;
        }
        current.prev = addedNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new RuntimeException("Can't add list");
        }
        for (T element:list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldVal = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNodeByIndex(index);
        T deletedObject = getNodeByIndex(index).value;
        deleteNode(current);
        return deletedObject;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if ((current.value == object)
                    || current.value != null && current.value.equals(object)) {
                deleteNode(current);
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

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is invalid");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        validateIndex(index);
        Node<T> node;
        if (index < size >> 1) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void deleteNode(Node<T> current) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (current.prev == null) {
            head = current.next;
            current.next.prev = null;
        } else if (current.next == null) {
            tail = current.prev;
            current.prev.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
    }

    private static class Node<T> {
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
