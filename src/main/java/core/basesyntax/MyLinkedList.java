package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addLast(value);
        } else if (index == 0) {
            addFirst(value);
        } else {
            changeLink(getElementByIndex(index), value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T elementFromList : list) {
            add(elementFromList);
        }
    }

    @Override
    public T get(int index) {
        return getElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = getElementByIndex(index);
        T oldValue = oldNode.value;
        oldNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getElementByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (currentNode.value == object
                    || currentNode.value != null
                    && currentNode.value.equals(object)) {
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
        return size == 0;
    }

    private boolean checkValidIndex(int index) {
        return index < 0 || index > size;
    }

    private void addFirst(T value) {
        Node<T> newElement = new Node<>(value, null, first);
        first.previous = newElement;
        first = newElement;
        size++;
    }

    private void addLast(T value) {
        if (first == null && last == null) {
            first = new Node<>(value, null, null);
            last = first;
        } else {
            Node<T> newElement = new Node<>(value, last, null);
            last.next = newElement;
            last = newElement;
        }
        size++;
    }

    private Node<T> getElementByIndex(int index) {
        if (index == size || checkValidIndex(index)) {
            throw new IndexOutOfBoundsException("Exception");
        }
        Node<T> result;
        if (index < size / 2) {
            result = first;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = last;
            for (int i = size - 1; i > index; i--) {
                result = result.previous;
            }
        }
        return result;
    }

    private void changeLink(Node<T> node, T value) {
        Node<T> nextNode = node;
        Node<T> previousNode = nextNode.previous;
        Node<T> newNode = new Node<>(value, previousNode, nextNode);
        nextNode.previous = newNode;
        previousNode.next = newNode;
        size++;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            first = null;
            last = null;
        } else {
            if (node.equals(first)) {
                node.next.previous = null;
                first = node.next;
            } else if (node.equals(last)) {
                node.previous.next = null;
                last = node.previous;
            } else {
                node.previous.next = node.next;
                node.next.previous = node.previous;
            }
        }
        size--;
    }
}
