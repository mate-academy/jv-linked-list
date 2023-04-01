package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds: " + index);
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> indexNode = getNodeAtIndex(index);
            Node<T> prevToIndexNode = indexNode.getPrev();
            Node<T> newNode = new Node<>(prevToIndexNode, value, indexNode);
            if (index == 0) {
                head = newNode;
            } else {
                prevToIndexNode.setNext(newNode);
            }
            indexNode.setPrev(newNode);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        indexRangeCheck(index);
        return getNodeAtIndex(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        indexRangeCheck(index);
        Node<T> indexNode = getNodeAtIndex(index);
        T oldValue = indexNode.getValue();
        indexNode.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexRangeCheck(index);
        Node<T> removedNode = getNodeAtIndex(index);
        if (index == 0) {
            head = removedNode.getNext();
        } else {
            removedNode.getPrev().setNext(removedNode.getNext());
        }
        if (index == size - 1) {
            tail = removedNode.getPrev();
        } else {
            removedNode.getNext().setPrev(removedNode.getPrev());
        }
        size--;
        return removedNode.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        int index = 0;
        while (temp != null) {
            if (object == temp.getValue() || temp.getValue() != null && temp.getValue().equals(object)) {
                remove(index);
                return true;
            }
            temp = temp.getNext();
            index++;
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

    private void indexRangeCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds: " + index);
        }
    }

    private Node<T> getNodeAtIndex(int index) {
        indexRangeCheck(index);
        Node<T> indexNode;
        if (index < size / 2) {
            indexNode = head;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.getNext();
            }
        } else {
            indexNode = tail;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.getPrev();
            }
        }
        return indexNode;
    }
}