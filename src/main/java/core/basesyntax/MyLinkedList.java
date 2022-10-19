package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;

        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> node = new Node<>(null, value, null);
        Node<T> firstNode = head;
        if (size == 0 || index == size) {
            add(value);
        } else if (index == 0) {
            node = new Node<>(null, value, head);
            node.next = head;
            head = node;
            size++;
        } else {
            for (int i = 0; i < index - 1; i++) {
                firstNode = firstNode.next;
            }
            node.next = firstNode.next;
            node.prev = firstNode;
            firstNode.next.prev = node;
            firstNode.next = node;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        T prevValue = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;

        return prevValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        unlink(getNodeByIndex(index));
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        if (head.value == object && size == 1) {
            unlink(node);
            return true;
        } else if (node.prev == null && head.value == object) {
            unlink(node);
            return true;
        }
        for (int i = 0; i < size; i++) {
            if (node.value != null && node.value.equals(object) || node.value == object) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
            size--;
        } else if (node == tail) {
            tail = node.prev;
            size--;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> countNode = head;
        int count = 0;
        while (countNode != null) {
            if (count == index) {
                return countNode;
            }
            count++;
            countNode = countNode.next;
        }
        return null;
    }

    static class Node<T> {
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
