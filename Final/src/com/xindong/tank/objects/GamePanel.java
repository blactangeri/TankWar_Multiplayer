package com.xindong.tank.objects;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GamePanel extends Parent {
    private Tk tk;
	public GamePanel() {
	}

	public void load(){
        getChildren().add(tk);
        getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
 			@Override
 			public void handle(KeyEvent event) {
 				onKeyPressed(event);
 			}
 		});
	}
	
	
	public void onKeyPressed(KeyEvent event){
		if(event.getCode() == KeyCode.LEFT){
		  tk.moveLeft();
	   }else if(event.getCode() == KeyCode.RIGHT){
		  tk.moveRight();
	   }else if(event.getCode() == KeyCode.UP){
		  tk.moveUp();
	   }else if(event.getCode() == KeyCode.DOWN){
		  tk.moveDown();
	   }
	}
	
	
	public void update(long now){
		
	}

}