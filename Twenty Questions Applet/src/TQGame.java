import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
//Victor Chan, Anirudh Makineni, Kashyap Gummaraju
public class TQGame extends JPanel{
	
	//definitions for variables that need to be used throughout the code
	static QuestionTree tree = new QuestionTree();
	static QuestionNode node = new QuestionNode("");
	
	static JLabel Question = new JLabel();
	JPanel MainGame = new JPanel();
	JPanel NewQuestion = new JPanel();
	JPanel Win = new JPanel();
	JPanel Lose = new JPanel();
	static int counter = 0;
	static QuestionNode node2 = new QuestionNode("");
	JCheckBox nqIsAnswer;
	static String QLocater = "";
	JTextArea nqInput;
	
	//stores the questions from the question file into a tree
	public static void StoreTree(){
		try {
			Scanner s = new Scanner(new FileReader("UpdatedAnimals.txt"));
			String string1 = s.nextLine();//first question
			string1 = s.nextLine();
			if (!string1.equals("")){
				tree.root.data = string1;
			}
			while (s.hasNextLine()){
				if (string1.equals("")){
					break;
				}
				node2 = tree.root;
				QLocater = s.nextLine();//should be a string of numbers, represents the path in the tree
				for (int i=0;i<QLocater.length();i++){
					if (Character.toString(QLocater.charAt(i)).equals("1")){
						if (node2.left!=null){//a "1" means travel to the left side
							node2 = node2.left;
						}
						else {//creates a new node if the node on the left doesn't already exist
							QuestionNode node3 = new QuestionNode("");
							node2.setLeft(node3);
							node2 = node2.left;
						}
					}
					else if (Character.toString(QLocater.charAt(i)).equals("2")){
						if (node2.right!=null){//a "2" means travel to the right side
							node2 = node2.right;
						}
						else {//creates a new node if the node on the right doesn't already exist
							QuestionNode node3 = new QuestionNode("");
							node2.setRight(node3);
							node2 = node2.right;
						}
					}
				}
				String string3 = s.nextLine();
				if (string3.equals("Answer")){//determines whether the question leads to an answer or not
					node2.isAnswer = true;
					node2.data = s.nextLine();
				}
				else {
					node2.data = string3;
				}
				QLocater = "";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void StartGame() throws IOException {//starts the game
		counter = 0;
		QLocater = "";
		//reads in the file UpdatedAnimals.txt
		StoreTree();//stores the questions from the file
		node = tree.root;
		Question.setText(node.data);//sets the root's data to the first question
		//also displays the question on the game
	}
	
	public void Yes(){//if the user clicks yes
		counter++;
		QLocater = QLocater + "2";//stores a "2" in the path to represent going right
		if (node.isAnswer==true){//shows the losing screen
			NewQuestion.setBounds(0,0,0,0);
			NewQuestion.setVisible(false);
			MainGame.setBounds(0,0,0,0);
			MainGame.setVisible(false);
			Win.setBounds(0,0,0,0);
			Win.setVisible(false);
			Lose.setBounds(0,0,500,300);
			Lose.setVisible(true);
		}
		else{//shows the next question
			if (node.right!=null){
				node = node.right;
				Question.setText(node.data);
			}
			else {//allows for a new question to be submitted
				NewQuestion();
				QuestionNode node4 = new QuestionNode("");
				node.setRight(node4);
				node = node.right;
			}
		}
	}
	
	public void No(){//if the user clicks no
		counter++;
		QLocater = QLocater + "1";
		if (counter==20){//shows the winning screen if twenty questions have been asked
			NewQuestion.setBounds(0,0,0,0);
			NewQuestion.setVisible(false);
			MainGame.setBounds(0,0,0,0);
			MainGame.setVisible(false);
			Win.setBounds(0,0,500,300);
			Win.setVisible(true);
			Lose.setBounds(0,0,0,0);
			Lose.setVisible(false);
		}
		else{
			if (node.left!=null){//shows the next question
				node = node.left;
				Question.setText(node.data);
			}
			else {//allows the user to submit a new question
				NewQuestion();
				QuestionNode node4 = new QuestionNode("");
				node.setLeft(node4);
				node = node.left;
			}
		}
	}
	
	public void NewQuestion(){//shows the NewQuestion screen
		NewQuestion.setBounds(0,0,500,300);
		NewQuestion.setVisible(true);
		MainGame.setBounds(0,0,0,0);
		MainGame.setVisible(false);
		Win.setBounds(0,0,0,0);
		Win.setVisible(false);
		Lose.setBounds(0,0,0,0);
		Lose.setVisible(false);
	}
	
	//allows user to submit a new question
	public void SubmitNewQuestion(String s) throws IOException {
		PrintWriter QuestionOut = new PrintWriter(new FileWriter("UpdatedAnimals.txt",true));
		QuestionOut.println(QLocater);//prints the pathway
		QuestionOut.flush();
		if (nqIsAnswer.isSelected()==true){//denotes whether the question leads to an answer or not
			QuestionOut.println("Answer");
			QuestionOut.flush();
		}
		QuestionOut.println(s);//prints the actual question
		QuestionOut.flush();
		NewQuestion.setBounds(0,0,0,0);
		NewQuestion.setVisible(false);
		MainGame.setBounds(0,0,500,300);
		MainGame.setVisible(true);
		Win.setBounds(0,0,0,0);
		Win.setVisible(false);
		Lose.setBounds(0,0,0,0);
		Lose.setVisible(false);
		node.setData(s);
		if (nqIsAnswer.isSelected()==true){
			node.setAnswer(true);
		}
		nqIsAnswer.setSelected(false);
		Question.setText(node.data);//shows the submitted question
		nqInput.setText("");
	}
	
	public void reset(){//shows the MainGame
		NewQuestion.setBounds(0,0,0,0);
		NewQuestion.setVisible(false);
		MainGame.setBounds(0,0,500,300);
		MainGame.setVisible(true);
		Win.setBounds(0,0,0,0);
		Win.setVisible(false);
		Lose.setBounds(0,0,0,0);
		Lose.setVisible(false);
	}
	
	public TQGame(){//the class of TQGame, the actual GUI
		/*Note: All of the following code goes towards programming
		 * the labels, textareas, buttons, and what the buttons do.
		 * The code and the names are self explanatory.*/
		
		Font font = new Font("Sans Serif",0,20);//font for almost all text
		
		JPanel window = new JPanel();//creates new JFrame
		window.setSize(500,300);
		window.setVisible(true);
		this.add(window);
		
		JButton StartOver = new JButton("Start Over");
		StartOver.setBounds(300,165,175,25);
		StartOver.setFont(font);
		StartOver.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				try {
					StartGame();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				reset();
			}
		});
		
		MainGame.setLayout(null);
		NewQuestion.setLayout(null);
		Win.setLayout(null);
		Lose.setLayout(null);
		this.add(NewQuestion);
		this.add(MainGame);
		this.add(Win);
		this.add(Lose);
		NewQuestion.setBounds(0,0,0,0);
		NewQuestion.setVisible(false);
		MainGame.setBounds(0,0,500,300);
		MainGame.setVisible(true);
		Win.setBounds(0,0,0,0);
		Win.setVisible(false);
		Lose.setBounds(0,0,0,0);
		Lose.setVisible(false);
		window.add(StartOver);
		
		JLabel QuestionLabel = new JLabel("Computer's Question:");
		QuestionLabel.setBounds(20,20,200,20);
	    QuestionLabel.setFont(font);
	    MainGame.add(QuestionLabel);

		Question.setBounds(20,50,400,20);
		Question.setFont(font);
		MainGame.add(Question);
		
		JButton Yes = new JButton("Yes");
		Yes.setBounds(20,200,75,25);
		Yes.setFont(font);
		MainGame.add(Yes);
		Yes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Yes();
			}
		});
		
		JButton No = new JButton("No");
		No.setBounds(120,200,75,25);
		No.setFont(font);
		MainGame.add(No);
		No.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				No();
			}
		});
		
		JButton Quit = new JButton("Quit");
		Quit.setBounds(400,200,75,25);
		Quit.setFont(font);
		MainGame.add(Quit);
		Quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});
		
		JLabel nqDirections = new JLabel("Please input your new question.");
		nqDirections.setBounds(20,20,400,20);
		nqDirections.setFont(font);
		NewQuestion.add(nqDirections);

		nqInput = new JTextArea();
		nqInput.setLineWrap(true);
		nqInput.setBounds(20,50,460,50);
		NewQuestion.add(nqInput);
		
		JButton nqSubmit = new JButton("Submit");
		nqSubmit.setBounds(20,200,200,25);
		nqSubmit.setFont(font);
		NewQuestion.add(nqSubmit);
		nqSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				try {
					SubmitNewQuestion(nqInput.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JButton nqQuit = new JButton("Quit");
		nqQuit.setBounds(400,200,75,25);
		nqQuit.setFont(font);
		NewQuestion.add(nqQuit);
		nqQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});
		
		nqIsAnswer = new JCheckBox("Does this question lead to an answer?");
		nqIsAnswer.setBounds(20,125,460,30);
		nqIsAnswer.setFont(font);
		NewQuestion.add(nqIsAnswer);
		
		JLabel Won = new JLabel("Congratulations! You win!");
		Won.setBounds(20,20,400,30);
		Won.setFont(font);
		Win.add(Won);
		
		JButton wonNewGame = new JButton("New Game");
		wonNewGame.setBounds(20,200,200,25);
		wonNewGame.setFont(font);
		Win.add(wonNewGame);
		wonNewGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				try {
					StartGame();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				reset();
			}
		});
		
		JButton winQuit = new JButton("Quit");
		winQuit.setBounds(400,200,75,25);
		winQuit.setFont(font);
		Win.add(winQuit);
		winQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});
		
		JLabel Lost = new JLabel("Sorry, You Lost.");
		Lost.setBounds(20,20,400,30);
		Lost.setFont(font);
		Lose.add(Lost);
		
		JButton lostNewGame = new JButton("New Game");
		lostNewGame.setBounds(20,200,200,25);
		lostNewGame.setFont(font);
		Lose.add(lostNewGame);
		lostNewGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				try {
					StartGame();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				reset();
			}
		});
		
		JButton lostQuit = new JButton("Quit");
		lostQuit.setBounds(400,200,75,25);
		lostQuit.setFont(font);
		Lose.add(lostQuit);
		lostQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});
	}
}