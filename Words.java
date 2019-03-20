import java.util.*;

public class Words implements Comparable<Words>
{
	private String word;
	private int noRepeats = 1;
	private ArrayList<String> list = new ArrayList<String>();
	
	public Words(String word)
	{
		this.word = word;
	}
	
	//Method to check if a given word is a neighbour of this word
	public int isNeighbour(String word)
	{
		int differBy = 0;
		for(int x = 0; x < this.word.length(); x++)
		{
			if(this.word.charAt(x) != word.charAt(x))
			{
				differBy++;
			}
			if(differBy == 2)
			{
				return 2;
			}
		}
		return differBy;
	}
	
	public void addNeighbour(String neighbour)
	{
		list.add(neighbour);
	}
	
	public int compareTo(Words w)
	{
		int wordDifference = this.word.compareToIgnoreCase(w.word);
		return wordDifference;
	}
	
	public void repeat()
	{
		noRepeats++;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public int getRepeats()
	{
		return this.noRepeats;
	}
	
	public String toString()
	{
		String neighbours = "";
		for(int i = 0; i < list.size(); i++)
		{
			neighbours += list.get(i);
			neighbours += " ";
		}
		return word + " " + getRepeats() + "[ " + neighbours + " ]";
	}
}