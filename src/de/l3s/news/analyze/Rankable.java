package de.l3s.news.analyze;

public interface Rankable<T> {
	public int rankTo(T t);
}
