package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        Node newNode = new Node(value);
        if (index == 0) {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.getNext().setPrev(newNode);
            current.setNext(newNode);
            newNode.setPrev(current);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return (T) current.getData();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        T oldValue = (T) current.getData();
        current.setData(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node removedNode;
        if (size == 1) {
            removedNode = head;
            head = null;
            tail = null;
        } else if (index == 0) {
            removedNode = head;
            head = head.getNext();
            head.setPrev(null);
        } else if (index == size - 1) {
            removedNode = tail;
            tail = tail.getPrev();
            tail.setNext(null);
        } else {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            removedNode = current;
            current.getPrev().setNext(current.getNext());
            current.getNext().setPrev(current.getPrev());
        }
        size--;
        return (T) removedNode.getData();
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        while (current != null) {
            if (Objects.equals(current.getData(), object)) {
                if (current == head) {
                    head = head.getNext();
                    if (head != null) {
                        head.setPrev(null);
                    } else {
                        tail = null;
                    }
                } else if (current == tail) {
                    tail = tail.getPrev();
                    tail.setNext(null);
                } else {
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                }
                size--;
                return true;
            }
            current = current.getNext();
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index out of bounds:"
                    + " %d at size %d", index, size));
        }
    }
}
