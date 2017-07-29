package dijkstrasMethod;

public class Node {

	private long id, degree;

	private String label;

	public Node(long id, String label) {

		this.id = id;
		this.label = label;

		degree = 0;

	}

	public String getLabel() {
		return label;
	}

	public long getID() {
		return id;
	}

	public long getDegree() {
		return degree;
	}

	public void change(long n) {
		degree += n;
	}

}
