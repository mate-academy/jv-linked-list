package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    private class Node {
        T value;
        Node next;
        Node prev;

        public Node(Node next, T value, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(null, value, tail);
        if (tail == null) {
            this.head = newNode;
        } else {
            tail.next = newNode;
        }
        this.tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0 || index == size) {
            add(value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }

    }

    @Override
    public T get(int index) {
        if (index < 0 ^ index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return node(index).value;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 ^ index >= size) {
            throw new IndexOutOfBoundsException();
        }
        this.node(index).value = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 ^ index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return delete(node(index));
    }

    @Override
    public T remove(T t) {
        Node temp;
        if (t == null) {
            for (temp = head; temp != null; temp = temp.next) {
                if (temp.value == null) {
                    delete(temp);
                    return temp.value;
                }
            }
        } else {
            for (temp = head; temp != null; temp = temp.next) {
                if (t.equals(temp.value)) {
                    delete(temp);
                    return temp.value;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    Node node(int index) {
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

    T delete(Node target) {
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
