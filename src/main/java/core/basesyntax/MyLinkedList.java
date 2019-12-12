package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
    }

    static class Node<T> {
        T value;
        Node next;
        Node prev;

        Node(T d) {
            prev = null;
            value = d;
            next = null;
        }

        Node(T value, Node prev, Node next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    public void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("" + index + "out of bounds");
        }
    }

    public Node findByIndex(int index) {
        checkIndex(index);
        Node result = head;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                return result;
            }
            result = result.next;
        }
        return null;
    }

    public int findByValue(T value) {
        Node result = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(value, result.value)) {
                return i;
            }
            result = result.next;
        }
        return size;
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Node shiftedElement = findByIndex(index);
            Node newNode = new Node(value, shiftedElement.prev, shiftedElement);
            shiftedElement.prev = newNode;
            if (index == 0) {
                head = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return (T) findByIndex(index).value;
    }

    @Override
    public void set(T value, int index) {
        findByIndex(index).value = value;
    }

    public T removeTail(Node last, int index) {
        findByIndex(index - 1).next = null;
        tail = findByIndex(index - 1);
        size--;
        return (T) last.value;
    }

    public T removeHead(Node firs) {
        findByIndex(1).prev = null;
        head = findByIndex(1);
        size--;
        return (T) firs.value;
    }

    public T removeFromInside(Node element, int index) {
        Node prevNode = findByIndex(index - 1);
        Node nextNode = findByIndex(index + 1);
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        size--;
        return (T) element.value;
    }

    @Override
    public T remove(int index) {
        Node removedNode = findByIndex(index);
        if (size == 1) {
            head = tail = null;
            size--;
            return (T) removedNode.value;
        }
        return removedNode == tail
                ? removeTail(removedNode, index) : removedNode == head
                ? removeHead(removedNode) : removeFromInside(removedNode, index);
    }

    @Override
    public T remove(T t) {
        int index = findByValue(t);
        if (index == size) {
            return null;
        }
        return remove(index);
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

