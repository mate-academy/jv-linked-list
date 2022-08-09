package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value,null);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Your index " + index + " is out of range " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> insertNode = new Node<>(null,value,head);
            head.setPrev(insertNode);
            head = insertNode;
        } else {
            Node<T> refNode = findNodeByIndex(index);
            Node<T> insertNode = new Node<>(refNode.getPrev(),value,refNode);
            refNode.getPrev().setNext(insertNode);
            refNode.setPrev(insertNode);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = findNodeByIndex(index);
        T oldValue = oldNode.getValue();
        oldNode.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> removeNode = head; removeNode != null; removeNode = removeNode.getNext()) {
                if (removeNode.getValue() == null) {
                    unlink(removeNode);
                    return true;
                }
            }
        } else {
            for (Node<T> removeNode = head; removeNode != null; removeNode = removeNode.getNext()) {
                if (object.equals(removeNode.getValue())) {
                    unlink(removeNode);
                    return true;
                }
            }
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

    private T unlink(Node<T> unlinkNode) {
        final T element = unlinkNode.getValue();
        final Node<T> next = unlinkNode.getNext();
        final Node<T> prev = unlinkNode.getPrev();
        if (prev == null) {
            head = next;
        } else {
            prev.setNext(next);
            unlinkNode.setPrev(null);
        }
        if (next == null) {
            tail = prev;
        } else {
            next.setPrev(prev);
            unlinkNode.setNext(null);
        }
        unlinkNode.setValue(null);
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Your index " + index + "is out of range " + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        int count = 0;
        Node<T> findNode = head;
        while (count != index) {
            findNode = findNode.getNext();
            count++;
        }
        return findNode;
    }
}
