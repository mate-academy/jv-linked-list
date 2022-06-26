package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (first == null) {
            first = newNode;
        } else {
            newNode.previous = last;
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
        if (index == 0) {
            first.previous = new Node<>(value, null, first);
            first = first.previous;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(value, currentNode.previous, currentNode);
            currentNode.previous = newNode;
            newNode.previous.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToDelete = getNodeByIndex(index);
        unlink(nodeToDelete);
        return nodeToDelete.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToDelete = getNodeByValue(object);
        if (nodeToDelete == null) {
            return false;
        }
        unlink(nodeToDelete);
        return true;
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
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index != 0 && index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("Incorrect index! Index: %s, Size: %s", index, size));
        }
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = first;
            for (int i = 0; i != index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i != index; i--) {
                currentNode = currentNode.previous;
            }
        }
        return currentNode;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> node = first;
        while (node != null) {
            if (node.value == value || node.value != null && node.value.equals(value)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.previous;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.previous = prevNode;
        }
        size--;
    }

}
