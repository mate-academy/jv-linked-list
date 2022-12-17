package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        final Node<T> temp = tail;
        final Node<T> newNode = new Node<>(temp, value, null);
        tail = newNode;
        if (temp == null) {
            head = newNode;
        } else {
            temp.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            addBefore(value, searchNode(index));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addAll(List<T> list) {
        Object[] items = list.toArray();
        int numNew = items.length;
        if (numNew == 0) {
            return;
        }
        Node<T> beforeCurrent;
        beforeCurrent = tail;
        for (Object item : items) {
            Node<T> newNode = new Node<>(beforeCurrent, (T) item, null);
            if (beforeCurrent == null) {
                head = newNode;
            } else {
                beforeCurrent.next = newNode;
            }
            beforeCurrent = newNode;
        }
        tail = beforeCurrent;
        size += numNew;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return searchNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> current = searchNode(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(searchNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (object == null && current.item == null) {
                unlink(current);
                return true;
            } else if (object != null && object.equals(current.item)) {
                unlink(current);
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

    private static class Node<T> {
        private T item;
        private Node<T> next;

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private Node<T> searchNode(int index) {
        checkPositionIndex(index);
        Node<T> temp;
        if (index < size / 2) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }
        return temp;
    }

    private void addBefore(T value, Node<T> current) {
        final Node<T> beforeCurrent = current.prev;
        final Node<T> newNode = new Node<>(beforeCurrent, value, current);
        current.prev = newNode;
        if (beforeCurrent == null) {
            head = newNode;
        } else {
            beforeCurrent.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;

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

        node.item = null;
        node.prev = null;
        node.next = null;
        size--;
        return element;
    }
}
