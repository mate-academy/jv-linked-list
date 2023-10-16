package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INVALID_INDEX = "The provided index is invalid: ";
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndexForAdd(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0 || index == size) {
            add(value);
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> preAddingNode = getNodeByIndex(index - 1);
            newNode.prev = preAddingNode;
            newNode.next = preAddingNode.next;
            preAddingNode.next = newNode;
            newNode.next.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeAtIndex = getNodeByIndex(index);
        T oldValue = nodeAtIndex.item;
        nodeAtIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndexForGetAndRemove(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        int index = getIndexByObject(object);
        if (index != -1) {
            unlink(getNodeByIndex(index));
            return true;
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

    private void validateIndexForGetAndRemove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(INVALID_INDEX + index);
        }
    }

    private void validateIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(INVALID_INDEX + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        validateIndexForGetAndRemove(index);
        Node<T> current;
        if (size / 2 > index) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size; i > index + 1; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private T unlink(Node<T> x) {
        final T element = x.item;
        Node<T> next = x.next;
        Node<T> prev = x.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        return element;
    }

    private int getIndexByObject(T object) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if ((current.item == object) || (current.item != null && current.item.equals(object))) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
