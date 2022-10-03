package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        final Node<T> oldValue = last;
        last = new Node<>(oldValue, value, null);
        if (oldValue == null) {
            first = last;
        } else {
            oldValue.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToUnlink = getNode(index);
        return unlink(nodeToUnlink);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (object == node.value || object != null && object.equals(node.value)) {
                unlink(node);
                return true;
            }
            node = node.next;
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
        private Node<T> prev;
        private T value;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Illegal index" + index);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (index < (size >> 1)) {
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

    private void linkBefore(T value, int index) {
        if (index == 0) {
            final Node<T> link = first;
            first = new Node<>(null, value, link);
            if (link == null) {
                last = first;
            } else {
                link.prev = first;
            }
            size++;
        } else {
            Node<T> nextNode = getNode(index);
            final Node<T> newNode = new Node<>(nextNode.prev, value, nextNode);
            nextNode.prev = newNode;
            newNode.prev.next = newNode;
            size++;
        }
    }

    private T unlink(Node<T> node) {
        if (node == first) { // unlink first
            if (node.next != null) {
                first = first.next;
                first.prev = null;
            } else {
                first = null;
                last = null;
            }
            size--;
        } else if (node == last) { // unlink last
            last = last.prev;
            last.next = null;
            size--;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        return node.value;
    }

}
