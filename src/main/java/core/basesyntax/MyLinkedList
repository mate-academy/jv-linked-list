package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size; // The number of elements in the linked list

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = newNode; // If the list is empty, set the new node as head
        } else {
            tail.next = newNode; // Connect the current tail to the new node
        }
        tail = newNode; // Update the tail to the new node
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode;
            if (index == 0) {
                newNode = new Node<>(null, value, head);
                head.prev = newNode;
                head = newNode;
            } else {
                // Add an element at the specified index
                Node<T> nextNode = getNode(index);
                newNode = new Node<T>(nextNode.prev, value, nextNode);
                nextNode.prev.next = newNode;
                nextNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i)); // Add each element from the given list
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value; // Get the value at the specified index
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T replacedValue = node.value;
        node.value = value; // Set the new value at the specified index
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNode(index);
        unlink(removedNode); // Remove the node at the specified index
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = head;
        for (int i = 0; i < size; i++) {
            if ((removedNode.value == object)
                    || (removedNode.value != null && removedNode.value.equals(object))) {
                unlink(removedNode); // Remove the node with the specified value
                return true;
            }
            removedNode = removedNode.next;
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
        private T value; // The value stored in the node
        private Node<T> prev; // Reference to the previous node
        private Node<T> next; // Reference to the next node

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    // Helper method to check if the index is valid
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Non-existent list index");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index); // Check if the index is valid
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    // Helper method to unlink (remove) a node from the linked list
    private void unlink(Node<T> currentNode) {
        if (currentNode == head) {
            head = currentNode.next; // Update the head if the node is the first one
        } else if (currentNode == tail) {
            tail = currentNode.prev; // Update the tail if the node is the last one
        } else {
            // Connect the previous node to the next node to remove the current node
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
    }
}
