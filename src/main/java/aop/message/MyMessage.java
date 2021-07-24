package aop.message;

import org.springframework.stereotype.Component;

@Component
public class MyMessage {
    
	public void showMessage() {
		System.out.println("showMessage++++++");
	}
	
	public void showMessageSecond() {
		System.out.println("showMessageSecond++++++");
	}
	

}
