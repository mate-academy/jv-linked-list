package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = tail;
        Node<T> newNode = new Node<>(node, value, null);
        tail = newNode;

        if (node == null) {
            head = newNode;
        } else {
            node.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index);
            Node<T> prevNodeByIndex = nodeByIndex.previous;
            Node<T> newNode = new Node<>(prevNodeByIndex, value, nodeByIndex);
            nodeByIndex.previous = newNode;
            if (prevNodeByIndex == null) {
                head = newNode;
            } else {
                prevNodeByIndex.next = newNode;
            }
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
        checkPositionIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> nodeByIndex = getNodeByIndex(index);
        T oldValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == object
                    || (current.value != null && current.value.equals(object))) {
                unlink(current);
                return true;
            }
            current = current.next;
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

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkPositionIndex(index);

        if (index < (size / 2)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
            return current;
        }
    }

    private T unlink(Node<T> currentNode) {
        final T element = currentNode.value;
        Node<T> next = currentNode.next;
        Node<T> prev = currentNode.previous;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            currentNode.previous = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.previous = prev;
            currentNode.next = null;
        }

        currentNode.value = null;
        size--;
        return element;
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> previous;
        private T value;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.next = next;
            this.previous = previous;
            this.value = value;
        }
    }
}
