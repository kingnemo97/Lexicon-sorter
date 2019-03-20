import java.io.*;
import java.util.*;
public class WordMatchTesterBT
{
	public static void main(String[] args)throws Exception
	{
		BinarySearchTree bt = new BinarySearchTree();
		
		if(args.length != 4)
		{
			System.out.println("WordMatchTester <in1.txt> <out1.txt> <in2.txt> <out2.txt>");	
		}
		
		String file1 = args[0];
		String file2 = args[1];
		String file3 = args[2];
		String file4 = args[3];
		
		Scanner fileList = new Scanner(new File(file1));
		
		while(fileList.hasNext())
		{
			readData(fileList.next(),bt);
		}
	
		/*ArrayList<Words> list = new ArrayList<Words>(50000);
		
		bt.traverseInOrder(list);
		list.remove(0);*/
		
		/*//method to check neighbours
		String word;
		char next;
		wordNode w;
		for(int y = 0; y < list.size(); y++)
		{
			word = list.get(y).getWord();
			for(int i = 0; i < word.length(); i++)
			{
				next = word.charAt(i);
				for(int x = 0; x < 25; x++)
				{
					next++;
					if(next == '{')
					{
						
						next = 'a';
					}
					word = word.substring(0,i) + (next) + word.substring(i+1,word.length());
					w = bt.search(word);
					if(w != null)
					{				
						if(w.getWords().compareTo(word) == 0)
						{
							list.get(y).addNeighbour(word);
							
						}
					}
					
				}
			}
		}*/
		
		//write data to a text file
		//writeData(list, file2);	
		//call to check read words against patterns
		//checkPaterns(list, file3, file4);
	}
	
	//method to read in words from a file and create the lexicon
	public static void readData(String fileName, BinarySearchTree bt) throws Exception
	{
		Scanner file = new Scanner(new File(fileName));
		String word;
		while(file.hasNext())
		{
			word = file.next().toLowerCase().trim().replaceAll("[^a-z]","");
			if(bt.search(word) != null)
			{
				bt.search(word).getWordO().repeat();
			}
			else
			{
				bt.insert(word);
			}
		}
		file.close();
	}
	
	//metod to save lexicon to a file
	public static void writeData(ArrayList<Words> list, String file2) throws FileNotFoundException
	{	
		PrintWriter outFile = new PrintWriter(new File(file2));
		for(Words w:list)
		{
			outFile.println(w);
		}
		outFile.close();

	}
	
	//method to read in patterns and check them against Words in a list
	public static void checkPaterns(ArrayList<Words> list, String file3, String file4) throws FileNotFoundException
	{		
		Scanner readPatterns = new Scanner(new File(file3));
		PrintWriter writePatterns = new PrintWriter(new File(file4));
		String word;
		int correct = 0;
		boolean exists = false;
		boolean hasStar;
		while(readPatterns.hasNext())//keeps goint till there are no patterns left
		{
			word = readPatterns.next();
			writePatterns.println(word);//print the pattern to the file
			//System.out.println(word);//print the pattern to the screen
			exists = false;
			for(int x = 0; x < list.size(); x++)//cycle through the list of words
			{
				correct = 0;
				hasStar = false;
				for(int y = 0; y < word.length(); y++)//cycle through the charchters
				{
					if(word.length() <= list.get(x).getWord().length())//remove all words shorter then the pattern
					{
						if(list.get(x).getWord().charAt(y) == word.charAt(y))//check basic charchters
						{
							correct++;
						}	
						else if(word.charAt(y) == '*')//if it has a star print it
						{
							correct++;
							writePatterns.println(list.get(x).getWord() + " " + list.get(x).getRepeats());
							//System.out.println(list.get(x).getWord() + " " + list.get(x).getRepeats());
							hasStar = true;
							exists = true;
						}
						else if(word.charAt(y) == '?')
						{
							correct++;
						}
						else//not equal to the pattern
						{
							break;
						}
						if(correct == word.length())//check if its a valid word
						{
							//if( hasStar == false)//to remove all that have a star in them
							//{
								if(word.length() == list.get(x).getWord().length())//ensure the words are the same length
								{
								writePatterns.println(list.get(x).getWord() + " " + list.get(x).getRepeats());//writes the word to the file
								//System.out.println(list.get(x).getWord() + " " + list.get(x).getRepeats());//writes the word to the screen
								exists = true;
								}
							//}
						}
					}
				}
			}
			if(exists == false)//check if there were any matches
			{
				//System.out.println("No words in the lexicon match the pattern");
				writePatterns.println("No words in the lexicon match the pattern");
			}
		}
		readPatterns.close();
		writePatterns.close();
		
		
	
	}
	
	
}

