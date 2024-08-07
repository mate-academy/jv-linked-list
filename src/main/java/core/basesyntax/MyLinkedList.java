package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item) {
            this.item = item;
        }
    }

    @Override
    public void add(T value) {
        Node<T> temporaryItem = tail;
        Node<T> newNode = new Node<>(value);
        tail = newNode;
        if (temporaryItem == null) {
            head = newNode;
        } else {
            temporaryItem.next = newNode;
            newNode.prev = temporaryItem;
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
            Node<T> temporaryItem = head;
            Node<T> newNode = new Node<>(value);
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    if (temporaryItem == head) {
                        temporaryItem.prev = newNode;
                        newNode.next = temporaryItem;
                        head = newNode;
                    } else {
                        temporaryItem.prev.next = newNode;
                        newNode.prev = temporaryItem.prev;
                        temporaryItem.prev = newNode;
                        newNode.next = temporaryItem;
                    }
                    size++;
                }
                temporaryItem = temporaryItem.next;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            Node<T> temporaryItem = tail;
            Node<T> newNode = new Node<>(list.get(i));
            tail = newNode;
            if (temporaryItem == null) {
                head = newNode;
            } else {
                temporaryItem.next = newNode;
                newNode.prev = temporaryItem;
            }
            size++;
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> temporaryItem = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return temporaryItem.item;
            }
            temporaryItem = temporaryItem.next;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> temporaryItem = head;
        Node<T> newNode = new Node<>(value);
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                T element = temporaryItem.item;
                temporaryItem.item = newNode.item;
                return element;
            }
            temporaryItem = temporaryItem.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> temporaryItem = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                T element = temporaryItem.item;
                if (size == 1) {
                    head = null;
                    tail = null;
                } else if (temporaryItem == head) {
                    temporaryItem.next.prev = null;
                    head = temporaryItem.next;
                } else if (temporaryItem == tail) {
                    temporaryItem.prev.next = null;
                    tail = temporaryItem.prev;
                } else {
                    temporaryItem.prev.next = temporaryItem.next;
                    temporaryItem.next.prev = temporaryItem.prev;
                }
                size--;
                return element;
            }
            temporaryItem = temporaryItem.next;
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
        Node<T> temporaryItem = head;
        for (int i = 0; i < size; i++) {
            if (temporaryItem.item == element
                    || temporaryItem.item != null && temporaryItem.item.equals(element)) {
                return i;
            }
            temporaryItem = temporaryItem.next;
        }
        return index;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
}
