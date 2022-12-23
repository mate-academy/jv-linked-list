package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(last, value, null);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
            node.prev = last;
        }
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> replacedNode = getNodeByIndex(index);
        Node<T> node = new Node<>(replacedNode.prev, value, replacedNode);
        if (replacedNode.prev == null) {
            first = node;
        } else {
            replacedNode.prev.next = node;
        }
        replacedNode.prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> replasedNode = getNodeByIndex(index);
        T removedItem = replasedNode.value;
        replasedNode.value = value;
        return removedItem;
    }

    @Override
    public T remove(int index) {
        T node = getNodeByIndex(index).value;
        unlink(getNodeByIndex(index));
        return node;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if ((node.value != null && node.value.equals(object))
                    || node.value == object) {
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

    private void unlink(Node<T> element) {
        if (element == first) {
            first = element.next;
        } else if (element == last) {
            last = element.prev;
        } else {
            element.prev.next = element.next;
            element.next.prev = element.prev;
        }
        element.value = null;
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index "
                    + index + " is not valid for size " + size + ".");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index <= (size / 2)) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
