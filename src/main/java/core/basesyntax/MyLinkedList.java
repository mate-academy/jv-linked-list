package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
        } else {
            checkIndex(index);
            Node<T> targetNode = getNode(index);
            Node<T> node = new Node<>(targetNode.prev, value, targetNode);
            targetNode.prev.next = node;
            targetNode.prev = node;
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
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> targetNode = getNode(index);
        T returnElement = targetNode.value;
        targetNode.value = value;
        return returnElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> targetNode = getNode(index);
        return unlink(targetNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node;
        for (node = first; node != null; node = node.next) {
            if ((object != null && object.equals(node.value)) || node.value == object) {
                unlink(node);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> item) {
        Node<T> next = item.next;
        Node<T> prev = item.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            item.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            item.next = null;
        }
        T value = item.value;
        item.value = null;
        size--;
        return value;
    }
}
