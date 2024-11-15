//insert a package here
package Main.Screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class LevelGenerator {

    public static class GameObject {
        public String type;
        public float x, y;
        public float width, height; // For rectangles
        public float radius;        // For circles
        public Texture texture;

        public GameObject(String type, float x, float y, float width, float height, float radius, Texture texture) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.radius = radius;
            this.texture = texture;
        }
    }

    public static List<GameObject> loadLevel(int levelNumber) {
        List<GameObject> gameObjects = new ArrayList<>();
        FileHandle file = Gdx.files.internal("levels.json");
        Json json = new Json();

        JsonValue root = json.fromJson(null, file.readString());
        for (JsonValue level : root.get("levels")) {
            if (level.getInt("levelNumber") == levelNumber) {
                for (JsonValue obj : level.get("objects")) {
                    String type = obj.getString("type");
                    float x = obj.getFloat("x");
                    float y = obj.getFloat("y");
                    Texture texture = new Texture(Gdx.files.internal(obj.getString("texture")));

                    if (type.equals("block")) {
                        float width = obj.getFloat("width");
                        float height = obj.getFloat("height");
                        gameObjects.add(new GameObject(type, x, y, width, height, 0, texture));
                    } else if (type.equals("target")) {
                        float radius = obj.getFloat("radius");
                        gameObjects.add(new GameObject(type, x, y, 0, 0, radius, texture));
                    } else if (type.equals("slingshot")) {
                        gameObjects.add(new GameObject(type, x, y, 0, 0, 0, texture));
                    }
                }
                break;
            }
        }

        return gameObjects;
    }
}
