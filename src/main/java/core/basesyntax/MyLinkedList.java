package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int sizeOfList;
    private Node firstNode;
    private Node lastNode;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node node = new Node(value);
        if (sizeOfList == 0) {
            firstNode = node;
            lastNode = node;
            sizeOfList += 1;
            return;
        }
        lastNode.nextNode = node;
        node.previousNode = lastNode;
        lastNode = node;
        sizeOfList += 1;
    }

    @Override
    public void add(T value, int index) {
        if (index == sizeOfList) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            addToStart(value, index);
            return;
        }
        Node currentNextNode = getNodeByIndex(index);
        Node currentPrevNode = currentNextNode.previousNode;
        Node node = new Node(value);
        currentPrevNode.nextNode = node;
        node.previousNode = currentPrevNode;
        node.nextNode = currentNextNode;
        currentNextNode.previousNode = node;
        sizeOfList += 1;
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
        Node currentNode = getNodeByIndex(index);
        return (T) currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node currentNode = getNodeByIndex(index);
        T currentValue = (T) currentNode.value;
        currentNode.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node deletedNode = getNodeByIndex(index);
        unlink(deletedNode, index);
        return (T) deletedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node deletedNode = firstNode;
        for (int i = 0; i < sizeOfList; i++) {
            if (object == deletedNode.value || object != null && object.equals(deletedNode.value)) {
                remove(i);
                return true;
            }
            deletedNode = deletedNode.nextNode;
        }
        return false;
    }

    @Override
    public int size() {
        return sizeOfList;
    }

    @Override
    public boolean isEmpty() {
        if (sizeOfList == 0) {
            return true;
        }
        return false;
    }

    private void checkIndex(int index) {
        if (index < sizeOfList && index >= 0) {
            return;
        }
        throw new IndexOutOfBoundsException("Index out of bounds of MyLinkedList.");
    }

    private void addToStart(T value, int index) {
        Node node = new Node(value);
        firstNode.previousNode = node;
        node.nextNode = firstNode;
        firstNode = node;
        sizeOfList += 1;
    }

    private Node getNodeByIndex(int index) {
        if (index == 0) {
            return firstNode;
        }
        int currentIndex = 0;
        Node currentNode = firstNode;
        while (currentIndex < index) {
            currentNode = currentNode.nextNode;
            currentIndex += 1;
        }
        return currentNode;
    }

    private void unlink(Node node, int index) {
        Node currentNextNode = node.nextNode;
        Node currentPrevNode = node.previousNode;
        if (index > 0) {
            currentPrevNode.nextNode = currentNextNode;
        }
        if (index == 0) {
            firstNode = currentNextNode;
        }
        if (index < sizeOfList - 1) {
            currentNextNode.previousNode = currentPrevNode;
        }
        if (index == sizeOfList - 1) {
            lastNode = currentPrevNode;
        }
        node.previousNode = null;
        node.nextNode = null;
        sizeOfList -= 1;
    }

    class Node<T> {
        private T value;
        private Node previousNode;
        private Node nextNode;

        public Node(T value) {
            this.value = value;
        }
    }
}
