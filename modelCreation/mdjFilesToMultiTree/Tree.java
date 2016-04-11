import java.util.ArrayList;

public class Tree {
	
    private TreeNode root; 
    private TreeNode fNode = null;
    private int rank = 0;
	public Tree(){
		
	}
	public Tree(TreeNode treeNode){
		
		this.root=treeNode;
	}
	public void bianli(TreeNode treeNode){
		this.rank++;
		String space = "";
		for(int i=0;i<rank;i++){
			space+=" ";
		}
		System.out.println(space+treeNode.getContent());
		if(!treeNode.isEndNode()){
			
			ArrayList<TreeNode> list=treeNode.getChildList();
			
			if(list!=null&&list.size()>0){
				
				for(TreeNode node:list){
					bianli(node);
					this.rank--;
				}
			}	
		}
	}
	
	public TreeNode getRoot(){
		
		return this.root;
	}
	public TreeNode findNode(TreeNode treeRoot,String s){
		//System.out.println(treeRoot.getContent());
		if(!treeRoot.getContent().equals(s)){
			if(!treeRoot.isEndNode()){
				ArrayList<TreeNode> list=treeRoot.getChildList();
				if(list!=null&&list.size()>0){
					for(TreeNode node:list){
						findNode(node,s);
					}
				}
			}
		}
		else{
			this.fNode = treeRoot;
		}
		return this.fNode;
		
	}
	
	/*public static void main(String args[]){
		
		TreeNode root=new TreeNode("root");
		Tree modelTree=new Tree(root);
		TreeNode child1 = new TreeNode("child1");
		TreeNode child2 = new TreeNode("child2");
		root.addchild(child1);
		root.addchild(child2);
		TreeNode child1_1 = new TreeNode("child1-1");
		TreeNode child1_2 = new TreeNode("child1-2");
		TreeNode child1_3 = new TreeNode("child1-3");
		child1.addchild(child1_1);
		child1.addchild(child1_2);
		child1.addchild(child1_3);
		
		modelTree.bianli(child1);
	}*/
}