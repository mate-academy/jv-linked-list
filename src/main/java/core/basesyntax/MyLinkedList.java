package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
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
        Node<T> newNode = new Node<>(value, null, null);
        checkIndexStrict(index);
        if (first == null || index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            newNode.next = first;
            first.previous = newNode;
            first = newNode;
        } else {
            Node<T> previousElement = findNodeByIndex(index - 1);
            newNode.next = previousElement.next;
            newNode.previous = previousElement;
            previousElement.next = newNode;
            newNode.next.previous = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> setNewNode = findNodeByIndex(index);
        T item = setNewNode.element;
        setNewNode.element = value;
        return item;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedElement = findNodeByIndex(index);
        unlink(removedElement);
        return removedElement.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (current.element == object || object != null && object.equals(current.element)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndexStrict(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds of the List: ");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds of the List: ");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current;
        int side = (size / 2);
        if (index < side) {
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

    private void unlink(Node node) {
        if (first == null) {
            first = last = node;
        } else if (node == first) {
            first = first.next;
        } else if (node == last) {
            last = last.previous;
            last.next = null;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        size--;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        Node(T element, Node<T> next, Node<T> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }
}
