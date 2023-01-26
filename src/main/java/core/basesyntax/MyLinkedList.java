package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node nodeOfIndex(int index) {
        Node currentNode;
        if (index <= size) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (tail == null) {
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds for length %d", index, size));
        }
        if (index == 0 && head == null || index == size) {
            add(value);
        } else {
            Node newNode = new Node(value);
            Node nextNode = nodeOfIndex(index);
            if (nextNode.prev == null) {
                head = newNode;
            } else {
                newNode.prev = nextNode.prev;
                nextNode.prev.next = newNode;
            }
            newNode.next = nextNode;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds for length %d", index, size));
        }
        Node currentNode = nodeOfIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds for length %d", index, size));
        }
        Node currentNode = nodeOfIndex(index);
        T currentValue = currentNode.value;
        currentNode.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds for length %d", index, size));
        }
        Node currentNode = nodeOfIndex(index);
        Node nextNode = currentNode.next;
        Node prevNode = currentNode.prev;
        if (nextNode != null) {
            nextNode.prev = currentNode.prev;
        } else {
            tail = prevNode;
        }
        if (prevNode != null) {
            prevNode.next = currentNode.next;
        } else {
            head = nextNode;
        }
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == null && object == null
                    || currentNode.value != null && currentNode.value.equals(object)) {
                remove(i);
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
}
