package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
        first = null;
        last = null;
    }

    @Override
    public boolean add(T value) {
        Node<T> l = last;
        last = new Node<>(l, value, null);
        if (l == null) {
            first = last;
        } else {
            l.next = last;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> next = getNode(index);
            Node<T> newNode = new Node<>(null, value, next);
            if (next.prev == null) {
                first = newNode;
            } else {
                next.prev.next = newNode;
            }
            next.prev = newNode;
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T n : list) {
            add(n);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> changed = getNode(index);
        T old = changed.item;
        changed.item = value;
        return old;
    }

    @Override
    public T remove(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
        }
        Node<T> removable;
        removable = getNode(index);
        if (removable.prev == null) {
            first = removable.next;
        } else {
            removable.prev.next = removable.next;
        }
        if (removable.next == null) {
            last = removable.prev;
        } else {
            removable.next.prev = removable.prev;
        }
        removable.prev = null;
        removable.next = null;
        size--;
        return removable.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> currentNode = first;
        int i = 0;
        while (!((currentNode.item == t) || (currentNode != null && currentNode.item.equals(t)))) {
            if (i == size - 1) {
                return false;
            }
            currentNode = currentNode.next;
            i++;
        }
        remove(i);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public void indexExtends(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
        }
    }

    public Node<T> getNode(int index) {
        indexExtends(index);
        int current;
        Node<T> currentNode = first;
        if (index < size / 2) {
            current = 0;
            while (current != index) {
                currentNode = currentNode.next;
                current++;
            }
        } else {
            currentNode = last;
            current = size - 1;
            while (current != index) {
                currentNode = currentNode.prev;
                current--;
            }
        }
        return currentNode;
    }

}
