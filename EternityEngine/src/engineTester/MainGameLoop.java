package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.CreateDisplay();
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		//OpenGL Counter clockwise vertices
		
		
		RawModel model = OBJLoader.loadObjModel("stall", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
		TexturedModel staticModel = new TexturedModel(model, texture);
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-50),0,0,0,1);
		Camera camera = new Camera();
		entity.increasePosition(0, 0, -1.5f);
		
		
		while(!Display.isCloseRequested()){
			entity.increasePosition(0, 0, 0);
			camera.move();
			entity.increaseRotation(0, 1, 0);
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			 shader.stop();
			DisplayManager.UpdateDisplay();
		}
		
		loader.cleanUp();
		DisplayManager.CloseDisplay();

	}

}
