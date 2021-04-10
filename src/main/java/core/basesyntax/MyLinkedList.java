package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String MESSAGE_INDEX_OUT_OF_BOUNDS_EXCEPTION
            = "The index passed to the method is invalid";
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        if (first == null) {
            first = new Node(value);
            last = first;
        } else {
            Node<T> newNode = new Node(last, value, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(MESSAGE_INDEX_OUT_OF_BOUNDS_EXCEPTION);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeWithInputIndex = getNodeNodeByIndex(index);
        Node<T> newNode = new Node(nodeWithInputIndex.prev, value, nodeWithInputIndex);
        nodeWithInputIndex.prev = newNode;
        if (index == 0) {
            first = newNode;
        }
        if (index != 0) {
            newNode.prev.next = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(MESSAGE_INDEX_OUT_OF_BOUNDS_EXCEPTION);
        }
        Node<T> nodeWithInputIndex = getNodeNodeByIndex(index);
        return nodeWithInputIndex.item;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(MESSAGE_INDEX_OUT_OF_BOUNDS_EXCEPTION);
        }
        Node<T> actualNode = getNodeNodeByIndex(index);
        T oldValue = actualNode.item;
        actualNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(MESSAGE_INDEX_OUT_OF_BOUNDS_EXCEPTION);
        }
        Node<T> nodeForDeletion = getNodeNodeByIndex(index);
        unlinkNode(nodeForDeletion);
        return nodeForDeletion.item;
    }

    @Override
    public boolean remove(T item) {
        Node<T> nodeForDeletion = getNodeByItem(item);
        if (nodeForDeletion == null) {
            return false;
        }
        unlinkNode(nodeForDeletion);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNodeNodeByIndex(int index) {
        if (size / 2 > index) {
            return getNodeFromFirstHalfOfList(index);
        } else {
            return getNodeFromSecondHalfOfList(index);
        }
    }

    private Node<T> getNodeFromFirstHalfOfList(int index) {
        int indexCounter = 0;
        Node<T> nodeWithInputIndex = first;
        while (indexCounter < index) {
            nodeWithInputIndex = nodeWithInputIndex.next;
            indexCounter++;
        }
        return nodeWithInputIndex;
    }

    private Node<T> getNodeFromSecondHalfOfList(int index) {
        int indexCounter = size - 1;
        Node<T> nodeWithInputIndex = last;
        while (indexCounter > index) {
            nodeWithInputIndex = nodeWithInputIndex.prev;
            indexCounter--;
        }
        return nodeWithInputIndex;
    }

    private void unlinkNode(Node<T> nodeForDeletion) {
        if (nodeForDeletion == first) {
            deleteFirstNode(nodeForDeletion);
        } else if (nodeForDeletion == last) {
            deleteLastNode(nodeForDeletion);
        } else {
            nodeForDeletion.next.prev = nodeForDeletion.prev;
            nodeForDeletion.prev.next = nodeForDeletion.next;
        }
        size--;
    }

    private void deleteLastNode(Node<T> nodeForDeletion) {
        nodeForDeletion.prev.next = null;
        last = nodeForDeletion.prev;
    }

    private void deleteFirstNode(Node<T> nodeForDeletion) {
        first = nodeForDeletion.next;
        if (first != null) {
            nodeForDeletion.next.prev = null;
        }
    }

    private Node<T> getNodeByItem(T item) {
        Node<T> node = first;
        while (node != null && !node.equalsItems(item)) {
            node = node.next;
        }
        return node;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item) {
            this.item = item;
        }

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

        private boolean equalsItems(T newItem) {
            return (newItem == this.item || newItem != null && newItem.equals(this.item));
        }
    }
}
