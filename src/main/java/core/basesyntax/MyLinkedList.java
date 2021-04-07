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
            last.setNext(newNode);
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
        if (index == 0) {
            prepend(value);
            return;
        }
        if (index < size) {
            addByIndexAtTheMiddle(value, index);
            return;
        }
        add(value);
    }

    private void addByIndexAtTheMiddle(T value, int index) {
        Node<T> nodeWithInputIndex = getNodeNodeByIndex(index);
        Node<T> newNode = new Node(nodeWithInputIndex.getPrev(), value, nodeWithInputIndex);
        nodeWithInputIndex.getPrev().setNext(newNode);
        nodeWithInputIndex.setPrev(newNode);
        size++;
    }

    private void prepend(T value) {
        Node<T> nodeWithInputIndex = getNodeNodeByIndex(0);
        if (nodeWithInputIndex == null) {
            add(value);
            return;
        }
        Node<T> newNode = new Node(nodeWithInputIndex.getPrev(), value, nodeWithInputIndex);
        nodeWithInputIndex.setPrev(newNode);
        first = newNode;
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
        return nodeWithInputIndex.getItem();
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
            nodeWithInputIndex = nodeWithInputIndex.getNext();
            indexCounter++;
        }
        return nodeWithInputIndex;
    }

    private Node<T> getNodeFromSecondHalfOfList(int index) {
        int indexCounter = size - 1;
        Node<T> nodeWithInputIndex = last;
        while (indexCounter > index) {
            nodeWithInputIndex = nodeWithInputIndex.getPrev();
            indexCounter--;
        }
        return nodeWithInputIndex;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(MESSAGE_INDEX_OUT_OF_BOUNDS_EXCEPTION);
        }
        Node<T> actualNode = getNodeNodeByIndex(index);
        T oldValue = actualNode.getItem();
        actualNode.setItem(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(MESSAGE_INDEX_OUT_OF_BOUNDS_EXCEPTION);
        }
        Node<T> nodeForDeletion = getNodeNodeByIndex(index);
        unlinkNode(nodeForDeletion);
        return nodeForDeletion.getItem();
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

    private void unlinkNode(Node<T> nodeForDeletion) {
        if (nodeForDeletion == first) {
            deleteFirstNode(nodeForDeletion);
        } else if (nodeForDeletion == last) {
            deleteLastNode(nodeForDeletion);
        } else {
            nodeForDeletion.getNext().setPrev(nodeForDeletion.getPrev());
            nodeForDeletion.getPrev().setNext(nodeForDeletion.getNext());
        }
        size--;
    }

    private void deleteLastNode(Node<T> nodeForDeletion) {
        nodeForDeletion.getPrev().setNext(null);
        last = nodeForDeletion.getPrev();
    }

    private void deleteFirstNode(Node<T> nodeForDeletion) {
        first = nodeForDeletion.getNext();
        if (first != null) {
            nodeForDeletion.getNext().setPrev(null);
        }
    }

    private Node<T> getNodeByItem(T item) {
        Node<T> nodeForDeletion = first;
        while (nodeForDeletion != null) {
            if (nodeForDeletion.equalsItems(item)) {
                break;
            }
            nodeForDeletion = nodeForDeletion.getNext();
        }
        return nodeForDeletion;
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

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        private boolean equalsItems(T newItem) {
            return (newItem == this.getItem() || newItem != null && newItem.equals(this.getItem()));
        }
    }
}
