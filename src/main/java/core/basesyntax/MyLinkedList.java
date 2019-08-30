package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    Node<T> first;
    Node<T> last;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> back = last;
        last = new Node<>(back, value, null);
        if (back == null) {
            first = last;
        } else {
            back.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> forward = getObjectByIndex(index);
            Node<T> back = forward.previous;
            Node<T> object = new Node<>(back, value, forward);
            forward.previous = back;
            if (back == null) {
                first = back;
            } else {
                back.next = object;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getObjectByIndex(index).current;
    }

    @Override
    public void set(T value, int index) {
        getObjectByIndex(index).current = value;
    }

    @Override
    public T remove(int index) {
        return removeObject(getObjectByIndex(index));
    }

    @Override
    public T remove(T value) {
        if (value == null) {
            for (Node<T> object = first; object != null; object = object.next) {
                if (object.current == null) {
                    return removeObject(object);
                }
            }
        } else {
            for (Node<T> object = first; object != null; object = object.next) {
                if (object.current.equals(value)) {
                    return removeObject(object);
                }
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

    private Node<T> getObjectByIndex(int index) {
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
