package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        }
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index);
        }
        if (size == 0 || index == size) {
            add(value);
            return;
        }
        Node<T> nodeTemp = getNodeByIndex(index);
        Node<T> newNode = new Node<>(nodeTemp.prev, value, nodeTemp);
        if (index > 0) {
            nodeTemp.prev.next = newNode;
        } else {
            head = newNode;
        }
        nodeTemp.prev = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        if (index == size) {
            T temp = tail.value;
            tail.value = value;
            return temp;
        }
        Node<T> nodeTemp = getNodeByIndex(index);
        T temp = nodeTemp.value;
        nodeTemp.value = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedValue = null;
        if (index == 0) {
            removedValue = head.value;
            head = head.next;
            if (head != null && head.next != null) {
                head.next.prev = null;
            }
            size--;
            return removedValue;
        }
        Node<T> nodeTemp = getNodeByIndex(index);
        if (nodeTemp.prev != null) {
            removedValue = nodeTemp.value;
            nodeTemp.prev.next = nodeTemp.next;
            if (nodeTemp.next != null) {
                nodeTemp.next.prev = nodeTemp.prev;
            }
            if (index == size - 1) {
                tail = nodeTemp.prev;
            }
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeTemp = head;
        for (int i = 0; i < size; i++) {
            if (nodeTemp.value == object || (nodeTemp.value != null
                    && nodeTemp.value.equals(object))) {
                remove(i);
                return true;
            }
            nodeTemp = nodeTemp.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> nodeTemp;
        if (size / 2 > index) {
            nodeTemp = head;
            for (int i = 0; i < index; i++) {
                nodeTemp = nodeTemp.next;
            }
        } else {
            nodeTemp = tail;
            for (int i = size - 1; i > index; i--) {
                nodeTemp = nodeTemp.prev;
            }
        }
        return nodeTemp;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
