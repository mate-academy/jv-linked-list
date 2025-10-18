package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = tail = newNode;
        } else {
            Node<T> oldTail = tail;
            oldTail.next = newNode;
            newNode.prev = oldTail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        checkIndexForAdd(index);
        if (size == 0) {
            head = tail = newNode;
        } else if (index == size) {
            Node<T> oldTail = tail;
            oldTail.next = newNode;
            newNode.prev = oldTail;
            tail = newNode;
        } else if (index == 0) {
            newNode.prev = null;
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> succ = findNodeByIndex(index);
            Node<T> pred = succ.prev;
            pred.next = newNode;
            newNode.prev = pred;
            newNode.next = succ;
            succ.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                add(list.get(i));
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> succ = findNodeByIndex(index);
        return succ.element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> succ;
        succ = findNodeByIndex(index);
        T oldValue = succ.element;
        succ.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> succ = findNodeByIndex(index);
        return unlink(succ);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.element == null)
                    || (object != null && object.equals(current.element))) {
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
        return size == 0;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index <= size / 2) {
            Node<T> succ = head;
            for (int i = 0; i < index; i++) {
                succ = succ.next;
            }
            return succ;
        } else {
            Node<T> succ = tail;
            for (int i = size - 1; i > index; i--) {
                succ = succ.prev;
            }
            return succ;
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds for add: " + index);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private T unlink(Node<T> node) {
        final T oldValue = node.element;
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        node.next = null;
        node.prev = null;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        size--;
        return oldValue;
    }

    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
