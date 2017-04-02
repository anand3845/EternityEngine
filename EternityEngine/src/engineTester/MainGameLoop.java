package engineTester;

import org.lwjgl.opengl.Display;



import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;

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
		
		RawModel model = loader.loadToVAO(vertices, indices );
		
		
		while(!Display.isCloseRequested()){
			renderer.prepare();
			//game logic
			//render
			shader.start();
			renderer.render(model);
			 shader.stop();
			DisplayManager.UpdateDisplay();
		}
		
		loader.cleanUp();
		DisplayManager.CloseDisplay();

	}

}
