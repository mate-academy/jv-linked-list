package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> object = new Node<>(last, value, null);
        if (last == null) {
            first = object;
        } else {
            last.next = object;
        }
        last = object;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> oldObject = getObject(index);
            Node<T> newObject = new Node<>(oldObject.previous, value, oldObject);
            if (oldObject.previous == null) {
                first = newObject;
            } else {
                oldObject.previous.next = newObject;
            }
            oldObject.previous = newObject;
            size++;
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
        return getObject(index).current;
    }

    @Override
    public void set(T value, int index) {
        getObject(index).current = value;
    }

    @Override
    public T remove(int index) {
        return removeObject(getObject(index));
    }

    @Override
    public T remove(T value) {
        Node<T> object = first;
        if (value == null) {
            while (object != null) {
                if (object.current == null) {
                    return removeObject(object);
                }
                object = object.next;
            }
        } else {
            while (object != null) {
                if (object.current.equals(value)) {
                    return removeObject(object);
                }
                object = object.next;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getObject(int index) {
        checkIndex(index);
        Node<T> object;
        if (index < size / 2) {
            object = first;
            for (int i = 0; i < index; i++) {
                object = object.next;
            }
        } else {
            object = last;
            for (int i = size - 1; i > index; i--) {
                object = object.previous;
            }
        }
        return object;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private T removeObject(Node<T> object) {
        if (object.previous == null) {
            first = object.next;
        } else {
            object.previous.next = object.next;
        }
        if (object.next == null) {
            last = object.previous;
        } else {
            object.next.previous = object.previous;
        }
        T value = object.current;
        object = null;
        size--;
        return value;
    }

    private static class Node<T> {
        Node<T> previous;
        T current;
        Node<T> next;

        Node(Node<T> previous, T current, Node<T> next) {
            this.previous = previous;
            this.current = current;
            this.next = next;
        }
    }
}
