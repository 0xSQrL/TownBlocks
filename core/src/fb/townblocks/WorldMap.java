package fb.townblocks;

public class WorldMap {
    private Block[][][] blocks;

    public WorldMap(int width, int depth, int height){
        blocks = new Block[width][height][depth];
        generateFlatLand(height / 2);
    }

    private void generateFlatLand(int groundLevel){
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                for (int k = 0; k < blocks[0][0].length; k++) {
                    blocks[i][j][k] = new Block(j <= groundLevel, false);
                }
            }
        }
    }

    private boolean isBlockWithinWorld(int i, int j, int k){
        return (i >= 0 && i < blocks.length) && (j >= 0 && j < blocks[0].length) && (k >= 0 && k < blocks[0][0].length);
    }

    public boolean isBlockWithinWorld(Vector3I position){
        return isBlockWithinWorld(position.x, position.y, position.z);
    }

    public boolean isBlockWalkable(Vector3I position){
        return isBlockWalkable(position.x, position.y, position.z);
    }

    private boolean isBlockWalkable(int i, int j, int k){
        if(!isBlockWithinWorld(i,j,k))
            return false;
        if(blocks[i][j][k].isClimbable)
            return true;
        if(!isBlockWithinWorld(i, j - 1, k))
            return false;
        return blocks[i][j-1][k].isSolid;
    }

    public Block getBlock(Vector3I position){
        if(!isBlockWithinWorld(position))
            return null;
        return blocks[position.x][position.y][position.z];
    }

    public void setBlock(Vector3I position, Block block){
        if(!isBlockWithinWorld(position))
            return;
        blocks[position.x][position.y][position.z] = block;
    }

    public int GetWidth()
    {
        return blocks.length;
    }

    public int GetHeight(){
        return blocks[0].length;
    }

    public int GetDepth(){
        return blocks[0][0].length;
    }

}
