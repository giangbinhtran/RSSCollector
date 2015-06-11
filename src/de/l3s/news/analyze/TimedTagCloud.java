package de.l3s.news.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.StringTokenizer;

import de.l3s.analyze.mi.MIselection;
import de.l3s.rss.RSSEntry;
import de.l3s.util.date.DateInterval;

public class TimedTagCloud {
	DateInterval di;
	ArrayList<ScoredTag<String>> st = null;
	ArrayList<RSSEntry> rawItems = new ArrayList<RSSEntry>();
	ArrayList<RSSEntry> generalTerms = new ArrayList<RSSEntry>();

	public TimedTagCloud(DateInterval di) {
		this.di = di;
	}

	public void addItem(RSSEntry cur) {
		rawItems.add(cur);

	}

	public ArrayList<ScoredTag<String>> calculatecloud(int n) {
		if (st == null) {
			st = new ArrayList<ScoredTag<String>>();

			ArrayList<ArrayList<String>> poslist = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> neglist = new ArrayList<ArrayList<String>>();

			for (RSSEntry item : rawItems) {
				poslist.add(extractterms(item.getTitle()));
				// poslist.add(extractterms(item.getTitle()+" "+item.getDescription()));
			}
			for (RSSEntry item : generalTerms) {
				neglist.add(extractterms(item.getTitle()));
				// neglist.add(extractterms(item.getTitle()+" "+item.getDescription()));
			}
			MIselection mi = new MIselection(poslist, neglist, true);
			mi.computePositiveAndNegativeMIvalues();
			Hashtable<String, Double> scores = mi.getTopNnegResultScoreList();

			for (String s : scores.keySet()) {
				st.add(new ScoredTag<String>(s, scores.get(s)));
			}
			Collections.sort(st);

			Collections.reverse(st);
		}
		ArrayList<ScoredTag<String>> ret = new ArrayList<ScoredTag<String>>();
		for (int i = 0; i < n && i < st.size(); i++) {
			ret.add(st.get(i));
		}
		return ret;
	}

	private ArrayList<String> extractterms(String string) {
		ArrayList<String> terms = new ArrayList<String>();
		if (string == null)
			return terms;
		StringTokenizer tokenizer = new StringTokenizer(string.toLowerCase());

		while (tokenizer.hasMoreTokens()) {
			String s = tokenizer.nextToken();
			s = s.replaceAll("[^A-Za-z0-9\\-]", "");
			if (s.length() > 3)
				terms.add(s);
		}

		// TODO Auto-generated method stub
		return terms;
	}

	public void empty() {
		generalTerms = null;
		rawItems = null;

	}
}
