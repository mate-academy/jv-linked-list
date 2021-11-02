package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    private static class Node<T> {
        private T item;
        private MyLinkedList.Node<T> next;
        private MyLinkedList.Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, MyLinkedList.Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> item = new Node<>(null, value, null);
            head = item;
            tail = item;
        } else {
            Node<T> item = new Node<T>(tail, value, null);
            tail.next = item;
            tail = item;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index outside the array");
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> second = head;
            head = new Node<>(null, value, second);
            second.prev = head;
            size++;
            return;
        }
        Node<T> x = getNode(index);
        Node<T> newItem = new Node<>(null, value, null);
        newItem.next = x;
        newItem.prev = x.prev;
        x.prev.next = newItem;
        x.prev = newItem;
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
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index outside the array");
        }
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> x = getNode(index);
        T result = x.item;
        x.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index outside the array");
        }
        if (size == 1) {
            T removed = head.item;
            head = null;
            tail = null;
            return removed;
        }
        if (index == 0) {
            head = head.next;
            size--;
            return head.prev.item;
        } else if (index == size - 1) {
            tail = tail.prev;
            size--;
            return tail.next.item;
        }
        Node<T> x = getNode(index);
        x.prev.next = x.next;
        x.next.prev = x.prev;
        size--;
        return x.item;
    }

    @Override
    public boolean remove(T object) {
        if (head.item.equals(object)) {
            head = head.next;
            size--;
            return true;
        }
        Node<T> x = head;
        if (object == null) {
            int maxIndex = 0;
            while (x.next.item != null) {
                x = x.next;
                if (maxIndex + 2 >= size) {
                    return false;
                }
                maxIndex++;
            }
            x.next.next.prev = x;
            x.next = x.next.next;
        } else {
            while (x != null && !x.item.equals(object)) {
                x = x.next;
            }
            if (x == null) {
                return false;
            }
            x.prev.next = x.next;
            x.next.prev = x.prev;
        }
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index outside the array");
        }
        int indexLinked = 0;
        Node<T> x = head;
        while (indexLinked != index) {
            x = x.next;
            indexLinked++;
        }
        return x;
    }
}
