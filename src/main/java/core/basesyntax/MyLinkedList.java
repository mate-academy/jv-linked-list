package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node prev, T item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            linkFirst(new Node<>(null, value, null));
            size++;
            return;
        } else if (index == size - 1) {
            linkLast(new Node<>(null, value, null));
            size++;
            return;
        }
        if (index >= (int)((double)size / 2)) {
            Node current = tail;
            for (int i = size - 1; i >= index; i--) {
                if (i == index) {
                    Node newNode = new Node(current.prev, value, current);
                    current.prev.next = newNode;
                    current.prev = newNode;
                    size++;
                    return;
                }
                current = current.prev;
            }
        } else {
            Node current = head;
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    Node newNode = new Node(current.prev, value, current);
                    if (current.prev != null) {
                        current.prev.next = newNode;
                    }
                    current.prev = newNode;
                    size++;
                    return;
                }
                current = current.next;
            }
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
        return (T) findLink(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node current = findLink(index);
        T temp = (T) current.item;
        findLink(index).item = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        Node current = findLink(index);
        unlink(current);
        size--;
        return (T) current.item;
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        while (current != null) {
            if (Objects.equals(object, current.item)) {
                unlink(current);
                size--;
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

    private void linkFirst(Node current) {
        current.next = head;
        current.prev = null;
        head = current;
    }

    private void linkLast(Node current) {
        current.next = tail;
        current.prev = tail.prev;
        tail.prev = current;
    }

    private void unlink(Node current) {
        if (size == 1) {
            head = null;
            tail = null;
        }
        if (current.prev == null) {
            unlinkFirst(current);
        } else if (current.next == null) {
            unlinkLast(current);
        } else {
            unlinkCurrent(current);
        }
    }

    private void unlinkCurrent(Node current) {
        current.prev.next = current.next;
        current.next.prev = current.prev;
    }

    private void unlinkFirst(Node current) {
        if (current.next != null) {
            head = current.next;
            current.next.prev = null;
        } else {
            head = null;
        }
    }

    private void unlinkLast(Node current) {
        tail = current.prev;
        current.prev.next = null;
    }

    private void checkIndex(int index) {
        if (index > size) {
            throw new ArrayIndexOutOfBoundsException("Out of bounds exception with index: "
                    + index);
        }
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private Node findLink(int index) {
        checkIndex(index);
        if (index > size / 2) {
            Node current = tail;
            for (int i = size - 1; i >= index; i--) {
                if (i == index) {
                    return current;
                }
                current = current.prev;
            }

        } else {
            Node current = head;
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    return current;
                }
                current = current.next;
            }
        }
        throw new ArrayIndexOutOfBoundsException("Invalid index: " + index);
    }
}
