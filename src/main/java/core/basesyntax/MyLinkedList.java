package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if(head == null) {
            head = this.tail = newNode;
        } else {
            this.tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    private Node<T> findNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if(head == null) {
            head = tail = newNode;
        } else if(index == 0) {
            newNode.next = head;
            head = newNode;
        } else  if(index == size) {
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> prevNode = findNodeByIndex(index - 1);
            newNode.next = prevNode.next;
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index, size);
        Node<T> current = findNodeByIndex(index);
        T prev = current.value;
        current.value = value;
        return prev;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        T removedElement;
        if(index == 0) {
            removedElement = head.value;
            head = head.next;
            if(head == null) {
                tail = null;
            }
        } else {
            Node<T> prev = findNodeByIndex(index - 1);
            removedElement = prev.next.value;
            prev.next = prev.next.next;
            if(index == size - 1) {
                tail = prev;
            }
        }
        size --;
        return  removedElement;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }
        if (Objects.equals(head.value, object)) {
            remove(0);
            return true;
        }
        Node<T> current = head;
        while (current.next != null) {
            if (Objects.equals(current.next.value, object)) {
                if (current.next == tail) {
                    tail = current;
                }
                current.next = current.next.next;
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
       return head == null;
    }
}
