package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException; // Import for NoSuchElementException

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        // Create a new node with the given value,
        // linking its 'prev' to the current tail and its 'next' to null
        // (as it will be the new tail).
        Node<T> newNode = new Node<>(value, tail, null);

        // If the list is not empty, link the current tail's 'next' to the new node.
        if (tail != null) {
            tail.next = newNode;
        }

        // Update the tail to be the new node.
        tail = newNode;

        // If the list was empty before this add, the new node is also the head.
        if (head == null) {
            head = newNode;
        }
        size++; // Increment the size of the list.
    }

    @Override
    public void add(T value, int index) {
        // Validate index for add operation.
        validateIndex(index, true);

        // If index equals size, add to end (delegate to add(T value)).
        if (index == size) {
            add(value); // Delegate to the simpler add(T value) method
            return;
        }

        // Find the node that will be immediately after the new node.
        Node<T> nextNode = findNodeByIndex(index);

        // Create the new node, linking it to its previous and next nodes.
        Node<T> newNode = new Node<>(value, nextNode.prev, nextNode);

        // If 'nextNode' has a previous node, update that previous node's
        // 'next' to point to 'newNode'.
        if (nextNode.prev != null) {
            nextNode.prev.next = newNode;
        } else {
            // If 'nextNode' does not have a previous node, it means 'nextNode' was the head.
            // So, 'newNode' becomes the new head.
            head = newNode;
        }

        // Update 'nextNode's 'prev' to point to 'newNode'.
        nextNode.prev = newNode;
        size++; // Increment the size of the list.
    }

    @Override
    public void addAll(List<T> list) {
        // Iterate through the provided list and add each element
        // using the 'add(T value)' method.
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        // Validate the index. For get operations, 'index' must be between 0 and size-1.
        validateIndex(index, false);
        // Find the node at the specified index and return its value.
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        // Validate the index.
        validateIndex(index, false);
        // Find the node at the specified index.
        Node<T> targetNode = findNodeByIndex(index);
        // Store the old value of the node.
        T oldValue = targetNode.value;
        // Update the node's value with the new value.
        targetNode.value = value;
        // Return the old value.
        return oldValue;
    }

    @Override
    public T remove(int index) {
        // Validate the index.
        validateIndex(index, false);
        // Find the node at the specified index and then unlink it from the list.
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head; // Start from the head of the list.

        // Iterate through the list until the end is reached.
        while (current != null) {
            // Check if the current node's value matches the object to be removed.
            // Handles both null values and non-null values using .equals().
            if ((object == null && current.value == null)
                    || (object != null && object.equals(current.value))) {
                unlink(current); // If a match is found, unlink the node.
                return true; // Return true to indicate successful removal.
            }
            current = current.next; // Move to the next node.
        }
        return false; // If the object was not found after iterating through the entire list.
    }

    @Override
    public int size() {
        return size; // Return the current size of the list.
    }

    @Override
    public boolean isEmpty() {
        return size == 0; // Return true if the size is 0, indicating an empty list.
    }

    // Helper method to unlink a given node from the list.
    private T unlink(Node<T> node) {
        final T removedValue = node.value; // Store the value of the node to be removed.

        // Adjust the 'next' link of the previous node.
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            // If the node to be removed is the head, update the head to the next node.
            head = node.next;
        }

        // Adjust the 'prev' link of the next node.
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            // If the node to be removed is the tail, update the tail to the previous node.
            tail = node.prev;
        }

        // Help garbage collection by nulling out references from the removed node.
        // This is good practice to prevent memory leaks in some scenarios.
        node.value = null;
        node.prev = null;
        node.next = null;

        size--; // Decrement the size of the list.
        return removedValue; // Return the value of the removed node.
    }

    // Helper method to find a node by its index.
    private Node<T> findNodeByIndex(int index) {
        // Optimization: Traverse from head or tail based on which is closer to the index.
        Node<T> current;
        if (index < size / 2) { // If the index is in the first half of the list, start from head.
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else { // If the index is in the second half, start from tail.
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current; // Return the found node.
    }

    // Helper method to validate index bounds for various operations.
    // 'isAddOperation' is true for 'add(value, index)' where index can be 'size'.
    // 'isAddOperation' is false for 'get', 'set', 'remove' where index must be within 0 to size-1.
    private void validateIndex(int index, boolean isAddOperation) {
        if (isAddOperation) {
            // For add operations, the index can be from 0 to size (inclusive).
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException(
                        "Index " + index + " is out of bounds for size " + size);
            }
        } else {
            // For get, set, remove operations, the index must be from 0 to size-1.
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException(
                        "Index " + index + " is out of bounds for size " + size);
            }
            // Additionally, for get/set/remove, the list cannot be empty.
            if (size == 0) {
                throw new NoSuchElementException("Cannot perform operation on an empty list.");
            }
        }
    }
}
