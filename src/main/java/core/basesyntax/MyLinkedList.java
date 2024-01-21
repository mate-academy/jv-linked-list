package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private T data;
        private Node<T> next;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            addFirstNode(newNode);
        } else {
            addAtIndex(index, newNode);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndexOutOfBounds(index);
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        checkIndexOutOfBounds(index);
        Node<T> current = getNode(index);
        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexOutOfBounds(index);
        Node<T> nodeToRemove = getNode(index);
        unlink(nodeToRemove);
        size--;
        return nodeToRemove.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (current.data == null) {
                if (object == null) {
                    unlink(current);
                    size--;
                    return true;
                }
            } else if (current.data.equals(object)) {
                unlink(current);
                size--;
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

    private void addFirstNode(Node<T> newNode) {
        newNode.next = first;
        first.prev = newNode;
        first = newNode;
    }

    private void addAtIndex(int index, Node<T> newNode) {
        Node<T> current = getNode(index);
        Node<T> prevNode = current.prev;

        prevNode.next = newNode;
        newNode.prev = prevNode;
        newNode.next = current;
        current.prev = newNode;
    }

    private Node<T> getNode(int index) {
        if (index < size / 2) {
            return getNodeFromStart(index);
        } else {
            return getNodeFromEnd(index);
        }
    }

    private Node<T> getNodeFromStart(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> getNodeFromEnd(int index) {
        Node<T> current = last;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;

        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            first = nextNode;
        }

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            last = prevNode;
        }
    }

    private void checkIndexOutOfBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }
}
