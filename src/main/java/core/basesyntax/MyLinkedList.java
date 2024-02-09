package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int ONE = 1;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = createNewNode(value);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size()) {
            add(value);
        } else {
            Node<T> newNode = createNewNode(value);
            if (index == 0) {
                addFirst(newNode);
            } else {
                Node<T> current = getNodeAtIndex(index - ONE);
                insertAfter(current, newNode);
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = getNodeAtIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNodeAtIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getNodeAtIndex(index);
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == null ? current.value == null :
                    object.equals(current.value)) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> createNewNode(T value) {
        return new Node<>(null, value, null);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index "
                    + index + " is out of bounds. Size: " + size());
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index "
                    + index + " is out of bounds. Size: " + size());
        }
    }

    private void addFirst(Node<T> newNode) {
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }

    private void insertAfter(Node<T> current, Node<T> newNode) {
        newNode.prev = current;
        newNode.next = current.next;
        if (current.next != null) {
            newNode.next.prev = newNode;
        }
        current.next = newNode;
    }

    private void removeNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    private Node<T> getNodeAtIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
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
