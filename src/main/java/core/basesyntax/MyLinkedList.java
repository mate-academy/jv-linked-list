package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        Node<T> current;
        Node<T> newNode = new Node<>(value);

        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (size == 0) {
                tail = newNode;
            }
            size++;
            return;
        }

        if (index == size) {
            add(value);
            return;
        }

        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            newNode.prev = current;
            if (current.next != null) {
                current.next.prev = newNode;
            }
            current.next = newNode;
        } else if (index > size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            newNode.prev = current.prev;
            newNode.next = current;
            if (current.prev != null) {
                current.prev.next = newNode;
            }
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T each : list) {
            add(each);
        }
    }

    @Override
    public T get(int index) {
        checkIndexForGetSetRemove(index);
        return fastIndexFinder(index).data;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForGetSetRemove(index);
        Node<T> current = fastIndexFinder(index);
        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForGetSetRemove(index);
        T oldValue;
        Node<T> current = fastIndexFinder(index);
        oldValue = current.data;

        if (current == head) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (current == tail) {
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            if (current.prev != null) {
                current.prev.next = current.next;
            }
            if (current.next != null) {
                current.next.prev = current.prev;
            }
        }

        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if ((current.data != null && current.data.equals(object))
                    || (current.data == null && object == null)) {
                if (current == head) {
                    head = head.next;
                    if (head != null) {
                        head.prev = null;
                    }
                } else if (current == tail) {
                    tail = tail.prev;
                    if (tail != null) {
                        tail.next = null;
                    }
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                size--;
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
        return size == 0;
    }

    private void checkIndexForGetSetRemove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " is incorrect index");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + " is incorrect index");
        }
    }

    private Node<T> fastIndexFinder(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T data;

        public Node(T data) {
            this.prev = null;
            this.data = data;
            this.next = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node<?> node = (Node<?>) o;
            return Objects.equals(prev, node.prev) && Objects.equals(next, node.next)
                    && Objects.equals(data, node.data);
        }
    }
}
