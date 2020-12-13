package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> headNode;
    private Node<T> lastNode;

    @Override
    public boolean add(T value) {
        Node<T> temp = new Node<>(value, lastNode, null);
        if (size == 0) {
            headNode = temp;
        } else {
            lastNode.next = temp;
        }
        lastNode = temp;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> tempNode = new Node<>(value, null, null);
        if (size == 0) {
            add(value);
            size--;
        } else if (index == 0) {
            tempNode.next = headNode;
            headNode.prev = tempNode;
            headNode = tempNode;
        } else if (index == size) {
            tempNode.prev = lastNode;
            lastNode.next = tempNode;
            lastNode = tempNode;
        } else {
            Node<T> indexNode = getNode(index);
            tempNode.prev = indexNode.prev;
            tempNode.next = indexNode;
            indexNode.prev = tempNode;
            tempNode.prev.next = tempNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                add(list.get(i));
            }
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> tempNode = getNode(index);
        T tempValue = tempNode.data;
        tempNode.data = value;
        return tempValue;
    }

    @Override
    public T remove(int index) {
        Node<T> temp = getNode(index);
        if (size == 1) {
            headNode = null;
            lastNode = null;
        } else if (index == 0) {
            headNode.next.prev = null;
            headNode = headNode.next;
        } else if (index == size - 1) {
            lastNode.prev.next = null;
            lastNode = lastNode.prev;
        } else {
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
        }
        size--;
        return temp.data;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (getNode(i).data == object
                    || (object != null && object.equals(getNode(i).data))) {
                remove(i);
                return true;
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

    private static void checkIndex(int index, int length) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException(String.format("Index %d incorrect.", index));
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index, size);
        if (size - 1 == index) {
            return lastNode;
        }
        Node<T> temp = headNode;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        Node(T element, Node<T> prev, Node<T> next) {
            this.data = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
