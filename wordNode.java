public class wordNode
{
   private Words word;
   private wordNode left;
   private wordNode right;
   private int height;

   public wordNode(Words word)
   {
      this.word = word;
      left = null;
      right = null;
	  height = 0;
   }

   public void setLeft(wordNode node)
   {
      left = node;
   }

   public Words getWordO()
   {
	   return word;
   }
   
   public void setRight(wordNode node)
   {
      right = node;
   }

   public wordNode getLeft()
   {
      return left;
   }

   public wordNode getRight()
   {
      return right;
   }

 	public String getWords()
 	{
		return word.getWord();
	}
	
	public wordNode getNode()
	{
		return this;
	}

 
	protected void setData(String data)
	{
		this.word = word;
	}
	
	public void repeats()
	{
		word.repeat();
	}
	
	public void setHeight(int height)
    {
       this.height = height;
    }
   
   public int getHeight()
    {
       return height;
    }
	
	public String displayNode()
	{
		return word.toString();
	}
	
}
