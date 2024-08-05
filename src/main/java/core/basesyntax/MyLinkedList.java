package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateAdditionIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            newNode.next.prev = newNode;
            head = newNode;
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> previousToNew = findNodeByIndex(index - 1);
            newNode.next = previousToNew.next;
            newNode.prev = previousToNew;
            previousToNew.next = newNode;
            newNode.next.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> needed = findNodeByIndex(index);
        T old = needed.item;
        needed.item = value;
        return old;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> removed = findNodeByIndex(index);
        if (removed == head) {
            head = head.next;
        } else if (removed == tail) {
            tail = tail.prev;
        } else {
            removed.prev.next = removed.next;
            removed.next.prev = removed.prev;
        }
        size--;
        return removed.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removed = findNodeByElement(object);
        if (removed == null) {
            return false;
        } else if (removed == head) {
            head = head.next;
        } else if (removed == tail) {
            tail = tail.prev;
        } else {
            removed.prev.next = removed.next;
            removed.next.prev = removed.prev;
        }
        size--;
        return true;
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
        validateIndex(index);
        Node<T> current = head;
        if (index == 0) {
            return current;
        } else if (index == size - 1) {
            return tail;
        } else {
            int counter = 0;
            while (counter < index) {
                current = current.next;
                counter++;
            }
        }
        return current;
    }

    private Node<T> findNodeByElement(T element) {
        Node<T> current = head;
        while (!areEqual(element, current.item) && current.next != null) {
            current = current.next;
        }
        if (areEqual(current.item, element)) {
            return current;
        }
        return null;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't reach element with index "
                    + index + " in a list with size of " + size + " .");
        }
    }

    private void validateAdditionIndex(int index) {
        if (index < 0 || index > size + 1) {
            throw new IndexOutOfBoundsException("Can't add element to position "
                    + index + " in a list with size of " + size + " .");
        }
    }

    private boolean areEqual(Object object1, Object object2) {
        if (object1 == null || object2 == null) {
            return object1 == object2;
        }
        return object1.equals(object2);
    }
}
