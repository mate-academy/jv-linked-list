package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail = newNode;
        if (size == 0) {
            head = newNode;
        } else {
            tail.prev.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index <= size && index >= 0) {
            if (index == size) {
                add(value);
            } else if (index == 0) {
                Node<T> newNode = new Node<>(null, value, head);
                head = newNode;
                head.next.prev = newNode;
                size++;
            } else {
                Node<T> tmpNode = findAppropriateNode(index);
                Node<T> newNode = new Node<>(tmpNode.prev, value, tmpNode);
                tmpNode.prev.next = newNode;
                tmpNode.prev = newNode;
                size++;
            }
        } else {
            throw new IndexOutOfBoundsException("invalid index " + index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIfIndexIsValid(index);
        return findAppropriateNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexIsValid(index);
        T oldValue = findAppropriateNode(index).value;
        findAppropriateNode(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexIsValid(index);
        Node<T> removedNode = head;
        if (size == 1) {
            head = null;
            tail = null;
        } else if (index == 0) {
            head.next.prev = null;
            head = head.next;
        } else if (index == size - 1) {
            removedNode = tail;
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            removedNode = findAppropriateNode(index);
            unlink(removedNode);
        }
        size--;
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> tmpNode = head;
        for (int i = 0; i < size; i++) {
            if (object != null && tmpNode.value != null) {
                if (tmpNode.value.equals(object)) {
                    remove(i);
                    return true;
                }
            } else {
                if (object == null && tmpNode.value == null) {
                    remove(i);
                    return true;
                }
            }
            tmpNode = tmpNode.next;
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

    private void checkIfIndexIsValid(int index) {
        if (!(index < size && index >= 0)) {
            throw new IndexOutOfBoundsException("invalid index " + index);
        }
    }

    private void unlink(Node<T> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private Node<T> findAppropriateNode(int index) {
        Node<T> tmpNode;
        if (index <= size / 2) {
            tmpNode = head;
            for (int i = 0; i < index; i++) {
                tmpNode = tmpNode.next;
            }
        } else {
            tmpNode = tail;
            for (int i = size - 1; i > index; i--) {
                tmpNode = tmpNode.prev;
            }
        }
        return tmpNode;
    }

    static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
