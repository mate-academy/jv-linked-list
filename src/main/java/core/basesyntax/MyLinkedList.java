package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> addedElement = new Node<>(null, value, null);
        if (isEmpty()) {
            first = addedElement;
            last = addedElement;
        } else {
            addedElement.prev = last;
            last.next = addedElement;
            last = addedElement;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                   + " out of List bounds with size " + size);
        }
        if (index == size()) {
            add(value);
            return;
        }
        Node<T> nextElement = findElement(index);
        Node<T> addedElement = new Node<>(nextElement.prev, value, nextElement);
        if (nextElement.prev != null) {
            nextElement.prev.next = addedElement;
        } else {
            first = addedElement;
        }
        nextElement.prev = addedElement;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findElement(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentElement = findElement(index);
        T changedValue = currentElement.value;
        currentElement.value = value;
        return changedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> deletedElement = unlink(null, index);
        return deletedElement.value;
    }

    @Override
    public boolean remove(T object) {
        int i = 0;
        Node<T> currentElement = first;
        while (currentElement != null) {
            if (currentElement.value == object || currentElement.value != null
                    && currentElement.value.equals(object)) {
                unlink(currentElement, i);
                return true;
            }
            i++;
            currentElement = currentElement.next;
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
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of List bounds with size " + size);
        }
    }

    private Node<T> findElement(int index) {
        Node<T> nextElement;
        if (index < size / 2) {
            nextElement = first;
            int i = 0;
            while (i < index) {
                nextElement = nextElement.next;
                i++;
            }
        } else {
            nextElement = last;
            int i = size - 1;
            while (i > index) {
                nextElement = nextElement.prev;
                i--;
            }
        }
        return nextElement;
    }

    private Node<T> unlink(Node<T> unlinkedNode, int index) {
        Node<T> deletedNode = deleteBorderNode(index);
        if (deletedNode == null) {
            if (unlinkedNode == null) {
                deletedNode = findElement(index);
            } else {
                deletedNode = unlinkedNode;
            }
            deletedNode.prev.next = deletedNode.next;
            deletedNode.next.prev = deletedNode.prev;
        }
        size--;
        return deletedNode;
    }

    private Node<T> deleteBorderNode(int index) {
        Node<T> deletedElement;
        if (size == 1) {
            return first;
        }
        if (index == size - 1) {
            deletedElement = last;
            last = last.prev;
            last.next = null;
            return deletedElement;
        }
        if (index == 0) {
            deletedElement = first;
            first = first.next;
            first.prev = null;
            return deletedElement;
        }
        return null;
    }
}
