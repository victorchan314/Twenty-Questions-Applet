import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;
//Victor Chan, Anirudh Makineni, Kashyap Gummaraju

public class TQApplet extends JApplet {
			
			public void init(){
				setSize(500,300);
		        try {
		            SwingUtilities.invokeAndWait(new Runnable() {
		                public void run() {
		                    try {
								CreateApplet();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                }
		            });
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        showStatus("Initialize");
			}
			
			public void start() {
				showStatus("Start");
			}
			
			public void stop() {
				showStatus("Stop");
			}
			
			public void destroy() {
				showStatus("Destroy");
			}
			
			public void CreateApplet() throws IOException {
				TQGame TwentyQuestions = new TQGame();
				TwentyQuestions.setOpaque(true);
				setContentPane(TwentyQuestions);
				TwentyQuestions.StartGame();
			}
}