package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> node;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<T>(null, value, null);
            node = head;
            tail = head;
        } else {
            tail = new Node<T>(tail, value, null);
            rewindToSelectedNode(size - 1);
            node.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        if (index == 0) {
            head = new Node<T>(null, value, head);
            node = head;
            size++;
        } else if (index == size) {
            add(value);
        } else {
            rewindToSelectedNode(index);
            node = new Node<>(node.prev, value, node);
            node.next.prev = node;
            node.prev.next = node;
            size++;
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
        checkBounds(index);
        rewindToSelectedNode(index);
        return node.element;
    }

    @Override
    public T set(T value, int index) {
        checkBounds(index);
        T res = null;
        if (index == 0) {
            res = head.element;
            head.element = value;
        } else if (index == size - 1) {
            res = tail.element;
            tail.element = value;
            rewindToSelectedNode(index);
            node = tail;
        } else {
            rewindToSelectedNode(index);
            res = node.element;
            node.element = value;
        }
        return res;
    }

    @Override
    public T remove(int index) {
        checkBounds(index);
        T value = removeByIndex(index);
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        node = head;
        int index = 0;
        while (node != null) {
            if (Objects.equals(node.element, object)) {
                removeByIndex(index);
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

    private T removeByIndex(int index) {
        T object = null;
        if (size == 1) {
            object = node.element;
            head = null;
            tail = null;
            node = null;
        } else if (index == 0) {
            object = head.element;
            head = head.next;
            head.prev = null;
            node = head;
        } else if (index == size - 1) {
            rewindToSelectedNode(index);
            object = node.element;
            node = node.prev;
            node.next = null;
            tail = node;
        } else {
            rewindToSelectedNode(index);
            object = node.element;
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        return object;
    }

    private void rewindToSelectedNode(int index) {
        node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
    }

    private void checkBounds(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
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
}
