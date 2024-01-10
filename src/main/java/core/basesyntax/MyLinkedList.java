package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        final Node<T> lastNode = last;
        final Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index " + index);
        }
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, findNode(index));
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
        checkElementIndex(index);
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> changedNode = findNode(index);
        T oldValue = changedNode.value;
        changedNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current;
        for (current = first; current != null; current = current.next) {
            if (current.value == object || object != null && object.equals(current.value)) {
                unlink(current);
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
        return size == 0;
    }

    private void linkBefore(T value, Node<T> nextNode) {
        Node<T> previousNode = nextNode.prev;
        Node<T> newNode = new Node<>(previousNode, value, nextNode);
        nextNode.prev = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Wrong index " + index);
        }
    }

    private Node<T> findNode(int index) {
        Node<T> current;
        if (index < (size >> 1)) {
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

    private T unlink(Node<T> intakeNode) {
        Node<T> next = intakeNode.next;
        Node<T> previous = intakeNode.prev;

        if (next == null && previous == null) {
            last = null;
            first = null;
        } else if (previous == null) {
            first = next;
            next.prev = null;
        } else if (next == null) {
            last = previous;
            previous.next = null;
        } else {
            previous.next = next;
            next.prev = previous;
        }
        size--;
        return intakeNode.value;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
