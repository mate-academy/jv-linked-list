package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirstElement(value);
        } else {
            addNodeToTheEnd(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Impossible to add element on the pointed index"
            );
        }
        if (size == 0) {
            addFirstElement(value);
        } else if (index == size) {
            addNodeToTheEnd(value);
        } else if (index == 0) {
            Node<T> prevHead = head;
            Node<T> newHead = new Node<>(null, value, prevHead);
            prevHead.prev = newHead;
            this.head = newHead;
            size++;
        } else {
            Node<T> nodeOnIndex = findNodeByIndex(index);
            Node<T> prevNode = nodeOnIndex.prev;
            Node<T> newNode = new Node<>(nodeOnIndex.prev, value, nodeOnIndex);
            nodeOnIndex.prev = newNode;
            prevNode.next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addNodeToTheEnd(value);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexOutOfBounds(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexOutOfBounds(index);
        Node<T> removingNode;
        if (index == 0 && size == 1) {
            removingNode = head;
            this.head = null;
        } else if (index == 0) {
            removingNode = head;
            Node<T> newHead = head.next;
            newHead.prev = null;
            this.head.next = null;
            this.head = newHead;
        } else if (index == size - 1) {
            removingNode = tail;
            Node<T> newTail = tail.prev;
            newTail.next = null;
            this.tail.prev = null;
            this.tail = newTail;
        } else {
            removingNode = findNodeByIndex(index);
            Node<T> prevNode = removingNode.prev;
            Node<T> nextNode = removingNode.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        unlink(removingNode);
        size--;
        return removingNode.value;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            throw new IndexOutOfBoundsException(
                    "Impossible to remove element"
            );
        }
        T removingNodeValue = null;
        boolean isNullRemoved = false;
        Node<T> searchingNode = head;
        for (int i = 0; i < size; i++) {
            T value = searchingNode.value;
            if ((value == null && object == null) || (value != null && value.equals(object))) {
                removingNodeValue = searchingNode.value;
                isNullRemoved = searchingNode.value == null;
                remove(i);
                unlink(searchingNode);
                break;
            }
            searchingNode = searchingNode.next;
        }
        return removingNodeValue != null || isNullRemoved;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void addFirstElement(T value) {
        this.head = new Node<>(null, value, null);
        this.tail = head;
        size++;
    }

    private void addNodeToTheEnd(T value) {
        Node<T> prevLastNode = this.tail;
        this.tail = new Node<>(prevLastNode, value, null);
        prevLastNode.next = this.tail;
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index > size - 1 || size == 0 || index < 0) {
            throw new IndexOutOfBoundsException(
                    "No element on the pointed index"
            );
        }
        if (index == 0) {
            return head;
        }
        if (index == size - 1) {
            return tail;
        }
        boolean isCloserToHead = (size / 2) > index;
        Node<T> searchingElement;
        if (isCloserToHead) {
            searchingElement = head;
            int loopIndex = 0;
            while (loopIndex < index) {
                searchingElement = searchingElement.next;
                loopIndex++;
            }
        } else {
            searchingElement = tail;
            int loopIndex = size - 1;
            while (loopIndex > index) {
                searchingElement = searchingElement.prev;
                loopIndex--;
            }
        }
        return searchingElement;
    }

    private void unlink(Node<T> node) {
        node.next = null;
        node.prev = null;
    }

    private void checkIndexOutOfBounds(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Impossible to remove element on the pointed index"
            );
        }
    }
}
