package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> addedElement = new Node<>(null, value, null);
        if (size == 0) {
            head = addedElement;
        } else {
            addedElement.prev = tail;
            tail.next = addedElement;
        }
        tail = addedElement;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: "
                    + index + " is out of bounds this list size: " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nextElement = findElementByIndex(index);
        Node<T> addedElement = new Node<>(nextElement.prev, value, nextElement);
        if (nextElement.prev != null) {
            nextElement.prev.next = addedElement;
        } else {
            head = addedElement;
        }
        nextElement.prev = addedElement;
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
        checkPositionIndex(index);
        return findElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> setElement = findElementByIndex(index);
        T currentSetElement = setElement.value;
        setElement.value = value;
        return currentSetElement;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        Node<T> deletedElement = deletedLinks(null, index);
        return deletedElement.value;
    }

    @Override
    public boolean remove(T object) {
        int counter = 0;
        Node<T> element = head;
        while (element != null) {
            if (element.value == object || element.value != null && element.value.equals(object)) {
                deletedLinks(element, counter);
                return true;
            }
            element = element.next;
            counter++;
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
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    private void checkPositionIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: "
                    + index + " is out of bounds this list size: " + size);
        }
    }

    private Node<T> findElementByIndex(int index) {
        Node<T> nextElementByIndex;
        if (index < size / 2) {
            nextElementByIndex = head;
            int counter = 0;
            while (counter < index) {
                nextElementByIndex = nextElementByIndex.next;
                counter++;
            }
        } else {
            nextElementByIndex = tail;
            int counter = size - 1;
            while (counter > index) {
                nextElementByIndex = nextElementByIndex.prev;
                counter--;
            }
        }
        return nextElementByIndex;
    }

    private Node<T> deletedLinks(Node<T> deletedLinksNode, int index) {
        Node<T> deletedNode = deletedLinksNodeByIndex(index);
        if (deletedNode == null) {
            if (deletedLinksNode == null) {
                deletedNode = findElementByIndex(index);
            } else {
                deletedNode = deletedLinksNode;
            }
            deletedNode.next.prev = deletedNode.prev;
            deletedNode.prev.next = deletedNode.next;
        }
        size--;
        return deletedNode;
    }

    private Node<T> deletedLinksNodeByIndex(int index) {
        Node<T> deletedElement;
        if (size == 1) {
            return head;
        }
        if (index == size - 1) {
            deletedElement = tail;
            tail = tail.prev;
            tail.next = null;
            return deletedElement;
        }
        if (index == 0) {
            deletedElement = head;
            head = head.next;
            head.prev = null;
            return deletedElement;
        }
        return null;
    }
}
