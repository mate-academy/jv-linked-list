package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    public MyLinkedList() {
        setFirst(null);
        setLast(null);
    }

    public Node<T> getFirst() {
        return first;
    }

    public void setFirst(Node<T> first) {
        this.first = first;
    }

    public Node<T> getLast() {
        return last;
    }

    public void setLast(Node<T> last) {
        this.last = last;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            setFirst(newNode);
        } else {
            Node<T> current = first;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
            newNode.previous = current;
        }
        setLast(newNode);
        grow();
    }

    @Override
    public void add(T value, int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = getFirst();
            setFirst(newNode);
            if (size() == 0) {
                setLast(getFirst());
            }
        } else if (index == size()) {
            Node<T> current = getLast();
            current.next = newNode;
            current = current.next;
            current.previous = getLast();
            setLast(current);
        } else {
            Node<T> current = getFirst();
            while (index != 0) {
                current = current.next;
                index--;
            }
            newNode.previous = current.previous;
            newNode.next = current;
            current.previous.next = newNode;
            current.previous = newNode;
        }
        grow();
    }

    public void grow() {
        size = size() + 1;
    }

    public void shrink() {
        size = size() - 1;
    }

    @Override
    public void addAll(List<T> list) {
        Node<T> current = getLast();
        for (T t : list) {
            current.next = new Node<>(t);
            current.next.previous = current;
            current = current.next;
            grow();
        }
    }

    @Override
    public T get(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (index == 0) {
            return getFirst().data;
        }
        Node<T> current = getFirst();

        for (int i = 1; i <= index; i++) {
            current = current.next;;
        }
        return current.data;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        Node<T> current = getFirst();
        while (index != 0) {
            current = current.next;
            index--;
        }
        T old = current.data;
        current.data = value;

        return old;
    }

    @Override
    public T remove(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (index == 0) {
            T removed = getFirst().data;
            removeFirst();
            shrink();
            return removed;
        } else if (index == size() - 1) {
            T removed = getLast().data;
            removeLast();
            shrink();
            return removed;
        }
        Node<T> current = getFirst();
        for (int i = 1; i <= index; i++) {
            current = current.next;
        }
        unlink(current);
        return current.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = getFirst();
        for (int i = 0; i < size(); i++) {
            if (current.equals(object)) {
                if (i == 0) {
                    removeFirst();
                    shrink();
                    return true;
                }
                if (i == size() - 1) {
                    removeLast();
                    shrink();
                    return true;
                }
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public void unlink(Node<T> node) {
        node.next.previous = node.previous;
        node.previous.next = node.next;
        shrink();
    }

    private void removeLast() {
        Node<T> current = getLast().previous;
        current.next = null;
        setLast(current);
    }

    private void removeFirst() {
        if (size() == 1) {
            setFirst(null);
        } else {
            Node<T> current = getFirst().next;
            current.previous = null;
            setFirst(current);
        }
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> previous;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }

        @Override
        public boolean equals(Object value) {
            if (this.data == null && value == null) {
                return true;
            }
            if (this.data == null || value == null) {
                return false;
            }
            return this.data == value || this.data.equals(value);
        }
    }
}
