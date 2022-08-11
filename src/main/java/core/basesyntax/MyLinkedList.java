package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        final Node<T> lastNode = last;
        final Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public T add(T value, int index) {
        if (index == size) {
            add(value);
            return value;
        }
        insertNodeByIndex(value, index);
        return value;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeByIndex = getNode(index);
        T oldValue = nodeByIndex.item;
        nodeByIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (current.item == object || current.item != null && current.item.equals(object)) {
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

    private void insertNodeByIndex(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        Node<T> previousNode = currentNode.prev;
        Node<T> newNode = new Node<>(previousNode, value, currentNode);
        currentNode.prev = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> deletedNode) {
        Node<T> next = deletedNode.next;
        Node<T> previous = deletedNode.prev;

        if (next == null && previous == null) {
            last = null;
            first = null;
        } else if (previous == null) {
            first = next;
            next.prev = null;
        } else if (next == null) {
            last = previous;
            previous.next = null;
        } else {
            previous.next = next;
            next.prev = previous;
        }
        size--;
        return deletedNode.item;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Exception, check your index " + index);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> nodeByIndex;
        if (index < (size >> 1)) {
            nodeByIndex = first;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
        } else {
            nodeByIndex = last;
            for (int i = size - 1; i > index; i--) {
                nodeByIndex = nodeByIndex.prev;
            }
        }
        return nodeByIndex;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
