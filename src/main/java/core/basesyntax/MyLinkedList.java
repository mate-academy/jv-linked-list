package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirst(value);
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdding(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size() || size() == 0) {
            add(value);
        } else {
            Node<T> prevNode = getByIndex(index - 1);
            Node<T> nextNode = prevNode.next;
            Node<T> newNode = new Node<>(prevNode, value, nextNode);
            nextNode.previous = newNode;
            newNode.previous = prevNode;
            prevNode.next = newNode;
            newNode.next = nextNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T obj : list) {
            add(obj);
        }
    }

    @Override
    public T get(int index) {
        return getByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> byIndex = getByIndex(index);
        T result = byIndex.value;
        byIndex.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToDelete = getByIndex(index);
        Node<T> prev = nodeToDelete.previous;
        Node<T> next = nodeToDelete.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
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
            Node<T> searchedNode = getByIndex(index);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("wrong index"
                    + "\n"
                    + "Mast be from 0 to " + (size() - 1));
        }
    }

    private void checkIndexForAdding(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("wrong index for add"
                    + "\n"
                    + "Mast be from 0 to " + size());
        }
    }

    private Node<T> getByIndex(int index) {
        checkIndex(index);
        Node<T> searchNode = head;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                searchNode = searchNode.next;
            }
        } else {
            searchNode = tail;
            for (int i = size() - 1; i > index; i--) {
                searchNode = searchNode.previous;
            }
        }
        return searchNode;
    }

    private void addFirst(T value) {
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            newNode = new Node<>(null, value, head);
            head.previous = newNode;
            head = newNode;
        }
        size++;
    }

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
}
