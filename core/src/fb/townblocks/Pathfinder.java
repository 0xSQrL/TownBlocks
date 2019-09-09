package fb.townblocks;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Pathfinder{

    private WorldMap map;

    private PathNode[][][] allNodes;

    private ArrayList<Vector3I> navigationRules;

    private Vector3I startPosition, endPosition;

    public ArrayList<Vector3I> path;

    public Pathfinder(WorldMap map, ArrayList<Vector3I> navigationRules, Vector3I startPosition, Vector3I endPosition){
        this.map = map;
        allNodes = new PathNode[map.GetWidth()][map.GetHeight()][map.GetDepth()];
        this.navigationRules = navigationRules;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    private PathNode getNode(Vector3I position){
        if(!map.isBlockWithinWorld(position))
            return null;
        return allNodes[position.x][position.y][position.z];
    }

    private PathNode initalizeNode(Vector3I position){
        if(!map.isBlockWithinWorld(position))
            return null;
        if(allNodes[position.x][position.y][position.z] == null) {
            allNodes[position.x][position.y][position.z] = new PathNode(position, endPosition);
            allNodes[position.x][position.y][position.z].evaluated = !map.isBlockWalkable(position);
        }
        return allNodes[position.x][position.y][position.z];
    }

    private void linkNode(PathNode parent, PathNode child){

        int pathingCost = 1;
        if(child.parent == null) {
            child.parent = parent;
            child.pathLength = parent.pathLength + pathingCost;
            return;
        }
        if(child.pathLength > parent.pathLength + pathingCost){
            child.pathLength = parent.pathLength + pathingCost;
            child.parent = parent;
        }
    }

    private final static NodeComparer comparer = new NodeComparer();
    public void findPath(){
        ArrayList<PathNode> evaluationList = new ArrayList<>();
        PathNode initialNode = initalizeNode(startPosition);
        initialNode.pathLength = 0;
        evaluationList.add(initialNode);

        while (evaluationList.size() > 0){
            PathNode firstNode = evaluationList.get(0);
            for (Vector3I offset: navigationRules) {
                Vector3I offsetLocation = firstNode.location.add(offset);
                PathNode tempNode = getNode(offsetLocation);
                boolean notCreated = tempNode == null;
                tempNode = initalizeNode(offsetLocation);
                if(tempNode != null && !tempNode.evaluated){
                    if(notCreated)
                        evaluationList.add(tempNode);
                    linkNode(firstNode, tempNode);

                    if(tempNode.location.equals(endPosition)){
                        path = unravelPath(tempNode);
                        return;
                    }
                }
            }
            firstNode.evaluated = true;
            evaluationList.remove(0);
            Collections.sort(evaluationList, comparer);
        }
    }

    private ArrayList<Vector3I> unravelPath(PathNode endNode){
        ArrayList<Vector3I> path = new ArrayList<>();
        while (endNode != null){
            path.add(endNode.location);
            endNode = endNode.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private static class PathNode{
        Vector3I location;
        int pathLength = 0x8FFFFFFF;
        int distanceFromTarget;
        PathNode parent;
        boolean evaluated = false;

        PathNode(Vector3I location, Vector3I targetLocation){
            this.location = location;
            this.distanceFromTarget = Vector3I.distance(targetLocation, location);
        }

        PathNode(Vector3I location, Vector3I targetLocation, PathNode parent){
            this(location, targetLocation);
            this.parent = parent;
        }
    }

    private static class NodeComparer implements Comparator<PathNode>{

        @Override
        public int compare(PathNode o1, PathNode o2) {
            return (o1.pathLength + o1.distanceFromTarget) - (o2.pathLength + o2.distanceFromTarget);
        }
    }
}
