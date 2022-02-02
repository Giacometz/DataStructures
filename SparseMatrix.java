// package sparsematrix;
import java.io.*;
import java.text.*;
import java.util.*;

class Node
{

  int row; // the row this node is in.
  int col; // the column this node is in.
  int value; // the value to be stored
  Node down; // a pointer pointing to the next non-zero element below it or
  // a  dummy node.
  Node right; // a pointer pointing to the next non-zero element to the right of
  // it or a dummy node.

  /*
This constructor creates a new Node object based on the parameters
   */

  public Node(int row, int col, int value, Node down, Node right)
  {

    this.row = row;
    this.col = col;
    this.value = value;
    this.down = down;
    this.right = right;
  }

}

public class SparseMatrix
{

  Node tlCorner; // points to the top left corner of linked list structure
  int maxRow; // number of rows
  int maxCol; // number of columns

  /*
   This constructor for the SparseMatrix accepts bounds for it and
   creates a structure for the SparseMatrix to be used.
   */

  public SparseMatrix(int rowsize, int colsize)
  {

    maxRow = rowsize;
    maxCol = colsize;
    Node lastRow = new Node(rowsize + 1, 0, 0, null, null); // creates a dummy
   
    Node lastCol = new Node(0, colsize + 1, 0, null, null); // creates a dummy
    
    tlCorner = new Node(0, 0, 0, lastRow, lastCol); // creates the tlCorner node that will be used extensivley

  }

  /*
getValue returns the integer value stored at the coordinates passed to it.
   
   */

  public int getValue(int row, int col)
  {

    Node curr; // the node that will traverse the sparse matrix
    int value = 0; // the value to be returned. If the node does not exist,
    // it will return 0.

    curr = find(row, col); // utilizes the find method to return the
    // node
    // at row, col if one exists.

    if (curr != null) // if a node was found
    value = curr.value; // the value is returned

    return value;

  }

  /*
insert sets a particular coordinate in the SparseMatrix to a certain value.
   */

  public void insert(int row, int col, int newEntryValue)
  {

    Node above; // the node above the node that we wish to deal with
    Node prev = null; // previous node used to manage links
    Node curr; // current node
    int oldValue; // the old value at that coordinate

    curr = find(row, col); // makes curr the old node if one exists

    if (curr != null) // if one exists, it merely changes the value stored in
    // it.
    {
      curr.value = newEntryValue;
      if (curr.value == 0) // if it changes that value to 0, it removes that
      // node.
      {
        findDown(row, col).down = curr.down; // bypasses that node in its
        // column
        findRight(row, col).right = curr.right; // bypasses the node in its row
        if (RowEmpty(row)) // if that row is now empty, it will remove it
        deleteRow(row);
        if (isColEmpty(col)) // if that column is now empty, it will remove it.
        deleteCol(col);
      }
    }

    else
    // if that node does not yet exist, it will create it
    {

      if (find(row, 0) == null) // if the row does not exist, it is
      // created
      AddRow(row);
      if (find(0, col) == null) // if the column does not exist, it is
      // created
      AddCol(col);

      curr = tlCorner;

      // traverses the linked list until curr is pointing to the node after the
      // node we
      // wish to create will be, and prev points to the node before it
      while (curr.row < row)
        curr = curr.down;

      while (curr.col < col)
      {
        prev = curr;
        curr = curr.right;
      }

      // makes the new node, pointed to by the node to the left of it, pointing
      // to the
      // node to the right and below it
      prev.right = new Node(row, col, newEntryValue, findDown(row, col).down,
          curr);

      // creates a link from the node above it to the node just created
      findDown(row, col).down = prev.right;

    }

  }

  /*
find is a private method that returns the node at a specified coordinate or null is there is no node at that coordinate

   */

  private Node find(int row, int col)
  {

    Node curr = tlCorner;
    Node found = null; // the node to be returned. Remains null if not found.

    while (curr.row < row)
      // moves to the row we wish to find
      curr = curr.down;
    if (curr.row == row) // if that row is present, traverses to the column we
    // wish to find
    {
      while (curr.col < col)
        curr = curr.right;
      if (curr.col == col) // if that column exists, then we have found the
      // node and return it.
      found = curr;
    }

    return found;

  }


  /*
findDown is used by insert to determine the Node that would be directly above a node at a specified coordinate
   */

  private Node findDown(int row, int col)
  {

    Node curr = tlCorner;
    Node prev = null;

    // traverses right across the linked list to the correct column first
    while (curr.col < col)
      curr = curr.right;

    // then traverses down until curr is the node below the specified
    // coordinates
    // and prev is the node above the specified coordinates
    while (curr.row < row)
    {
      prev = curr;
      curr = curr.down;
    }

    return prev;

  }


  /*
findRight is used by insert to determine the Node that would be directly left of a node at a specified coordinate
   */

  private Node findRight(int row, int col)
  {

    Node curr = tlCorner;
    Node prev = null;

    // traverses down across the linked list to the correct row first
    while (curr.row < row)
      curr = curr.down;

    // then traverses right until curr is the node right of the specified
    // coordinates
    // and prev is the node left of the specified coordinates
    while (curr.col < col)
    {
      prev = curr;
      curr = curr.right;
    }

    return prev;

  }


  /**
deleteRow deletes a specific row by bypassing the first dummy
   */

  private void deleteRow(int row)
  {

    Node prev = null;
    Node curr = tlCorner;

    // traverses down until curr is the row header to be deleted and prev
    // is the row header after it.
    while (curr.row < row)
    {
      prev = curr;
      curr = curr.down;
    }

    // bypasses the empty row
    prev.down = curr.down;

  }

  /*
  deleteCol deletes a specific column by bypassing the first dummy node. This is only used if we have confirmed that the column is indeed empty.
   */

  private void deleteCol(int col)
  {

    Node prev = null;
    Node curr = tlCorner;

    // traverses right until curr is the column header to be deleted and prev
    // is the column header after it.
    while (curr.col < col)
    {
      prev = curr;
      curr = curr.right;
    }

    // bypasses the empty column
    prev.right = curr.right;

  }

  /*
AddRow creates a new empty row by creating the header and trailer nodes for that row.
   */

  private void AddRow(int row)
  {

    Node prev = null;
    Node curr = tlCorner;

    // traverses down until curr is the row header to be deleted and prev
    // is the row header after it.
    while (curr.row < row)
    {
      prev = curr;
      curr = curr.down;
    }

    // creates a new header node that points to a trailer node which points to
    // nothing.
    prev.down = new Node(row, 0, 0, curr, new Node(row, maxCol + 1, 0, null,
        null));

  }

  /**
AddCol creates a new empty column by creating the header and trailer nodes for that column.
   */

  private void AddCol(int col)
  {

    Node prev = null;
    Node curr = tlCorner;

    // traverses right until curr is the column header to be deleted and prev
    // is the column header after it.
    while (curr.col < col)
    {
      prev = curr;
      curr = curr.right;
    }

    // creates a new header node that points to a trailer node which points to
    // nothing.
    prev.right = new Node(0, col, 0, new Node(maxRow + 1, col, 0, null, null),
        curr);

  }

  /**
RowEmpty determines if the row is empty; that is, there are only two nodes, a header and trailer
   */

  private boolean RowEmpty(int row)
  {

    boolean rowEmpty = true;
    Node test = tlCorner;

    // traverses the row headers
    while (test.row < row)
      test = test.down;

    // if the correct row header is found, determine if there are only 2 nodes.
    if (test.row == row)

    // if so, then return false as there are elements.
    if (test.right.right != null) rowEmpty = false;

    return rowEmpty;

  }

  /*
isColEmpty determines if the column is empty; that is, there are only two nodes, a header and trailer

   */

  private boolean isColEmpty(int col)
  {

    boolean colEmpty = true;
    Node test = tlCorner;

    // traverses the column headers
    while (test.col < col)
      test = test.right;

    // if the correct column header is found, determine if there are only 2
    // nodes.
    if (test.col == col)

    // if so, then return false as there are elements.
    if (test.down.down != null) colEmpty = false;

    return colEmpty;

  }
  
  private void printMatrix(int maxRow , int maxCol)
  {   int row,col;
     int i;
    int entry;
  
      for (row = 1; row <= maxRow; row++)
    {
      for (col = 1; col <= maxCol; col++)
      {
        entry = getValue(row, col);
        if (entry == 0) System.out.print("  0");
        else
          System.out.print(" " + entry);
      } 
      System.out.println();
    } 
  }
  
  private void printRowCol(int maxRow , int maxCol)
  { int row,col;
    int i;
    int entry;
  
      for (row = 1; row <= maxRow; row++)
    {
      for (col = 1; col <= maxCol; col++)
      {
        entry = getValue(row, col);
        
            System.out.print('<');
            System.out.print(row);
            System.out.print(',');
            System.out.print(col);
            System.out.print('>');
            System.out.println(" "+entry);
        
        
        } 
      
    } 
  }
  
   public static void main(String[] args) {

    SparseMatrix sm = new SparseMatrix(20, 15);

    // Other variables.
    int i;
    int row;
    int col;
    int entry;

    System.out.println();
    System.out.println("The matrix is " + 20 + " x " + 15 + ".");
    System.out.println();

    System.out.println("Adding value 80 to position [2,5].");
    sm.insert(2, 5, 80);
    System.out.println("Adding value 81 to position [13,5].");
    sm.insert(13, 5, 81);
    System.out.println("Adding value 82 to position [2,15].");
    sm.insert(2, 15, 82);
    System.out.println("Adding value 83 to position [8,8].");
    sm.insert(8, 8, 83);
    System.out.println("Adding value 84 to position [20,15].");
    sm.insert(20, 15, 84);
    System.out.println("Adding value 85 to position [12,13].");
    sm.insert(12, 13, 85);
    System.out.println("Adding value 86 to position [1,6].");
    sm.insert(1, 6, 86);
    System.out.println("Adding value 87 to position [3,3].");
    sm.insert(3, 3, 87);
    System.out.println("Adding value 88 to position [1,15].");
    sm.insert(1, 15, 88);
    System.out.println("Adding value 89 to position [12,6].");
    sm.insert(12, 6, 89);
    System.out.println("Adding value 90 to position [6,4].");
    sm.insert(6, 4, 90);

    System.out.println();
    System.out.println("The entire matrix:");
    System.out.println();
    
    sm.printRowCol(20,15);

    System.out.println();
    }  
}


   

