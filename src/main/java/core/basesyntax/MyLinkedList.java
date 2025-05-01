package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> item = new Node<>(value);
        if (size == 0) {
            head = item;
        } else {
            item.prev = tail;
            tail.next = item;
        }
        tail = item;
        this.size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, "size");
        if (head == null) {
            this.add(value);
            return;
        }
        Node<T> item = new Node<>(value);
        if (index == 0) {
            head.prev = item;
            item.next = head;
            head = item;
            size++;
            return;
        }
        if (index == size) {
            item.prev = tail;
            tail.next = item;
            tail = item;
            size++;
            return;
        }
        Node<T> currentItem = getNodeByIndex(index);
        currentItem.prev.next = item;
        item.prev = currentItem.prev;
        item.next = currentItem;
        currentItem.prev = item;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            this.add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, "index");
        if (index == 0) {
            return head.data;
        }
        if (index == size - 1) {
            return tail.data;
        }
        Node<T> currentItem = getNodeByIndex(index);
        return currentItem.data;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, "index");
        Node<T> currentItem = getNodeByIndex(index);
        T oldData = currentItem.data;
        currentItem.data = value;
        return oldData;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, "index");
        if (index == 0) {
            T oldData = head.data;
            if (size > 1 && head.next != null) {
                head = head.next;
                head.next.prev = null;
            } else {
                head = null;
                tail = null;
            }
            size--;
            return oldData;
        }
        if (index == size - 1) {
            T oldData = tail.data;
            if (tail.prev != null) {
                tail = tail.prev;
                tail.prev.next = null;
            } else {
                tail = null;
                head = null;
            }
            size--;
            return oldData;
        }
        Node<T> currentItem = getNodeByIndex(index);
        unlink(currentItem);
        size--;
        return currentItem.data;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> currentItem = head;
        while (currentItem != null) {
            if (Objects.equals(currentItem.data, object)) {
                if (currentItem == head) {
                    head = head.next;
                    if (head != null) {
                        head.prev = null;
                    }
                    size--;
                    return true;
                }
                if (currentItem == tail) {
                    if (tail.prev != null) {
                        tail = tail.prev;
                        tail.prev.next = null;
                    } else {
                        tail = null;
                        head = null;
                    }
                    size--;
                    return true;
                }
                unlink(currentItem);
                size--;
                return true;
            }
            currentItem = currentItem.next;
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

    private void checkIndex(int index, String context) {
        switch (context) {
            case "size":
                if (index < 0 || index > size) {
                    throw new IndexOutOfBoundsException();
                }
                break;
            case "index":
                if (index < 0 || index >= size) {
                    throw new IndexOutOfBoundsException();
                }
                break;
            default:
                System.out.println("There's no such context: " + context);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentItem = head;
        for (int i = 1; i <= index; i++) {
            currentItem = currentItem.next;
        }
        return currentItem;
    }

    private void unlink(Node<T> currentItem) {
        currentItem.prev.next = currentItem.next;
        currentItem.next.prev = currentItem.prev;
    }

    static class Node<T> {
        private Node<T> prev = null;
        private T data;
        private Node<T> next = null;

        public Node() {
            this.data = null;
        }

        public Node(T data) {
            this.data = data;
        }

        public Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}
