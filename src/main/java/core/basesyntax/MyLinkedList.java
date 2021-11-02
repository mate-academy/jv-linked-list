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
            Node<T> head = null;
            Node<T> tail = null;
        }

    public MyLinkedList(T item) {
        this.item = item;
    }

    public MyLinkedList(){

    }

    public void checkIndex(int index) {
            if (index >= size || index < 0) {
                throw  new IndexOutOfBoundsException("exception");
            }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
            head.prev = null;
            tail.next = null;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
            tail.next = null;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw  new IndexOutOfBoundsException("exception");
        }
        Node<T> node = new Node<>(null, value, null);
        if (size > 0 && index < (size - 1) && index > 0 ) {
            node.next = tail;
            node.prev = head;
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
            head.prev = node;
            node.next = head;
            head = node;
            head.prev = null;
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
        checkIndex(index);
        if (size == 0) {
            return null;
        }
        if (index == 0) {
            return head.item;
        }
        if (index > 0) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode.item;
        }
        return  null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        if (size > 0 && index < (size - 1) && index > 0 ) {
            Node<T> node = new Node<>(head,value,tail);
            for (int i = 1; i < index; i++) {
                node.prev = node.prev.next;
            }
            for (int i = 1; i < size - index - 1; i++) {
                node.next = node.next.prev;
            }
        }
        if (index == 0 && size > 0) {
            Node<T> node = new Node<>(null, value, head.next);
            head.next.prev = node;
            head = node;
        }
        return value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (size == 1) {
            head.prev = null;
            head.next = null;
            head.item = null;
        }
        return get(index);
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
