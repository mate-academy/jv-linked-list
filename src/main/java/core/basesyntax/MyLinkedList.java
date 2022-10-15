package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            newNode.next = first;
            first.previous = newNode;
            first = newNode;
        } else {
            Node<T> preceding = getNodeByIndex(index - 1);
            newNode.next = preceding.next;
            newNode.next.previous = newNode;
            newNode.previous = preceding;
            preceding.next = newNode;
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
        checkIndex(index, size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> current = getNodeByIndex(index);
        T removedValue = current.value;
        unlink(current);
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = getNodeByValue(object);
        if (current != null) {
            unlink(current);
            size--;
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
        return first == null;
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index " + index);
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
                current = current.previous;
            }
        }
        return current;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.value != null && current.value.equals(value) || current.value == value) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (node == first) {
            first = first.next;
            if (first == null) {
                last = null;
            }
        } else {
            Node<T> preceding = node.previous;
            preceding.next = node.next;
            if (node.next == null) {
                last = preceding;
            } else {
                node.next.previous = preceding;
            }
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value) {
            this.value = value;
        }
    }
}
