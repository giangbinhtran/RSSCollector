package de.l3s.news.analyze;

public class ScoredTag<T extends Comparable<T>> implements
		Comparable<ScoredTag<T>> {

	T object;
	Double score;

	public ScoredTag(T object, Double score) {
		this.object = object;
		this.score = score;
	}

	@Override
	public int compareTo(ScoredTag<T> arg0) {
		// TODO Auto-generated method stub
		return this.score.compareTo(arg0.score);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return object.toString() + "(" + score + ")";
	}
}
