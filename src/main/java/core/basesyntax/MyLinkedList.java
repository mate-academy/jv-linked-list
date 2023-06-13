package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        Node(T element) {
            this.element = element;
        }
    }

    @Override
    public void add(T value) {
        if (first == null) {
            addToEmptyList(value);
        } else {
            addLastNode(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index, size);
        if (first == null) {
            addToEmptyList(value);
        }
        if (index == 0) {
            addToFirstIndex(value);
        } else {
            addToMiddleIndex(value, index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> removedElement = getNodeByIndex(index);
        unlink(removedElement);
        size--;
        return removedElement.element;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if ((object != null && get(i) != null && get(i).equals(object))
                    || (object == null && get(i) == null)) {
                Node<T> removedNode = getNodeByIndex(i);
                unlink(removedNode);
                size--;
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
        return first == null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = first;
        if (index == 0) {
            return first;
        }
        if (index == size - 1) {
            return last;
        }
        if (index <= (size / 2)) {
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

    private int checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds: " + index);
        }
        return index;
    }

    private void addToEmptyList(T value) {
        Node<T> newNode = new Node<>(value);
        first = last = newNode;
    }

    private void addLastNode(T value) {
        Node<T> newNode = new Node<>(value);
        last.next = newNode;
        newNode.prev = last;
        last = newNode;
    }

    private void addToFirstIndex(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.next = first;
        first.prev = newNode;
        first = newNode;
    }

    private void addToMiddleIndex(T value, int index) {
        Node<T> newNode = new Node<>(value);
        Node<T> current = getNodeByIndex(index);
        Node<T> prev = current.prev;
        newNode.next = current;
        newNode.prev = prev;
        prev.next = newNode;
        current.prev = newNode;
    }

    public void unlink(Node<T> node) {
        if (first == null || node == null) {
            return;
        }
        if (node == first) {
            first = first.next;
            return;
        }
        Node<T> prevNode = first;
        while (prevNode != null && prevNode.next != node) {
            prevNode = prevNode.next;
        }
        if (prevNode != null) {
            prevNode.next = node.next;
        }
    }
}
