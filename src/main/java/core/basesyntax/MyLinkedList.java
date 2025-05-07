package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index < 0");
        }
        if (index == size) {
            addTail(value);
        } else if (index == 0) {
            addHead(value);
        } else {
            Node<T> nodePerIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(value, nodePerIndex.prev, nodePerIndex);
            nodePerIndex.prev.next = newNode;
            nodePerIndex.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            addTail(value);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Exception");
        }
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Not valide index");
        }
        Node<T> nodePerIndex = getNodeByIndex(index);
        T oldValue = nodePerIndex.value;
        nodePerIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Not valide index");
        }
        Node<T> nodePerIndex = getNodeByIndex(index);
        T oldValue = nodePerIndex.value;
        unlink(nodePerIndex);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> result = first;
        while (result != null) {
            if (object == result.value || object != null && object.equals(result.value)) {
                unlink(result);
                return true;
            }
            result = result.next;
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

    private void addHead(T value) {
        Node<T> newNode = new Node<>(value, null, first);
        first.prev = newNode;
        first = newNode;
        size++;
    }

    private void addTail(T value) {
        if (first == null && last == null) {
            first = new Node<>(value, null, null);
            last = first;
        } else {
            Node<T> newNode = new Node<>(value, last, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            first = null;
        } else if (node.prev == null) {
            node.next.prev = null;
            first = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> result;
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Not valide index");
        }
        if (index < size / 2) {
            result = first;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = last;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }
}
