import java.util.ArrayList;

public class TreeNode {
	
    private String content;
	private TreeNode parentchild;
	private ArrayList<TreeNode> childlist;
	
	public TreeNode(String content){
		this.content=content;
	}
	
	public void addchild(TreeNode treeNode){
		
		if(this.childlist==null){
			
			this.childlist=new ArrayList<TreeNode>();
		}
		
		this.childlist.add(treeNode);
		treeNode.parentchild=this;
	}
	
	public String getContent(){
		
		return this.content;
	}
	
	public boolean isEndNode(){
		
		return((this.childlist==null) ? true : false);
	}
	
	public ArrayList<TreeNode> getChildList(){
		
		return this.childlist;
	}
	

	
}
