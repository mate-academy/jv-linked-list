package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> prevNode = getNodeAtIndex(index - 1);
            newNode.next = prevNode.next;
            prevNode.next = newNode;
        }

        if (newNode.next != null) {
            newNode.next.prev = newNode;
        }
        if (index == size - 1) {
            tail = newNode;
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndexForSetGetRemove(index);
        Node<T> current = getNodeAtIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForSetGetRemove(index);
        Node<T> current = getNodeAtIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForSetGetRemove(index);
        return unlink(getNodeAtIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if (current.value == object
                    || (current.value != null && current.value.equals(object))) {
                unlink(current);
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

    private Node<T> getNodeAtIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private T unlink(Node<T> node) {
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

        size--;

        return node.value;
    }

    private void removeNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next; // Removing the head node
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev; // Removing the tail node
        }
        size--;
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForSetGetRemove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
