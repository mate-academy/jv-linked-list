package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index. "
                + "Index is out of bounds of array");
        }
    }

    private void checkIndexWithEquals(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index. "
                + "Index is out of bounds of array");
        }
    }

    private Node<T> getElementByIndex(int index) {
        checkIndex(index);

        if (index < (size >> 1)) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    @Override
    public void add(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> current = getElementByIndex(index);
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

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndexWithEquals(index);

        if (index < (size >> 1)) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x.value;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x.value;
        }
    }

    @Override
    public T set(T value, int index) {
        checkIndexWithEquals(index);

        Node<T> current = getElementByIndex(index);
        Node<T> prev = current.prev;
        Node<T> next = current.next;
        Node<T> newNode = new Node<>(prev, value, next);
        T oldValue = current.value;
        current = null;

        if (prev == null) {
            first = newNode;
        } else if (next == null) {
            last = newNode;
        } else {
            prev.next = newNode;
            next.prev = newNode;
        }

        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexWithEquals(index);

        Node<T> current = getElementByIndex(index);
        Node<T> next = current.next;
        Node<T> prev = current.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            current.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            current.next = null;
        }
        T oldValue = current.value;
        current = null;
        size--;

        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        int k = 0;
        if (object == null) {
            for (Node<T> x = first; x != null; x = x.next) {
                if (x.value == null) {
                    remove(k);
                    return true;
                }
                k++;
            }
        } else {
            for (Node<T> x = first; x != null; x = x.next) {
                if (object.equals(x.value)) {
                    remove(k);
                    return true;
                }
                k++;
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
}
