package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        validateIndex(index);
        Node<T> newNode = new Node<>(value);
        newNode.next = getNodeByIndex(index);
        newNode.prev = newNode.next.prev;
        if (index == 0) {
            head.prev = newNode;
            head = newNode;
        } else {
            newNode.next.prev = newNode;
            newNode.prev.next = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setNode = getNodeByIndex(index);
        T nodeValue = setNode.value;
        setNode.value = value;
        return nodeValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T nodeValue = currentNode.value;
        unlinkNode(currentNode);
        return nodeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if ((object == node.value) || (object != null && object.equals(node.value))) {
                unlinkNode(node);
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

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: "
                    + index + " for size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        validateIndex(index);
        if (index > size / 2) {
            Node<T> node = tail;
            for (int i = 0; i < size - index - 1; i++) {
                node = node.prev;
            }
            return node;
        }
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void unlinkNode(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            head = head.next;
        } else if (node.next == null) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
