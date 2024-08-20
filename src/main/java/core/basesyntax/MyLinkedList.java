package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int INVALID_INDEX = -1;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> prevNode = tail;
        Node<T> newNode = new Node<T>(prevNode, value, null);
        tail = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("This index '" + index + "' are wrong!");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> actualNode = findNodeByIndex(index);
            Node<T> prevNode = actualNode.previous;
            Node<T> newNode = new Node<>(prevNode, value, actualNode);
            actualNode.previous = newNode;
            if (prevNode == null) {
                head = newNode;
            } else {
                prevNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = findNodeByIndex(index).item;
        findNodeByIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldValue = get(index);
        unlink(index);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        int objectIndex = findIndexByValue(object);
        if (objectIndex != INVALID_INDEX) {
            unlink(objectIndex);
            return true;
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> node;
        if (index > size / 2) {
            node = tail;
            for (int i = 1; i < size - index; i++) {
                node = node.previous;
            }
            return node;
        }
        node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private int findIndexByValue(T value) {
        for (int i = 0; i < size; i++) {
            T nodeValue = findNodeByIndex(i).item;
            if (nodeValue == null && nodeValue == value
                    || nodeValue != null && nodeValue.equals(value)) {
                return i;
            }
        }
        return INVALID_INDEX;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index '" + index + "' are wrong!");
        }
    }

    private void unlink(int index) {
        if (index == 0 && size > 1) {
            Node<T> newHead = head.next;
            newHead.previous = head.previous;
            head = newHead;
        } else if (index == 0 && size == 1) {
            head.item = null;
            head = null;
            tail = null;
        } else if (index == size - 1) {
            Node<T> last = findNodeByIndex(index).previous;
            last.next = findNodeByIndex(index).next;
            tail = last;
        } else {
            Node<T> prevNode = findNodeByIndex(index).previous;
            Node<T> nextNode = findNodeByIndex(index).next;
            prevNode.next = nextNode;
            nextNode.previous = prevNode;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T item, Node<T> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }
}
