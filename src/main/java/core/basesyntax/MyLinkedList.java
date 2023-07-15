package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> node = new Node<>(null, value, null);
            head = node;
            tail = node;
            size++;
        } else {
            addNode(value);
        }
    }

    @Override
    public void add(T value, int index) {
        findIndex(index);
        if (index == size && index != 0) {
            addNode(value);
        } else if (index == 0) {
            addFirst(value);
        } else if (size / 2 >= index) {
            addFromHead(value, index);
        } else {
            addFromTail(value, index);
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
        findIndexGet(index);
        return size / 2 >= index ? getFromHead(index) : getFromTail(index);
    }

    @Override
    public T set(T value, int index) {
        findIndexGet(index);
        return size / 2 >= index ? setFromHead(value, index) : setFromTail(value, index);
    }

    @Override
    public T remove(int index) {
        Node<T> current = findNode(index, null);
        if (current == head) {
            head = current.next;
        } else {
            current.prev.next = current.next;
        }
        if (current == tail) {
            tail = current.prev;
        } else {
            current.next.prev = current.prev;
        }
        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = findNodeByValue(object);
        if (current != null) {
            if (current.prev != null) {
                current.prev.next = current.next;
            } else {
                head = current.next;
            }
            if (current.next != null) {
                current.next.prev = current.prev;
            } else {
                tail = current.prev;
            }
            size--;
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

    private Node<T> findNodeByValue(T value) {
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.item, value)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private Node<T> findNode(int index, T object) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Node<T> current = head;
        if (object != null) {
            while (current != null) {
                if (Objects.equals(current.item, object)) {
                    return current;
                }
                current = current.next;
            }
        } else {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }

        throw new NoSuchElementException("Object not found: " + object);
    }

    private T setFromHead(T value, int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    private T setFromTail(T value, int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    private T getFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    private T getFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current.item;
    }

    private void addFromHead(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        newNode.next = current.next;
        newNode.prev = current;
        current.next.prev = newNode;
        current.next = newNode;
        size++;
    }

    private void addFromTail(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    private void addNode(T value) {
        Node<T> node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
        size++;
    }

    private void findIndexGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void findIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void addFirst(T value) {
        if (size == 0) {
            Node<T> node = new Node<>(null, value, null);
            head = node;
            tail = node;
            size++;
        } else {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
            size++;
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
