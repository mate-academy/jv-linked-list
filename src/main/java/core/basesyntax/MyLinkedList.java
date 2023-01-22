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
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            Node<T> target = getNodeByIndex(index);
            linkBefore(value,target);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        list.stream().forEach(this::add);
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T removedValue = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedNode = getNodeByIndex(index);
        T removedElement = removedNode.value;
        unlink(removedNode);
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object == getNodeByIndex(i).value
                    || object != null && object.equals(getNodeByIndex(i).value)) {
                unlink(getNodeByIndex(i));
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
        return head == null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void linkBefore(T value, Node<T> target) {
        if (target.prev == null) {
            head = new Node<>(null, value, target);
        } else {
            Node<T> newNode = new Node<>(target.prev, value, target);
            target.prev.next = newNode;
            target.prev = newNode;
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev == null && node.next == null) {
            head = tail = null;
        } else if (node == tail) {
            tail.prev.next = null;
            tail = tail.prev;
        } else if (node == head) {
            head.next.prev = null;
            head = head.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index value(Out of bound). "
                    + "Index should be not less than 0 and not more than " + (size - 1) + ".");
        }
    }
}
