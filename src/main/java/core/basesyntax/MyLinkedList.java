package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexPosition(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
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
        checkElementPosition(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementPosition(index);
        Node<T> replaceNode = getNodeByIndex(index);
        T oldValue = replaceNode.value;
        replaceNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementPosition(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if ((object != null && object.equals(node.value))
                    || (object == null && node.value == object)) {
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
        return head == null;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < (size / 2)) {
            Node<T> requiredNode = head;
            for (int i = 0; i < index; i++) {
                requiredNode = requiredNode.next;
            }
            return requiredNode;
        } else {
            Node<T> requiredNode = tail;
            for (int i = size - 1; i > index; i--) {
                requiredNode = requiredNode.prev;
            }
            return requiredNode;
        }
    }

    private boolean isElementValid(int index) {
        return (index >= 0 && index < size);
    }

    private void checkElementPosition(int index) {
        if (!isElementValid(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds " + size);
        }
    }

    private boolean isIndexValid(int index) {
        return (index >= 0 && index <= size);
    }

    private void checkIndexPosition(int index) {
        if (!isIndexValid(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds " + size);
        }
    }

    private void linkLast(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> nextNode) {
        final Node<T> prevNode = nextNode.prev;
        final Node<T> newNode = new Node<>(prevNode, value, nextNode);
        nextNode.prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        final T value = node.value;
        final Node<T> prevNode = node.prev;
        final Node<T> nextNode = node.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        node.value = null;
        size--;
        return value;
    }
}
