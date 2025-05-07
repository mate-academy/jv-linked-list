package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index, size);
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            Node<T> oldNode = findElenemt(index);
            Node<T> newNode = new Node<T>(oldNode.prev, value, oldNode);
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index, size - 1);
        return findElenemt(index).item;
    }

    @Override
    public T set(T value, int index) {
        T removed = remove(index);
        add(value, index);
        return removed;
    }

    @Override
    public T remove(int index) {
        validateIndex(index, size - 1);
        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<T> oldNode = findElenemt(index);
            unlink(oldNode);
            size--;
            return oldNode.item;
        }
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        int index = -1;
        Node<T> currNode = head;
        for (int i = 0; i < size; i++) {
            if (currNode.item == object
                    || (currNode.item != null && currNode.item.equals(object))) {
                index = i;
                break;
            }
            currNode = currNode.next;
        }
        if (index == -1) {
            return false;
        }
        if (index == 0) {
            removeFirst();
            return true;
        } else if (index == size - 1) {
            removeLast();
            return true;
        } else {
            unlink(currNode);
            size--;
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void validateIndex(int index, int limit) {
        if (index < 0 || index > limit) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node<T> findElenemt(int index) {
        Node<T> currNode;
        if (index < size / 2) {
            currNode = head;
            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }
        } else {
            currNode = tail;
            for (int i = size - 1; i > index; i--) {
                currNode = currNode.prev;
            }
        }
        return currNode;
    }

    private void addFirst(T value) {
        Node<T> oldNode = head;
        head = new Node<T>(null, value, oldNode);
        if (tail == null) {
            tail = head;
        } else {
            oldNode.prev = head;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> oldNode = tail;
        tail = new Node<T>(oldNode, value, null);
        if (head == null) {
            head = tail;
        } else {
            oldNode.next = tail;
        }
        size++;
    }

    private T removeFirst() {
        Node<T> oldNode = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        oldNode.next = null;
        size--;
        return oldNode.item;
    }

    private T removeLast() {
        Node<T> oldNode = tail;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        oldNode.prev = null;
        size--;
        return oldNode.item;
    }

    private void unlink(Node<T> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
