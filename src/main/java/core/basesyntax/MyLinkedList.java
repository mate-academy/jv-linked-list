package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            first = last = newNode;
        } else {
            addLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionByElement(index);
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            addFirst(value);
        } else {
            addEach(value, index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T values : list) {
            add(values);
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
        Node<T> newNode = getNodeByIndex(index);
        T previous = newNode.value;
        newNode.value = value;
        return previous;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentElement = first;
        while (currentElement != null) {
            if (currentElement.value == object
                    || currentElement.value != null && currentElement.value.equals(object)) {
                unlink(currentElement);
                return true;
            }
            currentElement = currentElement.next;
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

    public void addFirst(T element) {
        Node<T> newNode = new Node<>(null, element, first);
        first.prev = newNode;
        first = newNode;
    }

    private void addLast(T element) {
        Node<T> newNode = new Node<>(last, element, null);
        last.next = newNode;
        last = newNode;
    }

    private void addEach(T value, int index) {
        Node<T> prev = getNodeByIndex(index - 1);
        Node<T> node = new Node<>(prev, value, prev.next);
        prev.next.prev = node;
        prev.next = node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Non-valid index" + index);
        }
    }

    private void checkPositionByElement(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't add value by index" + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node == first) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == last) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
