package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_LESS_THAN_ZERO_MESSAGE = "Index can't be less than zero";
    private static final String INDEX_NOT_FOUND_MESSAGE = "Not found index: ";
    private int size;
    private Node<T> head;
    private Node<T> tail;

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
        Node<T> nextNode = elementAt(index);
        Node<T> newNode = new Node<>(nextNode.prev, value, nextNode);
        nextNode.prev.next = newNode;
        nextNode.prev = newNode;
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
        return elementAt(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = elementAt(index).value;
        elementAt(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removingNode = elementAt(index);
        return removeNode(removingNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> removingNode = nodeWithValue(object);
        if (removingNode == null) {
            return false;
        }
        removeNode(removingNode);
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

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
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

    private Node<T> nodeWithValue(T object) {
        Node<T> tempElement = head;
        for (int i = 0; i < size; i++) {
            T item = tempElement.value;
            if ((item == object)
                    || (item != null && item.equals(object))) {
                return tempElement;
            }
            tempElement = tempElement.next;
        }
        return null;
    }

    private T removeNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
            if (node.equals(tail)) {
                tail = node.prev;
            }
        }
        if (node.next != null) {
            node.next.prev = node.prev;
            if (node.equals(head)) {
                head = node.next;
            }
        }
        T value = node.value;
        node.value = null;
        size--;
        if (size == 0) {
            head = null;
            tail = null;
        }
        return value;
    }
}
