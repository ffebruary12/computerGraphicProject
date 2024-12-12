
package com.mygame;
//import static com.jme3.system.jopenvr.JOpenVRLibrary.init;
import com.jme3.system.jopenvr.JOpenVRLibrary.init;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glLoadMatrixf;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.FloatBuffer;
import org.lwjgl.system.MemoryStack;

/**
 *
 * @author February12
 */
public class Window {
    
    public static final float FOV=(float)Math.toRadians(60);
    public static final float Z_NEAR= 0.01f;
    public static final float Z_FAR=1000f;
    
    private  String title = "3D MODELING PROGRAM";
    private int width,height;
    private float aspectRatio=width/height;
    private long window;
    private boolean resize,vSync;
    private final Matrix4f projectionMatrix;
    private boolean setResize;
    private int key;
    private int action = 0;
    public Window(String title ,int width, int height, boolean vSync) {
        this.title=title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        projectionMatrix=new Matrix4f();
    }
    public void Init(){
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("unable to initialize GLFW");}
            GLFW.glfwDefaultWindowHints();
            GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE,GL33.GL_TRUE);
            GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL33.GL_TRUE);
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL33.GL_TRUE);

            boolean maximised=false;
            if (width==0&&height==0) {
                width=100;
                height=100;
                GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
                maximised=true;
            }
            window=GLFW.glfwCreateWindow(width, height, title,MemoryUtil.NULL , MemoryUtil.NULL);
            if (window==MemoryUtil.NULL) {
                throw new RuntimeException("Failed to create window");
            }
       GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) ->{
    glViewport(0, 0, width, height); // OpenGL görünümünü yeni boyutlara göre güncelle
    this.width = width; // Yeni genişlik değerini kaydet
    this.height = height; // Yeni yükseklik değerini kaydet
    this.setResize = true; // Yeniden boyutlandırma bayrağını işaretle
});
            
       if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
    GLFW.glfwSetWindowShouldClose(window, true);
}if (maximised) {
                GLFW.glfwMaximizeWindow(window);
            }
else{
    // Birincil monitörün video modunu al
GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

if (vidMode != null) {
    // Pencerenin ortalanmış pozisyonunu hesapla
    int xpos = (vidMode.width() - width) / 2;
    int ypos = (vidMode.height() - height) / 2;

    // Pencere pozisyonunu ayarla
    GLFW.glfwSetWindowPos(window, xpos, ypos);
}
    
}
GLFW.glfwMakeContextCurrent(window);
            if (isvSync()) {
                GLFW.glfwSwapInterval(1);
                GLFW.glfwShowWindow(window);
               GL.createCapabilities();
               GL33.glClearColor(0.0f,0.0f ,0.0f, 0.0f);
               GL33.glEnable(GL33.GL_DEPTH_TEST);
                GL33.glEnable(GL33.GL_STENCIL_TEST);
                        GL33.glEnable(GL33.GL_CULL_FACE);
                        GL33.glCullFace(GL33.GL_BACK);
                        
            }
    }
   
    public boolean isvSync(){
        return vSync;
    }
    public void update() {
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }
    public void cleanup(){
        GLFW.glfwDestroyWindow(window);
    }
    public void setClearColor(float r,float g,float b,float a){
        GL33.glClearColor(r, g, b, a);
    }
    public boolean isKeyPressed(int keycode){
        return GLFW.glfwGetKey(window, keycode)==GLFW.GLFW_PRESS;
    }
    public boolean windowShouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        GLFW.glfwSetWindowTitle(window, title);
    }
    public boolean isResize() {
    return resize;
}

public boolean isVSync() {
    return vSync;
}

public void setResize(boolean resize) {
    this.resize = resize;
}

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindow() {
        return window;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
    public Matrix4f updateProjectionMatrix(){
        float aspectRatio=(float)width/height;
        return projectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }
    public Matrix4f updateProjectionMatrix(Matrix4f matrix,int width,int height){
        float aspectRatio=(float)width/height;
        return projectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }
    public void loop(){
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            float perspectiveMatrix[] = new float[16];
            createPerspectiveMatrix(perspectiveMatrix, FOV, aspectRatio, Z_NEAR, Z_FAR);
            glLoadMatrixf(perspectiveMatrix);

            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glTranslatef(0.0f, 0.0f, -5.0f);

            drawGrids();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }}
        public void drawGrids() {
        // Draw 5 grid surfaces (2 on Y-Z, 2 on X-Z, 1 on X-Y)

        glColor3f(1.0f, 1.0f, 1.0f); // White color for grid lines

        // Y-Z planes
        drawGrid(10, 10, -1.0f, 0.0f, 0.0f); // Left Y-Z plane
        drawGrid(10, 10, 1.0f, 0.0f, 0.0f);  // Right Y-Z plane

        // X-Z planes
        drawGrid(10, 10, 0.0f, -1.0f, 0.0f); // Bottom X-Z plane
        drawGrid(10, 10, 0.0f, 1.0f, 0.0f);  // Top X-Z plane

        // X-Y plane
        drawGrid(10, 10, 0.0f, 0.0f, 1.0f);  // Back X-Y plane
    }

    private void drawGrid(int width, int height, float offsetX, float offsetY, float offsetZ) {
        glPushMatrix();
        glTranslatef(offsetX, offsetY, offsetZ);
        glBegin(GL_LINES);

        for (int i = -width; i <= width; i++) {
            // Vertical lines
            glVertex3f(i, -height, 0.0f);
            glVertex3f(i, height, 0.0f);
        }
        for (int j = -height; j <= height; j++) {
            // Horizontal lines
            glVertex3f(-width, j, 0.0f);
            glVertex3f(width, j, 0.0f);
        }

        glEnd();
        glPopMatrix();
    }

    private void createPerspectiveMatrix(float[] matrix, float fov, float aspect, float zNear, float zFar) {
        float yScale = (float) (1.0 / Math.tan(Math.toRadians(fov / 2.0)));
        float xScale = yScale / aspect;
        float frustumLength = zFar - zNear;

        matrix[0] = xScale;
        matrix[1] = 0.0f;
        matrix[2] = 0.0f;
        matrix[3] = 0.0f;

        matrix[4] = 0.0f;
        matrix[5] = yScale;
        matrix[6] = 0.0f;
        matrix[7] = 0.0f;

        matrix[8] = 0.0f;
        matrix[9] = 0.0f;
        matrix[10] = -((zFar + zNear) / frustumLength);
        matrix[11] = -1.0f;

        matrix[12] = 0.0f;
        matrix[13] = 0.0f;
        matrix[14] = -((2 * zNear * zFar) / frustumLength);
        matrix[15] = 0.0f;
    }
     public void run() {
        init();
        loop();
       
    }

     public static void main(String[] args) {
          System.out.println(Version.getVersion());
        Window myWindow=new Window("3D MODELING PROGRAM", 1000, 700, false);
        myWindow.Init();
        while (!myWindow.windowShouldClose()) {            
            myWindow.update();
            
        }
        myWindow.cleanup();
        new GridExample().run();
    }
    }

    

    

