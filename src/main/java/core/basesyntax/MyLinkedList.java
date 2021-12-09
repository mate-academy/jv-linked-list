package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> node = new Node<T>(last, value, null);
        if (last != null) {
            last.next = node;
        }
        last = node;
        if (first == null) {
            first = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index does not exist");
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        } else {
            Node<T> nodeByIndex = searchNode(index);
            newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            nodeByIndex.prev.next = newNode;
            nodeByIndex.prev = newNode;
        }
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
        checkElementIndex(index);
        return searchNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> nodeByIndex = searchNode(index);
        T deletedItem = nodeByIndex.item;
        nodeByIndex.item = value;
        return deletedItem;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> targetNode = searchNode(index);
        T deletedItem = targetNode.item;
        unlink(targetNode);
        return deletedItem;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (node.item == object || node.item != null && node.item.equals(object)) {
                unlink(node);
                return true;
            }
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

    private void unlink(Node<T> value) {
        Node<T> next = value.next;
        Node<T> prev = value.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            value.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            value.next = null;
        }
        value.item = null;
        size--;
    }

    private Node<T> searchNode(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("There is no such index");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
