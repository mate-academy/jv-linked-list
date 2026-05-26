package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            Node newNode = new Node(null, value, null);
            head = newNode;
            tail = newNode;
            size++;
            return;
        }

        tail.next = new Node(tail, value, null);
        tail = tail.next;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (head == null && index == 0) {
            add(value);
            return;
        }

        if (index == size) {
            tail.next = new Node(tail, value, null);//????
            tail = tail.next;
            size++;
            return;
        }

        if (index == 0) {
            head.prev = new Node(null, value, head);
            head = head.prev;
            size++;
            return;
        }

        Node current = findNodeByIndex(index);

        Node newValue = new Node(current.prev, value, current);
        current.prev.next = newValue;
        current.prev = newValue;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list.isEmpty()) {
            return;
        }
        if (head == null) {
            head = new Node(null, list.get(0), null);
            tail = head;
            size++;
            for (int i = 1; i < list.size(); i++) {
                add(list.get(i));
            }
        } else {
            for (T t : list) {
                add(t);
            }
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || head == null || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node current = findNodeByIndex(index);

        T oldValue = current.value;
        current.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {

        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node current = findNodeByIndex(index);
        if (current == null) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return current.value;
        }

        if (index == 0) {
            head = head.next;
            head.prev = null;
            size--;
            return current.value;
        }

        if (index == size - 1) {
            tail = tail.prev;
            tail.next = null;
            size--;
            return current.value;
        }

        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {

        Node current = head;
        while (current != null) {
            if (object == current.value || (current.value != null
                    && current.value.equals(object))) {
                break;
            }
            current = current.next;
        }
        if (current == null) {
            return false;
        }

        if (current.next == null && current.prev == null) {
            head = null;
            tail = null;
            size--;
            return true;
        }

        if (current.next == null) {
            tail = tail.prev;
            tail.next = null;
            size--;
            return true;
        }

        if (current.prev == null) {
            head = head.next;
            head.prev = null;
            size--;
            return true;
        }

        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return true;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (head == null);
    }

    private Node findNodeByIndex(int index) {
        if (head == null || index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node current;
        if (index > size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private class Node {

        private T value;
        private Node next;
        private Node prev;

        public Node(Node prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
