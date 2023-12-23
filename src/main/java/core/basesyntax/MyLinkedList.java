package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> currentNode;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;
        private int index;

        public Node(Node<T> prev, T value, Node<T> next, int index) {
            this.prev = prev;
            this.next = next;
            this.value = value;
            this.index = index;
        }
    }

    @Override
    public void add(T value) {
        if (currentNode == null) {
            currentNode = new Node<>(null, value, null, size);
            size++;
            head = currentNode;
            tail = currentNode;
        } else {
            Node<T> nodeToAdd = new Node<>(currentNode, value, null, size);
            currentNode.next = nodeToAdd;
            tail = nodeToAdd;
            currentNode = nodeToAdd;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Can't add the element by index " + index);
        }
        Node<T> nodeToAdd;
        if (index == size) {
            add(value);
            return;
        }
        findNodeByIndex(index);
        if (index == 0) {
            currentNode = head;
            nodeToAdd = new Node<>(null, value, currentNode, 0);
            currentNode.prev = nodeToAdd;
            head = nodeToAdd;
            size++;
            setIndexesAfterAdding();
        } else {
            nodeToAdd = new Node<>(currentNode.prev, value, currentNode, currentNode.index);
            currentNode.prev.next = nodeToAdd;
            currentNode.prev = nodeToAdd;
            size++;
            setIndexesAfterAdding();
        }
    }

    @Override
    public void addAll(List<T> list) {
        T[] arrayList = (T[]) new Object[list.size()];
        for (int i = 0; i < arrayList.length; i++) {
            arrayList[i] = list.get(i);
            add(arrayList[i]);
        }
    }

    @Override
    public T get(int index) {
        findNodeByIndex(index);
        T result = currentNode.value;
        currentNode = tail;
        return result;
    }

    @Override
    public T set(T value, int index) {
        findNodeByIndex(index);
        T result = currentNode.value;
        currentNode.value = value;
        currentNode = tail;
        return result;
    }

    @Override
    public T remove(int index) {
        findNodeByIndex(index);
        T result = currentNode.value;
        if (size == 1 && index == 0) {
            currentNode = null;
            size--;
        } else if (index == 0 && size > 1) {
            currentNode.next.prev = null;
            head = currentNode.next;
            setIndexesAfterRemoving();
            size--;
        } else if (index == (size - 1)) {
            currentNode.prev.next = null;
            currentNode = currentNode.prev;
            tail = currentNode;
            size--;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            setIndexesAfterRemoving();
            size--;
        }
        return result;
    }

    @Override
    public boolean remove(T object) {
        currentNode = head;
        boolean result = false;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                remove(currentNode.index);
                result = true;
                break;
            }
            currentNode = currentNode.next;
        }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void setIndexesAfterRemoving() {
        while (currentNode != null) {
            currentNode.index--;
            currentNode = currentNode.next;
        }
        currentNode = head;
    }

    private void setIndexesAfterAdding() {
        while (currentNode != null) {
            currentNode.index++;
            currentNode = currentNode.next;
        }
    }

    private void findNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Can't find the element by index " + index);
        }
        currentNode = head;
        while (currentNode.index != index) {
            currentNode = currentNode.next;
        }
    }
}
