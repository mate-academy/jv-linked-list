package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(null, value, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<T>(node.prev, value, node);
        if (node.prev == null) {
            first = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeByIndex = getNode(index);
        T removedElement = nodeByIndex.item;
        nodeByIndex.item = value;
        return removedElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedNode = getNode(index);
        T removedElement = removedNode.item;
        unlink(removedNode);
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        while (node != null) {
            if (object == node.item || object != null && object.equals(node.item)) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is incorrect: " + index);
        }
    }

    private void unlink(Node<T> removedNode) {
        if (removedNode == first) {
            first = removedNode.next;
        } else if (removedNode == last) {
            last = removedNode.prev;
        } else {
            removedNode.next.prev = removedNode.prev;
            removedNode.prev.next = removedNode.next;
        }
        size--;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> nodeByIndex;
        if (index < size / 2) {
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
}
