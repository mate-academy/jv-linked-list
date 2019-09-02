package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

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

    private int size;
    private Node<T> headNode;
    private Node<T> tailNode;

    private Node<T> getNodeFromIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("wrong index" + "\n" + "Mast be from 0 to " + (size() - 1));
        }
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

    public void add(T value) {
        if (size == 0) {
            addToEmpty(value);
        } else {
            Node<T> newNode = new Node<>(value, tailNode, null);
            tailNode.next = newNode;
            tailNode = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("wrong index" + "\n" + "Mast be from 0 to " + (size() - 1));
        }
        if (size() == 0) {
            addToEmpty(value);
        } else if (index == 0) {
            addFirst(value);
        } else if (index == size()) {
            add(value);
        } else {
            Node<T> prevNode = getNodeFromIndex(index);
            Node<T> nextNode = getNodeFromIndex(index);

            Node<T> newNode = new Node<>(value, prevNode, nextNode);
            prevNode.next = nextNode;
            newNode.previous = newNode;
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
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("wrong index" + "\n" + "Mast be from 0 to " + (size() - 1));
        }
        getNodeFromIndex(index).value = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("wrong index" + "\n" + "Mast be from 0 to " + (size() - 1));
        }
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
    public T remove(T t) {
        for (int index = 0; index < size(); index++) {
            if (getNodeFromIndex(index).value.equals(t)) {
                T searchedItem = getNodeFromIndex(index).value;
                remove(index);
                return searchedItem;
            }
        }
        return null;
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
