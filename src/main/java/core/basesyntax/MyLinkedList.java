package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        public Node(T item) {
            this.item = item;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            tail = new Node<>(value);
            head = tail;
        } else {
            Node<T> addElement = new Node<>(tail, value, null);
            tail.next = addElement;
            tail = addElement;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index != size) {
            indexCheck(index);
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> previousNode = getNodeByTheIndex(index - 1);
            newNode.next = previousNode.next;
            previousNode.next = newNode;
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
        indexCheck(index);
        return getNodeByTheIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> neededNode = getNodeByTheIndex(index);
        T tempItem = neededNode.item;
        neededNode.item = value;
        return tempItem;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> removed = getNodeByTheIndex(index);
        T result = removed.item;
        unlink(removed);
        return result;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if (object == temp.item || temp.item != null && temp.item.equals(object)) {
                unlink(temp);
                return true;
            }
            temp = temp.next;
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

    private void indexCheck(int listIndex) {
        if (listIndex < 0 || listIndex >= size) {
            throw new IndexOutOfBoundsException("Entered index " + listIndex + " is incorrect");
        }
    }

    private Node<T> getNodeByTheIndex(int index) {
        indexCheck(index);
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    private void unlink(Node<T> removed) {
        if (removed.next == null) {
            tail = removed.prev;
        } else {
            removed.next.prev = removed.prev;
        }
        if (removed.prev == null) {
            head = removed.next;
        } else {
            removed.prev.next = removed.next;
        }
        size--;
    }
}
