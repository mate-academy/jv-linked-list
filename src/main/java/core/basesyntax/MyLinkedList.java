package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            addNodeWhenListIsEmpty(newNode);
        } else {
            addNodeToEndOfList(newNode);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index,size + 1);
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            addNodeWhenListIsEmpty(newNode);
        } else if (index == 0) {
            addNodeToTheBeginningOfTheList(newNode);
        } else if (index == size) {
            addNodeToEndOfList(newNode);
        } else {
            addNodeToTheMiddleOfTheList(newNode, index);
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
        checkIndex(index, size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> removedNode;
        if (size == 1) {
            removedNode = removeFromTheListIfThereIsOnlyOne();
        } else if (index == 0) {
            removedNode = removeFirstFromTheList();
        } else if (index == size - 1) {
            removedNode = removeLastOneFromTheList();
        } else {
            removedNode = removeFromMiddleOfList(index);
        }
        size--;
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        int flag = 0;
        for (Node<T> node = head; node != null; node = node.next) {
            if (objectEquals(node.value, object)) {
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> targetNode;
        int nodeIndex;
        if (index < size / 2) {
            targetNode = head;
            nodeIndex = 0;
            while (nodeIndex != index) {
                targetNode = targetNode.next;
                nodeIndex++;
            }
        } else {
            targetNode = tail;
            nodeIndex = size - 1;
            while (nodeIndex != index) {
                targetNode = targetNode.prev;
                nodeIndex--;
            }
        }
        return targetNode;
    }

    private boolean objectEquals(T value1, T value2) {
        if (value1 == value2) {
            return true;
        }
        return value1 != null && value1.equals(value2);
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index doesn't exist. "
                    + "You should enter a number between 0 and "
                    + (size - 1));
        }
    }

    private Node<T> removeFirstFromTheList() {
        Node<T> removedNode = head;
        head = head.next;
        head.prev = null;
        return removedNode;
    }

    private Node<T> removeLastOneFromTheList() {
        Node<T> removedNode = tail;
        tail = tail.prev;
        tail.next = null;
        return removedNode;
    }

    private Node<T> removeFromMiddleOfList(int index) {
        Node<T> current = getNodeByIndex(index);
        Node<T> removedNode = current;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        return removedNode;
    }

    private Node<T> removeFromTheListIfThereIsOnlyOne() {
        Node<T> removedNode = tail;
        head = null;
        tail = null;
        return removedNode;
    }

    private void addNodeWhenListIsEmpty(Node<T> newNode) {
        head = tail = newNode;
    }

    private void addNodeToEndOfList(Node<T> newNode) {
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }

    private void addNodeToTheBeginningOfTheList(Node<T> newNode) {
        head.prev = newNode;
        newNode.next = head;
        head = newNode;
    }

    private void addNodeToTheMiddleOfTheList(Node<T> newNode, int index) {
        Node<T> current = getNodeByIndex(index);
        newNode.prev = current.prev;
        newNode.next = current;
        current.prev.next = newNode;
        current.prev = newNode;
    }
}
