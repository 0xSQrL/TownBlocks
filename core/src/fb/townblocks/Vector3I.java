package fb.townblocks;

public class Vector3I {
    public int x, y, z;

    public Vector3I(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3I add(Vector3I o){
        return new Vector3I(o.x + x, o.y + y, o.z + z);
    }

    public static int distance(Vector3I a, Vector3I b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y) + Math.abs(a.z - b.z);
    }

    public final static Vector3I Up = new Vector3I(0, 1, 0);
    public final static Vector3I Down = new Vector3I(0, -1, 0);
    public final static Vector3I Left = new Vector3I(1, 0, 0);
    public final static Vector3I Right = new Vector3I(-1, 0, 0);
    public final static Vector3I Forward = new Vector3I(0, 0, 1);
    public final static Vector3I Backward = new Vector3I(0, 0, -1);

    @Override
    public String toString() {
        return String.format("[%d %d %d]", x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Vector3I))
            return false;
        Vector3I v = (Vector3I) obj;
        return (v.x == this.x && v.y == this.y && v.z == this.z);
    }
}
