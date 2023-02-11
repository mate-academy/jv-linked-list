package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private  int size;

   private static class Node<T>{
        T value;
        Node<T> prev;
        Node<T> next;

       public Node(T value, Node<T> prev, Node<T> next) {
           this.value = value;
           this.prev = prev;
           this.next = next;
       }
   }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value,tail,head);
            if (head == null ) {
               tail = newNode;
               head = newNode;

            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
                tail.next = null;
            }
            size++;
        }


    @Override
    public void add(T value, int index) {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        Node<T> newNode = new Node(value,tail,head);
        if (head == null){
            add(value);
        }
        else if (index == size) {
           add(value);
        }
        else {
            Node<T> nodeFind = head;
            for (int i = 0; i < index; i++){
                nodeFind = nodeFind.next;
            }
            newNode.next = nodeFind.next;
            nodeFind.next = newNode;
            newNode.prev = nodeFind;
            newNode.next.prev = newNode;

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
        indexCheck(index);

        Node<T> findByIndex = head;
        for (int i = 0; i < index; i++){
            findByIndex = findByIndex.next;
        }
        System.out.println(findByIndex.value);
        return findByIndex.value;
    }

    @Override
    public T set(T value, int index) {
       if(value == null || index > size-1 || index < 0){
           throw new IndexOutOfBoundsException("value or index not found " + "value - " + value + ", "
                   + "index - " + index);
       }
        Node<T>removeNode = head;
        for (int i = 1; i < index; i++){
            removeNode = removeNode.next;
        }
        return removeNode.value = value;
    }

    @Override
    public T remove(int index) {
       indexCheck(index);
       Node<T>removeNode = head;
       if(index == 0){
          head = head.next;
       }else if(index == size-1){
          tail = removeNode.prev;
        }else{
           for (int i = 0; i < index; i++){
               removeNode = removeNode.next;
           }
           removeNode.prev.next = removeNode.next;
           removeNode.next.prev = removeNode.prev;
       }
        size--;
        return removeNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeObject = head;
        while (removeObject != null) {
            if (removeObject.value == object || object != null && object.equals(removeObject.value)) {
                if (removeObject == head) {
                    head = removeObject.next;
                } else if (removeObject == tail) {
                    tail = removeObject.prev;
                } else {
                    removeObject.next.prev = removeObject.prev;
                    removeObject.prev.next = removeObject.next;
                }
                size--;
                return true;
            }
            removeObject = removeObject.next;
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

    private void indexCheck(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("index out of bounds " + index);
        }
    }

    private void findNode(int index){
       Node removeNode = head;
        for (int i = 1; i < index; i++){
            removeNode = removeNode.next;
        }
    }

}
