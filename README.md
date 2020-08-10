# jv-linked-list
Дано класс MyLinkedList который имплементирует интерфейс MyLinkedListInterface. Реализовать в нём свой LinkedList 
private Node<T> nodeByIndex(int index) {
        check(index);
        Node<T> resultNode;
        if (index >= size / 2) {
            resultNode = last;
            while (index < size) {
                resultNode = resultNode.prev;
                index++;
            }
        } else {
            resultNode = first;
            while (index >= 0) {
                resultNode = resultNode.prev;
                index--;
            }
        }
        return resultNode;
    }
