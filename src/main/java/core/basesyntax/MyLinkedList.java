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
        Node<T> newNode = new Node(last, value, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
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
        Node<T> nodeWithInputIndex = getNodeByIndex(index);
        Node<T> newNode = new Node(nodeWithInputIndex.prev, value, nodeWithInputIndex);
        nodeWithInputIndex.prev = newNode;
        if (index == 0) {
            first = newNode;
        } else {
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
        checkIndex(index);
        Node<T> nodeWithInputIndex = getNodeByIndex(index);
        return nodeWithInputIndex.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> actualNode = getNodeByIndex(index);
        T oldValue = actualNode.item;
        actualNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeForDeletion = getNodeByIndex(index);
        unlinkNode(nodeForDeletion);
        return nodeForDeletion.item;
    }

    @Override
    public boolean remove(T item) {
        return searchAndUnlinkNodeByItem(item);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(MESSAGE_INDEX_OUT_OF_BOUNDS_EXCEPTION);
        }
    }

    private Node<T> getNodeByIndex(int index) {
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

    private boolean searchAndUnlinkNodeByItem(T item) {
        Node<T> node = first;
        while (node != null) {
            if (node.item == item || node.item != null && node.item.equals(item)) {
                unlinkNode(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
