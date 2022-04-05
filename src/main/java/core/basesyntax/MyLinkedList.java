package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirstNode(value);
        } else {
            linkLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index when add node");
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNode(index));
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T elementOfList : list) {
            linkLast(elementOfList);
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index when get node");
        }
        Node<T> resultNode = getNode(index);
        return resultNode.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index when set node");
        }
        Node<T> nodeForSet = getNode(index);
        T setValue = nodeForSet.value;
        nodeForSet.value = value;
        return setValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index when remove");
        }
        Node<T> nodeToRemove = getNode(index);
        unlink(index);
        size--;
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        int index = 0;
        while (node != null) {
            if (node.value == object || (node.value != null && node.value.equals(object))) {
                unlink(index);
                size--;
                return true;
            }
            index++;
            node = node.next;
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

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> nodeByIndex;
        if (index < size >> 1) {
            nodeByIndex = first;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
        } else {
            nodeByIndex = last;
            for (int i = size - 1; i > index; i--) {
                nodeByIndex = nodeByIndex.prev;
            }
        }
        return nodeByIndex;
    }

    private void addFirstNode(T value) {
        Node<T> currentFirstNode = first;
        Node<T> currentNode = new Node<>(value, null, null);
        first = currentNode;
        if (currentFirstNode == null) {
            last = currentNode;
        } else {
            currentFirstNode.prev = currentNode;
        }
    }

    private void linkLast(T value) {
        Node<T> currentLastNode = last;
        Node<T> currentNewNode = new Node<>(value, currentLastNode, null);
        last = currentNewNode;
        if (currentLastNode == null) {
            first = currentNewNode;
        } else {
            currentLastNode.next = currentNewNode;
        }
    }

    private void linkBefore(T value, Node<T> indexNode) {
        Node<T> currentIndexNode = indexNode.prev;
        Node<T> currentNewNode = new Node<>(value, currentIndexNode, indexNode);
        indexNode.prev = currentNewNode;
        if (currentIndexNode == null) {
            first = currentNewNode;
        } else {
            currentIndexNode.next = currentNewNode;
        }
    }

    private void unlink(int index) {
        Node<T> currentNode = getNode(index);
        Node<T> prev = currentNode.prev;
        Node<T> next = currentNode.next;

        if (prev == null && next == null) {
            first = null;
            last = null;
            return;
        }
        if (prev == null) {
            first = next;
            next.prev = null;
            currentNode.next = null;
            return;
        }
        if (next == null) {
            last = prev;
            prev.next = null;
            currentNode.prev = null;
            return;
        }
        prev.next = currentNode.next;
        next.prev = currentNode.prev;
        currentNode.next = null;
        currentNode.prev = null;

    }
}
