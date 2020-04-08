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
        Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            Node<T> f = first;
            Node<T> newNode = new Node<>(null, value, f);
            first = newNode;
            if (f != null) {
                f.prev = newNode;
            } else {
                last = newNode;
            }
        } else if (index == size) {
            Node<T> l = last;
            Node<T> newNode = new Node<>(l, value, null);
            last = newNode;
            l.next = newNode;
        } else {
            Node<T> prevNode = getNode(index - 1);
            Node<T> nextNode = prevNode.next;
            Node<T> newNode = new Node<>(prevNode, value, nextNode);
            prevNode.next = newNode;
            nextNode.prev = newNode;
        }
        size++;
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
        while (!(Objects.equals(currentNode.item, t))) {
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

    public Node<T> getNode(int index) {
        indexExtends(index);
        int current = 0;
        Node<T> currentNode = first;
        while (current != index) {
            currentNode = currentNode.next;
            current++;
        }
        return currentNode;
    }

    public void indexExtends(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
        }
    }

}
