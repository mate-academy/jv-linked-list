package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        this.size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node(null, value, null);
        if (this.first == null) {
            this.first = newNode;
            this.last = newNode;
        } else {
            newNode.setPrev(this.last);
            this.last.setNext(newNode);
            this.last = newNode;
        }
        this.size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException("This index is not valid");
        }
        if (index == 0) {
            addByIndexAtTheBegin(value, index);
            return;
        }
        if (index < this.size) {
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
        this.size++;
    }

    private void addByIndexAtTheBegin(T value, int index) {
        Node<T> nodeWithInputIndex = getNodeNodeByIndex(index);
        if (nodeWithInputIndex == null) {
            add(value);
            return;
        }
        Node<T> newNode = new Node(nodeWithInputIndex.getPrev(), value, nodeWithInputIndex);
        nodeWithInputIndex.setPrev(newNode);
        this.first = newNode;
        this.size++;
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
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("This index is not valid");
        }
        Node<T> nodeWithInputIndex = getNodeNodeByIndex(index);
        return nodeWithInputIndex.getItem();
    }

    private Node<T> getNodeNodeByIndex(int index) {
        int indexCounter = 0;
        Node<T> nodeWithInputIndex = this.first;
        while (indexCounter < index) {
            nodeWithInputIndex = nodeWithInputIndex.getNext();
            indexCounter++;
        }
        return nodeWithInputIndex;
    }

    @Override
    public T set(T value, int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("This index is not valid");
        }
        Node<T> actualNode = getNodeNodeByIndex(index);
        T oldValue = actualNode.getItem();
        actualNode.setItem(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("This index is not valid");
        }
        Node<T> nodeForDeletion = getNodeNodeByIndex(index);
        deleteInputNode(nodeForDeletion);
        return nodeForDeletion.getItem();
    }

    @Override
    public boolean remove(T item) {
        Node<T> nodeForDeletion = getNodeByItem(item);
        if (nodeForDeletion == null) {
            return false;
        }
        deleteInputNode(nodeForDeletion);
        return true;
    }

    private void deleteInputNode(Node<T> nodeForDeletion) {
        if (nodeForDeletion == this.first) {
            deleteFirstNode(nodeForDeletion);
        } else if (nodeForDeletion == this.last) {
            deleteLastNode(nodeForDeletion);
        } else {
            nodeForDeletion.getNext().setPrev(nodeForDeletion.getPrev());
            nodeForDeletion.getPrev().setNext(nodeForDeletion.getNext());
        }
        this.size--;
    }

    private void deleteLastNode(Node<T> nodeForDeletion) {
        nodeForDeletion.getPrev().setNext(null);
        this.last = nodeForDeletion.getPrev();
    }

    private void deleteFirstNode(Node<T> nodeForDeletion) {
        this.first = nodeForDeletion.getNext();
        if (this.first != null) {
            nodeForDeletion.getNext().setPrev(null);
        }
    }

    private Node<T> getNodeByItem(T item) {
        Node<T> nodeForDeletion = this.first;
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
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
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
