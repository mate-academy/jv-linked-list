package core.basesyntax;

import java.util.List;
import java.util.Objects;

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
        if (checkValidIndex(index)) {
            throw new IndexOutOfBoundsException("Exception");
        } else if (index == size) {
            addLast(value);
        } else if (index == 0) {
            addFirst(value);
        } else {
            Node<T> nextNode = getElementByIndex(index);
            //changeByIndex(value, nextNode);
            Node<T> previousNode = nextNode.previous;
            Node<T> newNode = new Node<>(value, previousNode, nextNode);
            nextNode.previous = newNode;
            previousNode.next = newNode;
            size++;
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
        if (index == size || checkValidIndex(index)) {
            throw new IndexOutOfBoundsException("Exception");
        }
        return getElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index == size || checkValidIndex(index)) {
            throw new IndexOutOfBoundsException("Exception");
        }
        Node<T> oldNode = getElementByIndex(index);
        T oldValue = oldNode.value;
        oldNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index == size || checkValidIndex(index)) {
            throw new IndexOutOfBoundsException("Exception");
        }
        Node<T> currentNode = getElementByIndex(index);
        Node<T> nextNode = currentNode.next;
        Node<T> previousNode = currentNode.previous;
        if (previousNode == null) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
            currentNode.previous = null;
        }
        if (nextNode == null) {
            last = previousNode;
        } else {
            nextNode.previous = previousNode;
            currentNode.next = null;
        }
        T result = currentNode.value;
        size--;
        return result;
    }

    @Override
    public boolean remove(T object) {
        Node<T> thisNode = first;
        while (thisNode != null) {
            if (Objects.equals(thisNode.value, object)) {
                Node<T> currentNode = thisNode;
                Node<T> nextNode = currentNode.next;
                Node<T> previousNode = currentNode.previous;
                if (previousNode == null) {
                    first = nextNode;
                } else {
                    previousNode.next = nextNode;
                    currentNode.previous = null;
                }
                if (nextNode == null) {
                    last = previousNode;
                } else {
                    nextNode.previous = previousNode;
                    currentNode.next = null;
                }
                T result = currentNode.value;
                size--;
                return true;
            }
            thisNode = thisNode.next;
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
        if (first == null && last == null) {
            first = new Node<>(value, null, null);
            last = first;
        } else {
            Node<T> newElement = new Node<>(value, null, first);
            first.previous = newElement;
            first = newElement;
        }
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

    private void changeByIndex(T value, Node<T> elementByIndex) {
        Node<T> nextElement = elementByIndex;
        Node<T> previousElement = nextElement.previous;
        Node<T> newElement = new Node<>(value, previousElement,nextElement);
        newElement.previous = newElement;
        previousElement.next = nextElement;
        size++;
    }
}
