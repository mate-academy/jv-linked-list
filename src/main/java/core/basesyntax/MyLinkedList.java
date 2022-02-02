package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
            return;
        }
        checkIndex(index);
        Node<T> elBefore = searchNode(index).prev;
        Node<T> elAfter = searchNode(index);
        Node<T> newNode = new Node<>(elBefore, value, elAfter);
        if (elBefore == null) {
            head = newNode;
        } else {
            elBefore.next = newNode;
            elAfter.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        Object[] arrayFromList = list.toArray();
        for (Object obj: arrayFromList) {
            T t = (T) obj;
            linkLast(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = searchNode(index).value;
        searchNode(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unLink(searchNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (object == node.value || object != null && object.equals(node.value)) {
                unLink(node);
                return true;
            }
            node = node.next;
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
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    private Node<T> searchNode(int index) {
        checkIndex(index);
        Node<T> targetNode;
        if (index < (size / 2)) {
            targetNode = head;
            for (int i = 0; i < index; i++) {
                targetNode = targetNode.next;
            }
            return targetNode;
        } else {
            targetNode = tail;
            for (int i = size - 1; i > index; i--) {
                targetNode = targetNode.prev;
            }
            return targetNode;
        }
    }

    private T unLink(Node<T> node) {
        T value = node.value;
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.value = null;
        size--;
        return value;
    }

    private void linkLast(T value) {
        Node<T> lastEl = tail;
        Node<T> newNode = new Node<>(tail, value, null);
        tail = newNode;
        if (lastEl == null) {
            head = newNode;
        } else {
            lastEl.next = newNode;
        }
        size++;
    }
}
