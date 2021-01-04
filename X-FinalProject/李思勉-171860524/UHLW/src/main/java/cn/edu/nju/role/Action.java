package cn.edu.nju.role;
import cn.edu.nju.field.BattleField;

public interface Action {
    void walkTo(BattleField<Creature> battleField, int row, int column);
    void tellName();
}
