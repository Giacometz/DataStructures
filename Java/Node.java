public class Node {
    Node next;
    Double num;

    Node(Double num, Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    Node(double num) {
        this.num = num;
    }
    Node() {}

    public boolean internalRecursiveSearch(Double match) {
        if (this.num.equals((Double) match))
            return true;
        else if (this.next == null && !(this.num.equals((Double) match)))
            return false;
        else return this.next.internalRecursiveSearch(match);
    }
}