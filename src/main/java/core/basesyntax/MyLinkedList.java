package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> preview;
        private Node<T> next;

        Node(Node<T> preview, T value, Node<T> next) {
            this.value = value;
            this.preview = preview;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        addInEnd(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.preview = newNode;
            head = newNode;
        } else if (size == index) {
            addInEnd(value);
        } else {
            Node<T> currentNode = findNode(index);
            Node<T> prevNode = currentNode.preview;
            Node<T> newNode = new Node<>(prevNode, value, currentNode);
            currentNode.preview = newNode;
            prevNode.next = newNode;
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
        checkIndex(index);
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if ((object == null && findNode(i).value == null)
                    || object != null && object.equals(findNode(i).value)) {
                unlink(findNode(i));
                return true;
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

    private void addInEnd(T value) {
        Node<T> newNode = new Node<>(tail,value,null);
        if (size > 0) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    private Node<T> findNode (int index) {
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        return current;
    }

    private T unlink (Node<T> someNode) {
        Node<T> nextNode = someNode.next;
        Node<T> prevNode = someNode.preview;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.preview = prevNode;
        }
        size--;
        return someNode.value;
    }

    private void checkIndex(int index) {
        if (index < 0 && index >= size) {
            throw new IndexOutOfBoundsException("Not valid index");
        }
    }


}
