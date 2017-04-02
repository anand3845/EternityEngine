package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
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
		
		float[] vertices = {
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0f
								
		};
		
		int[] indices = {
				0,1,3,
				3,1,2
				
		};
		
		float[] textureCoords = {
				0,0,
				0,1,
				1,0,
				1,1
				
		};
		
		RawModel model = loader.loadToVAO(vertices, textureCoords,  indices );
		ModelTexture texture = new ModelTexture(loader.loadTexture("default"));
		TexturedModel staticModel = new TexturedModel(model, texture);
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-1),0,0,0,1);
		Camera camera = new Camera();
		
		
		while(!Display.isCloseRequested()){
			entity.increasePosition(0, 0, -0.01f);
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
