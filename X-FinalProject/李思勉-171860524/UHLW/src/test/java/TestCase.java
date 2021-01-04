import cn.edu.nju.field.BattleField;
import cn.edu.nju.role.Creature;
import cn.edu.nju.role.HuLuWa;
import cn.edu.nju.role.Snake;
import cn.edu.nju.web.Connection;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


import java.awt.*;
public class TestCase {
    public static class test extends Application {
        HuLuWa huLuWas[] = new HuLuWa[6];
        public void start(Stage primaryStage) throws Exception {
            testLoadCreature();
            testSimpleSkill();
            testSimpleReplay();
            testSimpleMove();
            testWeb();
        }
        public static void main(String[] args) {
            launch(args);
        }
        public void testLoadCreature(){
            for(int i=0;i<6;i++){
                huLuWas[i] = new HuLuWa();
                assertNotEquals(null,huLuWas[i].getSkill(3).getImg());
                assertNotEquals(null,huLuWas[i].getHead());
                assertNotEquals(null,huLuWas[i].getSWalk());
            }
        }
        public void testSimpleSkill(){
            HuLuWa huLuWa = huLuWas[0];
            huLuWa.useSkill(huLuWa.getSkill(3),new Snake());
            assertNotEquals(huLuWa.getMaxMP(),huLuWa.getMP());
        }
        public void testSimpleMove(){
            BattleField<Creature> battleField= new BattleField<Creature>(Creature.class,10,10);
            HuLuWa huLuWa = huLuWas[0];
            battleField.set(0,0,huLuWa);
            huLuWa.walkTo(battleField,2,2);
            assertEquals(huLuWa.getPosx(),2);
            assertEquals(huLuWa.getPosy(),2);
            assertEquals(huLuWa.getID(),battleField.getCreature(2,2).getID());
        }
        public void testSimpleReplay(){
            BattleField<Creature> battleField =  new BattleField<>(Creature.class,20,20);
            String status = "300 600 20 30 20 50 3 300 600 3 3 2";
            HuLuWa huLuWa = huLuWas[0];
            battleField.set(10,10,huLuWa);
            huLuWa.setStatus(battleField,status);
            assertEquals(huLuWa.getPosy(),2);
            assertEquals(huLuWa.getPosx(),3);
            assertEquals(huLuWa.getHP(),300);
        }
        public void testWeb(){
            Connection createConnection = new Connection();
            Connection findConnection = new Connection();
            Task createTask = createConnection.getCreateTask();
            Task findTask = findConnection.getFindTask();

            findTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                public void handle(WorkerStateEvent event) {
                    assertEquals(true,findTask.getValue());
                    createConnection.close();
                    findConnection.close();
                    Platform.exit();
                }
            });
            new Thread(createTask).start();
            new Thread(findTask).start();
        }
    }
    @Test
    public void testStart(){
        test.main(null);
    }


}
