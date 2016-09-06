
public class QuestionNode{
	String data;
	QuestionNode left;
	QuestionNode right;
	boolean isAnswer;
	
	public QuestionNode(String data){
		this.data = data;
		this.left = null;
		this.right = null;
		this.isAnswer = false;
	}
	
	public void setData(String data){
		this.data = data;
	}

	public QuestionNode getLeft(){
		return left;
	}

	public void setLeft(QuestionNode left){
		this.left = left;
	}

	public QuestionNode getRight(){
		return right;
	}

	public void setRight(QuestionNode right){
		this.right = right;
	}
	
	public void setAnswer(boolean b){
		this.isAnswer = b;
	}
}