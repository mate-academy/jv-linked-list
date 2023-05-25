package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Index of the bounds. Check your input index";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(T value) {
            this.prev = null;
            this.value = value;
            this.next = null;
        }

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> currentNode = getNodeByIndex(index - 1);
            newNode.next = currentNode.next;
            currentNode.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        indexOnRangeEqualsSize(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    @Override
    public void addAll(List<T> list) {
        Node<T> currentNode = tail;
        for (T e : list) {
            Node<T> newNode = new Node<>(currentNode, e, null);
            if (currentNode == null) {
                head = newNode;
            } else {
                currentNode.next = newNode;
            }
            currentNode = newNode;
            size++;
        }
        tail = currentNode;
    }

    @Override
    public T get(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        if (currentNode != null) {
            return currentNode.value;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexOnRangeEqualsSize(index);
        T removedData;
        if (index == 0) {
            removedData = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> prevNode = getNodeByIndex(index - 1);
            removedData = prevNode.next.value;
            prevNode.next = prevNode.next.next;
            if (index == size - 1) {
                tail = prevNode;
            }
        }
        size--;
        return removedData;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object || (currentNode.value != null
                    && currentNode.value.equals(object))) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private void unlink(Node<T> currentNode) {
        Node<T> prevNode = currentNode.prev;
        Node<T> nextNode = currentNode.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            currentNode.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            currentNode.next = null;
        }
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void indexOnRangeEqualsSize(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }
}
