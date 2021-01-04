package game;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import game.model.Character;
import game.model.character.Dawa;
import game.model.character.Erwa;
import game.model.character.Hama;
import game.model.character.Huangchong;
import game.model.character.Liuwa;
import game.model.character.Qiwa;
import game.model.character.Sanwa;
import game.model.character.Shejing;
import game.model.character.Siwa;
import game.model.character.Wugong;
import game.model.character.Wuwa;
import game.model.character.Xiezi;
import game.view.BulletView;
import game.view.CharacterView;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tool.Camp;
import tool.Screen;
import tool.point.Grid;
import tool.point.Pixel;

public class BattleGround {
    public static Character character[] = new Character[12];
    public static CharacterView characterView[] = new CharacterView[12];
    private static Character field[][] = new Character[8][12];
    public static ReentrantLock fieldLock[][] = new ReentrantLock[8][12];
    public static ExecutorService bulletThreadPool = Executors.newCachedThreadPool();
    public static Set<BulletView> bulletViewSet = Collections.synchronizedSet(new HashSet<>());
    public static ReentrantLock bulletViewSetLock = new ReentrantLock();
    public static Pane root;
    private static int flagPoint;
    private static int characterCnt;
    private static ReentrantLock characterCntLock;
    private static Circle cir;
    private static Camp camp;
    static {
        ConcurrentHashMap<BulletView, Integer> H = new ConcurrentHashMap<>();

        characterCnt = 12;
        characterCntLock = new ReentrantLock();

        character[0] = new Dawa();
        field[1][0] = character[0];
        characterView[0] = new CharacterView(character[0], "Dawa.png");

        character[1] = new Erwa();
        field[2][0] = character[1];
        characterView[1] = new CharacterView(character[1], "Erwa.png");

        character[2] = new Sanwa();
        field[3][0] = character[2];
        characterView[2] = new CharacterView(character[2], "Sanwa.png");

        character[3] = new Siwa();
        field[4][0] = character[3];
        characterView[3] = new CharacterView(character[3], "Siwa.png");

        character[4] = new Wuwa();
        field[5][0] = character[4];
        characterView[4] = new CharacterView(character[4], "Wuwa.png");

        character[5] = new Liuwa();
        field[6][0] = character[5];
        characterView[5] = new CharacterView(character[5], "Liuwa.png");

        character[6] = new Huangchong();
        field[1][11] = character[6];
        characterView[6] = new CharacterView(character[6], "HuangChong.png");

        character[7] = new Wugong();
        field[2][11] = character[7];
        characterView[7] = new CharacterView(character[7], "Wugong.png");

        character[8] = new Xiezi();
        field[3][11] = character[8];
        characterView[8] = new CharacterView(character[8], "Xiezi.png");

        character[9] = new Shejing();
        field[4][11] = character[9];
        characterView[9] = new CharacterView(character[9], "Shejing.png");

        character[10] = new Qiwa();
        field[5][11] = character[10];
        characterView[10] = new CharacterView(character[10], "Qiwa.png");

        character[11] = new Hama();
        field[6][11] = character[11];
        characterView[11] = new CharacterView(character[11], "Hama.png");

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 12; j++) {
                fieldLock[i][j] = new ReentrantLock();

            }
    }

    public static void setPane(Pane root) {
        BattleGround.root = root;

    }

    public static void setFlag() {
        cir = new Circle(0, 0, 4);
        cir.setFill(Color.YELLOW);
        if (camp == Camp.Hulu) {
            flagPoint = 0;
        } else {
            flagPoint = 11;
        }
        Pixel p = Screen.gridToFlagPixel(character[flagPoint].getGrid());
        cir.setLayoutX(p.getX());
        cir.setLayoutY(p.getY());
        Platform.runLater(() -> {
            root.getChildren().add(cir);
        });
    }

    public static void setCharacterView() {
        Platform.runLater(() -> {
            for (int i = 0; i < 12; i++) {
                characterView[i].addScene(root);
            }
        });

    }

    public static void setCamp(Camp camp) {
        BattleGround.camp = camp;
    }

    public static Character getCharacter(Grid grid) {
        int x = grid.getX();
        int y = grid.getY();
        Character ret = null;
        fieldLock[x][y].lock();
        ret = field[x][y];
        fieldLock[x][y].unlock();
        return ret;
    }

    public synchronized static boolean move(Grid src, Grid dest) {
        int x1 = src.getX();
        int y1 = src.getY();
        int x2 = dest.getX();
        int y2 = dest.getY();
        if ((y1 == 6 && y2 == 5) || (y1 == 5 && y2 == 6)) {
            return false;
        }
        if (x2 < 0 || x2 > 7 || y2 < 0 || y2 > 11) {
            return false;
        }
        // avoid deadlock
        while (true) {
            // System.out.println("move
            // ("+src.getX()+","+src.getY()+")->"+"("+dest.getX()+","+dest.getY()+")
            // "+fieldLock[x1][y1]+fieldLock[x2][y2]);
            fieldLock[x1][y1].lock();
            if (fieldLock[x2][y2].tryLock()) {
                break;
            } else {
                fieldLock[x1][y1].unlock();
                Thread.yield();
            }
        }
        if (field[x1][y1] == null || field[x2][y2] != null) {
            fieldLock[x1][y1].unlock();
            fieldLock[x2][y2].unlock();
            // System.out.println("here: ("+x1+","+y1+") "+field[x1][y1]+" ("+x2+","+y2+")
            // "+field[x2][y2]);
            return false;
        }
        Character temp = field[x1][y1];
        field[x1][y1] = field[x2][y2];
        field[x2][y2] = temp;

        fieldLock[x1][y1].unlock();
        fieldLock[x2][y2].unlock();
        return true;
    }

    public static void setDead(Grid grid) {
        int x = grid.getX();
        int y = grid.getY();
        fieldLock[x][y].lock();
        field[x][y] = null;
        fieldLock[x][y].unlock();

        characterCntLock.lock();
        characterCnt--;
        if (characterCnt == 0) {
            Platform.runLater(() -> {
                root.getChildren().remove(cir);
            });

        } else {
            if (character[flagPoint].isDead()) {
                nextFlag();
            }
        }
        characterCntLock.unlock();
    }

    public static int getCharacterCnt() {
        characterCntLock.lock();
        int ret = characterCnt;
        characterCntLock.unlock();
        return ret;
    }

    public static Character nextFlag() {
        if (cir == null) {
            return null;
        }
        for (int i = 1; i <= 6; i++) {
            flagPoint++;
            if (camp == Camp.Hulu) {
                if (flagPoint >= 6) {
                    flagPoint = flagPoint - 6;
                }
            } else if (camp == Camp.Yaojing) {
                if (flagPoint >= 12) {
                    flagPoint = flagPoint - 6;
                }
            }
            // System.out.println(" "+flagPoint+characterView[flagPoint]);
            if (characterView[flagPoint] == null) {
                continue;
            }
            if (characterView[flagPoint].getRed().getWidth() > 0 && character[flagPoint] != null) {
                Pixel p = Screen.gridToFlagPixel(character[flagPoint].getGrid());
                System.out.println(cir);
                cir.setLayoutX(p.getX());
                cir.setLayoutY(p.getY());
                break;
            }
        }
        return character[flagPoint];
    }

    public static Character lastFlag() {
        for (int i = 1; i <= 6; i++) {
            flagPoint--;
            if (camp == Camp.Hulu) {
                if (flagPoint < 0)
                    flagPoint = flagPoint + 6;
            } else if (camp == Camp.Yaojing) {
                if (flagPoint < 6)
                    flagPoint = flagPoint + 6;
            }
            if (characterView[flagPoint].getRed().getWidth() > 0) {
                Pixel p = Screen.gridToFlagPixel(character[flagPoint].getGrid());
                cir.setLayoutX(p.getX());
                cir.setLayoutY(p.getY());
                break;
            }
        }
        return character[flagPoint];
    }

    public static Character getNowCharacter() {
        return character[flagPoint];
    }

    public static int getNOwCharacterIndex() {
        return flagPoint;
    }

    public static void updateFlag() {
        if (cir == null) {
            return;
        }
        if (characterView[flagPoint].getRed().getWidth() <= 0) {
            nextFlag();
            if (characterView[flagPoint].getRed().getWidth() <= 0) {
                cir.setVisible(false);
            }
        }
        Pixel p = Screen.gridToFlagPixel(character[flagPoint].getGrid());
        cir.setLayoutX(p.getX());
        cir.setLayoutY(p.getY());
    }

    public static Circle getFlag() {
        return cir;
    }
}