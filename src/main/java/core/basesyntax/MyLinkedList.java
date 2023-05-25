package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        Node newNode = new Node(value, tail, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        if (index == size) {
            add(value);
        } else {
            Node nextNode = findNodeByIndex(index);
            Node newNode = new Node(value, null, nextNode);
            if (nextNode.prev == null) {
                head = newNode;
            } else {
                newNode.prev = nextNode.prev;
                nextNode.prev.next = newNode;
            }
            nextNode.prev = newNode;
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
        Node currentNode = findNodeByIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node currentNode = findNodeByIndex(index);
        T currentValue = currentNode.value;
        currentNode.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node currentNode = findNodeByIndex(index);
        return unlink(currentNode);
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == null && object == null
                    || currentNode.value != null && currentNode.value.equals(object)) {
                unlink(currentNode);
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

    private void checkIndex(int index, int upperBound) {
        if (index < 0 || index >= upperBound) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds for length %d", index, size));
        }
    }

    private Node findNodeByIndex(int index) {
        Node currentNode;
        if (index <= size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unlink(Node node) {
        Node nextNode = node.next;
        Node prevNode = node.prev;
        if (nextNode != null) {
            nextNode.prev = node.prev;
        } else {
            tail = prevNode;
        }
        if (prevNode != null) {
            prevNode.next = node.next;
        } else {
            head = nextNode;
        }
        size--;
        return node.value;
    }

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        public Node(T value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
