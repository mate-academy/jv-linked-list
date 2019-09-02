package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    int size;
    Node<T> head;
    Node<T> tail;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node(value, null, null);
            tail = head;
            size = 1;
        } else {
            Node newTail = new Node(value, tail, null);
            tail.next = newTail;
            tail = newTail;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            linkTail(value);
        } else {
            link(value, getNodeByIndex(index));
        }
    }

    private void linkTail(T value) {
        Node<T> prev = tail;
        Node<T> newNode = new Node<T>(value, prev, null);
        tail = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private void link(T value, Node<T> x) {
        Node<T> prev = x.prev;
        Node<T> newNode = new Node<T>(value, prev, x);
        x.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
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
        return getNodeByIndex(index).item;
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        Node pointer = getNodeByIndex(index);
        pointer.item = value;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node returnNode;
        if (index < (size >> 1)) {
            returnNode = head;
            for (int i = 0; i < index; i++) {
                returnNode = returnNode.next;
            }
            return returnNode;
        } else {
            returnNode = tail;
            for (int i = size - 1; i > index; i--) {
                returnNode = returnNode.prev;
            }
            return returnNode;
        }
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return (T) unlink(getNodeByIndex(index));
    }

    @Override
    public T remove(T t) {
        if (t == null) {
            for (Node<T> node = head; node != null; node = node.next) {
                if (node.item == null) {
                    return unlink(node);
                }
            }
        } else {
            for (Node<T> node = head; node != null; node = node.next) {
                if (node.item.equals(t)) {
                    return unlink(node);
                }
            }
        }
        return null;
    }

    private T unlink(Node<T> x) {
        final T returnData = x.item;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        return returnData;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {
        T item;
        Node next;
        Node prev;

        Node(T item, Node prev, Node next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
