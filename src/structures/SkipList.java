package structures;

import java.lang.*;
import java.util.*;


public class SkipList {

  //
  // Constants:
  //
  public static final int NOT_FOUND  = -1;    // element=-1 means not found
  public static final int HEADER_KEY = -2;    // key=-2 means header element
  // all keys have to be smaller than this one:
   public static final int NIL_KEY    = Integer.MAX_VALUE;
  
  public static final float OPT_PROB = 0.25f; // optimum probability
  private float prob;            // probability to increase level
  private int maxLevel;                 // upper bound of levels
  private int level;                    // greatest level so far
  private node headNode;  
 /*
  * 
  */
  protected class node{
     protected int key;
     protected Object element;
     protected node[] next;
     protected node(int theKey, Object theElment, int size){
         key=theKey;
         element=theElment;
         next=new node[size+1];
        }
       protected int getLevel() {
    return next.length -1;
  }
   public String toString() {
    String result = "";

    // element data:
    result += " k:" + key + ", v:" + element + ", l:" + getLevel() + ". fwd k: ";

    // key of next pointed nodes:
    for (int i = 0; i <= getLevel(); i++) {
      if (next[i] != null) {
        result += i + ": " + next[i].key + ", ";
      } else {
        result += i + ": nil, ";
      }
    }

    return result;
  }
    }
  ///////////////////////////////////////////////////////////////////////////
  // Constructor (1):
  //   Constructs a new skip list optimized for the given
  //   expected upper bound for the number of nodes.
  //   So if you expect to have 10000 nodes, call the
  //   constructor as SkipList(10000).
  //
  public SkipList(long maxNodes) {
    // call the constructor (2) with a calculated maximum level
    // and probability set to OPT_PROP=0.25
    // Maximum level of list is depending on expected number of nodes
    // (see paper for mathematical background)
    this(OPT_PROB, 
        (int)Math.ceil(Math.log(maxNodes)/Math.log(1/OPT_PROB))-1);
  }

  
  ///////////////////////////////////////////////////////////////////////////
  // Constructor (2):
  //   Constructs a new skip list, where you can directly set the
  //   probability to increase the level of a new node (often 0.25)
  //   and maximum level of node in the list.
  //   If you are not sure, take constructor (1)!
  //
  public SkipList(float probability, int maxLevel) {
    level = 0;                  // level of empty list
    prob = probability;
    maxLevel = level;
    
    // generate the header of the list:
    headNode = new node(HEADER_KEY, null,maxLevel);
    
    // append the "NIL" element to the header:
    node nilElement = 
        new node(NIL_KEY, null, maxLevel);
    for (int i=0; i<=maxLevel; i++) {
      headNode.next[i] = nilElement;
    }

  }

    
  ///////////////////////////////////////////////////////////////////////////
  // level():
  //   Generates with help of randomizer the level of a new element.
  //   The higher a level, the less probable it is (see paper).
  //   Levels begin at 0 (not at 1 like in the paper).
  //
  protected int level() {
    int newLevel = 0;
    while (newLevel<maxLevel && Math.random()<prob ) {
      newLevel++;
    }
    return newLevel;
  }


  ///////////////////////////////////////////////////////////////////////////
  // insert():
  //   Inserts a new node into the list.
  //   If the key already exists, its node is updated to the new element.
  //
  public void insert(int searchKey, Object element) {
    // update holds pointers to next elements on each level;
    // levels run from 0 up to maxLevel:
    node[] update = new node[maxLevel+1];
    
    // init "cursor" element to header:
    node temp= headNode;
    
    // find place to insert the new node:
    for (int i=level; i>=0; i--) {
      while (temp.next[i].key < searchKey) {
        temp = temp.next[i];
      }
      update[i] = temp;
    }
    temp = temp.next[0];
    
    // element with same key is overwritten:
    if (temp.key == searchKey) {
      temp.element = element;
    }

    // or an additional element is inserted:
    else {
      int newLevel = level();
      // element has biggest level seen in this list: update list
      if (newLevel > level) {
        for (int i=level+1; i<=newLevel; i++) {
          update[i] = headNode;
        }
        level = newLevel;
      }

      // allocate new element:
      temp = new node(searchKey,element,newLevel);
      for (short i=0; i<=newLevel; i++) {
        temp.next[i] = update[i].next[i];
        update[i].next[i] = temp;
      }
    }
  }


  ///////////////////////////////////////////////////////////////////////////
  // search():
  //   Search for a given key in list. you get the element associated
  //   with that key or the NOT_FOUND constant.
  //
  public Object search(int searchKey) {
    // init "cursor"-element to header:
    node temp = headNode;
    
    // find element in list:
    for (int i=level; i>=0; i--) {
      node nextElement = temp.next[i];
      while (nextElement.key < searchKey) {
        temp = nextElement;
        nextElement = temp.next[i];
      }
    }
    temp = temp.next[0];
    
    // if key exists return element else return predefined NOT_FOUND:
    if (temp.key == searchKey) {
      return temp.element;
    }
    else {
      return NOT_FOUND;
    }
  }

  
  ///////////////////////////////////////////////////////////////////////////
  // delete():
  //   If a node with the given key exists, remove it from list.
  //
  public void delete(int searchKey) {
    // update holds pointers to next elements of each level
    node update[] = new node[maxLevel+1];

    // init "cursor"-element to header:
    node temp = headNode;
    
    // find element in list:
    for (int i=level; i>=0; i--) {
      node nextElement = temp.next[i];
      while (nextElement.key < searchKey) {
        temp = nextElement;
        nextElement = temp.next[i];
      }
      update[i] = temp;
    }
    temp = temp.next[0];
    
    // element found, so rebuild list without node:
    if (temp.key == searchKey) {
      for (int i=0; i<=level; i++) {
        if (update[i].next[i] == temp) {
          update[i].next[i] = temp.next[i];
        }
      }
      // element can be freed now (would happen automatically):
      temp = null;               // garbage collector does the rest...

      // maybe we have to downcorrect the level of the list: 
      while (level>0  &&  headNode.next[level].key==NIL_KEY) {
        level--;
      }
    }
  }


  ///////////////////////////////////////////////////////////////////////////
  // toString() overwrites java.lang.Object.toString()
  //   Composes a multiline-string describing this list:
  //
  public String toString() {
    // inits:
    String result = "";

    // header info:
    result += "SkipList:\n";
    result += "  probability = " + prob + "\n";
    result += "  level       = " + level + "\n";
    result += "  max. level  = " + maxLevel + "\n";

//traverse the list and collect the levels:
    node temp = headNode.next[0];
    int[] countLevel = new int[maxLevel+1];
    while (temp.key < NIL_KEY) {
      countLevel[temp.getLevel()]++;
      temp = temp.next[0];
    }

    for (int i=maxLevel; i>=0; i--) {
      result += "    Number of Elements at level " + i + " = " + countLevel[i] +"\n";
    }
    return result;
  }


  ///////////////////////////////////////////////////////////////////////////
  // elementsToString()
  //   Composes a multiline-string describing the elements of this list:
  //
  public String getToString() {
    // inits:
    String result = "Elements:\n";

    // all elements:
    node temp = headNode;
    while (temp.key < NIL_KEY) {
      temp = temp.next[0];
      result += temp.toString() + "\n";
    }

    return result;
  }

}