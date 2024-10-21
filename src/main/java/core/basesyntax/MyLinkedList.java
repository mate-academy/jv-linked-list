package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> node;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        rewindNodeToHead();
        if (size == 0) {
            head = new Node<T>(null, value, null);
            tail = new Node<T>(null, value, null);
            node = head;
        } else {
            rewindToLastNode();
            node.next = new Node<T>(node, value, null);
            tail = node;

        }
        size += 1;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        rewindNodeToHead();
        if (index == size) {
            add(value);
        } else {
            rewindToSelectedNode(index);
            insertNodeInCurrentPosition(index, value);
            size += 1;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkBoundsIndex(index, "Wrong index");
        Node<T> temp = node;
        rewindNodeToHead();
        for (int i = 0; i < size; i++) {
            if (i != index) {
                node = node.next;
                continue;
            }
            temp = node;
        }
        return temp.element;
    }

    @Override
    public T set(T value, int index) {
        checkBoundsIndex(index, "Incorrect index");
        rewindNodeToHead();
        rewindToSelectedNode(index);
        T res = node.element;
        node.element = value;
        return res;
    }

    @Override
    public T remove(int index) {
        checkBoundsIndex(index, "Incorrect index");
        rewindNodeToHead();
        rewindToSelectedNode(index);
        T value = node.element;
        chooseConditionOfRemoving(index);
        size -= 1;
        return value;
    }

    @Override
    public boolean remove(T object) {
        rewindNodeToHead();
        int index = 0;
        while (node != null) {
            if (node.element == object || node.element != null
                    && node.element.equals(object)) {
                chooseConditionOfRemoving(index);
                size -= 1;
                return true;
            }
            node = node.next;
            index += 1;
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

    private void rewindNodeToHead() {
        if (node != null) {
            while (node.prev != null) {
                node = node.prev;
            }
        }
    }

    private void chooseConditionOfRemoving(int index) {
        if (size == 1) {
            node = null;
        } else if (index == 0) {
            node = node.next;
            node.prev = null;
        } else if (index == size - 1) {
            node = node.prev;
            node.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private void rewindToSelectedNode(int index) {
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
    }

    private void rewindToLastNode() {
        while (node.next != null) {
            node = node.next;
        }
    }

    private void insertNodeInCurrentPosition(int index, T value) {
        if (index == 0) {
            node = new Node<>(null, value, node);
            node.next.prev = node;
        } else {
            node = new Node<>(node.prev, value, node);
            node.next.prev = node;
            node.prev.next = node;
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T element;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    private void checkBoundsIndex(int index, String message) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(message);
        }
    }
}
