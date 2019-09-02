package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node(null, value, null);
            last = first;
        } else {
            Node<T> last1 = new Node(last, value, null);
            last.next = last1;
            last = last1;
        }
        size++;
    }


    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> node = getNode(index);
            Node<T> anotherNode = new Node(node.prev, value, node);
            node.prev = anotherNode;
            if (size == 0) {
                first = anotherNode;
            } else {
                node = anotherNode;
            }
            size++;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).value;
    }

    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> change = getNode(index);
        change.value = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> removedNode = getNode(index);
        Node<T> next = removedNode.next;
        Node<T> prev = removedNode.prev;
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
        T element = removedNode.value;
        size--;
        return element;
    }

    @Override
    public T remove(T t) {
        int counter = 0;
        for (Node node1 = first; node1 != null; node1 = node1.next) {
            if (node1.value == null || t.equals(node1.value)) {
                return remove(counter);
            }
            counter++;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        Node<T> toGet;
        if (index < size / 2) {
            toGet = first;
            for (int i = 0; i < index; i++) {
                toGet = toGet.next;
            }
            return toGet;
        } else {
            toGet = last;
            for (int i = size - 1; i > index; i--) {
                toGet = toGet.prev;
            }
            return toGet;
        }
    }
}