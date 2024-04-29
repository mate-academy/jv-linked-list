package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int halfOfSize = size / 2;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node currentNode = new Node<>(null, value, null);
            tail = currentNode;
            head = currentNode;
            size++;
        } else if (size > 0) {
            Node previousNode = head;
            Node currentNode = new Node(previousNode, value, null);
            previousNode.next = currentNode;
            head = currentNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Your index was: " + index);
        }
        if (index == size) {
            add(value);
            return;
        } else if (index == 0 && size > 0) {
            Node newNode = new Node<>(null, value, tail);
            tail.prev = newNode;
            tail = newNode;
            size++;
            return;
        }
        int counter = 0;
        Node currentNode = tail;
        if (index >= halfOfSize) {
            counter = size - 1;
            currentNode = head;
        }
        if (index >= 0 && index < size) {
            while (currentNode != null) {
                if (counter == index) {
                    Node previousNode = currentNode.prev;
                    Node addedNode = new Node(previousNode, value, currentNode);
                    if (previousNode != null) {
                        previousNode.next = addedNode;
                    }
                    currentNode.prev = addedNode;
                    size++;
                }
                if (size < halfOfSize) {
                    counter++;
                    currentNode = currentNode.next;
                } else if (size >= halfOfSize) {
                    counter--;
                    currentNode = currentNode.prev;
                }
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T type : list) {
            Node previousNode = head;
            Node currentNode = new Node(previousNode, type, null);
            previousNode.next = currentNode;
            head = currentNode;
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int counter = 0;
        Node currentNode = tail;
        if (index >= halfOfSize) {
            counter = size - 1;
            currentNode = head;
        }
        while (counter >= 0 && counter < size) {
            if (counter == index) {
                return (T) currentNode.value;
            }
            if (size < halfOfSize) {
                counter++;
                currentNode = currentNode.next;
            } else if (size >= halfOfSize) {
                counter--;
                currentNode = currentNode.prev;
            }
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int counter = 0;
        Node currentNode = tail;
        if (index >= halfOfSize) {
            counter = size - 1;
            currentNode = head;
        }
        while (counter >= 0 && counter < size) {
            if (counter == index) {
                T previousValue = (T) currentNode.value;
                currentNode.value = value;
                return previousValue;
            }
            if (size < halfOfSize) {
                counter++;
                currentNode = currentNode.next;
            } else if (size >= halfOfSize) {
                counter--;
                currentNode = currentNode.prev;
            }
        }
        return (T) currentNode.value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int counter = 0;
        Node currentNode = tail;
        if (index >= halfOfSize) {
            counter = size - 1;
            currentNode = head;
        }
        while (counter >= 0 && counter < size) {
            if (counter == index) {
                T previousValue = (T) currentNode.value;
                unlink(currentNode);
                size--;
                return previousValue;
            }
            if (size < halfOfSize) {
                counter++;
                currentNode = currentNode.next;
            } else if (size >= halfOfSize) {
                counter--;
                currentNode = currentNode.prev;
            }
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = tail;
        int counter = 0;
        while (counter >= 0 && counter < size) {
            if (object != null && object.equals(currentNode.value)
                    || object == null && currentNode.value == null) {
                unlink(currentNode);
                size--;
                return true;
            }
            counter++;
            currentNode = currentNode.next;
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

    public void unlink(Node<T> node) {
        Node previousNode = node.prev;
        Node nextNode = node.next;
        if (previousNode == null && nextNode != null) {
            nextNode.prev = null;
            tail = nextNode;
        } else if (previousNode != null && nextNode == null) {
            previousNode.next = null;
            head = previousNode;
        } else if (previousNode != null && nextNode != null) {
            previousNode.next = nextNode;
            nextNode.prev = previousNode;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
