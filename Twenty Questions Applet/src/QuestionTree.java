
public class QuestionTree
{
	QuestionNode root;
	
	public QuestionTree(){
		root = new QuestionNode("");
		root.setLeft(new QuestionNode(""));
		root.setRight(new QuestionNode(""));
	}
	
	public void setRoot(QuestionNode n){
		root = n;
	}
}