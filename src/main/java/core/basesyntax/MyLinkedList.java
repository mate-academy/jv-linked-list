package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        checkIfHeadIsNull(newNode);
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index >= 0 && size == 0 || index == size) {
            add(value);
            return;
        }
        if (index >= 0 && index < size) {
            Node<T> nextNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nextNode.prev, value, nextNode);
            checkIfHeadIsNull(newNode);
            nextNode.prev = newNode;
            size++;
            return;
        }
        throw new IndexOutOfBoundsException("Index " + index + " is invalid!");
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index >= 0 && index < size) {
            Node<T> requiredNode = getNodeByIndex(index);
            unlink(requiredNode);
            return requiredNode.value;
        }
        throw new IndexOutOfBoundsException("Index " + index + " is invalid!");
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object
                    ||
                    currentNode.value != null && currentNode.value.equals(object)) {
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        int counter;
        if (index < (size / 2) && index >= 0) {
            counter = 0;
            currentNode = head;
            while (counter != index) {
                currentNode = currentNode.next;
                counter++;
            }
            return currentNode;
        } else if (index >= (size / 2) && index < size) {
            counter = size - 1;
            currentNode = tail;
            while (counter != index) {
                currentNode = currentNode.prev;
                counter--;
            }
            return currentNode;
        }
        throw new IndexOutOfBoundsException("Index " + index + " is invalid!");
    }

    private void checkIfHeadIsNull(Node<T> node) {
        if (node.prev == null) {
            head = node;
        } else {
            node.prev.next = node;
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
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
