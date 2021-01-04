package junit;

import static org.junit.Assert.assertEquals;

import javax.swing.text.html.parser.Entity;

import org.junit.Test;

import view.PlayView;
import world.BlueBaby;
import world.EntityState;
import world.RedBaby;

public class JunitTest {
	
	@Test
	public void testCollide1() { // 碰撞检测1
		PlayView view = new PlayView();
		
		assertEquals(true, view.isCollided(0, 0, 50, 50, 20, 20, 30, 30));
	}
	
	@Test
	public void testCollide2() { // 碰撞检测2
		PlayView view = new PlayView();
		
		assertEquals(true, view.isCollided(0, 50, 50, 50, 20, 20, 40, 40));
	}
	
	@Test
	public void testCollide3() { // 碰撞检测3
		PlayView view = new PlayView();
		
		assertEquals(false, view.isCollided(0, 50, 50, 50, 101, 50, 30, 30));
	}
	
	@Test
	public void testCollide4() { // 碰撞检测4
		PlayView view = new PlayView();
		
		assertEquals(false, view.isCollided(20, 20, 50, 50, 20, 80, 30, 30));
	}

	public void testGetHurt1() { // 伤害值计算1
		RedBaby redBaby = new RedBaby("大娃");
		
		assertEquals(true, redBaby.getHurt(50) == 50);
	}
	
	public void testGetHurt2() { // 伤害值计算2
		BlueBaby blueBaby = new BlueBaby("五娃");
		blueBaby.setDefendable(true);
		assertEquals(false, blueBaby.getHurt(50) == 50);
	}
}
