package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T>, GetNodeBy {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = node;
        } else {
            node.setPrev(tail);
            tail.setNext(node);
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head.setPrev(new Node<>(null, value, head));
            head = head.getPrev();
        } else {
            Node<T> validNode = getNodeByIndex(index, tail, size);
            Node<T> newNode = new Node<>(validNode.getPrev(), value, validNode);
            validNode.setPrev(newNode);
            newNode.getNext().setPrev(newNode);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return (T) getNodeByIndex(index, tail, size).getValue();
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index, tail, size);
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> target = getNodeByIndex(index, tail, size);
        unlink(target);
        return target.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> targetNode = getNodeByValue(object, head);
        if (targetNode == null) {
            return false;
        }
        unlink(targetNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void unlink(Node<? extends T> target) {
        Node prevNode = target.getPrev();
        Node nextNode = target.getNext();
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.setNext(nextNode);
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.setPrev(prevNode);
        }
        size--;
    }
}
