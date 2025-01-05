package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    public class MyNode<T> {
        private T value;
        private MyNode<T> prev;
        private MyNode<T> next;

        MyNode(T x) {
            value = x;
            next = null;
            prev = null;
        }
    }

    private MyNode head;
    private MyNode tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        MyNode myNode = new MyNode(value);
        if (size == 0) {
            head = myNode;
            tail = myNode;
        } else {
            tail.next = myNode;
            myNode.prev = tail;
            tail = myNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        MyNode myNode = new MyNode(value);
            if (index == size) {
                add(value);
            } else if (index == 0) {
            myNode.next = head;
            head.prev = myNode;
            head = myNode;
            size++;
            } else {
                MyNode current = head;
                for (int i = 0; i < index; i++) {
                current = current.next;
                }
        myNode.next = current;
        myNode.prev = current.prev;
        current.prev.next = myNode;
        current.prev = myNode;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        MyNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return (T) current.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        MyNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T oldValue = (T) current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        MyNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        if (current.prev == null) {
            head = current.next;
        } else {
            current.prev.next = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        }
        size--;
        return (T) current.value;
    }

    @Override
    public boolean remove(T object) {
        MyNode current = head;
        while (current != null) {
            if (Objects.equals(current.value, object)) {
                if (current.prev == null) {
                    head = current.next;
                } else {
                    current.prev.next = current.next;
                }

                if (current.next == null) {
                    tail = current.prev;
                } else {
                    current.next.prev = current.prev;
                }
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

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
