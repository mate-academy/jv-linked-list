package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
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
            tail.setNext(myNode);
            myNode.setPrev(tail);
            tail = myNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }

        MyNode myNode = new MyNode(value);

        if (index == size) {

            add(value);
        } else if (index == 0) {
            myNode.setNext(head);
            head.setPrev(myNode);
            head = myNode;
            size++;
        } else {

            MyNode current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            myNode.setNext(current);
            myNode.setPrev(current.getPrev());
            current.getPrev().setNext(myNode);
            current.setPrev(myNode);
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
            throw new IndexOutOfBoundsException("Out of bounds");
        }

        MyNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return (T) current.getValue();
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        MyNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        T oldValue = (T) current.getValue();
        current.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        MyNode current = head;

        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        if (current.getPrev() == null) {
            head = current.getNext();
        } else {
            current.getPrev().setNext(current.getNext());
        }

        if (current.getNext() != null) {
            current.getNext().setPrev(current.getPrev());
        }

        size--;
        return (T) current.getValue();

    }

    @Override
    public boolean remove(T object) {

        MyNode current = head;
        while (current != null) {

            if (Objects.equals(current.getValue(), object)) {

                if (current.getPrev() == null) {
                    head = current.getNext();
                } else {
                    current.getPrev().setNext(current.getNext());
                }

                if (current.getNext() == null) {
                    tail = current.getPrev();
                } else {
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
}
