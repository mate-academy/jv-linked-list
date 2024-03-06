package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        addTail(value);
    }

    @Override
    public void add(T value, int index) {
        indexAddBoundariesCheck(index);
        if (index == size) {
            addTail(value);
        } else {
            addBetween(value, nodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            addTail(element);
        }
    }

    @Override
    public T get(int index) {
        indexGetBoundariesCheck(index);
        return nodeByIndex(index).nodeValue;
    }

    @Override
    public T set(T value, int index) {
        indexGetBoundariesCheck(index);
        Node<T> setNode = nodeByIndex(index);
        T oldValue = setNode.nodeValue;
        setNode.nodeValue = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexGetBoundariesCheck(index);
        Node<T> nodeToRemove = nodeByIndex(index);
        T oldValue = nodeToRemove.nodeValue;
        unlinkNode(nodeToRemove);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currenNode = head;
        while (currenNode != null) {
            if (currenNode.nodeValue == object
                    || (currenNode.nodeValue != null && currenNode.nodeValue.equals(object))) {
                unlinkNode(currenNode);
                return true;
            }
            currenNode = currenNode.nextNode;
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

    private void indexAddBoundariesCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(getBoundariesMessage(index));
        }
    }

    private void indexGetBoundariesCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(getBoundariesMessage(index));
        }
    }

    private void unlinkNode(Node<T> nodeToRemove) {
        if (nodeToRemove.lastNode == null) {
            unlinkHead();
        } else if (nodeToRemove.nextNode == null) {
            unlinkTail();
        } else {
            nodeToRemove.lastNode.nextNode = nodeToRemove.nextNode;
            nodeToRemove.nextNode.lastNode = nodeToRemove.lastNode;
            size--;
        }
    }

    private void unlinkTail() {
        Node<T> newTail = tail.lastNode;
        tail.nodeValue = null;
        tail.lastNode = null;
        tail = newTail;
        if (newTail == null) {
            head = null;
        } else {
            tail.nextNode = null;
        }
        size--;
    }

    private void unlinkHead() {
        Node<T> newHead = head.nextNode;
        head.nodeValue = null;
        head.nextNode = null;
        head = newHead;
        if (newHead == null) {
            tail = null;
        } else {
            head.lastNode = null;
        }
        size--;
    }

    private void addTail(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        Node<T> tempTail = tail;
        tail = newNode;
        if (tempTail == null) {
            head = newNode;
        } else {
            tempTail.nextNode = newNode;
        }
        size++;
    }

    private void addBetween(T value, Node<T> indexNode) {
        Node<T> newNode = new Node<>(indexNode.lastNode, value, indexNode);
        indexNode.lastNode = newNode;
        if (newNode.lastNode == null) {
            head = newNode;
        } else {
            newNode.lastNode.nextNode = newNode;
        }
        size++;
    }

    private Node<T> nodeByIndex(int index) {
        if (index <= size / 2) {
            int counter = 0;
            Node<T> currentNode = head;
            while (currentNode != null) {
                if (counter == index) {
                    return currentNode;
                }
                currentNode = currentNode.nextNode;
                counter++;
            }
        } else {
            int counter = size - 1;
            Node<T> currentNode = tail;
            while (currentNode != null) {
                if (counter == index) {
                    return currentNode;
                }
                currentNode = currentNode.lastNode;
                counter--;
            }
        }
        return null;
    }

    private String getBoundariesMessage(int index) {
        return "Index: " + index + ", Size:" + size;
    }

    private static class Node<T> {
        private Node<T> lastNode;
        private T nodeValue;
        private Node<T> nextNode;

        private Node(Node<T> last, T value, Node<T> next) {
            lastNode = last;
            nodeValue = value;
            nextNode = next;
        }
    }
}
