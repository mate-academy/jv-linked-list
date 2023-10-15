package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        size++;
        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }
        tail.setNext(newNode);
        newNode.setPrev(tail);
        tail = newNode;
    }

    @Override
    public void add(T value, int index) {
        Node<T> current = head;
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException(
                    "Index " + index + " is out bounds. List size is " + size);
        }
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        if (current == null) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value);
        Node<T> prev = current.getPrev();
        newNode.setPrev(prev);
        newNode.setNext(current);
        current.setPrev(newNode);
        if (prev != null) {
            prev.setNext(newNode);
        } else {
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T val: list) {
            add(val);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        Node<T> nextNode = node.getNext();
        Node<T> prevNode = node.getPrev();
        if (prevNode != null) {
            prevNode.setNext(nextNode);
        } else {
            head = nextNode;
        }
        if (nextNode != null) {
            nextNode.setPrev(prevNode);
        } else {
            tail = prevNode;
        }
        size--;
        return node.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(current.getValue(), object)) {
                break;
            }
            index++;
            current = current.getNext();
        }
        if (current != null) {
            remove(index);
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

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(
                    "Index " + index + " is out bounds. List size is " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }
}
