package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>((size == 0) ? null : tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexAddCheck(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nextNode = getNodeByTheIndex(index);
        Node<T> prevNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        if (prevNode != null && prevNode.next != null) {
            prevNode.next = newNode;
        } else if (prevNode == null || prevNode.prev == null) {
            head = newNode;
        }
        nextNode.prev = newNode;
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

    private void indexAddCheck(int listIndex) {
        if (listIndex < 0 || listIndex > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds " + listIndex);
        }
    }

    private void indexCheck(int listIndex) {
        if (listIndex < 0 || listIndex >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds " + listIndex);
        }
    }

    private Node<T> getNodeByTheIndex(int index) {
        indexCheck(index);
        Node<T> temp;
        if (index <= size / 2) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
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

    class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
