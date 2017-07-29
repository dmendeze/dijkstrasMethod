package dijkstrasMethod;

import java.util.*;
import java.util.Map.Entry;

public class DiGraph implements DiGraph_Interface {

	// in here go all your data and methods for the graph
	// and the topo sort operation

	private int numNodes, numEdges;

	HashMap<String, Node> nodes = new HashMap<String, Node>();
	HashMap<String, LinkedList<Edge>> verts = new HashMap<String, LinkedList<Edge>>();
	HashMap<Long, Node> ids = new HashMap<Long, Node>();
	HashMap<Long, Edge> edges = new HashMap<Long, Edge>();

	private boolean check(long id, String label) {

		if (id < 0 || ids.containsKey(id) || nodes.containsKey(label) || label == null)
			return true;

		else
			return false;

	}

	private boolean edgeCheck(long id, String sLabel, String dLabel) {

		if (id < 0 || !nodes.containsKey(dLabel) || !nodes.containsKey(sLabel))
			return true;

		else
			return false;

	}

	private boolean containsNode(HashMap map, String label) {

		if (map.containsKey(label))
			return true;

		else
			return false;

	}

	@Override
	public boolean addNode(long id, String label) {

		Node node = new Node(id, label);

		if (check(id, label)) {
			return false;
		} else {
			ids.put(id, node);
			nodes.put(label, node);
			verts.put(label, new LinkedList<Edge>());
			numNodes++;
			return true;
		}

	}

	public int size(String label) {
		return verts.get(label).size();
	}

	@Override
	public boolean addEdge(long id, String sLabel, String dLabel, long weight, String eLabel) {

		Node node = new Node(id, sLabel);
		Edge edge = new Edge(id, sLabel, dLabel, weight, eLabel);

		if (edgeCheck(id, sLabel, dLabel))
			return false;

		int size = size(sLabel);

		for (int i = 0; i < size; i++) {

			String vLabel = verts.get(sLabel).get(i).getDlabel();
			Long vID = verts.get(sLabel).get(i).getID();

			if (vLabel.equals(dLabel) || vID == id) {
				return false;
			}

		}

		edges.put(id, edge);
		verts.get(sLabel).add(edge);
		nodes.get(dLabel).change(1);
		numEdges++;

		return true;
	}

	@Override
	public boolean delNode(String label) {

		if (!containsNode(nodes, label))
			return false;

		int size = size(label);

		for (int i = 0; i < size; i++) {

			String t = verts.get(label).get(i).getDlabel();

			if (containsNode(nodes, t))
				nodes.get(t).change(-1);
		}

		long degree = nodes.get(label).getDegree();

		if (degree > 0) {

			for (Entry<String, LinkedList<Edge>> a : verts.entrySet()) {

				int size2 = size(a.getKey());

				for (int i = 0; i < size2; i++) {

					if (a.getValue().contains(label)) {
						delEdge(a.getKey(), label);
					}
				}

			}

			return false;
		}

		numEdges -= size;
		numNodes--;
		verts.remove(label);
		nodes.remove(label);

		return true;

	}

	@Override
	public boolean delEdge(String sLabel, String dLabel) {

		boolean tf = false;

		Node temp = nodes.get(sLabel);

		if (!containsNode(verts, sLabel) || !containsNode(verts, dLabel))
			return false;

		int size = size(sLabel);

		for (int i = 0; i < size; i++) {

			String vLabel = verts.get(sLabel).get(i).getDlabel();
			long vID = verts.get(sLabel).get(i).getID();

			if (vLabel.equals(dLabel)) {

				edges.remove(vID);
				verts.get(sLabel).remove(i);
				tf = true;

			}

		}

		if (tf == false)
			return tf;

		else {

			nodes.get(dLabel).change(-1);
			numEdges--;
			return true;

		}

	}

	@Override
	public long numNodes() {
		return numNodes;
	}

	@Override
	public long numEdges() {
		return numEdges;
	}

	@Override
	public String[] topoSort() {

		LinkedList<Node> queue = new LinkedList<Node>();

		String[] topo = new String[numNodes];

		DiGraph g = new DiGraph();

		Node tNode;

		int n = 0;

		for (Map.Entry<String, Node> entry : nodes.entrySet()) {
			g.nodes.put(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, Node> entry : g.nodes.entrySet()) {

			long degree = entry.getValue().getDegree();

			if (degree == 0)
				queue.add(entry.getValue());

		}

		while (queue.size() > 0) {

			tNode = queue.remove();

			String t = tNode.getLabel();

			int size = size(t);

			for (int i = 0; i < size; i++) {
				g.nodes.get(verts.get(t).get(i).getDlabel()).change(-1);

				long degree = g.nodes.get(verts.get(t).get(i).getDlabel()).getDegree();

				if (degree == 0)
					queue.add(g.nodes.get(verts.get(t).get(i).getDlabel()));

			}

			topo[n] = tNode.getLabel();
			n++;

		}

		String length = topo[topo.length - 1];

		if (length == null)
			return null;

		return topo;
	}

	@Override
	public ShortestPathInfo[] shortestPath(String label) {

		Queue<Node> order = new LinkedList<Node>();
		ArrayList<ShortestPathInfo> found = new ArrayList<ShortestPathInfo>();
		HashMap<String, Long> dMap = new HashMap<String, Long>();

		for (Entry<String, Node> entry : nodes.entrySet()) {

			String key = entry.getKey();
			Node node = nodes.get(label);

			if (key.equals(label)) {
				dMap.put(key, (long) 0);
				order.add(node);
			} else {
				dMap.put(key, Long.MAX_VALUE);
			}

		}

		while (!order.isEmpty()) {

			Node node = order.remove();

			for (Edge a : verts.get(node.getLabel())) {

				Node dNode = nodes.get(a.getDlabel());

				long dist = dMap.get(node.getLabel()) + a.getWeight();

				String distLabel = dNode.getLabel();
				long dDist = dMap.get(distLabel);

				if (dist < dDist || dDist == Long.MAX_VALUE) {

					Node newNode = new Node(dNode.getID(), dNode.getLabel());

					dMap.put(distLabel, dist);

					order.add(newNode);

				}

			}

		}

		for (Entry<String, Long> set : dMap.entrySet()) {

			Long sValue = set.getValue();
			String sKey = set.getKey();

			if (sValue == Long.MAX_VALUE) {
				found.add(new ShortestPathInfo(sKey, -1));
			} else {
				found.add(new ShortestPathInfo(sKey, sValue));
			}

		}

		ShortestPathInfo sPaths[] = new ShortestPathInfo[numNodes];
		found.toArray(sPaths);
		return sPaths;

	}

}