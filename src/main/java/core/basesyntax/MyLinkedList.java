package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last.setNext(newNode);
            newNode.setPrev(last);
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nextNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(value);
        newNode.setNext(nextNode);
        newNode.setPrev(nextNode.getPrev());
        if (nextNode.getPrev() != null) {
            nextNode.getPrev().setNext(newNode);
        } else {
            first = newNode;
        }
        nextNode.setPrev(newNode);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        if (nodeToRemove.getPrev() != null) {
            nodeToRemove.getPrev().setNext(nodeToRemove.getNext());
        } else {
            first = nodeToRemove.getNext();
        }
        if (nodeToRemove.getNext() != null) {
            nodeToRemove.getNext().setPrev(nodeToRemove.getPrev());
        } else {
            last = nodeToRemove.getPrev();
        }
        size--;
        return nodeToRemove.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if ((current.getValue() == object) || (current.getValue() != null
                    && current.getValue().equals(object))) {
                removeNode(current);
                return true;
            }
            current = current.getNext();
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
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public Node<T> setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> setNext(Node<T> next) {
            this.next = next;
        }
    }

    private T removeNode(Node<T> node) {
        if (node.prev == null) {
            first = node.getNext();
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            last = node.getPrev();
        } else {
            node.next.prev = node.prev;
        }
        size--;
        return node.value;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
            + " is out of bounds!");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index
            + " for insertion!");
        }
    }
}
