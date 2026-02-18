package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (size == 0 || index == size) {
            add(value);
            return;
        }
        Node<T> oldNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(null, value, oldNode);
        if (index != 0) {
            newNode.prev = oldNode.prev;
            oldNode.prev.next = newNode;
        } else {
            first = newNode;
        }
        oldNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException();
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldValue = findNodeByIndex(index);
        T oldItem = oldValue.item;
        oldValue.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> current = findNodeByIndex(index);
        T oldItem = current.item;
        unlink(current);
        size--;
        return oldItem;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> cur = first; cur != null; cur = cur.next) {
            if (object == null ? cur.item == null : object.equals(cur.item)) {
                unlink(cur);
                size--;
                return true;
            }
        } return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void unlink(Node<T> current) {
        if (current.prev != null) {
            current.prev.next = current.next;
        } else  {
            first = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            last = current.prev;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current = first;

        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }

        current = last;

        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }

        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;
        Node(Node<T> prev, T element, Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.item = element;
        }
    }
}
