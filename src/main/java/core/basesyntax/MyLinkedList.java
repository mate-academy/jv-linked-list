package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            Node<T> current = getNodeByIndex(index);
            Node<T> newNode = new Node<T>(current.prev, value, current);
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
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
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNodeByIndex(index);
        T oldElment = current.element;
        current.element = value;
        return oldElment;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.element == object
                    || (current.element != null && current.element.equals(object))) {
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
        return size == 0;
    }

    private void addFirst(T value) {
        Node<T> first = new Node<T>(null, value, head);
        if (head == null) {
            tail = head = first;
        } else {
            head = first;
            head.next.prev = first;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> last = new Node<T>(tail, value, null);
        if (size == 0) {
            head = tail = last;
        } else {
            tail.next = last;
            tail = last;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " does not exist");
        }
        if (index < size / 2) {
            current = head;
            int i = 0;
            while (i != index) {
                current = current.next;
                i++;
            }
        } else {
            current = tail;
            int i = size - 1;
            while (i != index) {
                current = current.prev;
                i--;
            }
        }
        return current;
    }

    private T unlink(Node<T> curent) {
        if (curent.prev == null) {
            head = curent.next;
            curent.next = null;
        } else if (curent.next == null) {
            tail = curent.prev;
            curent.prev.next = null;
            curent.prev = null;
        } else {
            curent.prev.next = curent.next;
            curent.next.prev = curent.prev;
        }
        size--;
        return curent.element;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.element = element;

        }
    }
}
