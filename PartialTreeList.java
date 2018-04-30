package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {
    
    /**
     * Inner class - to build the partial tree circular linked list 
     * 
     */
    public static class Node {
        /**
         * Partial tree
         */
        public PartialTree tree;
        
        /**
         * Next node in linked list
         */
        public Node next;
        
        /**
         * Initializes this node by setting the tree part to the given tree,
         * and setting next part to null
         * 
         * @param tree Partial tree
         */
        public Node(PartialTree tree) {
            this.tree = tree;
            next = null;
        }
    }

    /**
     * Pointer to last node of the circular linked list
     */
    private Node rear;
    
    /**
     * Number of nodes in the CLL
     */
    private int size;
    
    /**
     * Initializes this list to empty
     */
    public PartialTreeList() {
        rear = null;
        size = 0;
    }

    /**
     * Adds a new tree to the end of the list
     * 
     * @param tree Tree to be added to the end of the list
     */
    public void append(PartialTree tree) {
        Node ptr = new Node(tree);
        if (rear == null) {
            ptr.next = ptr;
        } else {
            ptr.next = rear.next;
            rear.next = ptr;
        }
        rear = ptr;
        size++;
    }

    /**
     * Removes the tree that is at the front of the list.
     * 
     * @return The tree that is removed from the front
     * @throws NoSuchElementException If the list is empty
     */
    public PartialTree remove() 
    throws NoSuchElementException {
        if (this==null || this.size()==0) {
            throw new NoSuchElementException("nothing is there");
        }
        Iterator<PartialTree> gothrough =iterator();
        PartialTree shrub = gothrough.next();
        rear.next=rear.next.next;
        size=size-1;
        return shrub;
    }
        
            

    /**
     * Removes the tree in this list that contains a given vertex.
     * 
     * @param vertex Vertex whose tree is to be removed
     * @return The tree that is removed
     * @throws NoSuchElementException If there is no matching tree
     */
    public PartialTree removeTreeContaining(Vertex vertex) 
    throws NoSuchElementException {
        
        if (rear==null) {
        throw new NoSuchElementException("you cant do this becuase ITS EMPTYYYYY");
    }
        // tree to return
        PartialTree ftree = null;
        Node pointer = rear.next;
        
        if (pointer == rear){
    //     System.out.println("only one node in the tree");
                Vertex dad = pointer.tree.getRoot();
                while(dad !=dad.parent) {
                if (dad == vertex||dad.parent == vertex){
            //  System.out.println("code finished1");
                    ftree = pointer.tree;
                  size=size-1;
                  rear = null;
                  return ftree;
              }
                dad=dad.parent;
          }
        }
          //resent current to last node
        pointer = rear;
        
          do{
              
              if(pointer.tree.getRoot().equals(vertex)){
                //  System.out.println("found node on  root");
                  // remove tree
                 ftree = pointer.tree;
                  size=size-1;
                  // gets the node right before the node you want to delete
                  Node remove = pointer;
                  do {
                     remove = remove.next;
                  }
                  while(remove.next != pointer);
                  
                  Node rearPrv = rear;
                  do {
                     rearPrv = rearPrv.next;
                  }
                  while (rearPrv.next != rear);
                      
                  // if the last node you must delete
                  if(pointer == rear){
                      rear =rearPrv;
                  }
                      remove.next = pointer.next;

                  return ftree;
              }
              
             Vertex vert = pointer.tree.getRoot();
             while (vert !=vert.parent) {
                if (vert == vertex||vertex == vert.parent){
                        
                      // remove tree
                    ftree = pointer.tree;
                      size=size-1;
                      Node remove = pointer;
                      do {
                          remove = remove.next;
                      }
                      while(remove.next != pointer);
                
                      Node rearPrv = rear;
                      do {
                         rearPrv = rearPrv.next;
                      }
                      while (rearPrv.next != rear);
                         
                      
                      // if the last node you must delete
                      if(pointer == rear){
                          rear =rearPrv;
                          
                      }
                          remove.next = pointer.next;
                      return ftree;

                }
                 vert= vert.parent;
             }
  //            
            pointer = pointer.next;
          }while(pointer != rear);
           
          if (ftree == null){
              throw new NoSuchElementException("nothing is in the list; what happened!!!!! :( Emptdafy.");
//            return returnTree;
          }
          return null;
      }

    /**
     * Gives the number of trees in this list
     * 
     * @return Number of trees
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns an Iterator that can be used to step through the trees in this list.
     * The iterator does NOT support remove.
     * 
     * @return Iterator for this list
     */
    public Iterator<PartialTree> iterator() {
        return new PartialTreeListIterator(this);
    }
    
    private class PartialTreeListIterator implements Iterator<PartialTree> {
        
        private PartialTreeList.Node ptr;
        private int rest;
        
        public PartialTreeListIterator(PartialTreeList target) {
            rest = target.size;
            ptr = rest > 0 ? target.rear.next : null;
        }
        
        public PartialTree next() 
        throws NoSuchElementException {
            if (rest <= 0) {
                throw new NoSuchElementException();
            }
            PartialTree ret = ptr.tree;
            ptr = ptr.next;
            rest--;
            return ret;
        }
        
        public boolean hasNext() {
            return rest != 0;
        }
        
        public void remove() 
        throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
        
    }
    
    // this is the right one
}

