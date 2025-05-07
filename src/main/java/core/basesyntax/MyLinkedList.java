package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        Node(Node<T> previous, T element, Node<T> next) {
            this.previous = previous;
            this.element = element;
            this.next = next;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Not correct index " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> previousNode = node.previous;
        if (previousNode == null) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
            node.previous = null;
        }
        if (nextNode == null) {
            last = previousNode;
        } else {
            nextNode.previous = previousNode;
            node.next = null;
        }
        T value = node.element;
        node.element = null;
        size--;
        return value;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.previous = newNode;
            first = newNode;
            size++;
        } else {
            checkElementIndex(index);
            Node<T> nodeByCurrentIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeByCurrentIndex.previous, value, nodeByCurrentIndex);
            nodeByCurrentIndex.previous.next = newNode;
            nodeByCurrentIndex.previous = newNode;
            size++;
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
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> temp = getNodeByIndex(index);
        T oldValue = temp.element;
        temp.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = first; node != null; node = node.next) {
                if (node.element == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = first; node != null; node = node.next) {
                if (object.equals(node.element)) {
                    unlink(node);
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
}
