package dijkstrasMethod;

public class ShortestPathInfo {

	// This class is to represent a single shortest path
	//from a source Node to a destination Node

	private String dest;
	private long totalWeight;

	public ShortestPathInfo(String dest, long totalWeight) {
		this.dest = dest;
		this.totalWeight = totalWeight;
	}

	public String getDest() {
		return dest;
	}

	public long getTotalWeight() {
		return totalWeight;
	}

	public String toString() {
		return "dest: " + dest + "\ttotalWeight: " + totalWeight;
	}
}