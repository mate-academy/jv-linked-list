package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String BOUND_EXCEPTION = "Index out of bound";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> addedNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = addedNode;
        } else {
            tail.next = addedNode;
        }
        tail = addedNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> addedNode = new Node<>(null, value, head);
            head.prev = addedNode;
            head = addedNode;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> addedNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = addedNode;
            currentNode.prev = addedNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> addedNode = getNode(index);
        T currentValue = addedNode.element;
        addedNode.element = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (object == current.element || object != null && object.equals(current.element)) {
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(BOUND_EXCEPTION);
        }
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> previousNode = node.prev;
        if (previousNode == null) {
            head = nextNode;
        } else {
            previousNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            tail = previousNode;
        } else {
            nextNode.prev = previousNode;
            node.next = null;
        }
        size--;
        return node.element;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current = null;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }
}
