package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<T>(tail, value, null);
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
        checkIndex(index);
        final Node<T> temporary = new Node<>(null, value, null);
        if (index == 0) {
            temporary.next = head;
            head.prev = temporary;
            head = temporary;
            size++;
            return;
        }
        Node<T> current = getByIndex(index);
        temporary.prev = current.prev;
        temporary.next = current;
        current.prev.next = temporary;
        current.prev = temporary;
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
        return getByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> element = getByIndex(index);
        T oldElement = element.value;
        element.value = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        Node<T> elementToRemove = getByIndex(index);
        removeLink(elementToRemove);
        return elementToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == current.value || (object != null && object.equals(current.value))) {
                removeLink(current);
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

    public Node<T> getByIndex(int index) {
        checkIndex(index);
        Node<T> current;
        if (size / 2 > index) {
            current = head;
            for (int i = 0;i < index;i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size; i > (index + 1);i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private T removeLink(Node<T> node) {
        if (node != head) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node != tail) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
        return node.value;
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound" + index);
        }
    }

    private class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
