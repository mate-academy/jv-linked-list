package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    public MyLinkedList(T value) {
        addFirst(value);
    }

    @Override
    public void add(T value) {
        if (head == null) {
            addFirst(value);
            return;
        }
        tail.next = new Node<>(tail, value, null);
        tail = tail.next;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkBound(index);
        if (size == 0) {
            addFirst(value);
            return;
        }
        if (index == 0) {
            head = new Node<>(null, value, head);
            head.next.prev = head;
            size++;
            return;
        }
        if (size == index) {
            addLast(value);
            return;
        }
        Node<T> current = findByIndex(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        current.prev.next = newNode;
        current.prev = newNode;
        size++;

    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = findByIndex(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = findByIndex(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = findByIndex(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((current.item == null && object == null)
                    || (current.item != null && current.item.equals(object))) {
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
        return head == null;
    }

    private T unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
        return node.item;
    }

    private void addFirst(T value) {
        head = tail = new Node<>(null, value, null);
        size++;
    }

    private void addLast(T value) {
        tail = new Node<>(tail, value, null);
        tail.prev.next = tail;
        size++;
    }

    private Node<T> findByIndex(int index) {
        Node<T> current;
        checkBound(index);
        if (index == size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (index > size() / 2) {
            current = searchTailToIndex(index);
        } else {
            current = searchHeadToIndex(index);
        }
        return current;
    }

    private Node<T> searchHeadToIndex(int index) {
        Node<T> current = head;
        int i = 0;
        while (i != index) {
            current = current.next;
            i++;
        }
        return current;
    }

    private Node<T> searchTailToIndex(int index) {
        Node<T> current = tail;
        int i = size - 1;
        while (current != null && i != index) {
            current = current.prev;
            i--;
        }
        return current;
    }

    private void checkBound(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Bad index: " + index + " for size: " + size);
        }
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
