package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> next, T value, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> nodeToAdd = new Node<>(null, value, null);
        if (first == null && last == null) { // when list hasn't first and last
            first = nodeToAdd;
            last = nodeToAdd;
        } else if (first != null && first.next == null) { // when list hasn't last
            first.next = nodeToAdd;
            nodeToAdd.prev = first;
            last = nodeToAdd;
        } else { // when list has first and last
            nodeToAdd.prev = last;
            last.next = nodeToAdd;
            last = nodeToAdd;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> nodeToAdd = new Node<>(null, value, null);
        Node<T> nodeNext = findNodeByIndexToAdd(index);
        if (index == 0) {
            if (nodeNext != null) {
                nodeToAdd.next = nodeNext;
                nodeNext.prev = nodeToAdd;
            }
            first = nodeToAdd;
            size++;
        } else if (index == size) {
            add(value);
        } else {
            nodeToAdd.prev = nodeNext.prev;
            nodeToAdd.next = nodeNext;
            Node<T> nodePrev = nodeNext.prev;
            nodeNext.prev = nodeToAdd;
            nodePrev.next = nodeToAdd;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Node<T> currentNode = findNodeByIndexToGetSetRemove(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeToSet = findNodeByIndexToGetSetRemove(index);
        T oldNodeValue = nodeToSet.value;
        nodeToSet.value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = findNodeByIndexToGetSetRemove(index);
        unlink(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        int k = 0;
        while (k != size) {
            if (currentNode.value == object
                    || (currentNode.value != null
                    && currentNode.value.equals(object))) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
            k++;
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode = first;
        int k = 0;
        while (k != index) {
            currentNode = currentNode.next;
            k++;
        }
        return currentNode;
    }

    private Node<T> findNodeByIndexToAdd(int index) {
        outOfBoundsCheckToAdd(index);
        return findNodeByIndex(index);
    }

    private Node<T> findNodeByIndexToGetSetRemove(int index) {
        outOfBoundsCheckToGetSetRemove(index);
        return findNodeByIndex(index);
    }

    private void outOfBoundsCheckToAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is negative or out of bounds with size: " + size);
        }
    }

    private void outOfBoundsCheckToGetSetRemove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is negative or out of bounds with size: " + size);
        }
    }

    private void unlink(Node<T> nodeToDelete) {
        Node<T> prevNode = nodeToDelete.prev;
        Node<T> nextNode = nodeToDelete.next;
        if (first == nodeToDelete && last == nodeToDelete) {
            first = null;
            last = null;
        } else if (first == nodeToDelete) {
            nextNode.prev = null;
            first = nextNode;
        } else if (last == nodeToDelete) {
            prevNode.next = null;
            last = prevNode;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
    }

    @Override
    public String toString() {
        Node<T> node = first;
        String res = "";
        while (node != null) {
            res = res + node.value;
            node = node.next;
        }
        return res;
    }
}
