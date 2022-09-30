package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int DIVIDE = 2;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(lastNode, value, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            head = new Node<>(null, value, head);
            size++;
        } else {
            checkIndex(index);
            Node<T> needNode = findingIndexNode(index);
            Node<T> beforeNode = needNode.prev;
            Node<T> newNode = new Node<>(beforeNode, value, needNode);
            beforeNode.next = newNode;
            needNode.prev = newNode;
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
        checkIndex(index);
        return findingIndexNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = findingIndexNode(index);
        T oldValue = oldNode.item;
        oldNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return deleteNode(findingIndexNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> needNode = head; needNode != null; needNode = needNode.next) {
            if (needNode.item == object
                    || needNode.item.equals(object)) {
                deleteNode(needNode);
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    private Node<T> findingIndexNode(int index) {
        Node<T> needNode;
        if (index < (size / DIVIDE)) {
            needNode = head;
            for (int i = 0; i < index; i++) {
                needNode = needNode.next;
            }
        } else {
            needNode = tail;
            for (int i = size - 1; i > index; i--) {
                needNode = needNode.prev;
            }
        }
        return needNode;
    }

    private boolean checkIndex(int index) {
        if (index >= 0 && index < size) {
            return true;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private T deleteNode(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return node.item;
    }
}
