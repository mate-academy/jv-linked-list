package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> temp = new Node<>(tail, value, null);
        if (tail == null) {
            tail = temp;
            head = temp;
        } else {
            tail.next = temp;
            temp.prev = tail;
            tail = temp;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        final Node<T> temp = new Node<>(null, value, null);
        if (index == 0) {
            temp.next = head;
            head.prev = temp;
            head = temp;
            size++;
            return;
        }
        Node<T> current = getNode(index);
        temp.prev = current.prev;
        temp.next = current;
        current.prev.next = temp;
        current.prev = temp;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = getNode(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNode(index);
        T temp = current.item;
        current.item = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        unlink(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == current.item || (object != null && object.equals(current.item))) {
                unlink(current);
                return true;
            }
            current = current.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index" + index);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size; i > (index + 1); i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private T unlink(Node<T> removeNode) {
        if (removeNode == head) {
            if (size != 1) {
                head.next.prev = null;
                head = head.next;
            } else {
                head = null;
                tail = null;
            }
        } else if (removeNode == tail) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            removeNode.prev.next = removeNode.next;
            removeNode.next.prev = removeNode.prev;
        }
        size--;
        return removeNode.item;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
