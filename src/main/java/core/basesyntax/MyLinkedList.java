package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private final T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        }
        if (size >= 1) {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        if (index == size) {
            add(value);
        } else {
            if (index != 0) {
                Node<T> currentNode = getNodeByIndex(index);
                newNode.next = currentNode;
                newNode.prev = currentNode.prev;
                currentNode.prev.next = newNode;
                currentNode.prev = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            size++;
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> currentValue = getNodeByIndex(index);
        if (index == 0) {
            currentValue.next.prev = newNode;
            newNode.next = currentValue.next;
            head = newNode;
        } else if (index == size) {
            currentValue.prev.next = newNode;
            newNode.prev = currentValue.prev;
            tail = newNode;
        } else {
            newNode.prev = currentValue.prev;
            newNode.next = currentValue.next;
            currentValue.prev.next = newNode;
            currentValue.next.prev = newNode;
        }
        currentValue.prev = null;
        currentValue.next = null;
        return currentValue.item;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        return removeNodeByValue(object);
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        validateIndex(index);
        int count = 0;
        Node<T> currentNode = head;
        while (count++ != index) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private boolean removeNodeByValue(T value) {
        Node<T> currentNode = head;
        boolean result = false;
        int count = 0;
        while (count++ != size) {
            if (currentNode.item != null) {
                if (!currentNode.item.equals(value)) {
                    currentNode = currentNode.next;
                } else {
                    return unlink(currentNode);
                }
            } else if (value == null) {
                return unlink(currentNode);
            } else {
                currentNode = currentNode.next;
            }
        }
        return result;
    }

    private boolean unlink(Node<T> node) {
        if (node != null) {
            if (node.prev == null && node.next == null) {
                head = null;
                tail = null;
            } else if (node.prev == null) {
                node.next.prev = null;
                head = node.next;
            } else if (node.next == null) {
                node.prev.next = null;
                tail = node.prev;
            } else {
                Node<T> previous = node.prev;
                Node<T> next = node.next;
                previous.next = next;
                next.prev = previous;
                node.prev = null;
                node.next = null;
            }
            size--;
        }
        return true;
    }
}
