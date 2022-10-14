package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> current = new Node<>(null, value, null);
        if (first == null) {
            first = current;
        } else {
            current.prevElement = last;
            last.nextElement = current;
        }
        last = current;
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexCheckerForAdding(index);
        if (size() == 0) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            newNode.nextElement = first;
            first.prevElement = newNode;
            first = newNode;
            size++;
            return;
        }
        if (index == size()) {
            add(value);
            return;
        }
        Node<T> current = getNodeByIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        newNode.prevElement = current.prevElement;
        newNode.nextElement = current;
        current.prevElement.nextElement = newNode;
        current.prevElement = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNodeByIndex(index);
        T removedValue = current.value;
        current.value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNodeByIndex(index);
        unlink(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if ((current.value == null && object == null)
                    || (current.value != null && (current.value.equals(object)))) {
                unlink(current);
                return true;
            }
            current = current.nextElement;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private void indexChecker(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index is out of bounds for size "
                    + size() + " with element " + index);
        }
    }

    private void indexCheckerForAdding(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index is out of bounds for size "
                    + size() + " with element " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        indexChecker(index);
        if (index <= size() / 2) {
            return countFromFirst(index);
        } else {
            return countFromLast(index);
        }
    }

    private Node<T> countFromFirst(int index) {
        int counter = 0;
        Node<T> current = first;
        while (counter < index) {
            current = current.nextElement;
            counter++;
        }
        return current;
    }

    private Node<T> countFromLast(int index) {
        int counter = size() - 1;
        Node<T> current = last;
        while (counter > index) {
            current = current.prevElement;
            counter--;
        }
        return current;
    }

    private void unlink(Node<T> removedNode) {
        Node<T> prevNode = removedNode.prevElement;
        Node<T> nextNode = removedNode.nextElement;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.nextElement = nextNode;
            removedNode.prevElement = null;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prevElement = prevNode;
            removedNode.nextElement = null;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prevElement;
        private Node<T> nextElement;

        Node(Node<T> prevElement, T value, Node<T> nextElement) {
            this.prevElement = prevElement;
            this.value = value;
            this.nextElement = nextElement;
        }
    }
}
