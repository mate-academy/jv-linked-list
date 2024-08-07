package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(value);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
            newNode.prev = lastNode;
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
            Node<T> lastNode = head;
            Node<T> newNode = new Node<>(value);
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    if (lastNode == head) {
                        lastNode.prev = newNode;
                        newNode.next = lastNode;
                        head = newNode;
                    } else {
                        lastNode.prev.next = newNode;
                        newNode.prev = lastNode.prev;
                        lastNode.prev = newNode;
                        newNode.next = lastNode;
                    }
                    size++;
                }
                lastNode = lastNode.next;
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
        checkIndex(index);
        Node<T> lastNode = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return lastNode.item;
            }
            lastNode = lastNode.next;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> lastNode = head;
        Node<T> newNode = new Node<>(value);
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                T element = lastNode.item;
                lastNode.item = newNode.item;
                return element;
            }
            lastNode = lastNode.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> lastNode = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                T element = lastNode.item;
                if (size == 1) {
                    head = null;
                    tail = null;
                } else if (lastNode == head) {
                    lastNode.next.prev = null;
                    head = lastNode.next;
                } else if (lastNode == tail) {
                    lastNode.prev.next = null;
                    tail = lastNode.prev;
                } else {
                    lastNode.prev.next = lastNode.next;
                    lastNode.next.prev = lastNode.prev;
                }
                size--;
                return element;
            }
            lastNode = lastNode.next;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        int index = index(object);
        if (index != -1) {
            remove(index);
            return true;
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

    private int index(T element) {
        int index = -1;
        Node<T> lastNode = head;
        for (int i = 0; i < size; i++) {
            if (lastNode.item == element
                    || lastNode.item != null && lastNode.item.equals(element)) {
                return i;
            }
            lastNode = lastNode.next;
        }
        return index;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item) {
            this.item = item;
        }
    }
}
