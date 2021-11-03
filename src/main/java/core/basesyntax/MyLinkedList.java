package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev,T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private Node<T> headNode;
    private Node<T> tailNode;
    private int size;
    
    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tailNode, value, null);
        if (headNode == null) {
            headNode = node;
        }
        if (tailNode != null) {
            tailNode.next = node;
        }
        tailNode = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeFromIndex = getNodeFromIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = nodeFromIndex;
            nodeFromIndex.prev = newNode;
            headNode = newNode;
        } else {
            newNode.prev = nodeFromIndex.prev;
            newNode.next = nodeFromIndex;
            nodeFromIndex.prev.next = newNode;
            nodeFromIndex.prev = newNode;
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
        return getNodeFromIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeFromIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeFromIndex(index);
        removeNode(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = headNode;
        while (node != null) {
            if (node.item == object || node.item != null && node.item.equals(object)) {
                removeNode(node);
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

    private void chekForTrueIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNodeFromIndex(int index) {
        chekForTrueIndex(index);
        Node<T> node = headNode;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                break;
            } else {
                node = node.next;
            }
        }
        return node;
    }

    private void removeNode(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;
        if (previous == null) {
            headNode = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            tailNode = previous;
        } else {
            next.prev = previous;
        }
        unlink(node);
        size--;
    }

    private void unlink(Node<T> node) {
        node = null;
    }
}
