package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> addedNode = new Node<>(tail, value, null);
        if (size > 0) {
            tail.next = addedNode;
        } else {
            head = addedNode;
        }
        tail = addedNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkPositionIndex(index);
        if (index == 0) {
            Node<T> addedNode = new Node<>(null, value, head);
            head.previous = addedNode;
            head = addedNode;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> prevNode = currentNode.previous;
            Node<T> addedNode = new Node<>(prevNode, value, currentNode);
            currentNode.previous = addedNode;
            prevNode.next = addedNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> currentNode = node(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = head; node != null; node = node.next) {
                if (node.value == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
                if (object.equals(currentNode.value)) {
                    unlink(currentNode);
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

    private boolean isPositionIndex(int index) {
        return index >= 0 && index < size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.previous;
            }
            return currentNode;
        }
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
        final Node<T> nextNode = node.next;
        final Node<T> prevNode = node.previous;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.previous = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.previous = prevNode;
            node.next = null;
        }
        node.value = null;
        size--;
        return element;
    }

    private boolean indexInSecondHalf(int index) {
        return index >= size / 2;
    }

    private Node<T> getNode(int index) {
        Node<T> neededNode = head;
        if (indexInSecondHalf(index)) {
            neededNode = tail;
            while (index < size - 1) {
                neededNode = neededNode.previous;
                index++;
            }
        } else {
            while (index > 0) {
                neededNode = neededNode.next;
                index--;
            }
        }
        return neededNode;
    }
}
