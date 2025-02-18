package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (!isEmpty()) {
            last.next = new Node<T>(last, value, null);
            last = last.next;
            size++;
            return;
        }
        first = new Node<T>(null, value, null);
        last = first;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > 0 && index < size) {
            Node<T> currentNode = findNodeByIndex(index);
            currentNode.prev.next = new Node<T>(currentNode.prev, value, currentNode);
            currentNode.prev = currentNode.prev.next;
            size++;
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            first.prev = new Node<T>(null, value, first);
            first = first.prev;
            size++;
            return;
        }
        throw new IndexOutOfBoundsException("The index " + index + " is out of range.");
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
        Node<T> currentNode = findNodeByIndex(index);
        T changedValue = currentNode.value;
        currentNode.value = value;
        return changedValue;
    }

    @Override
    public T remove(int index) {
        checkIndexInSize(index);
        Node<T> currentNode = findNodeByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (equality(currentNode.value, object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private boolean equality(T objectFirst, T objectSecond) {
        return objectFirst == objectSecond
                || objectFirst != null && objectFirst.equals(objectSecond);
    }

    private void checkIndexInSize(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "The index " + index + " not included in the list.");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndexInSize(index);
        Node<T> currentNode = first;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        throw new IndexOutOfBoundsException("The index " + index + " not found in MyLinkedList.");
    }

    private void unlink(Node<T> node) {
        if (node == first && size == 1) {
            first = null;
            last = null;
            size--;
            return;
        }
        if (node == first) {
            first.next.prev = null;
            first = first.next;
            size--;
            return;
        }
        if (node == last) {
            last.prev.next = null;
            last = last.prev;
            size--;
            return;
        }
        node.next.prev = node.prev;
        node.prev.next = node.next;
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
