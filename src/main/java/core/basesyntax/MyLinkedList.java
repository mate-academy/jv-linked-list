package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_LESS_THAN_ZERO_MESSAGE = "Index can't be less than zero";
    private static final String INDEX_NOT_FOUND_MESSAGE = "Not found index: ";
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            insertFirst(value);
            return;
        }
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            insertFirst(value);
            return;
        }
        Node<T> prevNode = elementAt(index - 1);
        Node<T> newNode = new Node<>(prevNode, value, prevNode.next);
        prevNode.next.prev = newNode;
        prevNode.next = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return elementAt(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = elementAt(index).item;
        elementAt(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> toRemove = elementAt(index);
        removeNode(toRemove);
        return toRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> toRemove = findElementWithValue(object);
        if (toRemove == null) {
            return false;
        }
        removeNode(toRemove);
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

    private Node<T> elementAt(int index) {
        Node<T> current;
        if (index <= size >> 1) {
            current = head;
            int i = 0;
            while (i < index) {
                i++;
                current = current.next;
            }
        } else {
            current = tail;
            int i = size - 1;
            while (i > index) {
                i--;
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(INDEX_LESS_THAN_ZERO_MESSAGE);
        }
        index++;
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INDEX_NOT_FOUND_MESSAGE + index);
        }
    }

    private void insertFirst(T value) {
        if (head == null) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    private Node<T> findElementWithValue(T object) {
        Node<T> tempElement = head;
        for (int i = 0; i < size; i++) {
            T item = tempElement.item;
            if ((item == null && object == null)
                    || (item != null && item.equals(object))) {
                return tempElement;
            }
            tempElement = tempElement.next;
        }
        return null;
    }

    private void removeNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
            if (node.equals(head)) {
                head = node.next;
            }
        }
        size--;
    }
}
