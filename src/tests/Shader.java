package tests;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;

public class Shader {
    protected int shaderProgramId = -1;
    protected int vertexShaderId = ARBShaderObjects.glCreateShaderObjectARB(35633);
    protected int fragmentShaderId = -1;
    protected int locationBaseTexture = -1;

    public Shader(String filename) {
        ARBShaderObjects.glShaderSourceARB(this.vertexShaderId, this.getProgramCode(String.valueOf(filename) + ".vert"));
        ARBShaderObjects.glCompileShaderARB(this.vertexShaderId);
        this.fragmentShaderId = ARBShaderObjects.glCreateShaderObjectARB(35632);
        ARBShaderObjects.glShaderSourceARB(this.fragmentShaderId, this.getProgramCode(String.valueOf(filename) + ".frag"));
        ARBShaderObjects.glCompileShaderARB(this.fragmentShaderId);
        this.shaderProgramId = ARBShaderObjects.glCreateProgramObjectARB();
        ARBShaderObjects.glAttachObjectARB(this.shaderProgramId, this.vertexShaderId);
        ARBShaderObjects.glAttachObjectARB(this.shaderProgramId, this.fragmentShaderId);
        ARBShaderObjects.glLinkProgramARB(this.shaderProgramId);
        ARBShaderObjects.glUseProgramObjectARB(this.shaderProgramId);
        this.locationBaseTexture = ARBShaderObjects.glGetUniformLocationARB(this.shaderProgramId, this.toByteString("baseTexture"));
        ARBShaderObjects.glUseProgramObjectARB(0);
    }

    public void render(Image image, float x, float y) {
        ARBShaderObjects.glUseProgramObjectARB(this.shaderProgramId);
        GL11.glBegin(7);
        image.drawEmbedded(x, y, image.getWidth(), image.getHeight());
        GL11.glEnd();
        ARBShaderObjects.glUseProgramObjectARB(0);
    }

    private ByteBuffer getProgramCode(String filename) {
        FileInputStream fileInputStream = null;
        byte[] shaderCode = null;
        try {
            if (fileInputStream == null) {
                fileInputStream = new FileInputStream(filename);
            }
            DataInputStream dataStream = new DataInputStream(fileInputStream);
            shaderCode = new byte[((InputStream)fileInputStream).available()];
            dataStream.readFully(shaderCode);
            ((InputStream)fileInputStream).close();
            dataStream.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ByteBuffer shaderPro = BufferUtils.createByteBuffer(shaderCode.length);
        shaderPro.put(shaderCode);
        shaderPro.flip();
        return shaderPro;
    }

    private ByteBuffer toByteString(String str, boolean isNullTerminated) {
        int length = str.length();
        if (isNullTerminated) {
            ++length;
        }
        ByteBuffer buff = BufferUtils.createByteBuffer(length);
        buff.put(str.getBytes());
        if (isNullTerminated) {
            buff.put((byte)0);
        }
        buff.flip();
        return buff;
    }

    private ByteBuffer toByteString(String str) {
        return this.toByteString(str, true);
    }
}
