package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> headNode;
    private Node<T> tailNode;

    private Node<T> getNodeFromIndex(int index) {
        checkIndex(index);
        Node<T> searchNode = headNode;

        if (index < (size >> 1)) {
            for (int i = 0; i < index; i++) {
                searchNode = searchNode.next;
            }
        } else {
            searchNode = tailNode;
            for (int i = size() - 1; i > index; i--) {
                searchNode = searchNode.previous;
            }
        }
        return searchNode;

    }

    private void addToEmpty(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        headNode = newNode;
        tailNode = newNode;
        size++;
    }

    private void addFirst(T value) {
        if (size == 0) {
            addToEmpty(value);
        } else {
            Node<T> newNode = new Node<>(value, null, headNode);
            headNode.previous = newNode;
            headNode = newNode;
            size++;
        }
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            addToEmpty(value);
        } else {
            Node<T> newNode = new Node<>(value, tailNode, null);
            tailNode.next = newNode;
            tailNode = newNode;
            size++;
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size() || size() == 0) {
            add(value);
        } else {
            Node<T> afterNode = getNodeFromIndex(index);
            Node<T> beforeNode = afterNode.previous;
            Node<T> newNode = new Node<>(value, beforeNode, afterNode);
            afterNode.previous = newNode;
            if (beforeNode == null) {
                headNode = newNode;
            } else {
                beforeNode.next = newNode;
            }
            size++;
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("wrong index"
                    + "\n" + "Mast be from 0 to "
                    + (size() - 1));
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("wrong index"
                    + "\n" + "Mast be from 0 to "
                    + (size() - 1));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNodeFromIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = getNodeFromIndex(index);
        T returnValue = oldNode.value;
        oldNode.value = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToDelete = getNodeFromIndex(index);
        Node<T> prev = nodeToDelete.previous;
        Node<T> next = nodeToDelete.next;

        if (prev == null) {
            headNode = next;
        } else {
            prev.next = next;
            nodeToDelete.previous = null;
        }
        if (next == null) {
            tailNode = prev;
        } else {
            next.previous = prev;
            nodeToDelete.next = null;
        }
        T element = nodeToDelete.value;
        size--;
        return element;
    }

    @Override
    public boolean remove(T t) {
        for (int index = 0; index < size(); index++) {
            if (getNodeFromIndex(index).value == t
                    || getNodeFromIndex(index).value != null
                    && getNodeFromIndex(index).value.equals(t)) {
                remove(index);
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

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        private Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}
