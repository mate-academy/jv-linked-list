package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    int size;

    static class Node<T> {
        private T value;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }
        public static <T> Node<T> valueOf(T value) {
            return new Node<>(value);
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = Node.valueOf(value);
        if (index == 0) {
            addAsHead(newNode);
        } else if (index == size) {
            addAsTail(newNode);
        } else {
            add(index, newNode);
        }
        size++;
    }

    private void addAsHead(Node<T> newNode) {
        newNode.next = first;
        first = newNode;
        if (first.next == null) {
            last = first;
        }
    }

    private void addAsTail(Node<T> newNode) {
        last.next = newNode;
        last = newNode;
    }

    private void add(int index, Node<T> newNode) {
        Node<T> node = findNodeByIndex(index - 1);
        newNode.next = node.next;
        node.next = newNode;
    }

    private Node<T> findNodeByIndex(int index) {
        Objects.checkIndex(index, size);
        if (index == size - 1) {
            return last;
        } else {
            return nodeAt(index);
        }
    }

    private Node<T> nodeAt(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }


    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = findNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNodeByIndex(index);
        node.value = value;
        return node.value;
    }

    @Override
    public T remove(int index) {
        T deletedElement;
        if (index == 0 && !isEmpty()) {
            deletedElement = first.value;
            removeHead();
        } else {
            Node<T> previousNode = findNodeByIndex(index - 1);
            deletedElement = previousNode.next.value;
            previousNode.next = previousNode.next.next;
            if (index == size - 1) {
                last = previousNode;
            }
        }
        size--;
        return deletedElement;
    }
    private void removeHead() {
        first = first.next;
        if (first == null) {
            last = null;
        }
    }

    @Override
    public boolean remove(T object) {

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
