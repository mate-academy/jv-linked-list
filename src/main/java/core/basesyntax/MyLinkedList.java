package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
    
    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Can't add element to index " + index);
        }
        if (index == size) {
            linkLast(value);
            return;
        }
        linkBefore(value, findByIndex(index));
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
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeByIndex = findByIndex(index);
        T oldValue = nodeByIndex.item;
        nodeByIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index); 
        return unlink(findByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToUnlink = first;
        for (int i = 0; i < size; i++) {
            if (nodeToUnlink.item == object || (nodeToUnlink.item != null
                    && nodeToUnlink.item.equals(object))) {
                unlink(nodeToUnlink);
                return true;
            }
            nodeToUnlink = nodeToUnlink.next;
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Can't find element with index " + index);
        }
    }

    private Node<T> findByIndex(int index) {
        if (index > size / 2) {
            return findElementInLastHalf(index);
        } else {
            return findElementInFirstHalf(index);
        }
    }

    private Node<T> findElementInFirstHalf(int index) {
        Node<T> nodeByIndex = first;
        for (int i = 0; i < index; i++) {
            nodeByIndex = nodeByIndex.next;
        }
        return nodeByIndex;
    }

    private Node<T> findElementInLastHalf(int index) {
        Node<T> nodeByIndex = last;
        for (int i = size - 1; i > index; i--) {
            nodeByIndex = nodeByIndex.prev;
        }
        return nodeByIndex;
    }

    private void linkBefore(T value, Node<T> nodeByIndex) {
        Node<T> prevNode = nodeByIndex.prev;
        Node<T> newNode = new Node<>(prevNode, value, nodeByIndex);
        nodeByIndex.prev = newNode;
        if (prevNode == null) {
            first = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> nodeToUnlink) {
        Node<T> nextNode = nodeToUnlink.next;
        Node<T> prevNode = nodeToUnlink.prev;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            nodeToUnlink.prev = null;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            nodeToUnlink.next = null;
        }
        T value = nodeToUnlink.item;
        nodeToUnlink.item = null;
        size--;
        return value;
    }
}
