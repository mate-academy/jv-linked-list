package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            addLast(value);
        } else {
            addInMiddle(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item:list) {
            addLast(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = (T) node(index).value;
        node(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return delete(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> entity = head; entity != null; entity = entity.next) {
                if (entity.value == null) {
                    delete(entity);
                    return true;
                }
            }
        } else {
            for (Node<T> entity = head; entity != null; entity = entity.next) {
                if (object.equals(entity.value)) {
                    delete(entity);
                    return true;
                }
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + "Size: " + size);
        }
    }

    private Node<T> node(int index) {
        Node<T> search;
        if (index < (size >> 1)) {
            search = head;
            for (int i = 0; i < index; i++) {
                search = search.next;
            }
        } else {
            search = tail;
            for (int i = size - 1; i > index; i--) {
                search = search.prev;
            }
        }
        return search;
    }

    private T delete(Node<T> target) {
        Node<T> next = target.next;
        Node<T> prev = target.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return target.value;
    }

    private void addLast(T value) {
        Node<T> t = tail;
        Node<T> newNode = new Node<T>(t, value, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size++;
    }

    private void addInMiddle(T value, Node<T> onIndex) {
        Node<T> previous = onIndex.prev;
        Node<T> newNode = new Node<T>(previous, value, onIndex);
        onIndex.prev = newNode;
        if (previous == null) {
            head = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }
}
