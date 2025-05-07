package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
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
        checkIndexForAdd(index);
        if (size == index) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            Node<T> currentNode = getCurrentNode(index);
            if (index == 0) {
                newNode.next = currentNode;
                currentNode.prev = newNode;
                first = newNode;
            } else {
                currentNode.prev.next = newNode;
                newNode.prev = currentNode.prev;
                newNode.next = currentNode;
                currentNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        unlink(index);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(currentNode.value, object)) {
                int currentIndex = i;
                unlink(currentIndex);
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
        return size == 0;
    }

    private void unlink(int index) {
        Node<T> currentNode = getCurrentNode(index);
        if (index == 0 && size == 1) {
            first = null;
            last = null;
        }
        if (index == 0 && size != 1) {
            currentNode.next.prev = null;
            first = currentNode.next;
        } else if (index == size - 1 && size != 1) {
            currentNode.prev.next = null;
            last = currentNode.prev;
        } else if (size != 1) {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
    }

    private Node<T> getCurrentNode(int index) {
        Node<T> currentNode;
        if (index > size / 2) {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException: index "
                    + index + " with size " + size);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException: index "
                    + index + " with size " + size);
        }
    }

    private class Node<T> {
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
