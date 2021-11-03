package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (isEmpty()) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            addByIndex(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        Node<T> tempNode = findNodeByIndex(index);
        T res = tempNode.value;
        tempNode.value = value;
        return res;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> tempNode = findNodeByIndex(index);
        T res = tempNode.value;
        unlink(tempNode);
        return res;
    }

    @Override
    public boolean remove(T object) {
        Node<T> tempNode = findNode(object);
        if (tempNode == null) {
            return false;
        }
        unlink(tempNode);
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

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void addLast(T value) {
        Node<T> tempNode = new Node<>(last, value, null);
        last.next = tempNode;
        last = tempNode;
        size++;
    }

    private void addFirst(T value) {
        first = new Node(null, value, null);
        last = first;
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index, size - 1);
        Node<T> result;
        if (size / 2 > index) {
            result = first;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = last;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private Node<T> findNode(T object) {
        Node<T> tempNode = first;
        for (int i = 0; i < size; i++) {
            if (object == tempNode.value || (object != null && object.equals(tempNode.value))) {
                return tempNode;
            }
            tempNode = tempNode.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (!node.equals(first)) {
            node.prev.next = node.next;
        } else if (node.equals(first)) {
            first = first.next;
        }
        if (!node.equals(last)) {
            node.next.prev = node.prev;
        } else if (node.equals(last)) {
            last = last.prev;
        }
        size--;
    }

    private void checkIndex(int index, int limit) {
        if (index > limit || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
    }

    private void addByIndex(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> tempNode = findNodeByIndex(index);
        if (tempNode.equals(first)) {
            first = newNode;
        } else {
            tempNode.prev.next = newNode;
        }
        newNode.next = tempNode;
        newNode.prev = tempNode.prev;
        tempNode.prev = newNode;
        size++;
    }
}
