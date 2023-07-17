package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            addAfterTail(newNode);
        }
    }

    @Override
    public void add(T value, int index) {
        isIndexValid(index, true);
        Node<T> oldNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0 && size != 0) {
            addBeforeHead(newNode);
        } else if (index == 0) {
            add(value);
        } else if (index == size) {
            addAfterTail(newNode);
        } else {
            addInMiddle(oldNode, newNode);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        isIndexValid(index, false);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        isIndexValid(index, false);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexValid(index, false);
        Node<T> node = findNodeByIndex(index);
        unlink(node);
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = findNodeByValue(object);
        if (node != null) {
            unlink(node);
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

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
            if (isTail(node)) {
                tail = node.prev;
            }
        }
        if (node.next != null) {
            node.next.prev = node.prev;
            if (isHead(node)) {
                head = node.next;
            }
        }
    }

    private void addBeforeHead(Node<T> node) {
        head.prev = node;
        node.next = head;
        head = node;
        size++;
    }

    private void addAfterTail(Node<T> node) {
        tail.next = node;
        node.prev = tail;
        tail = node;
        size++;
    }

    private void addInMiddle(Node<T> currentNode, Node<T> newNode) {
        currentNode.prev.next = newNode;
        newNode.prev = currentNode.prev;

        newNode.next = currentNode;
        currentNode.prev = newNode;
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        return index <= size / 2 ? searchNodeFromHeadByIndex(index)
                                : searchNodeFromTailByIndex(index);
    }

    private Node<T> findNodeByValue(T value) {
        Node<T> iteratorNode = head;
        while (iteratorNode != null) {
            if ((iteratorNode.value == value) || (iteratorNode.value != null
                                                  && iteratorNode.value.equals(value))) {
                return iteratorNode;
            }
            iteratorNode = iteratorNode.next;
        }
        return null;
    }

    private Node<T> searchNodeFromHeadByIndex(int index) {
        int counter = 0;
        Node<T> iteratorNode = head;
        while (iteratorNode != null) {
            if (counter == index) {
                return iteratorNode;
            }
            iteratorNode = iteratorNode.next;
            counter++;
        }
        return iteratorNode;
    }

    private Node<T> searchNodeFromTailByIndex(int index) {
        int counter = size - 1;
        Node<T> iteratorNode = tail;
        while (iteratorNode != null) {
            if (counter == index) {
                return iteratorNode;
            }
            iteratorNode = iteratorNode.prev;
            counter--;
        }
        return iteratorNode;
    }

    private void isIndexValid(int index, boolean isAddition) {
        final String errorMessage = "The index - " + index + " is out of bound."
                + "Because the size of LinkedList is " + size;

        if ((isAddition && (index < 0 || index > size))
                || (!isAddition && (index < 0 || index >= size))) {
            throw new IndexOutOfBoundsException(errorMessage);
        }
    }

    private boolean isHead(Node<T> node) {
        return node == head;
    }

    private boolean isTail(Node<T> node) {
        return node == tail;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
