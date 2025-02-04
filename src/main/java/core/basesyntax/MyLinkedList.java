package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        } else {
            checkIndex(index);
            Node<T> nextNode = getNode(index);
            Node<T> prevNode = nextNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, nextNode);
            nextNode.prev = newNode;
            if (prevNode == null) {
                first = newNode;
            } else {
                prevNode.next = newNode;
            }

            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> target = getNode(index);
        T oldValue = target.element;
        target.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = first; current != null; current = current.next) {
            if ((object == null && current.element == null)
                    || (object != null && object.equals(current.element))) {
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
        return (size == 0);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current;
        current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private T unlink(Node<T> target) {
        T element = target.element;

        Node<T> prevNode = target.prev;
        Node<T> nextNode = target.next;

        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            target.prev = null;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            target.next = null;
        }

        target.element = null;
        size--;
        return element;
    }
}
