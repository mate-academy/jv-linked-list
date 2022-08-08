package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(null, value, null);
            unlinkNode(currentNode.prev, newNode, currentNode, "add");
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("The index is out of bounds");
        }
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("The index is out of bounds");
        }
        Node<T> currentNode = getNodeByIndex(index);
        T oldNodeValue = currentNode.value;
        currentNode.value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("The index is out of bounds");
        }
        Node<T> currentNode = getNodeByIndex(index);
        unlinkNode(currentNode.prev, currentNode, currentNode.next, "remove");
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if ((object == null && currentNode.value == null)
                    || (object != null && object.equals(currentNode.value))) {
                unlinkNode(currentNode.prev, currentNode, currentNode.next, "remove");
                return true;
            }
            currentNode = currentNode.next;
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

    private Node<T> getNodeByIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("The index is out of bounds");
        }
        Node<T> currentNode;
        if (index <= size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size; i > index + 1; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void unlinkNode(Node<T> previous, Node<T> current, Node<T> next, String operation) {
        switch (operation) {
            case "add":
                if (next.equals(head)) {
                    head = current;
                }
                if (previous != null) {
                    previous.next = current;
                }
                current.prev = previous;
                current.next = next;
                next.prev = current;
                size++;
                break;
            case "remove":
                if (head.equals(current)) {
                    head = next;
                }
                current.prev = null;
                current.next = null;
                if (previous != null) {
                    previous.next = next;
                }
                if (next != null) {
                    next.prev = previous;
                }
                size--;
                break;
            default:
                break;
        }
    }
}
