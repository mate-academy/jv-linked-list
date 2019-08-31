package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        this.size = 0;
        first = new Node<>(null, null, null);
        last = new Node<>(null, null, null);
    }

    private void backElement(T tail) {
        Node<T> last = this.last;
        Node<T> node = new Node<>(last, tail, (Node) null);
        if (last.element == null) {
            this.first = node;
        } else {
            last.next = node;
        }
        this.last = node;
        this.size++;
    }

    private void frontElement(T head) {
        Node<T> first = this.first;
        Node<T> node = new Node<>((Node) null, head, first);
        if (first.element == null) {
            this.last = node;
        } else {
            first.prev = node;
        }
        this.first = node;
        this.size++;
    }

    @Override
    public void add(T value) {
        if (first.element == null) {
            frontElement(value);
        } else {
            backElement(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> prev = first;
            for (int i = 0; i < size; i++) {
                if (index == i) {
                    Node<T> node = new Node<>(prev, value, prev.next);
                    prev = node;
                    if (prev == null) {
                        this.first = node;
                    } else {
                        prev.next = node;
                    }
                    size++;
                    break;
                }
                prev = prev.next;
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
        checkIndexException(index);
        Node<T> node = first;
        for (int i = 0; i < index; ++i) {
            node = node.next;
        }
        return node.element;
    }

    @Override
    public void set(T value, int index) {
        checkIndexException(index);
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                node.element = value;
                return;
            }
            node = node.next;
        }
    }

    @Override
    public T remove(int index) {
        checkIndexException(index);
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (index == 0) {
                node.next.prev = node.prev;
                first = node.next;
                break;
            } else if (index == i && i < size - 1) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                break;
            } else if (index == i && i == size - 1) {
                node.prev.next = node.next;
                last = node.prev;
                break;
            }
            node = node.next;
        }
        size--;
        return node.element;
    }

    @Override
    public T remove(T t) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (node.element == t) {
                return remove(i);
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndexException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
