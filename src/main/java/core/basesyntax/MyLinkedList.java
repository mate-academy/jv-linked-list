package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || size < index) {
            throw new IndexOutOfBoundsException("The index " + index + " is invalid "
                    + "the actual size of array is " + size);
        }
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            last = newNode;
        } else if (index > 0 && size > index) {
            Node<T> current = first;
            int c = 0;
            while (current != null && c != index) {
                current = current.next;
                c++;
            }
            current.prev.next = newNode;
            newNode.prev = current.prev;
            current.prev = newNode;
            newNode.next = current;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        Node<T> newNode;
        for (T t : list) {
            newNode = new Node<>(t);
            add(newNode.item);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> originalNode = getNodeByIndex(index);
        T oldValue = originalNode.item;
        originalNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedElement;
        if (index == 0) {
            removedElement = first.item;
            first = first.next;
            if (first == null) {
                last = null;
            }
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            removedElement = prev.next.item;
            prev.next = prev.next.next;
            if (index == size - 1) {
                last = prev;
            }
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        int index = getIndex(object);
        if (index == -1) {
            return false;
        } else {
            remove(index);
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void linkLast(T e) {
        final Node<T> lastNode = last;
        final Node<T> newNode = new Node<>(lastNode, e, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index " + index + " is invalid "
                    + "the actual size of array is " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private int getIndex(T object) {
        Node<T> current = first;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (current.item == object || current.item != null && current.item.equals(object)) {
                index = i;
                break;
            }
            current = current.next;
        }
        return index;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        public Node(T value) {
            this.item = value;
        }
    }
}
