package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private Node<T> target;
    private int size;

    public MyLinkedList() {
        tail = new Node<T>(head, null, null);
        head = new Node<T>(null, null, tail);
        tail.prev = head;
    }

    public class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    public Node targetSearch(int index) {

        if (size >> 1 > index) {
            target = head.next;
            for (int i = 0; i < index; i++) {
                target = target.next;
            }
        } else {
            target = tail.prev;
            for (int i = size - 1; i > index; i--) {
                target = target.prev;
            }
        }
        return target;
    }

    public void indexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index Out Of Bounds!!!");
        }
    }

    @Override
    public void add(T value) {

        Node<T> newNode = tail;
        newNode.value = value;
        tail = new Node<T>(newNode, null, null);
        newNode.next = tail;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index Out Of Bounds!!!");
        }
        targetSearch(index);

        Node newNode = new Node<T>(target, value, target.next);
        target.next.prev = newNode;
        target.next = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T x : list) {
            add(x);
        }
    }

    @Override
    public T get(int index) {

        indexValidation(index);
        targetSearch(index);
        return target.value;
    }

    @Override
    public void set(T value, int index) {
        indexValidation(index);
        targetSearch(index);
        target.value = value;
    }

    @Override
    public T remove(int index) {
        indexValidation(index);
        targetSearch(index);
        target.prev.next = target.next;
        target.next.prev = target.prev;
        size--;
        return target.value;
    }

    @Override
    public T remove(T t) {
        int index;
        target = head.next;
        for (index = 0; index < size; index++) {
            if (Objects.equals(target.value, t)) {
                return remove(index);
            }
            target = target.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
