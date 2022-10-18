package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

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

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void unlink(Node<T> node) {
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

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (size == 0) {
            head = tail = node;
        } else {
            node = new Node<>(tail, value, null);
            tail.next = node;
            node.prev = tail;
            node.next = null;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index, size + 1);
        Node<T> node = new Node<>(null, value, null);
        Node<T> firstNode = head;
        if (size == 0) {
            head = tail = node;
        } else if (index == 0) {
            node = new Node<>(null, value, head);
            node.next = head;
            head = node;
        } else if (index == size) {
            node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        } else {
            for (int i = 0; i < index - 1; i++) {
                firstNode = firstNode.next;
            }
            node.next = firstNode.next;
            node.prev = firstNode;
            firstNode.next.prev = node;
            firstNode.next = node;
        }
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
        Objects.checkIndex(index, size);
        Node<T> countNode = head;
        int count = 0;
        while (countNode != null) {
            if (count == index) {
                return countNode.value;
            }
            count++;
            countNode = countNode.next;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index, size);
        Node<T> countNode = head;
        int count = 0;
        while (count < index) {
            countNode = countNode.next;
            count++;
        }
        T prevValue = countNode.value;
        countNode.value = value;

        return prevValue;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        Node<T> countNode = head;
        int count = 0;
        while (countNode != null) {
            if (index == count) {
                unlink(countNode);
                return countNode.value;
            }
            count++;
            countNode = countNode.next;
        }
        return null;
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
}
