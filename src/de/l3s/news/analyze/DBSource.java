package de.l3s.news.analyze;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DB;
import de.l3s.rss.RSSEntry;
import de.l3s.util.date.FlexDate;

public class DBSource extends RSSItemSource {

	private RSSEntry next;
	private ResultSet rs;

	public DBSource(String sql) {
		try {
			Connection con = DB.getThreadConnection();
			Statement st = con.createStatement();
			
			rs=st.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean hasNext() {
	try {
		if(next==null&&rs.next())
		{
			String updatedatestring = rs.getString("updateddate");
			FlexDate updateddate = null;
			if(updatedatestring!=null){
			 updateddate = FlexDate.parseGregorianDate(updatedatestring);
			}
		FlexDate publisheddate = FlexDate.parseGregorianDate(rs.getString("publisheddate"));
			next=new RSSEntry(
					rs.getString("hashid"), 
					rs.getString("urlid"), 
							rs.getString("title"), 
									rs.getString("link"), 
											rs.getString("uri"), 
													rs.getString("author"), 
															rs.getString("description"), 
																	publisheddate, 
																	updateddate==null?publisheddate:updateddate, 
																					rs.getString("links"), 
					rs.getString("authors"), 
							rs.getString("categories"), 
									rs.getString("contents"), 
											rs.getString("contributors"), 
													rs.getString("enclosures"), 
															rs.getString("page"), 
																	rs.getInt("sourceid"), 
																			rs.getString("texthash")
					);
			return true;
		}else if(next!=null){return true;}
			
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;
	}

	@Override
	public RSSEntry next() {
		// TODO Auto-generated method stub
		RSSEntry ret = next;
		next=null;
		return ret;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
