package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirstNode(value);
        } else {
            addNodeToEnd(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (size == 0) {
            addFirstNode(value);
        } else if (index == 0) {
            addToTop(value);
        } else if (index == size) {
            addNodeToEnd(value);
        } else {
            Node<T> currentNode = findNodeByIndex(index);
            Node<T> newNode = new Node<>(value, currentNode, currentNode.prev);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
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
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.element;
        currentNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        return unlink(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if ((object == null && currentNode.element == null) || (object != null && object.equals(
                    currentNode.element))) {
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

    private void addFirstNode(T element) {
        first = new Node<>(element, null, null);
        last = first;
        size++;
    }

    private void addToTop(T value) {
        Node<T> newNode = new Node<>(value, first, null);
        first.prev = newNode;
        first = newNode;
        size++;
    }

    private void addNodeToEnd(T value) {
        Node<T> newNode = new Node<>(value, null, last);
        last.next = newNode;
        last = newNode;
        size++;
    }

    private static class Node<T> {

        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(T element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index <= size / 2) {
            Node<T> currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private T unlink(Node<T> node) {
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        T element = node.element;
        size--;
        return element;
    }
}
