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
        checkElementIndexForAdd(index);
        if (size == 0) {
            addFirstNode(value);
        } else if (index == 0) {
            addNodeToStart(value);
        } else if (index == size) {
            addNodeToEnd(value);
        } else {
            Node<T> currentNode = findNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        Node<T> node = findNodeByIndex(index);
        return node.element;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> nodeByIndex = findNodeByIndex(index);
        T newValue = nodeByIndex.element;
        nodeByIndex.element = value;
        return newValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = first; x != null; x = x.next) {
                if (x.element == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = first; x != null; x = x.next) {
                if (object.equals(x.element)) {
                    unlink(x);
                    return true;
                }
            }
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

    private void addNodeToStart(T value) {

        Node<T> newNode = new Node<>(first, value, null);
        first.prev = newNode;
        first = newNode;
        size++;
    }

    private void addNodeToEnd(T value) {

        Node<T> newNode = new Node<>(last, value, null);
        last.next = newNode;
        last = newNode;
        size++;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
    }

    private void checkElementIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
    }

    private T unlink(Node<T> x) {
         T element = x.element;
        Node<T> next = x.next;
        Node<T> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.element = null;
        size--;
        return element;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;

        if (index <= size / 2) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
            return currentNode;
    }

    private Node<T> node(int index) {

        if (index < (size >> 1)) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    public void addFirstNode(T element) {

        Node<T> newNode = new Node<>(null, element, null);
        last = first = newNode;
        size++;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
