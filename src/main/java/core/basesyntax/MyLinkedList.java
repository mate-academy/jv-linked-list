package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
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
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        } else {
            Node<T> current = getNodeByIndex(index - 1);
            newNode.next = current.next;
            newNode.prev = current.prev;
            current.next = newNode;
        }
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
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T old = node.value;
        node.value = value;
        return old;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(currentNode.value, object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throwIndexOutOfBounds(index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throwIndexOutOfBounds(index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            node = node.next;
        }
        return node;
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode.prev == null) {
            first = currentNode.next;
        }
        if (currentNode.next == null) {
            last = currentNode.prev;
        }
        if (currentNode.prev != null && currentNode.next != null) {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
    }

    private void throwIndexOutOfBounds(int index) {
        throw new IndexOutOfBoundsException("Wrong index" + index + "! "
                + "Index value must be less than size " + size);
    }
}
