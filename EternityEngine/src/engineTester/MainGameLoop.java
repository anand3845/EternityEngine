package engineTester;

import org.lwjgl.opengl.Display;

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
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
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
		
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices );
		ModelTexture texture = new ModelTexture(loader.loadTexture("default"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		
		while(!Display.isCloseRequested()){
			renderer.prepare();
			//game logic
			//render
			shader.start();
			renderer.render(texturedModel);
			 shader.stop();
			DisplayManager.UpdateDisplay();
		}
		
		loader.cleanUp();
		DisplayManager.CloseDisplay();

	}

}
