package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> oldLast = last;
        last = new Node<>(null, value, oldLast);
        if (oldLast == null) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        outOfBoundsCheckToAdd(index);
        Node<T> nodeToAdd = new Node<>(null, value, null);
        Node<T> nodeNext = findNodeByIndex(index);
        if (index == 0) {
            first = nodeToAdd;
            if (nodeNext != null) {
                nodeToAdd.next = nodeNext;
                nodeNext.prev = nodeToAdd;
            } else {
                last = first;
            }
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
        outOfBoundsCheckToGetSetRemove(index);
        Node<T> currentNode = findNodeByIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        outOfBoundsCheckToGetSetRemove(index);
        Node<T> nodeToSet = findNodeByIndex(index);
        T oldNodeValue = nodeToSet.value;
        nodeToSet.value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        outOfBoundsCheckToGetSetRemove(index);
        Node<T> nodeToRemove = findNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        int currentIndex = 0;
        while (currentIndex != size) {
            if (currentNode.value == object
                    || (currentNode.value != null
                    && currentNode.value.equals(object))) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
            currentIndex++;
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        int currentNodeIndex;
        if (index == 0) {
            currentNode = first;
        } else if (index == size) {
            currentNode = last;
        } else if (size / 2 >= index) { // from start
            currentNode = first;
            currentNodeIndex = 0;
            while (currentNodeIndex != index) {
                currentNode = currentNode.next;
                currentNodeIndex++;
            }
        } else { //from end
            currentNode = last;
            currentNodeIndex = size - 1;
            while (currentNodeIndex != index) {
                currentNode = currentNode.prev;
                currentNodeIndex--;
            }
        }
        return currentNode;
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
}
