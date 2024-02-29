package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> current = getNodeByIndex(index);
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null && !list.isEmpty()) {
            for (T element : list) {
                Node<T> newNode = new Node<>(tail, element, null);
                if (tail != null) {
                    tail.next = newNode;
                }
                tail = newNode;
                if (size == 0) {
                    head = newNode;
                }
                size++;
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndexElement(index);
        Node<T> current = getNodeByIndex(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexElement(index);
        Node<T> current = getNodeByIndex(index);
        T oldItem = current.item;
        current.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndexElement(index);
        Node<T> removed = getNodeByIndex(index);
        removeItem(removed);
        return removed.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.item == null) || (object != null
                    && object.equals(current.item))) {
                removeItem(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;

        }
    }

    private boolean getIndexElement(int index) {
        return index >= 0 && index < size;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIndexElement(int index) {
        if (!getIndexElement(index)) {
            throw new IndexOutOfBoundsException("Invalid index" + index);
        }
    }

    private void removeItem(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }
}
