package ibxm;

import ibxm.FastTracker2;
import ibxm.IBXM;
import ibxm.Module;
import ibxm.ProTracker;
import ibxm.ScreamTracker3;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

public class OpenALMODPlayer {
    private static final int sectionSize = 40960;
    private IntBuffer bufferNames;
    private IBXM ibxm;
    private int songDuration;
    private byte[] data = new byte[163840];
    private ByteBuffer bufferData = BufferUtils.createByteBuffer(163840);
    private IntBuffer unqueued = BufferUtils.createIntBuffer(1);
    private int source;
    private boolean soundWorks = true;
    private Module module;
    private boolean loop;
    private boolean done = true;
    private int remainingBufferCount;

    public void init() {
        try {
            AL.create();
            this.soundWorks = true;
        }
        catch (LWJGLException e) {
            System.err.println("Failed to initialise LWJGL OpenAL");
            this.soundWorks = false;
            return;
        }
        if (this.soundWorks) {
            IntBuffer sources = BufferUtils.createIntBuffer(1);
            AL10.alGenSources(sources);
            if (AL10.alGetError() != 0) {
                System.err.println("Failed to create sources");
                this.soundWorks = false;
            } else {
                this.source = sources.get(0);
            }
        }
    }

    public void play(InputStream in, boolean loop, boolean start) throws IOException {
        this.play(this.source, in, loop, start);
    }

    public void play(int source, InputStream in, boolean loop, boolean start) throws IOException {
        if (!this.soundWorks) {
            return;
        }
        this.done = false;
        this.loop = loop;
        this.source = source;
        this.module = OpenALMODPlayer.loadModule(in);
        this.play(this.module, source, loop, start);
    }

    public void play(Module module, int source, boolean loop, boolean start) {
        this.source = source;
        this.loop = loop;
        this.module = module;
        this.done = false;
        this.ibxm = new IBXM(48000);
        this.ibxm.set_module(module);
        this.songDuration = this.ibxm.calculate_song_duration();
        if (this.bufferNames != null) {
            AL10.alSourceStop(source);
            this.bufferNames.flip();
            AL10.alDeleteBuffers(this.bufferNames);
        }
        this.bufferNames = BufferUtils.createIntBuffer(2);
        AL10.alGenBuffers(this.bufferNames);
        this.remainingBufferCount = 2;
        int i = 0;
        while (i < 2) {
            this.stream(this.bufferNames.get(i));
            ++i;
        }
        AL10.alSourceQueueBuffers(source, this.bufferNames);
        AL10.alSourcef(source, 4099, 1.0f);
        AL10.alSourcef(source, 4106, 1.0f);
        if (start) {
            AL10.alSourcePlay(source);
        }
    }

    public void setup(float pitch, float gain) {
        AL10.alSourcef(this.source, 4099, pitch);
        AL10.alSourcef(this.source, 4106, gain);
    }

    public boolean done() {
        return this.done;
    }

    public static Module loadModule(InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);
        Module module = null;
        byte[] xm_header = new byte[60];
        din.readFully(xm_header);
        if (FastTracker2.is_xm(xm_header)) {
            module = FastTracker2.load_xm(xm_header, din);
        } else {
            byte[] s3m_header = new byte[96];
            System.arraycopy(xm_header, 0, s3m_header, 0, 60);
            din.readFully(s3m_header, 60, 36);
            if (ScreamTracker3.is_s3m(s3m_header)) {
                module = ScreamTracker3.load_s3m(s3m_header, din);
            } else {
                byte[] mod_header = new byte[1084];
                System.arraycopy(s3m_header, 0, mod_header, 0, 96);
                din.readFully(mod_header, 96, 988);
                module = ProTracker.load_mod(mod_header, din);
            }
        }
        din.close();
        return module;
    }

    public void update() {
        if (this.done) {
            return;
        }
        int processed = AL10.alGetSourcei(this.source, 4118);
        while (processed > 0) {
            this.unqueued.clear();
            AL10.alSourceUnqueueBuffers(this.source, this.unqueued);
            if (this.stream(this.unqueued.get(0))) {
                AL10.alSourceQueueBuffers(this.source, this.unqueued);
            } else {
                --this.remainingBufferCount;
                if (this.remainingBufferCount == 0) {
                    this.done = true;
                }
            }
            --processed;
        }
        int state = AL10.alGetSourcei(this.source, 4112);
        if (state != 4114) {
            AL10.alSourcePlay(this.source);
        }
    }

    public boolean stream(int bufferId) {
        int frames = 40960;
        boolean reset = false;
        boolean more = true;
        if (frames > this.songDuration) {
            frames = this.songDuration;
            reset = true;
        }
        this.ibxm.get_audio(this.data, frames);
        this.bufferData.clear();
        this.bufferData.put(this.data);
        this.bufferData.limit(frames * 4);
        if (reset) {
            if (this.loop) {
                this.ibxm.seek(0);
                this.ibxm.set_module(this.module);
                this.songDuration = this.ibxm.calculate_song_duration();
            } else {
                more = false;
                this.songDuration -= frames;
            }
        } else {
            this.songDuration -= frames;
        }
        this.bufferData.flip();
        AL10.alBufferData(bufferId, 4355, this.bufferData, 48000);
        return more;
    }
}
