package core.basesyntax;

import javax.security.auth.login.AccountLockedException;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private T item;
    private  int size;
    Node<T> head;
    Node<T> tail;

    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
        {
            Node<T> head = new Node<>(null, null, tail);
            Node<T> tail = new Node<>(head, null, null);
        }

    public MyLinkedList(T item) {
        this.item = item;
    }

    public MyLinkedList(){

    }

    public void checkIndex(int index) {
        try {
            if (index > size || index < 0) {
                throw new IndexOutOfBoundsException("exception");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("This index is invalid", e);
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
        }
        if (size == 1) {
            tail = new Node<>(head, value, null);
        }
        if (size > 0) {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (size == 0) {
            head = new Node<>(null, value, null);
        }
        if (size > 0 && index < (size - 1) && index > 0 ) {
            Node<T> node = new Node<>(head,value,tail);
            for (int i = 1; i < index; i++) {
                node.prev = node.prev.next;
            }
            for (int i = 1; i < size - index; i++) {
                node.next = node.next.prev;
            }
        }
        if (index == size){
            add(value);
        }
        if (index == 0 && size > 0) {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
        }
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
