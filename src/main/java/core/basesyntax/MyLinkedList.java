package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        newNode.prev = last;
        if (last != null) {
            last.next = newNode;
        }
        if (first == null) {
            first = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(value, null, null);
            Node<T> tempPoint = getByIndex(index);
            newNode.prev = tempPoint.prev;
            newNode.next = tempPoint;
            if (tempPoint.prev != null) {
                tempPoint.prev.next = newNode;
            }
            tempPoint.prev = newNode;
            if (newNode.prev == null) {
                first = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T element : list) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        Node<T> tempPoint = getByIndex(index);
        return tempPoint.data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> tempPoint = getByIndex(index);
        T result = tempPoint.data;
        tempPoint.data = value;
        return result;
    }

    @Override
    public T remove(int index) {
        Node<T> tempPoint = getByIndex(index);
        unlink(tempPoint);
        return tempPoint.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> tempPoint = first;
        for (int i = 0; i < size; i++) {
            if (tempPoint.data == object || (tempPoint.data != null
                    && tempPoint.data.equals(object))) {
                unlink(tempPoint);
                return true;
            }
            tempPoint = tempPoint.next;
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

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        size--;
    }

    private Node<T> getByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index : " + index + " Out bounds of List");
        }
        Node<T> tempPoint = index < (size >> 1) ? first : last;
        if (tempPoint == first) {
            for (int i = 0; i < index; i++) {
                tempPoint = tempPoint.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                tempPoint = tempPoint.prev;
            }
        }
        return tempPoint;
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T data;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.data = data;
        }
    }
}
