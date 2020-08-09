package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
    }

    @Override
    public boolean add(T value) {
        Node<T> node;
        if (size == 0) {
            node = new Node<>(value, null, null);
            head = node;
        } else {
            node = new Node<>(value, tail, null);
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> nodeBeforeInput = getNode(index);
            Node<T> oldPrevious = nodeBeforeInput.previous;
            Node<T> inputNode = new Node<>(value, oldPrevious, nodeBeforeInput);
            nodeBeforeInput.previous = inputNode;
            if (oldPrevious == null) {
                head = inputNode;
            } else {
                oldPrevious.next = inputNode;
            }
            size++;
        }

    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T previousValue = node.value;
        node.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        Node<T> target = getNode(index);
        removingAction(target);
        return target.value;
    }

    @Override
    public boolean remove(T t) {
        if (size > 0) {
            Node<T> node = head;
            for (int i = 0; i < size; i++) {
                if ((t == null || node.value == null)
                        ? t == node.value : node.value.equals(t)) {
                    removingAction(node);
                    return true;
                }
                node = node.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node;
    }

    private void removingAction(Node<T> target) {
        Node<T> previous = target.previous;
        Node<T> next = target.next;

        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
            target.previous = null;
        }
        if (next == null) {
            tail = previous;
        } else {
            next.previous = previous;
            target.next = null;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }
}
