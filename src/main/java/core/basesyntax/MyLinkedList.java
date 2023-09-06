package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        first = null;
        last = null;
    }

    @Override
    public void add(T value) {
        if (first != null) {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            last = newNode;
            first = newNode;
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
        if (index == 0) {
            addFirst(value);
        } else if (index < size - 1) {
            addInside(value, index);
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = get(index);
        getNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        final T oldValue = get(index);
        if (index == 0) {
            removeFirst(index);
        } else if (index == size - 1) {
            removeLast(index);
        } else {
            removeInside(index);
        }
        size--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        int index = searchElement(object);
        if (index != -1) {
            remove(index);
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index less then 0");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("index more then size");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void addFirst(T value) {
        Node<T> node = new Node<>(null, value, getNode(1));
        getNode(1).prev = node;
        first = node;
        size++;
    }

    private void addInside(T value, int index) {
        Node<T> previousNode = getNode(index - 1);
        Node<T> nextNode = getNode(index);
        Node<T> newNode = new Node<>(previousNode, value, nextNode);
        previousNode.next = newNode;
        nextNode.prev = newNode;

        size++;
    }

    private int searchElement(T object) {
        for (int i = 0; i < size; i++) {
            if (get(i) == null ? object == null : get(i).equals(object)) {
                return i;
            }
        }
        throw new NoSuchElementException("haven't this element");
    }

    private void removeFirst(int index) {
        getNode(index + 1).prev = null;
        first = getNode(index + 1);
    }

    private void removeLast(int index) {
        getNode(index - 1).next = null;
        last = getNode(index - 1);
    }

    private void removeInside(int index) {
        getNode(index - 1).next = getNode(index + 1);
        getNode(index + 1).prev = getNode(index - 1);
        getNode(index).prev = null;
        getNode(index).next = null;
    }
}
