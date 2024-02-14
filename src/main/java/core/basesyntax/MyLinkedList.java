package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (size == 0) {
            addFirst(value);
        } else if (index == 0) {
            head = new Node(value, head, null);
            head.next.prev = head;
            size++;
        } else if (index == size) {
            addLast(value);
        } else {
            Node current = getNodeByIndex(index);
            Node newNode = new Node(value, current, current.prev);
            current.prev.next = newNode;
            current.prev = newNode;
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
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node current = getNodeByIndex(index);
        T oldElment = current.element;
        current.element = value;
        return oldElment;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node current = getNodeByIndex(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        for (int i = 0; i < size; i++) {
            if ((current.element == null && object == null)
                    || (current.element != null && current.element.equals(object))) {
                unlink(current);
                return true;
            }
            current = current.next;
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

    private void addFirst(T value) {
        head = tail = new Node(value, null, null);
        size++;
    }

    private void addLast(T value) {
        tail = new Node(value, null, tail);
        tail.prev.next = tail;
        size++;
    }

    public void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " does not exist");
        }
    }

    private Node getNodeByIndex(int index) {
        Node current;
        if (index == size) {
            throw new IndexOutOfBoundsException("Index " + index + " does not exist");
        }
        if (index < size / 2) {
            current = head;
            int i = 0;
            while (i != index) {
                current = current.next;
                i++;
            }
        } else {
            current = tail;
            int i = size - 1;
            while (i != index) {
                current = current.prev;
                i--;
            }
        }
        return current;
    }

    private T unlink(Node curent) {
        if (curent.prev == null) {
            head = curent.next;
            curent.next = null;
        } else if (curent.next == null) {
            tail = curent.prev;
            curent.prev.next = null;
            curent.prev = null;
        } else {
            curent.prev.next = curent.next;
            curent.next.prev = curent.prev;
        }
        size--;
        return curent.element;
    }

    private class Node {
        private T element;
        private Node next;
        private Node prev;

        public Node(T element, Node next, Node prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
