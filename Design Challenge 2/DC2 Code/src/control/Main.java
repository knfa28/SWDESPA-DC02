package control;

import model.EventManager;
import view.AppView;

public class Main {

	public static void main(String[] args)
	{
		EventManager em = new EventManager();
		Controller controller = new Controller(em);
		em.addView(new AppView(controller));
	}
}
