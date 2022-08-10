package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Huston we have a BAG! Index it not valid: "
                    + index);
        }
        if (size == index) {
            add(value);
        } else {
            addBefore(value, getNodeByIndex(index));
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
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> x = getNodeByIndex(index);
        T oldValue = x.item;
        x.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        unlink(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = first; i != null; i = i.next) {
            if (i.item == null && object == null) {
                unlink(i);
                return true;
            } else if (object != null && object.equals(i.item)) {
                unlink(i);
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

    private void addBefore(T value, Node<T> current) {
        Node<T> previous = current.prev;
        Node<T> newNode = new Node<>(previous, value, current);
        current.prev = newNode;
        if (previous == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = first;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Huston we have a BAG! Index it not valid: "
                    + index);
        }
    }
}

