package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> last;
    private Node<T> first;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (isEmpty()) {
            addElementToEmptyList(node);
        } else {
            addElementToEndOfTheList(node);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            addElementToEmptyList(newNode);
            size++;
            return;
        }
        if (index == 0) {
            addElementToFirstPosition(newNode);
            size++;
            return;
        }
        if (index == size) {
            addElementToEndOfTheList(newNode);
            size++;
            return;
        }
        addElementToTheMiddle(newNode, index);
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
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode;
        if (index == 0) {
            removedNode = removeFirstElementFromList();
        } else if (size == 1) {
            removedNode = removeElementFromOneElementList();
        } else {
            removedNode = removeFromTheMiddleOfTheList(index);
        }
        size--;
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        int flag = 0;
        Node<T> node;
        for (node = first; node != null; node = node.next) {
            if (node.value == object || (node.value != null && node.value.equals(object))) {
                remove(flag);
                return true;
            }
            flag++;
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

    private void checkElementIndex(int index) {
        if (index >= 0 && index < size) {
            return;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private Node<T> findNodeByIndex(int index) {
        checkElementIndex(index);
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private void addElementToEmptyList(Node<T> node) {
        first = last = node;
    }

    private void addElementToEndOfTheList(Node<T> node) {
        last.next = node;
        node.prev = last;
        last = node;
    }

    private void addElementToFirstPosition(Node<T> node) {
        first.prev = node;
        node.next = first;
        first = node;
    }

    private void addElementToTheMiddle(Node<T> newNode, int index) {
        Node<T> currentNodeAtIndex = findNodeByIndex(index);
        newNode.next = currentNodeAtIndex;
        newNode.prev = currentNodeAtIndex.prev;
        if (currentNodeAtIndex.prev != null) {
            currentNodeAtIndex.prev.next = newNode;
        } else {
            first = newNode;
        }
        currentNodeAtIndex.prev = newNode;
    }

    private Node<T> removeFirstElementFromList() {
        Node<T> removedNode = first;
        if (first.next != null) {
            first = first.next;
            first.prev = null;
        } else {
            first = last = null;
        }
        return removedNode;
    }

    private Node<T> removeElementFromOneElementList() {
        Node<T> removedNode = last;
        last = null;
        first = null;
        return removedNode;
    }

    private Node<T> removeFromTheMiddleOfTheList(int index) {
        Node<T> currentNode = findNodeByIndex(index);
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        }
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        }
        if (currentNode == last) {
            last = currentNode.prev;
        }
        return currentNode;
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(T value) {
            this.value = value;
        }
    }
}
