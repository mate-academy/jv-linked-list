package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index has not been found");
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            nodeByIndex.prev.next = newNode;
            nodeByIndex.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> actualNode = getNodeByIndex(index);
        T oldValue = actualNode.item;
        actualNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> actualNode;
        for (actualNode = first; actualNode != null; actualNode = actualNode.next) {
            if (actualNode.item == object || object != null && object.equals(actualNode.item)) {
                unlink(actualNode);
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

    private boolean isIndexValid(int index) {
        return index <= size && index >= 0;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index == size || !isIndexValid(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> actualNode;
        int i;
        if (index < size / 2) {
            actualNode = first;
            for (i = 0; i < index; i++) {
                actualNode = actualNode.next;
            }
            return actualNode;
        }
        actualNode = last;
        for (i = size - 1; i > index; --i) {
            actualNode = actualNode.prev;
        }
        return actualNode;
    }

    private T unlink(Node<T> actualNode) {
        Node<T> nextNode = actualNode.next;
        Node<T> prevNode = actualNode.prev;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            actualNode.prev = null;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            actualNode.next = null;
        }
        T element = actualNode.item;
        actualNode.item = null;
        size--;
        return element;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
