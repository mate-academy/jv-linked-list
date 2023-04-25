package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

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

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (head == null) {
            addFirst(value);
        } else {
            addLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkRangeForAdd(index);
        Node<T> newNode;
        if (head == null) {
            addFirst(value);
        } else if (index == 0) {
            newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            addLast(value);
        } else {
            Node<T> searchNode = getNodeByIndex(index);
            newNode = new Node<>(searchNode.prev, value, searchNode);
            searchNode.prev.next = newNode;
            searchNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkRange(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkRange(index);
        Node<T> setNode = getNodeByIndex(index);
        T oldItem = setNode.item;
        setNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkRange(index);
        Node<T> nodeToDelete = getNodeByIndex(index);
        T value = nodeToDelete.item;
        unlink(nodeToDelete);
        return value;
    }

    @Override
    public boolean remove(Object object) {
        if (object == null) {
            for (Node<T> nodeToRemove = head; nodeToRemove != null;
                    nodeToRemove = nodeToRemove.next) {
                if (nodeToRemove.item == null) {
                    unlink(nodeToRemove);
                    return true;
                }
            }
        } else {
            for (Node<T> nodeToRemove = head; nodeToRemove != null;
                    nodeToRemove = nodeToRemove.next) {
                if (object.equals(nodeToRemove.item)) {
                    unlink(nodeToRemove);
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

    private void checkRange(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index);
        }
    }

    private void checkRangeForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> searchNode;
        int currentIndex;
        if (index < size / 2) {
            searchNode = head;
            currentIndex = 0;
            while (currentIndex < index) {
                searchNode = searchNode.next;
                currentIndex++;
            }
        } else {
            searchNode = tail;
            currentIndex = size - 1;
            while (currentIndex > index) {
                searchNode = searchNode.prev;
                currentIndex--;
            }
        }
        return searchNode;
    }

    private T unlink(Node<T> unlinkNode) {
        final T element = unlinkNode.item;
        Node<T> next = unlinkNode.next;
        Node<T> prev = unlinkNode.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            unlinkNode.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            unlinkNode.next = null;
        }
        unlinkNode.item = null;
        size--;
        return element;
    }

    private Node<T> addFirst(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        head = newNode;
        tail = newNode;
        return newNode;
    }

    private Node<T> addLast(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        return newNode;
    }
}
