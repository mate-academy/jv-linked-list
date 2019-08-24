package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;

    private int countOfNodes = 0;

    private class Node<N> {
        private N item;
        private Node<N> next;
        private Node<N> prev;

        private Node(Node<N> prev, N element, Node<N> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    @Override
    public void add(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        countOfNodes++;
    }

    @Override
    public void add(T value, int index) {
        if ((index < 0) || (index > countOfNodes)) {
            System.out.println("incorrect index!");
        } else {
            if (index == countOfNodes) {
                add(value);
            } else {
                Node<T> currentNode = getNode(index);
                final Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
                if (Objects.nonNull(newNode.prev)) {
                    newNode.prev.next = newNode;
                } else {
                    first = newNode;
                }
                currentNode.prev = newNode;
                countOfNodes++;
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
        if ((index > countOfNodes - 1) || (index < 0)) {
            System.out.println("incorrect index!");
            return null;
        } else {
            T thisItem;
            Node<T> currentNode = getNode(index);
            thisItem = currentNode.item;
            return thisItem;
        }
    }

    @Override
    public void set(T value, int index) {
        if ((index < 0) || (index > countOfNodes - 1)) {
            System.out.println("incorrect index!");
        } else {
            Node<T> currentNode = getNode(index);
            currentNode.item = value;
        }
    }

    @Override
    public T remove(int index) {
        T removed = null;
        if ((index < 0) || (index > countOfNodes - 1)) {
            System.out.println("incorrect index!");
        } else {
            Node<T> currentNode = getNode(index);
            if ((currentNode.next != null) & (currentNode.prev != null)) {
                currentNode.next.prev = currentNode.prev;
                currentNode.prev.next = currentNode.next;
            } else if ((currentNode.prev == null) & (currentNode.next != null)) {
                first = currentNode.next;
                first.prev = null;
            } else if (currentNode.prev != null) {
                last = currentNode.prev;
                last.next = null;
            }
            removed = currentNode.item;
            countOfNodes--;
        }
        return removed;
    }

    @Override
    public T remove(T t) {
        int tempIndex = 0;
        Node<T> currentNode = first;
        for (int i = 0; i < countOfNodes; i++) {
            if (currentNode.item == t) {
                break;
            }
            currentNode = currentNode.next;
            tempIndex++;
        }
        return remove(tempIndex);
    }

    @Override
    public int size() {
        return countOfNodes;
    }

    @Override
    public boolean isEmpty() {
        return countOfNodes == 0;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("[");
        Node<T> currentNode = first;
        if (countOfNodes > 0) {
            for (int i = 0; i < countOfNodes; i++) {
                output.append(currentNode.item).append(", ");
                currentNode = currentNode.next;
            }
            output.replace(output.length() - 2, output.length(), "]");
        } else {
            output.append("]");
        }
        return output.toString();
    }

    /*
        This method is added to this class to compare
        the result of executing methods of this class with
        the result of executing the appropriate methods of the
        java.util.LinkedList class in the MyLinkedListTest class
    */
    @Override
    public T[] toArray() {
        T[] array = (T[]) new Object[size()];
        for (int i = 0; i < size(); i++) {
            array[i] = get(i);
        }
        return array;
    }
}
