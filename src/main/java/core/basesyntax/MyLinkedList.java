package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> headNode;
    private Node<T> tailNode;
    private int sizeList = 0;

    @Override
    public void add(T value) {
        if (headNode == null) {
            Node<T> newNode = new Node<>(null, value, null);
            headNode = newNode;
            tailNode = newNode;
            sizeList++;
        } else {
            Node<T> newNode = new Node<>(tailNode, value, null);
            tailNode.nextNode = newNode;
            tailNode = newNode;
            sizeList++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }

        if (headNode == null) {
            Node<T> newNode = new Node<>(null, value, null);
            headNode = newNode;
            tailNode = newNode;
            sizeList++;
        } else {
            Node<T> nodeByIndex = null;

            try {
                nodeByIndex = findNodeByIndex(index);
            }catch (IndexOutOfBoundsException e) {
                new RuntimeException("Invalid index  " + e);
            }

            if (nodeByIndex == null) {
                Node<T> newNode = new Node<>(tailNode, value, null);
                tailNode.nextNode = newNode;
                tailNode = newNode;
                sizeList++;
            } else if (nodeByIndex.equals(headNode)) {
                Node<T> newNode = new Node<>(null, value, nodeByIndex);
                nodeByIndex.prevNode = newNode;
                headNode = newNode;
                sizeList++;
            } else {
                Node<T> newNode = new Node<>(nodeByIndex.prevNode, value, nodeByIndex);
                nodeByIndex.prevNode.nextNode = newNode;
                nodeByIndex.prevNode = newNode;
                sizeList++;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index > size() - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }

        Node<T> nodeByIndex = findNodeByIndex(index);

        return nodeByIndex.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeByIndex = findNodeByIndex(index);
        if (nodeByIndex == null || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        T value1 = nodeByIndex.value;

        nodeByIndex.value = value;

        return value1;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeByIndex = findNodeByIndex(index);

        if (nodeByIndex == null) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }

        unlink(nodeByIndex, index);

        return nodeByIndex.value;
    }

    @Override
    public boolean remove(T object) {
        int indexNode = 0;
        Node<T> findNode;

        if (headNode == null) {
            return false;
        } else {
            findNode = headNode;
        }

        boolean ifFindNode = false;

        while (findNode != null) {
            T value = findNode.value;
            if (value == object || value != null && value.equals(object)) {
                ifFindNode = true;
                break;
            }
            findNode = findNode.nextNode;
            indexNode++;
        }

        if (ifFindNode) {
            remove(indexNode);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return sizeList;
    }

    @Override
    public boolean isEmpty() {
        return headNode == null;
    }

    private void unlink(Node<T> nodeByIndex, int index) {
        if (index == 0 && size() == 1) {
            headNode = null;
            tailNode = null;
            sizeList--;
        } else if (nodeByIndex.equals(headNode)) {
            headNode.nextNode.prevNode = null;
            headNode = headNode.nextNode;
            sizeList--;
        } else if (nodeByIndex.equals(tailNode)) {
            tailNode.prevNode.nextNode = null;
            tailNode = tailNode.prevNode;
            sizeList--;
        } else {
            Node<T> prevNode = nodeByIndex.prevNode;
            Node<T> nextNode = nodeByIndex.nextNode;

            prevNode.nextNode = nextNode;
            nextNode.prevNode = prevNode;
            sizeList--;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index > size() -1  || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        int startIndex = 0;
        Node<T> findNode = headNode;

        if (index < size() / 2) {
            while (startIndex != index) {
                findNode = findNode.nextNode;
                startIndex++;
            }
        } else {
            findNode = tailNode;
            startIndex = size() - 1;

            while (startIndex != index) {
                findNode = findNode.prevNode;
                startIndex--;
            }
        }

        return findNode;
    }

    private static class Node<T> {

        private Node<T> prevNode;
        private T value;
        private Node<T> nextNode;

        public Node(Node<T> prevNode, T value, Node<T> nextNode) {
            this.prevNode = prevNode;
            this.value = value;
            this.nextNode = nextNode;
        }
    }
}
