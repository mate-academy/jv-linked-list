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
        checkIndexAdd(index);
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
        Node<T> nodeTemp = getNodeByIndex(index);
        T temp = nodeTemp.value;
        nodeTemp.value = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedValue;
        if (index == 0) {
            if (head.next != null) {
                head.next.prev = null;
            }
            removedValue = head.value;
            head = head.next;
        } else {
            Node<T> nodeTemp = getNodeByIndex(index);
            removedValue = nodeTemp.value;
            unlink(nodeTemp);
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
                unlink(nodeTemp);
                if (i == 0) {
                    head = head.next;
                }
                size--;
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

    private void checkIndexAdd(int index) {
        if (index < 0 || index > size) {
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

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }
}

