package apps;

import structures.*;


import java.util.ArrayList;

public class MST {
    
    /**
     * Initializes the algorithm by building single-vertex partial trees
     * 
     * @param graph Graph for which the MST is to be found
     * @return The initial partial tree list
     */
    public static PartialTreeList initialize(Graph graph) {
    //  Vertex [] vertexess = graph.vertices;
        if (graph==null) {
            return null;
        }
        PartialTreeList list = new PartialTreeList();
        int count = 0;
        while (count< graph.vertices.length) {
            Vertex vnum = graph.vertices[count];
            Vertex.Neighbor pointer; 
            PartialTree item = new PartialTree(vnum);
            MinHeap<PartialTree.Arc> nextup = item.getArcs();
                pointer =vnum.neighbors;
                while(pointer!=null) {
                PartialTree.Arc edges = new PartialTree.Arc(vnum, pointer.vertex, pointer.weight);
                
                nextup.insert(edges);
                pointer=pointer.next;
            }
//            System.out.println(item);
            list.append(item);
            count++;
            
        }
//System.out.println("hi there");
        return list;
    }

    /**
     * Executes the algorithm on a graph, starting with the initial partial tree list
     * 
     * @param ptlist Initial partial tree list
     * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
     */
    public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
       
      if (ptlist==null){
            return null;
        }

      ArrayList<PartialTree.Arc> finalarc = new ArrayList<>();
      
      while (ptlist.size()>1) {
          PartialTree orgin = ptlist.remove();
          MinHeap<PartialTree.Arc> second = orgin.getArcs();  
          PartialTree.Arc bend = second.deleteMin();
          int location = 0;
 
          do {
             location =0;
                     Vertex vert = orgin.getRoot();
                     while(vert!=vert.parent) {
                     if (bend.v2 == vert||bend.v2 == vert.parent){
                        location = 1;
                        bend = second.deleteMin();
                        break;
                    }   
                    vert=vert.parent;
              }
          } while (location==1);
    
          finalarc.add(bend);  
          
          PartialTree ptn = ptlist.removeTreeContaining(bend.v2);
          MinHeap<PartialTree.Arc> xxx= ptn.getArcs();
          orgin.getRoot().parent = ptn.getRoot();
          second.merge(xxx);
          ptlist.append(orgin);
      }

      return finalarc;
      }
}

// this is the right one 