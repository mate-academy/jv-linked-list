package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
            size = 1;
        } else {
            Node<T> previousNode = tail;
            previousNode.next = newNode;
            newNode.prev = previousNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(value);
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            if (count == index) {
                if (current == head) {
                    newNode.next = head;
                    head.prev = newNode;
                    head = newNode;
                    size++;
                } else {
                    Node<T> previousNode = current.prev;
                    previousNode.next = newNode;
                    newNode.prev = previousNode;
                    current.prev = newNode;
                    newNode.next = current;
                    size++;
                }
                break;
            }
            current = current.next;
            count++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            if (count == index) {
                return current.item;
            }
            current = current.next;
            count++;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        int count = 0;
        T currentIndex = null;
        while (current != null) {
            if (count == index) {
                currentIndex = current.item;
                current.item = value;
                break;
            }
            current = current.next;
            count++;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException();
        }
        return currentIndex;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        int count = 0;
        T result = null;
        while (current != null) {
            if (count == index) {
                result = current.item;
                Node<T> prevNode = current.prev;
                Node<T> nextNode = current.next;
                if (size == 1) {
                    head = null;
                    tail = null;
                    size = 0;
                    break;
                }
                if (prevNode == null) {
                    nextNode.prev = null;
                    head = nextNode;
                    size--;
                    break;
                }
                if (nextNode == null) {
                    prevNode.next = null;
                    tail = prevNode;
                    size--;
                    break;
                }
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
                size--;
                break;
            }
            current = current.next;
            count++;
        }
        return result;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (equals(current.item, object) && size == 1) {
                head = null;
                tail = null;
                size = 0;
                return true;
            }
            if (equals(current.item, object) && current.next == null) {
                Node<T> prevNode = current.prev;
                prevNode.next = null;
                tail = prevNode;
                size--;
                return true;
            }
            if (equals(current.item, object) && head == current) {
                Node<T> nextNode = current.next;
                nextNode.prev = null;
                head = nextNode;
                size--;
                return true;
            }
            if (equals(current.item, object)) {
                Node<T> prevNode = current.prev;
                Node<T> nextNode = current.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
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

    private boolean equals(T first, T second) {
        if (first == null && second == null) return true;
        if (first == null || second == null) return false;
        return first.equals(second);
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
        Node(E element) {
            this.item = element;
        }
    }
}
