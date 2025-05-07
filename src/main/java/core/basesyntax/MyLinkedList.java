package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = tail = new Node<>(null, value, null);
        } else {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "The index is not valid use, please use values in range: 0 - " + size);
        }

        if (size != 0 && index != size) {
            if (index == 0) {
                Node<T> nextNode = findNodeByIndex(index);
                Node<T> node = new Node<>(null, value, nextNode);

                nextNode.prev = node;
                head = node;
                size++;
            } else {
                Node<T> prevNode = findNodeByIndex(index - 1);
                Node<T> node = new Node<>(prevNode, value, prevNode.next);

                prevNode.next.prev = node;
                prevNode.next = node;
                size++;
            }
        } else {
            add(value);
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
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node<T> node = findNodeByIndex(index);
        T prevValue = node.value;
        node.value = value;

        return prevValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> node = findNodeByIndex(index);
        unlink(node);

        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, node.value)) {
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i != index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i != index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "The index is not valid use, please use values in range: 0 - " + size);
        }
    }

    private void unlink(Node node) {
        if (tail == head) {
            tail = head = null;
        } else if (node == head) {
            node.next.prev = null;
            head = node.next;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
