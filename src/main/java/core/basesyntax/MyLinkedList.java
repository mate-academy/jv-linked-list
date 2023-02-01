package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<T>(null, value, tail);
            tail = new Node<T>(head, value, null);
        }
        if (size == 1) {
            tail = new Node<>(head, value, null);
            head.next = tail;
        }
        if (size > 1) {
            Node<T> main = new Node<>(tail, value, null);
            tail.next = main;
            tail = main;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        checkIndexInBounds(index);
        addBeforeIndex(value, index);
        size++;
    }

    private void addBeforeIndex(T value, int index) {
        Node<T> node = getNode(index);
        Node<T> nodeValue = new Node<>(null, value, null);
        assert node != null;
        if (node.equals(head)) {
            nodeValue.next = node;
            node.prev = nodeValue;
            head = nodeValue;
            return;
        }
        nodeValue.next = node;
        nodeValue.prev = node.prev;
        node.prev.next = nodeValue;
        node.prev = nodeValue;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndexInBounds(index);
        Node<T> node = getNode(index);
        if (node != null) {
            return node.main;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        if (!isEmpty() || index < 0) {
            checkIndexInBounds(index);
        }
        Node<T> node = getNode(index);
        if (node != null) {
            T result = node.main;
            node.main = value;
            return result;
        }
        return value;
    }

    @Override
    public T remove(int index) {
        if (!isEmpty() || index < 0) {
            checkIndexInBounds(index);
        }
        Node<T> result = getNode(index);
        assert result != null;
        unlink(result);
        size--;
        return result.main;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        int curentIndex = 0;
        while (node != null) {
            if (isEquals(object, node.main)) {
                unlink(node);
                size--;
                return true;
            }
            node = node.next;
            curentIndex++;
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

    private Node<T> getNode(int index) {
        int currentIndex = 0;
        Node<T> current = head;
        while (current != null) {
            if (index == currentIndex) {
                return current;
            }
            current = current.next;
            currentIndex++;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    private void checkIndexInBounds(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("This index "
                    + index + " is not in size: " + size);
        }
    }

    private boolean isEquals(T firstObject, T secondObject) {
        return firstObject == secondObject || (firstObject != null && firstObject.equals(secondObject));
    }

    private class Node<T> {
        Node<T> prev;
        T main;
        Node<T> next;

        public Node(Node<T> prev, T main, Node<T> next) {
            this.prev = prev;
            this.main = main;
            this.next = next;
        }
    }
}
