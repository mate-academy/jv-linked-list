package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head = new Node<>(null, null, null);
    private Node<T> tail = head;
    private Node<T> previous = tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(previous, value, head);
        previous.next = newNode;
        head.prev = newNode;
        previous = newNode;
        head = newNode.next;
        if (size == 0) {
            tail = previous;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, tail);
            newNode.next = tail;
            tail.prev = newNode;
            tail = newNode;
            size++;
        } else {
            checkIndex(index);
            Node<T> myNode = tail;
            for (int i = 0; i < index; i++) {
                if (myNode.next != null) {
                    myNode = myNode.next;
                }
            }
            Node<T> prevNode = myNode.prev;
            Node<T> nextNode = myNode;
            Node<T> newNode = new Node<>(prevNode, value, nextNode);
            nextNode.prev = newNode;
            prevNode.next = newNode;
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
        checkIndex(index);
        Node<T> newNode = tail;
        for (int i = 0; i < index; i++) {
            if (newNode != null && newNode.next != null) {
                newNode = newNode.next;
            }
        }
        return newNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nextNode = tail.next;
        for (int i = 0; i < index; i++) {
            if (nextNode.next != null) {
                nextNode = nextNode.next;
            }
        }
        Node<T> prevNode = nextNode.prev.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        T result = nextNode.prev.item;
        if (index == 0) {
            result = tail.item;
            tail = newNode;
        }
        prevNode.next = newNode;
        nextNode.prev = newNode;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> newNode = tail;
        for (int i = 0; i < index; i++) {
            if (newNode != null) {
                newNode = newNode.next;
            }
        }
        return unlink(newNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = tail;
        for (int i = 0; i < size; i++) {
            if (newNode.item == object || newNode.item != null && newNode.item.equals(object)) {
                unlink(newNode);
                return true;
            } else if (newNode.next != null) {
                newNode = newNode.next;
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Wrong index, " + size);
        }
    }

    private T unlink(Node<T> newNode) {
        T result = newNode.item;
        if (newNode.prev == head) {
            tail = newNode.next;
            tail.next = newNode.next.next;
            newNode.next.prev = tail;
        } else if (newNode.next == tail) {
            head = newNode.prev;
            head.prev = newNode.prev.prev;
            newNode.prev.next = head;
        } else {
            newNode.prev.next = newNode.next;
            newNode.next.prev = newNode.prev;
        }
        size--;
        return result;
    }

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
}
