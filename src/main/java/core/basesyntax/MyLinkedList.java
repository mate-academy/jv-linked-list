package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = tail = new Node<>(null, value, null);
        } else {
            tail = tail.next = new Node<>(tail, value, null);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        addByIndexRoutine(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkSetIndexInBound(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        return setValueRoutine(value, index);
    }

    @Override
    public T remove(int index) {
        return unlinkNodeByIndex(index);
    }

    @Override
    public boolean remove(T object) {
        return unlinkNodeByObject(object);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void addByIndexRoutine(T value, int index) {
        checkIndexInBound(index);
        if (head == null) {
            head = tail = new Node<>(null, value, null);
        } else if (index == 0) {
            head = new Node<>(null, value, head);
        } else if (index == size) {
            tail = tail.next = new Node<>(tail, value, null);
        } else {
            Node<T> currentNode = getNode(index - 1);
            Node<T> node = new Node<>(currentNode, value, currentNode.next);
            currentNode.next.prev = node;
            currentNode.next = node;

        }
        size++;
    }

    private T setValueRoutine(T value, int index) {
        checkSetIndexInBound(index);
        Node<T> nodeToSet;
        if (index == 0) {
            nodeToSet = head;
        } else {
            nodeToSet = getNode(index - 1).next;
        }
        T returnValue = nodeToSet.value;
        nodeToSet.value = value;
        return returnValue;
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index < size / 2 + 1) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i >= index + 1; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (isCurrentNodeValueEquals(value, current)) {
                return current;
            }
            current = current.next;
        }
        return current;
    }

    private boolean isCurrentNodeValueEquals(T value, Node<T> current) {
        return (current.value != null && current.value.equals(value))
                || (current.value == null && value == null);
    }

    private T unlinkNodeByIndex(int index) {
        checkSetIndexInBound(index);
        T removedElement;
        if (index == 0) {
            removedElement = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        } else {
            Node<T> previousNode = getNode(index - 1);
            removedElement = previousNode.next.value;
            if (previousNode != head) {
                previousNode.next = previousNode.next.next;
            }
            if (index == size - 1) {
                tail = previousNode;
            }
        }
        size--;
        return removedElement;
    }

    private boolean unlinkNodeByObject(T object) {
        boolean elementFound = false;
        Node<T> removedElement = getNodeByValue(object);
        if (size == 1) {
            head = tail = null;
            elementFound = true;
        } else if (removedElement == head) {
            removedElement.next.prev = null;
            head = removedElement.next;
            elementFound = true;
        } else if (removedElement == tail) {
            removedElement.prev.next = null;
            tail = removedElement.prev;
            elementFound = true;
        } else if (removedElement != null) {
            removedElement.prev.next = removedElement.next;
            removedElement.next.prev = removedElement.prev;
            elementFound = true;
        }
        if (elementFound) {
            size--;
        }
        return elementFound;
    }

    private void checkIndexInBound(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("No such index exist");
        }
    }

    private void checkSetIndexInBound(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("No such index exist");
        }
    }

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
