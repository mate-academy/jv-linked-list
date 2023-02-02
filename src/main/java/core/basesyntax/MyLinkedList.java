package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
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
        Node<T> newNode = findByIndex(index);
        if (newNode == null || newNode.equals(tail)) {
            this.add(value);
        }else if (newNode.equals(head)){
            newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        }
        else if (newNode.equals(head.next)) {
            newNode = new Node<>(head, value, head.next);
            head.next = newNode;
            head.next.prev = newNode;
            size++;
        } else {
            newNode = new Node<>(newNode.prev, value, newNode);
            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
            size++;
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> nextNode;
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for the List size " + size);
        } else if (index == 0) {
            nextNode = head;
        } else if (size / 2 > index) {
            nextNode = head;
            while (index > 0) {
                nextNode = nextNode.next;
                index--;
            }
        } else {
            nextNode = tail;
            while (size - 1 > index) {
                nextNode = nextNode.prev;
                index++;
            }
        }
        return nextNode;
    }


    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            this.add(value);
        }
    }

    @Override
    public T get(int index) {
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }
}


