package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    private class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void checkIndex(int index, boolean isAdd) {
        if (index < 0 || index > size || (!isAdd && index == size)) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index, false);

        if (index < (size / 2)) {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            first = last = new Node<>(null, value, null);
        } else {
            last.next = new Node<>(last, value, null);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, true);
        if (index == size) {
            add(value);
            return;
        }

        Node<T> nextNode = getNode(index);
        Node<T> prevNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);

        if (prevNode != null) {
            prevNode.next = newNode;
        } else {
            first = newNode;
        }
        nextNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T l : list) {
            add(l);
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

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;

        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
        }

        // Очищаем ссылки у удаляемого узла
        node.prev = null;
        node.next = null;
        size--;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        T removedValue = nodeToRemove.value;
        unlink(nodeToRemove);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = first; current != null; current = current.next) {
            if ((object == null && current.value == null) ||
                    (object != null && object.equals(current.value))) {
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
}
