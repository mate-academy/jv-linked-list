package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (first == null) {
            first = newNode;
        }
        if (last != null) {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            addBefore(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> newNode = node(index);
        T oldElement = newNode.element;
        newNode.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> position = first; position != null; position = position.next) {
                if (position.element == object) {
                    unlink(position);
                    return true;
                }
            }
        } else {
            for (Node<T> position = first; position != null; position = position.next) {
                if (object.equals(position.element)) {
                    unlink(position);
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

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            outOfBoundMsg(index);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            outOfBoundMsg(index);
        }
    }

    private void outOfBoundMsg(int index) {
        throw new IndexOutOfBoundsException("Index: "
                + index + " not found, current size: " + size);
    }

    private Node<T> node(int index) {
        Node<T> position;
        if (index < size << 1) {
            position = first;
            for (int i = 0; i < index; i++) {
                position = position.next;

            }
        } else {
            position = last;
            for (int i = size; i > index; i--) {
                position = position.prev;
            }
        }
        return position;
    }

    private void addBefore(T value, int index) {
        Node<T> nodeByIndex = node(index);
        Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
        if (nodeByIndex.prev != null) {
            nodeByIndex.prev.next = newNode;
        } else {
            first = newNode;
        }
        nodeByIndex.prev = newNode;
        size++;
    }

    private T unlink(Node<T> node) {
        final T element = node.element;
        final Node<T> prev = node.prev;
        final Node<T> next = node.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.element = null;
        size--;
        return element;
    }

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
