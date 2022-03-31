package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> headNode;
    private Node<T> tailNode;

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        private Node(Node<T> previous, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("wrong index"
                    + "\n"
                    + "Mast be from 0 to " + (size() - 1));
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("wrong index for add"
                    + "\n"
                    + "Mast be from 0 to " + size());
        }
    }

    private Node<T> getNodeFromIndex(int index) {
        checkIndex(index);
        Node<T> searchNode = headNode;

        if (index < size / 2) {
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
        Node<T> newNode = new Node<>(null, value, null);
        headNode = newNode;
        tailNode = newNode;
        size++;
    }

    private void addFirst(T value) {
        if (size == 0) {
            addToEmpty(value);
        } else {
            Node<T> newNode = new Node<>(null, value, headNode);
            headNode.previous = newNode;
            headNode = newNode;
            size++;
        }
    }

    @Override

    public void add(T value) {
        if (size == 0) {
            addToEmpty(value);
        } else {
            Node<T> newNode = new Node<>(tailNode, value, null);
            tailNode.next = newNode;
            tailNode = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size() || size() == 0) {
            add(value);
        } else {
            Node<T> prevNode = getNodeFromIndex(index - 1);
            Node<T> nextNode = prevNode.next;
            Node<T> newNode = new Node<>(prevNode, value, nextNode);
            prevNode.next = newNode;
            newNode.previous = prevNode;
            newNode.next = nextNode;
            nextNode.previous = newNode;
            size++;
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
        return getNodeFromIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> byIndex = getNodeFromIndex(index);
        T result = byIndex.value;
        byIndex.value = value;
        return result;
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
        }
        if (next == null) {
            tailNode = prev;
        } else {
            next.previous = prev;
        }
        T element = nodeToDelete.value;
        nodeToDelete = null;
        size--;
        return element;
    }

    @Override
    public boolean remove(T t) {
        for (int index = 0; index < size(); index++) {
            Node<T> searchedNode = getNodeFromIndex(index);
            if (searchedNode.value != null && searchedNode.value.equals(t)
                    || searchedNode.value == t) {
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
}

