package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    private void linkLast(T value) {
        Node<T> current = last;
        Node<T> newNode = new Node<>(current, value, null);
        last = newNode;
        if (first == null) {
            first = newNode;
        } else {
            current.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> current) {
        Node<T> prev = current.prev;
        Node<T> newNode = new Node<>(prev, value, current);
        current.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private boolean isValidPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkValidPositionIndex(int index) {
        if (!isValidPositionIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkValidPositionIndex(index);

        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, findNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            linkLast(t);
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkValidIndex(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> findNode(int index) {
        checkValidIndex(index);

        int i;
        Node<T> current;
        if (index < size / 2) {
            i = 0;
            current = first;

            while (i != index) {
                current = current.next;
                i++;
            }
        } else {
            i = size - 1;
            current = last;
            while (i != index) {
                current = current.prev;
                i--;
            }
        }

        return current;
    }

    @Override
    public T get(int index) {
        checkValidIndex(index);

        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkValidIndex(index);
        Node<T> node = findNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    private T unlink(Node<T> node) {
        final T value = node.item;
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
        node.item = null;

        return value;
    }

    @Override
    public T remove(int index) {
        checkValidIndex(index);

        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> current = first; current != null; current = current.next) {
                if (current.item == null) {
                    unlink(current);
                    return true;
                }
            }
        } else {
            for (Node<T> current = first; current != null; current = current.next) {
                if (current.item.equals(object)) {
                    unlink(current);
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

    public static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

    }

}
