package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node current = new Node(value);
        if (size == 0) {
            head = current;
            tail = current;
            size++;
        } else {
            tail.next = current;
            current.prev = tail;
            tail = current;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        indexBoundsCheck(index);
        Node current = new Node(value);
        if (size == 0) {
            add(value);
            return;
        }
        if (index == 0) {
            head.prev = current;
            current.next = head;
            head = current;
            size++;
            return;
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> counter = getElementByIndex(index);
            current.prev = counter.prev;
            counter.prev.next = current;
            counter.prev = current;
            current.next = counter;
            size++;
            return;
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
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return getElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexBoundsCheck(index);
        Node<T> current = getElementByIndex(index);
        T result = current.value;
        current.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        indexBoundsCheck(index);
        Node<T> current = getElementByIndex(index);
        T result = current.value;
        unlink(current);
        return result;
    }

    @Override
    public boolean remove(T object) {
        if (size == 1 && head.value == object) {
            head = null;
            tail = null;
            size--;
            return true;
        }
        Node<T> current = head;
        while (current != null) {
            if (current.value == null && current.value == object || current.value.equals(object)) {
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
        return size == 0 ? true : false;
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node<T> getElementByIndex(int index) {
        if (index < size / 2) {
            Node<T> current = head;
            int count = 0;
            while (current != null) {
                if (count == index) {
                    return current;
                }
                count++;
                current = current.next;
            }
        } else {
            Node<T> current = tail;
            int count = size - 1;
            while (current != null) {
                if (count == index) {
                    return current;
                }
                count--;
                current = current.prev;
            }
        }
        throw new IndexOutOfBoundsException("Index out of bounds");
    }

    private void indexBoundsCheck(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (node.prev == null) {
            head = node.next;
            node.next.prev = null;
            node.next = null;
            size--;
            return;
        }
        if (node.next == null) {
            tail = node.prev;
            node.prev.next = null;
            node.prev = null;
            size--;
            return;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
            return;
        }
    }
}
