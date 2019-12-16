package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    private class Node {
        private T value;
        private Node next;
        private Node prev;

        private Node(Node next, T value, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(null, value, tail);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else {
            add(value);
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return node(index).value;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        node(index).value = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return delete(node(index));
    }

    @Override
    public T remove(T t) {
        Node temp;
        for (temp = head; temp != null; temp = temp.next) {
            if (Objects.equals(t, temp.value)) {
                delete(temp);
                return temp.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node node(int index) {
        Node temp;
        if (index <= size / 2) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp;
        } else {
            temp = tail;
            for (int i = size - 1; index < i; i--) {
                temp = temp.prev;
            }
            return temp;
        }
    }

    private T delete(Node target) {
        Node next = target.next;
        Node prev = target.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return target.value;
    }
}
