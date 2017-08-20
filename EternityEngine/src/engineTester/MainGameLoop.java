package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;


public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		ModelData treedata = OBJFileLoader.loadOBJ("tree");
		RawModel treeModel = loader.loadToVAO(treedata.getVertices(), treedata.getTextureCoords(), treedata.getNormals(), treedata.getIndices());
		TexturedModel tree = new TexturedModel(treeModel,new ModelTexture(loader.loadTexture("tree")));
		
		ModelData grassdata = OBJFileLoader.loadOBJ("grassModel");
		RawModel grassModel = loader.loadToVAO(grassdata.getVertices(), grassdata.getTextureCoords(), grassdata.getNormals(), grassdata.getIndices());
		TexturedModel grass = new TexturedModel(grassModel,new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		
		ModelData ferndata = OBJFileLoader.loadOBJ("fern");
		RawModel fernModel = loader.loadToVAO(ferndata.getVertices(), ferndata.getTextureCoords(), ferndata.getNormals(), ferndata.getIndices());
		TexturedModel fern = new TexturedModel(fernModel,new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setHasTransparency(true);
		
		
		
		
		
		
		
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		 		
		
		Terrain terrain = new Terrain(0,-1,loader, texturePack, blendMap, "heightmap");
		//Terrain terrain2 = new Terrain(0,-1,loader, texturePack, blendMap, "heightmap");
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i=0;i<500;i++){
			
			float x = random.nextFloat()*800 - 400;
			float z = random.nextFloat() * -600;
			float y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(tree, new Vector3f(x,y,z),0,0,0,3));
			entities.add(new Entity(grass, new Vector3f(x,y,z),0,0,0,1));
			entities.add(new Entity(fern, new Vector3f(x,y,z),0,0,0,0.6f));
		}
		
		MasterRenderer renderer = new MasterRenderer();
		
		//Player Init
		
		
		
		ModelData playerdata = OBJFileLoader.loadOBJ("person");
		RawModel playerRawModel = loader.loadToVAO(playerdata.getVertices(), playerdata.getTextureCoords(), playerdata.getNormals(), playerdata.getIndices());
		TexturedModel playerModel = new TexturedModel(playerRawModel,new ModelTexture(loader.loadTexture("playerTexture")));
		
		Player player = new Player(playerModel, new Vector3f(0, -50, 0), 0, 0, 0, 1);
		
		Camera camera = new Camera(player);
		
		while(!Display.isCloseRequested()){
			camera.move();
			player.move(terrain);
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			//renderer.processTerrain(terrain2);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
