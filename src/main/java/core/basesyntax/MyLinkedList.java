package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
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
        if (index == size) {
            add(value);
            return;
        }
        checkPositionIndex(index);
        Node<T> currentNode = findNodeAtIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        if (currentNode.prev == null) {
            currentNode.prev = newNode;
            first = newNode;
        } else {
            currentNode.prev.next = newNode;
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
        checkElementIndex(index);
        Node<T> currentNode = findNodeAtIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> currentNode = findNodeAtIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> findedNode = findNodeAtIndex(index);
        removeNode(findedNode);
        size--;
        return findedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> findedNode = findIndexAtValue(object);
        if (findedNode != null) {
            removeNode(findedNode);
            size--;
            return true;
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

    private Node<T> findNodeAtIndex(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i != index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> findIndexAtValue(T value) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (Objects.equals(node.value, value)) {
                return node;
            }
        }
        return null;
    }

    private void removeNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
