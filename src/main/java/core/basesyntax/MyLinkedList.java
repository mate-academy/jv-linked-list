package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> addedNode = new Node<>(value, null, null);
        if (size == 0) {
            firstNode = addedNode;
        } else {
            lastNode.nextNode = addedNode;
            addedNode.previousNode = lastNode;
        }
        lastNode = addedNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> addedNode = new Node<>(value, null, null);
        if (index == 0) {
            addedNode.nextNode = firstNode;
            firstNode.previousNode = addedNode;
            firstNode = addedNode;
        } else {
            Node<T> nextNode = getNodeByIndex(index);
            addedNode.previousNode = nextNode.previousNode;
            addedNode.nextNode = nextNode;
            nextNode.previousNode = addedNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> replaceableNode = getNodeByIndex(index);
        T oldItem = replaceableNode.item;
        replaceableNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removableNode = getNodeByIndex(index);
        if (index == 0) {
            firstNode = firstNode.nextNode;
            if (firstNode != null) {
                firstNode.previousNode = null;
            } else {
                lastNode = null;
            }
        } else if (index == size - 1) {
            lastNode = lastNode.previousNode;
            lastNode.nextNode = null;
        } else {
            removableNode.nextNode.previousNode = removableNode.previousNode;
            removableNode.previousNode.nextNode = removableNode.nextNode;
        }
        size--;
        return removableNode.item;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            Node<T> node = getNodeByIndex(i);
            if ((node.item != null && node.item.equals(t)) || node.item == t) {
                remove(i);
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> result = firstNode;
        if (size / 2 > index) {
            for (int i = 0; i < index; i++) {
                result = result.nextNode;
            }
        } else {
            result = lastNode;
            for (int i = size - 1; i > index; i--) {
                result = result.previousNode;
            }
        }
        return result;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> previousNode;
        private Node<T> nextNode;

        public Node(T item, Node<T> previousNode, Node<T> nextNode) {
            this.item = item;
            this.previousNode = previousNode;
            this.nextNode = nextNode;
        }
    }
}
