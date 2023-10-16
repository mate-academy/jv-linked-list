package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> lastNode = head;
        Node<T> newNode = new Node<>(lastNode, value, null);
        head = newNode;
        if (lastNode == null) {
            tail = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            addInside(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> i = tail; i != null; i = i.next) {
                if (object == i.item || (i.item != null && i.item.equals(object))) {
                    unlink(i);
                    return true;
                }
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

    private Node<T> getNodeByIndex(int index) {
        checkElementIndex(index);
        int currentIndex = 0;
        Node<T> currentNode = tail;
        while (currentNode != null) {
            if (currentIndex == index) {
                return currentNode;
            }
            currentIndex++;
            currentNode = currentNode.next;
        }
        return null;
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index should be in range 0 to " + size);
        }
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index should be in range 0 to " + (size - 1));
        }
    }

    private void addInside(T value, Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> newNode = new Node<>(prev, value, node);
        node.prev = newNode;
        if (prev == null) {
            tail = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        Node<T> nodeNext = node.next;
        Node<T> nodePrev = node.prev;

        if (nodePrev == null) {
            tail = nodeNext;
        } else {
            nodePrev.next = nodeNext;
            node.prev = null;
        }
        if (nodeNext == null) {
            head = nodeNext;
        } else {
            nodeNext.prev = nodePrev;
            node.next = null;
        }
        size--;
        return node.item;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
