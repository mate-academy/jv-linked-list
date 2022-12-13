package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            currentNode.prev = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.prev.next = currentNode.prev;
        }
        size++;

    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T result = node.value;
        node.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        T result = node.value;
        unlink(node);
        return result;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (object == null && node.value == null) {
                unlink(node);
                return true;
            } else if (node.value != null && node.value.equals(object)) {
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + " is out of Linked List size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        int middle = size / 2;
        int i = 0;
        if (middle >= index) {
            Node<T> node = head;
            while (i != index) {
                node = node.next;
                i++;
            }
            return node;
        } else {
            Node<T> node = tail;
            i = size - 1;
            while (i != index) {
                node = node.prev;
                i--;
            }
            return node;
        }
    }

    private void unlink(Node<T> node) {
        if (head == node && size == 1) {
            head = null;
        } else if (head == node && size > 1) {
            head = head.next;
            head.prev = null;
        } else if (tail == node) {
            tail.prev = tail;
            tail.prev.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}
