import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.WorldDimensions;

import java.util.concurrent.ConcurrentHashMap;

public class GlyphType {

    private static final ConcurrentHashMap<String, GlyphType> CACHE = new ConcurrentHashMap<>();

    public static final Codec<GlyphType> CODEC = RecordCodecBuilder.create(codec -> codec.group(Codec.STRING.fieldOf(
            "text").forGetter(GlyphType::text)).apply(codec, GlyphType::create));

    private final String text;

    private GlyphType(String text) {
        this.text = text;
    }

    public static GlyphType create(String text) {
        return CACHE.computeIfAbsent(text, GlyphType::new);
    }

    public String text() {
        return this.text;
    }

    @Override
    public int hashCode() {
        return this.text.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof GlyphType glyphType && glyphType.text.equals(this.text);
    }

    @Override
    public String toString() {
        return "GlyphType[text=" + text + "]";
    }

    public static class Constants {
        public static GlyphType A = GlyphType.create("A");
        public static GlyphType B = GlyphType.create("B");
        public static GlyphType C = GlyphType.create("C");
        public static GlyphType D = GlyphType.create("D");
        public static GlyphType E = GlyphType.create("E");
        public static GlyphType F = GlyphType.create("F");
        public static GlyphType G = GlyphType.create("G");
        public static GlyphType H = GlyphType.create("H");
        public static GlyphType I = GlyphType.create("I");
        public static GlyphType J = GlyphType.create("J");
        public static GlyphType K = GlyphType.create("K");
        public static GlyphType L = GlyphType.create("L");
        public static GlyphType M = GlyphType.create("M");
        public static GlyphType N = GlyphType.create("N");
        public static GlyphType O = GlyphType.create("O");
        public static GlyphType P = GlyphType.create("P");
        public static GlyphType Q = GlyphType.create("Q");
        public static GlyphType R = GlyphType.create("R");
        public static GlyphType S = GlyphType.create("S");
        public static GlyphType T = GlyphType.create("T");
        public static GlyphType U = GlyphType.create("U");
        public static GlyphType V = GlyphType.create("V");
        public static GlyphType W = GlyphType.create("W");
        public static GlyphType X = GlyphType.create("X");
        public static GlyphType Y = GlyphType.create("Y");
        public static GlyphType Z = GlyphType.create("Z");
        public static GlyphType BLANK = GlyphType.create(" ");
        public static GlyphType NONE = GlyphType.create("");

        private Constants() {
        }
    }
}
