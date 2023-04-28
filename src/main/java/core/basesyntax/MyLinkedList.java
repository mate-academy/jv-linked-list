package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value, tail, null);
        if (head == null) {
            head = node;
            tail = head;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        isValidIndexForAdd(index);
        if (isEmpty()) {
            add(value);
        } else if (index == 0) {
            addToHead(value);
        } else {
            Node<T> previous = findNodeByindex(index);
            if (previous == null) {
                addToTail(value);
            } else {
                insert(value, previous);
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        isValidIndexForSetAndGet(index);
        return findNodeByindex(index).value;
    }

    @Override
    public T set(T value, int index) {
        isValidIndexForSetAndGet(index);
        Node<T> foundNode = findNodeByindex(index);
        T oldValue = foundNode.value;
        foundNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> deletedElement = findNodeByindex(index);
        if (deletedElement == null) {
            throw new IndexOutOfBoundsException("wrond index: " + index + ", for empty list");
        }
        unlink(deletedElement);
        size--;
        return deletedElement.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> deletedElement = findNodeByElement(object);
        boolean isDeleted = unlink(deletedElement);
        if (isDeleted) {
            size--;
        }
        return isDeleted;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void insert(T value, Node<T> previous) {
        Node<T> newNode = new Node(value, previous.prev, previous);
        previous.prev.next = newNode;
        previous.prev = newNode;
        size++;
    }

    private void addToHead(T value) {
        Node<T> newNode = new Node<>(value, null, head);
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void addToTail(T value) {
        Node<T> newNode = new Node<>(value, tail, null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    private void isValidIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("invalid index: " + index);
        }
    }

    private void isValidIndexForSetAndGet(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("invalid index: " + index);
        }
    }

    private Node<T> findNodeByindex(int index) {
        isValidIndexForAdd(index);
        if (index == 0) {
            return head;
        } else if (index == size - 1) {
            return tail;
        }
        if (index < size / 2) {
            return startFromHead(index);
        } else {
            return startFromTail(index);
        }
    }

    private Node<T> startFromHead(int index) {
        Node<T> curNode = head;
        int counter = 0;
        while (curNode.next != null) {
            if (counter == index) {
                return curNode;
            }
            curNode = curNode.next;
            counter++;
        }
        return null;
    }

    private Node<T> startFromTail(int index) {
        Node<T> curNode = tail;
        int counter = size - 1;
        while (curNode.prev != null) {
            if (counter == index) {
                return curNode;
            }
            curNode = curNode.prev;
            counter--;
        }
        return null;
    }

    private Node<T> findNodeByElement(T element) {
        Node<T> curNode = head;
        while (curNode != null) {
            if ((curNode.value != null && curNode.value.equals(element))
                    || curNode.value == element) {
                return curNode;
            }
            curNode = curNode.next;
        }
        return null;
    }

    private boolean unlink(Node<T> node) {
        if (node == head) {
            head = head.next;
            return true;
        } else if (node == tail) {
            tail = tail.prev;
            return true;
        } else if (node != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return true;
        }
        return false;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
