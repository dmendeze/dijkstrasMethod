package dijkstrasMethod;

public class Edge {

	private long id, weight;

	private String sLabel, dLabel, eLabel;

	public Edge(long id, String sLabel, String dLabel, long weight, String eLabel) {

		this.id = id;
		this.weight = weight;
		this.sLabel = sLabel;
		this.dLabel = dLabel;
		this.eLabel = eLabel;

	}

	public String getSlabel() {
		return sLabel;
	}

	public String getDlabel() {
		return dLabel;
	}

	public String getElabel() {
		return eLabel;
	}

	public long getWeight() {
		return weight;
	}

	public long getID() {
		return id;
	}

}
