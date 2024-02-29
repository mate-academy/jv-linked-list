package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Provided index is out of valid range";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            addToHead(newNode);
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndexExclude(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            addToHead(newNode);
        } else {
            addByIndex(newNode, index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> returnNode = getNodeByIndex(index);
        T returnValue = returnNode.item;
        returnNode.item = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        validateIndexExclude(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        unlink(nodeToRemove);
        T returnValue = nodeToRemove.item;
        nodeToRemove.item = null;
        return returnValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == current.item)
                    || (object != null && object.equals(current.item))) {
                unlink(current);
                current.item = null;
                return true;
            }
            current = current.next;
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

    private void unlink(Node<T> nodeToUnlink) {
        if (nodeToUnlink.equals(head) && !nodeToUnlink.equals(tail)) {
            unlinkHead(nodeToUnlink);
        } else if (nodeToUnlink.equals(tail) && !nodeToUnlink.equals(head)) {
            unlinkTail(nodeToUnlink);
        } else if (nodeToUnlink.equals(head) && nodeToUnlink.equals(tail)) {
            unlinkSingleHeadTail();
        }
        else {
            unlinkFromMiddle(nodeToUnlink);
        }
        size--;
    }

    private void unlinkSingleHeadTail() {
        head = null;
        tail = null;
    }

    private void unlinkFromMiddle(Node<T> nodeToUnlink) {
        nodeToUnlink.prev.next = nodeToUnlink.next;
        nodeToUnlink.next.prev = nodeToUnlink.prev;
    }

    private void unlinkHead(Node<T> nodeToUnlink) {
        head = head.next;
        nodeToUnlink.next = null;
    }

    private void unlinkTail(Node<T> nodeToUnlink) {
        tail = tail.prev;
        nodeToUnlink.prev = null;
    }

    private void addToHead(Node<T> node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            head.prev = node;
            node.next = head;
            head = node;
        }
    }

    private void addByIndex(Node<T> node, int index) {
        if (index == size) {
            tail.next = node;
            node.prev = tail;
            tail = node;
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index);
            node.prev = nodeByIndex.prev;
            nodeByIndex.prev.next = node;
            nodeByIndex.prev = node;
            node.next = nodeByIndex;
        }
    }

    private void validateIndexExclude(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }

    private void validateIndexInclude(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        validateIndexInclude(index);
        if (index < size / 2) {
            return findFromHeadByIndex(index);
        }
        return findFromTailByIndex(index);
    }

    private Node<T> findFromHeadByIndex(int index) {
        Node<T> resultNode = head;
        for (int i = 0; i < index; i++) {
            resultNode = resultNode.next;
        }
        return resultNode;
    }

    private Node<T> findFromTailByIndex(int index) {
        Node<T> resultNode = tail;
        for (int i = size - 1; i > index; i--) {
            resultNode = resultNode.prev;
        }
        return  resultNode;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T item;

        public Node(Node<T> previousNode, T item, Node<T> nextNode) {
            this.prev = previousNode;
            this.next = nextNode;
            this.item = item;
        }
    }
}
