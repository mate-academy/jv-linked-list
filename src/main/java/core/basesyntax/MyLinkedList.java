package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> afterAdding = getNode(index);
        Node<T> beforeAdding = afterAdding.prev;
        Node<T> newNode = new Node<>(beforeAdding, value, afterAdding);
        afterAdding.prev = newNode;
        if (beforeAdding != null) {
            beforeAdding.next = newNode;
        } else {
            head = newNode;
        }
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> elementToEdit = getNode(index);
        T oldElement = elementToEdit.item;
        elementToEdit.item = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        Node<T> elementToRemove = getNode(index);
        unlink(elementToRemove);
        return elementToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentValue = head;
        for (int i = 0; i < size; i++) {
            if (areElementsEqual(currentValue.item, object)) {
                unlink(currentValue);
                return true;
            }
            currentValue = currentValue.next;
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

    private Node<T> getNode(int index) {
        indexCheckForGet(index);
        Node<T> currentValue;
        if ((size / 2) > index) {
            currentValue = head;
            for (int i = 0; i < index; i++) {
                currentValue = currentValue.next;
            }
        } else {
            currentValue = tail;
            for (int i = size - 1; i > index; i--) {
                currentValue = currentValue.prev;
            }
        }
        return currentValue;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            node.next = tail;
            tail.next = null;
            tail.prev = null;
            tail.item = null;
            head = tail;
        }
        if (node.next == null) {
            if (node.prev != null) {
                node.prev.next = null;
            }
            tail = node.prev;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private boolean areElementsEqual(T element1, T element2) {
        return element1 == element2
                || element1 != null && element1.equals(element2);
    }

    private void indexCheckForGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index " + index + " out of scope");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.next = next;
            this.item = item;
            this.prev = prev;
        }
    }
}
