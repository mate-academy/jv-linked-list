package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

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
        Node<T> deletedElement;
        deletedElement = checkBorderElement(index);
        if (deletedElement != null) {
            size--;
            return deletedElement.value;
        }
        Node<T> currentElement = findElement(index);
        unLink(currentElement);
        size--;
        return currentElement.value;
    }

    @Override
    public boolean remove(T object) {
        int i = 0;
        Node<T> currentElement = first;
        while (currentElement != null && !(object == null && currentElement.value == null
                || object != null && object.equals(currentElement.value))) {
            currentElement = currentElement.next;
            i++;
        }
        if (i < size) {
            Node<T> deletedElement = checkBorderElement(i);
            if (deletedElement == null) {
                unLink(currentElement);
            }
            size--;
            return true;
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

    private void unLink(Node<T> unlinkedNode) {
        unlinkedNode.prev.next = unlinkedNode.next;
        unlinkedNode.next.prev = unlinkedNode.prev;
    }

    private Node<T> checkBorderElement(int index) {
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
